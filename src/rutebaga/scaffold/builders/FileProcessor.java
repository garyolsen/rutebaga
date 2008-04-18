package rutebaga.scaffold.builders;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileProcessor
{
	public void execute(ReaderProcessor processor, String path)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null)
				processor.processLine(line);
			reader.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
