package info.puton.product.lab.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * Created by taoyang on 3/7/16.
 */
public class ReadJson {
    public static void main(String[] args) {


        // Create a Java Spark Context
        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(sc);

//        DataFrame df = sqlContext.read().json("archetype-bigdata/src/main/resources/people.json");
//
//        df.show();
//
//        df.printSchema();
//
//        df.select("name").show();
//
//        df.select(df.col("name"),df.col("age").plus(2)).show();
//
//        df.filter(df.col("age").gt(26)).show();
//
//        df.groupBy("age").count().show();


    }
}
