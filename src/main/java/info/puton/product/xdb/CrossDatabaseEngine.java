package info.puton.product.xdb;

import info.puton.product.xdb.constant.Stage;
import info.puton.product.xdb.constant.StageStatus;
import info.puton.product.xdb.context.ContextManager;
import info.puton.product.xdb.context.Store;
import info.puton.product.xdb.dao.ILogDao;
import info.puton.product.xdb.dao.impl.LogDaoImpl;
import info.puton.product.xdb.exception.XDBException;
import info.puton.product.xdb.fetcher.TableThread;
import info.puton.product.xdb.integrator.DataFrameIntegrator;
import info.puton.product.xdb.model.Join;
import info.puton.product.xdb.model.SQLJob;
import info.puton.product.xdb.model.StageLog;
import info.puton.product.xdb.parser.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by taoyang on 4/6/16.
 */
public class CrossDatabaseEngine {


    public static void main(String[] args) {

        Boolean debug = true;

        ILogDao ld = new LogDaoImpl();

        Integer jobId = 1234567;

        //PREPARE

//        String path = ClassLoader.getSystemResource("jsonResult.json").getPath();
        String path = ClassLoader.getSystemResource("jsonDemo.json").getPath();

        File f = new File(path);
        String data = null;
        try {
            data = FileUtils.readFileToString(f);

            ld.add(new StageLog(jobId, Stage.PREPARE, StageStatus.SUCCESS, "任务读取完成"));

        } catch (IOException e) {

            ld.add(new StageLog(jobId, Stage.PREPARE, StageStatus.FAILURE, "任务读取异常"));

            e.printStackTrace();
        }

//        System.out.println(data);


        //PARSE

        ld.add(new StageLog(jobId, Stage.PARSE, StageStatus.PROCESSING, "任务解析开始"));

        ContextManager contextManager = new ContextManager(new Store());

        JsonParser jsonParser = new JsonParser(contextManager);

        List<SQLJob> sqlJobs = jsonParser.parseSQLJob(data);

        if(debug){
            for (SQLJob sqlJob : sqlJobs) {
                System.out.println(sqlJob);
            }
        }

        ld.add(new StageLog(jobId, Stage.PARSE, StageStatus.SUCCESS, "任务解析完成"));

        //FETCH&CONVERT

        ld.add(new StageLog(jobId, Stage.FETCH, StageStatus.PROCESSING, "数据获取开始"));

        SparkConf conf = new SparkConf().setAppName("ReadTeradata").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

//        int taskSize = sqlJobs.size();
        int taskSize = 2;

        ExecutorService pool = Executors.newFixedThreadPool(taskSize);

        List<Future> futures = new ArrayList<>();

        for (int i = 0; i < sqlJobs.size(); i++) {
            Callable c = new TableThread(sqlJobs, i, sc);
            Future future = pool.submit(c);
            // System.out.println(">>>" + future.get().toString());
            futures.add(future);
        }
        pool.shutdown();

        ld.add(new StageLog(jobId, Stage.FETCH, StageStatus.SUCCESS, "数据获取完成"));


        Map<String,DataFrame> dataFrameMap = new HashMap<>();

        for (Future future : futures) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            try {

                Map<String,Object> resultMap = (Map<String, Object>) future.get();

                DataFrame df = (DataFrame) resultMap.get("dataFrame");

                SQLJob sqlJob = (SQLJob) resultMap.get("sqlJob");

                String table = sqlJob.getTable();

                dataFrameMap.put(table,df);

                if(debug){

                    System.out.println(table);

                    df.show();

                    df.printSchema();

                    System.out.println(">>> df: " + future.get().toString() +" got.");

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if(debug){

            for (String key : dataFrameMap.keySet()) {
                System.out.println(key);
                DataFrame df = dataFrameMap.get(key);
                df.show();
            }
        }

        //JOIN

        ld.add(new StageLog(jobId, Stage.JOIN, StageStatus.PROCESSING, "数据整合开始"));

        List<Join> joinList = jsonParser.parseJoinJob(data);

        //洪立支线start

        //TODO

        //洪立支线end

        //陶阳支线start

        DataFrameIntegrator dataFrameIntegrator = new DataFrameIntegrator(dataFrameMap, joinList, contextManager);

        DataFrame df = null;
        try {
            df = dataFrameIntegrator.integrate();
            df.show();

            ld.add(new StageLog(jobId, Stage.JOIN, StageStatus.SUCCESS, "数据整合完成"));

        } catch (XDBException e) {
            e.printStackTrace();
        }

        //TODO

        //陶阳支线end


//        DataFrame df1 = dataFrameMap.get("TD.DB_ALPHA.TB_USER");
//        DataFrame df2 = dataFrameMap.get("TD.DB_ALPHA.TB_USER_DETAIL");
//        DataFrame df3 = dataFrameMap.get("DB2.db_beta.TB_ORDER.ITEMID");
//
//        DataFrame dfFinal = df1.join(df2,"USERID");
//
//        String col1 = contextManager.getMinColumn("TD.DB_ALPHA.TB_USER.USERID");
//        String col2 = contextManager.getMinColumn("TD.DB_ALPHA.TB_USER_DETAIL.USERID");
//
//
//        DataFrame dfFinal = df1.join(df2, df1.col(col1).equalTo(df2.col(col2)), "inner");
//
//        dfFinal.printSchema();
//        dfFinal = dfFinal.join(df3,dfFinal.col("USERID").equalTo(df3.col("USERID")),"inner");
//
//        dfFinal.show();




//        DataFrame df1 = dataFrameMap.get("TD.DB_ALPHA.TB_USER");
//        DataFrame df2 = dataFrameMap.get("DB2.DB_BETA.TB_ORDER");
//
////        DataFrame dfFinal = df1.join(df2,"USERID");
//
//        String col1 = contextManager.getMinColumn("TD.db_alpha.TB_USER.USERID");
//        String col2 = contextManager.getMinColumn("DB2.db_beta.TB_ORDER.USERID");
//
//
//        DataFrame dfFinal = df1.join(df2, df1.col(col1).equalTo(df2.col(col2)), "inner");
//
//        dfFinal.printSchema();
////        dfFinal = dfFinal.join(df3,dfFinal.col("USERID").equalTo(df3.col("USERID")),"inner");
//
//        dfFinal.show();
//
//        System.out.println(col1+"=>TD.db_alpha.TB_USER.USERID");
//        System.out.println(col2+"=>DB2.db_beta.TB_ORDER.USERID");


    }

}
