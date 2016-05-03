package info.puton.product.lab.spark;

import info.puton.product.lab.model.Person;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoyang on 3/7/16.
 */
public class ReadText {

    public static void readByModel(){

        // Create a Java Spark Context
        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(sc);


        JavaRDD<Person> people = sc.textFile("archetype-bigdata/src/main/resources/people.txt").map(new Function<String, Person>() {
            @Override
            public Person call(String line) throws Exception {
                String[] parts = line.split(",");
                Person person = new Person();
                person.setName(parts[0].trim());
                person.setAge(Integer.parseInt(parts[1].trim()));
                return person;
            }
        });

        // Apply a schema to an RDD of JavaBeans and register it as a table.
        DataFrame df = sqlContext.createDataFrame(people, Person.class);
        df.registerTempTable("people");

        DataFrame dfOld = sqlContext.sql("SELECT name FROM people where age >=28");

//        dfOld.show();

        List<String> oldPeopleName = dfOld.javaRDD().map(
                new Function<Row, String>() {
                    @Override
                    public String call(Row row) throws Exception {
                        return row.getString(0);
                    }
                }
        ).collect();

        System.out.println(oldPeopleName);


    }

    public static void readBySchema(){

        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        // sc is an existing JavaSparkContext.
        SQLContext sqlContext = new SQLContext(sc);

                // Load a text file and convert each line to a JavaBean.
        JavaRDD<String> people = sc.textFile("archetype-bigdata/src/main/resources/people.txt");

        // The schema is encoded in a string
        String schemaString = "name age";

        List<StructField> fields = new ArrayList<StructField>();

        for (String fieldName : schemaString.split(" ")) {
            fields.add(DataTypes.createStructField(fieldName,DataTypes.StringType,true));
        }

        StructType schema = DataTypes.createStructType(fields);

        JavaRDD<Row> rowRDD = people.map(new Function<String, Row>() {
            @Override
            public Row call(String record) throws Exception {

                String fields[] = record.split(",");

                return RowFactory.create(fields[0].trim(),fields[1].trim());
            }
        });

        // Apply the schema to the RDD.
        DataFrame df = sqlContext.createDataFrame(rowRDD, schema);

        // Register the DataFrame as a table.
        df.registerTempTable("people");

        // SQL can be run over RDDs that have been registered as tables.
        DataFrame results = sqlContext.sql("SELECT name FROM people");

        results.show();

//        df.write().format("json").save("archetype-bigdata/src/main/resources/jsonout");

    }

    public static void main(String[] args) {

//        readByModel();

        readBySchema();

    }

}
