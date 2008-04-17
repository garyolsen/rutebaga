package temporary;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutebaga.model.environment.Instance;
import sun.misc.Regexp;
import sun.security.krb5.internal.UDPClient;
import temporary.serialization.CustomSaveMethod;
import temporary.serialization.Mappable;

public class CodeGenTest
{

	static Pattern javaPattern = Pattern.compile(".*\\.class");

	static String binPath;
	static String sourcePath;
	
	static boolean CLEAR_ONLY = false;

	public static void main(String... args)
	{
		String directoryName = args.length < 2 ? "bin" : args[1];
		File dir = new File(directoryName);
		binPath = dir.getAbsolutePath();
		
		directoryName = args.length < 3 ? "src" : args[2];
		File srcDir = new File(directoryName);
		sourcePath = srcDir.getAbsolutePath();
		
		findJavaFiles(dir);
	}

	public static void findJavaFiles(File file)
	{
		if (file.isDirectory())
			findJavaFiles(file.getAbsolutePath(), file.list());
		else
		{
			if (isJavaFile(file))
				getClassInfo(file);
		}
	}
	
	public static String getJavaFilePath(Class clazz)
	{
		String fqcn = clazz.getCanonicalName().replace(".", "/");
		fqcn = "/" + fqcn;
		return sourcePath + fqcn + ".java";
	}
	
