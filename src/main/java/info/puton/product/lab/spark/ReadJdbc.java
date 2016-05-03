package info.puton.product.lab.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoyang on 3/7/16.
 */
public class ReadJdbc {

    public static void readMysql(){

        // Create a Java Spark Context
        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(sc);


        Map<String,String> options = new HashMap<String,String>();
        options.put("driver","com.mysql.jdbc.Driver");
        options.put("url","jdbc:mysql://localhost:3306/puton_app?useUnicode=true&amp;characterEncoding=utf-8");
        options.put("user","root");
        options.put("password","root");
        options.put("dbtable","diviner");

//        DataFrame df = sqlContext.read().format("jdbc").options(options).load();
        DataFrame df = sqlContext.load("jdbc", options);

//        df.show();

        df.registerTempTable("diviner");

        DataFrame df2 = sqlContext.sql("SELECT TOTAL_VALUE,OVER_RATE FROM diviner where TOTAL_VALUE>8000.00");

        df2.show();

    }

    public static void readTeradata(){

        // Create a Java Spark Context
        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(sc);

        Map<String,String> options = new HashMap<String,String>();
        options.put("driver","com.teradata.jdbc.TeraDriver");
        options.put("url","jdbc:teradata://192.168.6.161/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=appida2,LOB_Support=ON");
        options.put("user","dbc");
        options.put("password","dbc");
        options.put("dbtable","SYS_MENU");

//        DataFrame df = sqlContext.read().format("jdbc").options(options).load();
        DataFrame df = sqlContext.load("jdbc", options);

//        df.show();

        df.registerTempTable("SYS_MENU");

        DataFrame df2 = sqlContext.sql("SELECT Menu_Id,Menu_Name,Menu_URL FROM SYS_MENU where Menu_Auth=0");

        df2.show();

    }

    public static void readDb2() {

        // Create a Java Spark Context
        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(sc);


        Map<String,String> options = new HashMap<String,String>();
        options.put("driver","com.ibm.db2.jcc.DB2Driver");
        options.put("url","jdbc:db2://192.168.6.162:50000/db2admin");
        options.put("dbtable","KPI_DATA_ITEM");
        options.put("user","db2admin");
        options.put("password","db2admin");

//        DataFrame df = sqlContext.read().format("jdbc").options(options).load();

        DataFrame df = sqlContext.load("jdbc", options);

        df.show();

        df.registerTempTable("KPI_DATA_ITEM");

        DataFrame df2 = sqlContext.sql("SELECT KPI_DATA_ITEM_ID,KPI_DATA_ITEM_SHORT_NAME,KPI_DATA_ITEM_NAME FROM KPI_DATA_ITEM");

        df2.show();

    }

    public static void main(String[] args) {

//       readMysql();
        readTeradata();
//        readDb2();

    }

}
