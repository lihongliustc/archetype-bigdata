package info.puton.product.xdb.analyzer;

import info.puton.product.xdb.context.ContextManager;
import info.puton.product.xdb.exception.XDBException;
import info.puton.product.xdb.model.DataSource;
import info.puton.product.xdb.model.SQLJob;
import info.puton.product.xdb.model.Where;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoyang on 3/22/16.
 */
public class SourceAnalyzer {

    private ContextManager contextManager;

    public SourceAnalyzer(ContextManager contextManager) {
        this.contextManager = contextManager;
    }

    public Map<String,Map<String,List<String>>> toDatabaseMap(List<DataSource> dataSources){

        Map<String,Map<String,List<String>>> databaseMap = new HashMap<>();

        //deal with each line of datasource
        for (DataSource dataSource : dataSources) {

            String key = dataSource.getDatabaseType()+"."+dataSource.getDatabaseName();

            if(databaseMap.containsKey(key)){
                //database already exist

                Map<String,List<String>> tableMap = databaseMap.get(key);

                if(tableMap.containsKey(dataSource.getTableName())){
                    //table already exist

                    List<String> columns = tableMap.get(dataSource.getTableName());

                    if(columns.contains(dataSource.getFieldName())){
                        //column already exist

                        //do nothing

                    }else{
                        //no column concerned
                        columns.add(dataSource.getFieldName());
                    }

                    tableMap.put(dataSource.getTableName(),columns);

                }else{
                    //no table concerned
                    List<String> columns = new ArrayList<>();

                    columns.add(dataSource.getFieldName());

                    tableMap.put(dataSource.getTableName(), columns);

                }

                databaseMap.put(key, tableMap);

            }else{
                //no database concerned

                Map<String,List<String>> tableMap = new HashMap<>();

                List<String> columns = new ArrayList<>();

                columns.add(dataSource.getFieldName());

                tableMap.put(dataSource.getTableName(), columns);

                databaseMap.put(key, tableMap);

            }

            System.out.println(dataSource);

        }

        return databaseMap;

    }

    public List<SQLJob> toSQLJobs(Map<String,Map<String,List<String>>> databaseMap,List<Where> whereList) throws XDBException {

        List<SQLJob> sqlJobs = new ArrayList<>();

        for (String databaseKey : databaseMap.keySet()) {

            Map<String,List<String>> tableMap = databaseMap.get(databaseKey);

            for (String tableKey : tableMap.keySet()) {

                SQLJob sqlJob = new SQLJob(databaseKey);

                sqlJob.setTable(databaseKey+"."+tableKey);

                sqlJob.setDatabase(databaseKey);

                String sql = "SELECT ";

                List<String> columns = tableMap.get(tableKey);

                for (String column : columns) {
//                    sql += column + ", ";
                    sql += " TRIM( CAST(" + column + " AS VARCHAR(100)) )" + " AS " + contextManager.getMinColumn(databaseKey+"."+tableKey + "." + column) + ", ";
//                    sql += column + " AS " + contextManager.getMinColumn(databaseKey+"."+tableKey + "." + column) + ", ";
                }

                sql = sql.substring(0,sql.length()-2);

                sql += " FROM " + tableKey + " WHERE 1=1 ";

                for (Where where : whereList) {
                    try {
                        DataSource dsWhere = new DataSource(where.getParamAtom());
                        if((dsWhere.getFullTableName().equals(databaseKey+"."+tableKey))){
                            sql += " AND " + where.generateWhere() ;
                        }
                    } catch (XDBException e) {
                        e.printStackTrace();
                    }

                }

//                System.out.println(sql);

                sqlJob.setSql(sql);

                sqlJobs.add(sqlJob);

            }

        }

//        System.out.println(sqlJobs);

        return sqlJobs;

    }

    public List<Map<String,String>> toSQLJobs(List<DataSource> dataSources){
        return toSQLJobs(dataSources);
    }

    public static void main(String[] args) {

    }


}
