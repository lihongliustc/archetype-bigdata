package info.puton.product.test;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by taoyang on 3/16/16.
 */
public class SaveTeradata {

    public static void saveTeradata(){
        SparkConf conf = new SparkConf().setAppName("SaveTeradata").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        Map<String,String> options = new HashMap<String,String>();
        options.put("driver","com.ibm.db2.jcc.DB2Driver");
        options.put("url","jdbc:db2://192.168.6.162:50000/sparktst");
        options.put("dbtable","TEST_USER");
        options.put("user","db2admin");
        options.put("password", "db2admin");

//        DataFrame df = sqlContext.read().format("jdbc").options(options).load();

        DataFrame df = sqlContext.load("jdbc", options);

        Properties properties = new Properties();
        properties.setProperty("driver","com.teradata.jdbc.TeraDriver");
        properties.setProperty("user","dbc");
        properties.setProperty("password","dbc");

        long startTime=System.currentTimeMillis();
//        df.write().mode(SaveMode.Append).jdbc("jdbc:teradata://192.168.6.161" +
//                "/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=sparktst,LOB_Support=ON","TEST_USER",properties);
        long endTime=System.currentTimeMillis();
        System.out.println("Took "+(endTime-startTime)+"ms");
    }

    public static void main(String[] args) {

        saveTeradata();

    }

}
