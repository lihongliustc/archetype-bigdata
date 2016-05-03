package info.puton.product.test;

import info.puton.product.common.sql.dao.BaseDao;
import info.puton.product.support.spark.DataFrameUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by intern on 5/2/2016.
 */
public class Test {
    public static void main(String[] args){
        BaseDao bd = new BaseDao();
        SparkConf conf = new SparkConf().setAppName("DF").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);
        //JavaRDD s = sc.textFile("sparkJava/resources/spark/employee.json");
        //s.count();
        DataFrame df = sqlContext.jsonFile("c:\\hadoop\\employee.json");
        StructType st = df.schema();
        String tableName = "EMPOLYEE4";
        String sql = DataFrameUtil.generateDDL(st, tableName);
        Connection conn = bd.getConnection();
        try {
            Statement stat = conn.createStatement();
            stat.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //df.createJDBCTable("jdbc:teradata://192.168.152.129/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=db_alpha,LOB_Support=ON,user=dbc,password=dbc","EMPLOYEE4" , false);
        df.insertIntoJDBC("jdbc:teradata://192.168.152.129/CLIENT_CHARSET=GBK,TMODE=TERA,CHARSET=ASCII,database=db_alpha,LOB_Support=ON,user=dbc,password=dbc", tableName, false);
    }
}
