package info.puton.product.common.sql;

import java.util.List;

public class JoinHelper {
	public static String Transfer(String s){
		if(s.equals("LEFT JOIN")){
			s= "RIGHT JOIN";
		}else if(s.equals("RIGHT JOIN")){
			s = "LEFT JOIN";
		}
		return s;
	}
	
	public static <T> List<T> IndexExchange(List<T> list,int i,int j){
		T t = list.get(i);
		list.set(i, list.get(j));
		list.set(j,t);
		return list;
	}
	
	public static String delColumn(String s){
		String[] element = s.split("\\.");
		String res = element[0] + "." + element[1] + "." + element[2];
		return res;
	}
}