	public static String getJavaFileText(Class clazz)
	{
		String path = getJavaFilePath(clazz);
		try
		{
	        StringBuffer fileData = new StringBuffer(1000);
	        BufferedReader reader = new BufferedReader(
	                new FileReader(path));
	        char[] buf = new char[1024];
	        int numRead=0;
	        while((numRead=reader.read(buf)) != -1){
	            String readData = String.valueOf(buf, 0, numRead);
	            fileData.append(readData);
	            buf = new char[1024];
	        }
	        reader.close();
	        return fileData.toString();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void getClassInfo(File javaFile)
	{
		try
		{
			String fqcn = javaFile.getAbsolutePath().replace(binPath, "")
					.replace("/", ".").substring(1).replace(".class", "");
			Class clazz = Class.forName(fqcn);
			if (!Mappable.class.isAssignableFrom(clazz))
				return;
			if (clazz.isInterface())
				return;
			System.out.println();
			System.out.println();
			System.out.println("class " + clazz.getSimpleName());
			System.out.println();
			try
			{
				Method saveMethod = clazz.getMethod("toMap");
				if (saveMethod != null
						&& saveMethod.getAnnotation(CustomSaveMethod.class) != null)
				{
					System.out
							.println("Custom save method (not autogenerating)");
					return;
				}
			}
			catch (Exception e)
			{

			}
			Field[] fields = clazz.getDeclaredFields();
			if (fields.length > 0)
			{
				System.out.println("\tFields:");
				for (Field field : fields)
				{
					System.out.println("\t\t" + field.getName() + " : "
							+ field.getType());
					System.out.println("\t\t\t" + identify(field));
				}
			}
			boolean superIsSaveable = Mappable.class.isAssignableFrom(clazz
					.getSuperclass());
			StringBuffer sb = new StringBuffer();
			String newline = "\n";
			sb.append("//" + getStartToken("TOMAP")).append(newline);
			sb.append("public ObjectMap toMap() {").append(newline);
			sb.append(newline);
			sb.append("ObjectMap AUTO_GEN_MAP = ");
			if (superIsSaveable)
				sb.append("super.toMap();");
			else
				sb.append("new ObjectMap(this);");
			sb.append(newline);
			for (Field field : fields)
			{
				FieldType fieldType = identify(field);
				String methodName = getMethodNameFor(fieldType);
				sb.append("AUTO_GEN_MAP." + methodName + "(\""
						+ field.getName() + "\", ");
				sb.append("this." + field.getName());
				sb.append(");").append(newline);
			}
			sb.append("return AUTO_GEN_MAP;").append(newline);
			sb.append(newline);
			sb.append("}").append(newline);
			sb.append("//" + getEndToken("TOMAP")).append(newline);
			
			String newText = insertBeforeLastBrace(filterOutOldMethods(getJavaFileText(clazz)), sb.toString());
			
			FileWriter outFile = new FileWriter(getJavaFilePath(clazz));
			BufferedWriter out = new BufferedWriter(outFile);
			out.write(newText);
			out.close();
			
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	public static boolean isJavaFile(File file)
	{
		Matcher m = javaPattern.matcher(file.getName());
		return m.matches();
	}

	public static void findJavaFiles(String path, String[] dirContents)
	{
		for (String string : dirContents)
		{
			findJavaFiles(new File(path + "/" + string));
		}
	}

	public static enum FieldType
	{
		PRIMITIVE, PRIMITIVE_COLLECTION, SAVEABLE, SAVEABLE_COLLECTION, SAVEABLE_MAP, PRIMITIVE_MAP, SAVEABLE_PRIM_MAP, PRIM_SAVEABLE_MAP;
	}

	public static String getMethodNameFor(FieldType type)
	{
		switch (type)
		{
		case PRIMITIVE:
			return "putPrimitive";
		case PRIMITIVE_COLLECTION:
			return "putPrimitiveCollection";
		case SAVEABLE:
			return "putSaveable";
		case SAVEABLE_COLLECTION:
			return "putSaveableCollection";
		case SAVEABLE_MAP:
			return "putSaveableMap";
		case PRIMITIVE_MAP:
			return "putPrimitiveMap";
		}
		return null;
	}

	public static FieldType identify(Field field)
	{
		Class clazz = field.getType();
		if (Mappable.class.isAssignableFrom(clazz))
			return FieldType.SAVEABLE;
		if (Collection.class.isAssignableFrom(clazz))
		{
			ParameterizedType obj = (ParameterizedType) field.getGenericType();
			Class paramClazz = (Class) obj.getActualTypeArguments()[0];
			if (Mappable.class.isAssignableFrom(paramClazz))
				return FieldType.SAVEABLE_COLLECTION;
			else if (isPrimitive(paramClazz))
				return FieldType.PRIMITIVE_COLLECTION;
		}
		if (isPrimitive(clazz))
			return FieldType.PRIMITIVE;
		return null;
	}

	public static boolean isPrimitive(Class clazz)
	{
		if (clazz.isPrimitive())
			return true;
		for (Class prim : primitiveClasses)
		{
			if (prim.isAssignableFrom(clazz))
				return true;
		}
		return false;
	}

	public static Class[] primitiveClasses =
	{ Integer.class, Short.class, Long.class, Boolean.class, String.class,
			Double.class, Float.class };
	
	public static String START_IDENTIFYING_TOKEN = "%%----BEGIN_AUTOGEN_METHOD--{METHOD_NAME}--%%";
	public static String END_IDENTIFYING_TOKEN = "%%----END_AUTOGEN_METHOD--{METHOD_NAME}--%%";
	
	public static String getStartToken(String name)
	{
		return START_IDENTIFYING_TOKEN.replace("{METHOD_NAME}", name);
	}
	
	public static String getEndToken(String name)
	{
		return END_IDENTIFYING_TOKEN.replace("{METHOD_NAME}", name);
	}
	
	public static String filterOutOldMethods(String source)
	{
		String startToken = START_IDENTIFYING_TOKEN.replace("{METHOD_NAME}", "TOMAP");
		String endToken = END_IDENTIFYING_TOKEN.replace("{METHOD_NAME}", "TOMAP");
		Pattern pattern = Pattern.compile("//.*" + startToken + ".*" + endToken + "$", Pattern.UNIX_LINES | Pattern.MULTILINE | Pattern.DOTALL);
//		Pattern pattern = Pattern.compile("BEGIN.*ObjectMap", Pattern.UNIX_LINES | Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(source);
		return matcher.replaceAll("");
	}
	
	public static String insertBeforeLastBrace(String source, String text)
	{
//		Pattern brace = Pattern.compile(".*}.*", Pattern.UNIX_LINES | Pattern.MULTILINE | Pattern.DOTALL);
//		Matcher matcher = brace.matcher(source);
//		int ct = matcher.groupCount();
//		return matcher.group(ct-1).replace("}", "\n" + text + "\n}\n");
		if(CLEAR_ONLY) return source;
		int col = source.lastIndexOf("}");
		return source.substring(0, col) + text + "}\n";
	}

}
