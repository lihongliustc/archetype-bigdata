package info.puton.product.xdb.constant;

/**
 * Created by taoyang on 4/20/16.
 */
public class JoinType {

    public static String get(String iniJoinType){


        switch(iniJoinType){

            case "INNER JOIN" : return "inner";
            case "LEFT JOIN" : return "left";
            case "RIGHT JOIN" : return "right";
            default : return "inner";

        }

    }

}
