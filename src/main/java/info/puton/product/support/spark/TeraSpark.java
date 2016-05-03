package info.puton.product.support.spark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by taoyang on 3/17/16.
 */
public class TeraSpark {

    public static void createDDL(String url, String user, String password, String tableName){

        Connection conn = null;
        Statement stmt = null;
        try{

            //STEP 2: Register JDBC driver
            Class.forName("com.teradata.jdbc.TeraDriver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(url, user, password);

            //STEP 4: Execute a query
            stmt = conn.createStatement();

//            String sql = "CREATE TABLE " + tableName.toUpperCase()+
//                    "(" +
//                    "USERID VARCHAR(100), " +
//                    "MOBILE VARCHAR(100)" +
//                    " )";

            String sql = "CREATE TABLE " + tableName.toUpperCase()+
                    "(" +
                    "calendar_date VARCHAR(100), " +
                    "day_of_year VARCHAR(100)" +
                    " )";

//            String sql = "CREATE TABLE " + tableName.toUpperCase()+
//                    "(" +
//                    "Menu_id VARCHAR(100), " +
//                    "Menu_Name VARCHAR(100)," +
//                    "Menu_URL VARCHAR(100)" +
//                    " )";

            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

    }
//???

    public static void main(String[] args) {

        String url = "jdbc:teradata://192.168.6.161/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=sparktst,LOB_Support=ON";

        String user = "dbc";

        String password = "dbc";

        String tableName = "test_calendar";

        createDDL(url, user, password, tableName);
        System.out.println(user);
    }

}
