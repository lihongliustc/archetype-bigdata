package info.puton.product.xdb.integrator;

import info.puton.product.xdb.constant.JoinType;
import info.puton.product.xdb.context.ContextManager;
import info.puton.product.xdb.exception.XDBException;
import info.puton.product.xdb.model.DataSource;
import info.puton.product.xdb.model.Join;
import org.apache.spark.sql.DataFrame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by taoyang on 4/14/16.
 */
public class DataFrameIntegrator {

    private ContextManager contextManager;

    private Map<String,DataFrame> dataFrameMap;

    private List<Join> joinList;

    public DataFrameIntegrator(Map<String, DataFrame> dataFrameMap, List<Join> joinList, ContextManager contextManager) {
        this.contextManager = contextManager;
        this.dataFrameMap = dataFrameMap;
        this.joinList = joinList;
    }

    public DataFrame integrate() throws XDBException {

        DataFrame dfFinal = null;

        Object[] joinArr = joinList.toArray();
        CopyOnWriteArrayList<Join> joinList = new CopyOnWriteArrayList(joinArr);

        CopyOnWriteArrayList<String> dealtList = new CopyOnWriteArrayList();

        return doIntegrate(dfFinal, joinList, dealtList);

    }

    public DataFrame doIntegrate(DataFrame dfFinal, CopyOnWriteArrayList<Join> joinList, CopyOnWriteArrayList<String> dealtList) throws XDBException {

        for (Join join : joinList) {
            DataSource masterDataSource = new DataSource(join.getMasterDriver());
            DataSource slaveDataSource = new DataSource(join.getSlaveDriver());

            String masterTableKey = masterDataSource.getFullTableName();
            String slaveTableKey = slaveDataSource.getFullTableName();

            if(dealtList.size()==0){

                String col1 = contextManager.getMinColumn(join.getMasterDriver());
                String col2 = contextManager.getMinColumn(join.getSlaveDriver());

                DataFrame df1 = dataFrameMap.get(masterTableKey);
                DataFrame df2 = dataFrameMap.get(slaveTableKey);
                dfFinal = df1.join(df2, df1.col(col1).equalTo(df2.col(col2)), JoinType.get(join.getJoinModel()));

                dfFinal.show();

                joinList.remove(join);
                if(!dealtList.contains(masterTableKey)){
                    dealtList.add(masterTableKey);
                }
                if(!dealtList.contains(slaveTableKey)){
                    dealtList.add(slaveTableKey);
                }

            } else if(dealtList.contains(masterTableKey) || dealtList.contains(slaveTableKey)){

                String col1 = contextManager.getMinColumn(join.getMasterDriver());
                String col2 = contextManager.getMinColumn(join.getSlaveDriver());

//                System.out.println(col1);
//                System.out.println(col2);

                if(dealtList.contains(masterTableKey)){

                    DataFrame df1 = dfFinal;
                    DataFrame df2 = dataFrameMap.get(slaveTableKey);
                    dfFinal = df1.join(df2, df1.col(col1).equalTo(df2.col(col2)), JoinType.get(join.getJoinModel()));

                    dfFinal.show();

                }else if(dealtList.contains(slaveTableKey)){

                    DataFrame df1 = dataFrameMap.get(masterTableKey);
                    DataFrame df2 = dfFinal;
                    dfFinal = df1.join(df2, df1.col(col1).equalTo(df2.col(col2)), JoinType.get(join.getJoinModel()));

                    dfFinal.show();

                }

                joinList.remove(join);
                if(!dealtList.contains(masterTableKey)){
                    dealtList.add(masterTableKey);
                }
                if(!dealtList.contains(slaveTableKey)){
                    dealtList.add(slaveTableKey);
                }


            }

//            System.out.println(joinList.size());
            if(joinList.size()==0){
                break;
            }

        }

        if(joinList.size()>0){

            doIntegrate(dfFinal, joinList, dealtList);

        }else{

            return dfFinal;

        }

        return null;

    }

    public static void main(String[] args) {

        List<String> strings = new CopyOnWriteArrayList<>();

        strings.add("aaa");
        strings.add("bbb");
        strings.add("ccc");
        strings.add("ddd");

        for (String string : strings) {
            strings.remove(string);
            System.out.println(strings.size());
        }

    }

}
