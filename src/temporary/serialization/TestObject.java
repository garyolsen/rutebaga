package temporary.serialization;

import java.util.List;
import java.util.Map;

public class TestObject implements Mappable
{

	private String string;

	private CustomTestObject otherObject;

	private int someint;

	private List<Integer> primitiveList;

	private List<TestObject> mappableList;

	
//%%----BEGIN_AUTOGEN_METHOD--TOMAP--%%
public ObjectMap toMap() {

ObjectMap AUTO_GEN_MAP = new ObjectMap(this);
AUTO_GEN_MAP.putPrimitive("string", this.string);
AUTO_GEN_MAP.putSaveable("otherObject", this.otherObject);
AUTO_GEN_MAP.putPrimitive("someint", this.someint);
AUTO_GEN_MAP.putPrimitiveCollection("primitiveList", this.primitiveList);
AUTO_GEN_MAP.putSaveableCollection("mappableList", this.mappableList);
return AUTO_GEN_MAP;

}
//%%----END_AUTOGEN_METHOD--TOMAP--%%
}
