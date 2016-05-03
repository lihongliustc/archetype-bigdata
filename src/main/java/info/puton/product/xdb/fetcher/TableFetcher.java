package info.puton.product.xdb.fetcher;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoyang on 4/6/16.
 */
public class TableFetcher {

    private JavaSparkContext sc;

    public JavaSparkContext getSc() {
        return sc;
    }

    public void setSc(JavaSparkContext sc) {
        this.sc = sc;
    }

    public TableFetcher(JavaSparkContext sc) {
        this.sc = sc;
    }

    public DataFrame getDataframe(Map<String,String> options) {

        SQLContext sqlContext = new SQLContext(this.sc);

//        long startTime=System.currentTimeMillis();
//        DataFrame df = sqlContext.read().format("jdbc").options(options).load();
        DataFrame df = sqlContext.load("jdbc", options);
//        long endTime=System.currentTimeMillis();
//        System.out.println("Took "+(endTime-startTime)+"ms");

        return df;

    }

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("ReadTeradata").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        TableFetcher tableFetcher = new TableFetcher(sc);

        Map<String,String> options = new HashMap<String,String>();
        options.put("driver","com.teradata.jdbc.TeraDriver");
        options.put("url","jdbc:teradata://192.168.6.161/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=db_alpha,LOB_Support=ON");
        options.put("dbtable","(SELECT FULLNAME, USERID FROM TB_USER_DETAIL WHERE 1=1  AND  USERID BETWEEN 50 AND 100) AS aaa");
        options.put("user","dbc");
        options.put("password","dbc");

        DataFrame df = tableFetcher.getDataframe(options);

        df.show();
    }

}
