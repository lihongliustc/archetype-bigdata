package info.puton.product.lab.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by taoyang on 3/11/16.
 */
public class SaveJdbc {

    public static void saveTeradata(){

        // Create a Java Spark Context
        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        SparkContext sc = new SparkContext(conf);

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
//        df2.printSchema();

        Properties properties = new Properties();

        properties.setProperty("driver","com.teradata.jdbc.TeraDriver");
        properties.setProperty("url","jdbc:teradata://192.168.6.161/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=sparktst,LOB_Support=ON");
        properties.setProperty("user","dbc");
        properties.setProperty("password","dbc");

//        df2.write().mode(SaveMode.Append).jdbc("jdbc:teradata://192.168.6.161/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=sparktst,LOB_Support=ON","testmenu",properties);

    }

    public static void saveMysql(){

        // Create a Java Spark Context
        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        SparkContext sc = new SparkContext(conf);

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


        Properties properties = new Properties();

        properties.setProperty("driver","com.mysql.jdbc.Driver");
        properties.setProperty("user","root");
        properties.setProperty("password","root");


//        df2.write().mode(SaveMode.Ignore).jdbc("jdbc:mysql://localhost:3306/puton_app?useUnicode=true&amp;characterEncoding=utf-8","spark01",properties);

    }

    public static void main(String[] args) {

        saveTeradata();

//        saveMysql();

    }

}
