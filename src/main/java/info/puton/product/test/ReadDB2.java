package info.puton.product.test;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
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
public class ReadDB2 {

    public static void readJdbc() {
        SparkConf conf = new SparkConf().setAppName("readDb2").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        Map<String,String> options = new HashMap<String,String>();
        options.put("driver","com.ibm.db2.jcc.DB2Driver");
        options.put("url","jdbc:db2://192.168.6.162:50000/DB_BETA?userid=db2admin,password=db2admin");
        options.put("dbtable","(SELECT ITEMNAME FROM TB_ITEM WHERE ITEMID > 10)");
        options.put("user","db2admin");
        options.put("password","db2admin");

        long startTime=System.currentTimeMillis();
//        DataFrame df = sqlContext.read().format("jdbc").options(options).load();
        DataFrame df = sqlContext.load("jdbc", options);
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("Took "+(endTime-startTime)+"ms");
        df.show();
        
    }

    public static void main(String[] args) {
        readJdbc();
    }

}
