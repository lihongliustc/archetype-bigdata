package info.puton.product.lab.plain;

import info.puton.product.common.sql.DBHelper;

import java.sql.*;
import java.util.List;

/**
 * Created by taoyang on 3/14/16.
 */
public class ReadJdbc {

    public static void readMysql(){

        Connection conn = null;

        String sql;

        String url = "jdbc:mysql://localhost:3306/puton_app?useUnicode=true&amp;characterEncoding=utf-8";

        String user = "root";

        String password = "root";

        try {
            conn = DriverManager.getConnection(url, user, password);

            Statement stmt = conn.createStatement();

            sql = "SELECT TOTAL_VALUE,OVER_RATE FROM diviner where TOTAL_VALUE>8000.00";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
//                System.out.println(rs.getMetaData());
                System.out.println(rs.getString("OVER_RATE"));
//                System.out.println(rs.getString(1));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void readTeradata(){

        Connection conn = null;

        String sql;

        String url = "jdbc:teradata://192.168.6.161/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=appida2,LOB_Support=ON";

        String user = "dbc";

        String password = "dbc";

        try {
            conn = DriverManager.getConnection(url, user, password);

            Statement stmt = conn.createStatement();

            sql = "SELECT Menu_Id,Menu_Name,Menu_URL FROM SYS_MENU where Menu_Auth=0";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
//                System.out.println(rs.getMetaData());
                System.out.println(rs.getString("Menu_Name"));
//                System.out.println(rs.getString(1));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void readDb2(){

        Connection conn = null;

        String sql;

        String url = "jdbc:db2://192.168.6.162:50000/db2admin";

        String user = "db2admin";

        String password = "db2admin";

        try {

            Class.forName("com.ibm.db2.jcc.DB2Driver");

            conn = DriverManager.getConnection(url, user, password);

            Statement stmt = conn.createStatement();

            sql = "SELECT KPI_DATA_ITEM_ID,KPI_DATA_ITEM_SHORT_NAME,KPI_DATA_ITEM_NAME FROM KPI_DATA_ITEM";

            ResultSet rs = stmt.executeQuery(sql);


            List list = DBHelper.resultSetToList(rs);

            System.out.println(list);

//            System.out.println(rs.getMetaData());
//
//            while (rs.next()){
//                System.out.println(rs.getString("KPI_DATA_ITEM_SHORT_NAME"));
////                System.out.println(rs.getString(1));
//            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

//        readMysql();

//        readTeradata();

//        readDb2();

    }

}
