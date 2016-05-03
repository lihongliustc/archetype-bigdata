package info.puton.product.test;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoyang on 3/16/16.
 */
public class ReadTeradata {

    public static void readJdbc() {
        SparkConf conf = new SparkConf().setAppName("ReadTeradata").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        Map<String,String> options = new HashMap<String,String>();
        options.put("driver","com.teradata.jdbc.TeraDriver");
        options.put("url","jdbc:teradata://192.168.6.161" +
                "/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=db_alpha,LOB_Support=ON,user=dbc,password=dbc");
//        options.put("dbtable","TEST_CALENDAR");
        options.put("dbtable","TB_USER");
        options.put("user","dbc");
        options.put("password","dbc");

        long startTime=System.currentTimeMillis();
//        DataFrame df = sqlContext.read().format("jdbc").options(options).load();
        DataFrame df = sqlContext.load("jdbc", options);
        long endTime=System.currentTimeMillis();
        System.out.println("Took "+(endTime-startTime)+"ms");
        df.show();
    }

    public static void main(String[] args) {
        readJdbc();
    }

}
