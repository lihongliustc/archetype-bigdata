package info.puton.product.xdb.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.puton.product.xdb.analyzer.SourceAnalyzer;
import info.puton.product.xdb.context.ContextManager;
import info.puton.product.xdb.exception.XDBException;
import info.puton.product.xdb.model.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by taoyang on 3/14/16.
 */
public class JsonParser {

    private ContextManager contextManager;

    public JsonParser(ContextManager contextManager) {
        this.contextManager = contextManager;
    }

    public List<SQLJob> parseSQLJob(String dsStr){

        Gson gson = new Gson();

        Type type = new TypeToken<SQLResult>(){}.getType();

        SQLResult sqlResult = gson.fromJson(dsStr,type);

//        System.out.println(sqlResult.getResultList().get(0));

        //resultList
        List<String> resultList = sqlResult.getResultList();
//        System.out.println(resultList);

        //paramList
        List<Where> paramList = sqlResult.getParamList();
//        System.out.println(paramList);
//        for (Where where : paramList) {
//            try {
//                System.out.println(where.getParamAtom());
//                System.out.println(where.generateWhere());
//            } catch (XDBException e) {
//                e.printStackTrace();
//            }
//        }

        //joinList
        List<Join> joinList = sqlResult.getJoinList();
//        for (Join join : joinList) {
//            System.out.println(join);
//        }


        List<DataSource> dataSources = new ArrayList<>();

        //add dataSource from resultList
        for (String result : resultList) {
            try {
                dataSources.add(new DataSource(result));
            } catch (XDBException e) {
                e.printStackTrace();
            }
        }

        //add dataSource from paramList
        for (Where param : paramList) {
            try {
                dataSources.add(new DataSource(param.getParamAtom()));
            } catch (XDBException e) {
                e.printStackTrace();
            }
        }

        //add dataSource from joinList
        for (Join join : joinList) {
            try {
                dataSources.add(new DataSource(join.getMasterDriver()));
                dataSources.add(new DataSource(join.getSlaveDriver()));
            } catch (XDBException e) {
                e.printStackTrace();
            }
        }

//        dataSources = SourceFilter.toTdDataSources(dataSources);

        SourceAnalyzer sourceAnalyzer =new SourceAnalyzer(contextManager);

        Map databaseMap = sourceAnalyzer.toDatabaseMap(dataSources);

        System.out.println(databaseMap);

        List<SQLJob> sqlJobs = null;
        try {
            sqlJobs = sourceAnalyzer.toSQLJobs(databaseMap, paramList);
        } catch (XDBException e) {
            e.printStackTrace();
        }

        return sqlJobs;

    }

    public List<Join> parseJoinJob(String dsStr){

        Gson gson = new Gson();

        Type type = new TypeToken<SQLResult>(){}.getType();

        SQLResult sqlResult = gson.fromJson(dsStr,type);

//        System.out.println(sqlResult.getResultList().get(0));

        //joinList
        List<Join> joinList = sqlResult.getJoinList();
//        for (Join join : joinList) {
//            System.out.println(join);
//        }

        return joinList;

    }

}
