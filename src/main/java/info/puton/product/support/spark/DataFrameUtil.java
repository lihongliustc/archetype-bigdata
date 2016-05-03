package info.puton.product.support.spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.types.StructType;

public class DataFrameUtil {
	
	public static List<Map> parseSchema(StructType structType){
		String stString = structType.toString();
		String[] structField = stString.split(", ");
		List<Map> schema = new ArrayList<>();
		for(int i = 0;i < structField.length;i++){
			Map temp = new HashMap();
			String[] sf = structField[i].split(",");
			String type = sf[1];
			String[] columnNameArr = sf[0].split("\\(");
			String columnName = columnNameArr[columnNameArr.length-1];
			temp.put("columnName",columnName );
			temp.put("type", type);
			schema.add(temp);
			
		}
		return schema;
	}
	
	public static String generateDDL(List<Map> schema,String tableName){
		String sql = "CREATE TABLE " + tableName + "(" ;
		for(int i = 0;i < schema.size();i++){
			String columnName = (String) schema.get(i).get("columnName");
			String columnType = (String) schema.get(i).get("type");
			if(columnType.equals("StringType")){
				columnType = "VARCHAR(50)";
			}
			sql += columnName + " " + columnType + ",";
		}
		sql = sql.substring(0, sql.length()-1);
		sql += ")";
		return sql;
	}
	
	public static String generateDDL(StructType st, String tableName){
		
		List<Map> schema = parseSchema(st);
		
		String sql = generateDDL(schema, tableName);
		
		return sql;
	}
}
