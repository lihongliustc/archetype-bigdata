package info.puton.product.xdb.integrator;


import info.puton.product.common.sql.JoinHelper;
import info.puton.product.xdb.context.ContextManager;
import info.puton.product.xdb.context.Store;
import info.puton.product.xdb.exception.XDBException;
import info.puton.product.xdb.model.Join;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by intern on 5/3/2016.
 */
public class TdTableIntegrator {
    public String generateSQL(List<Join> joinList) throws XDBException {

        ContextManager contextManager = new ContextManager(new Store());
        List<Join> delJoinList = new ArrayList<Join>();
        delJoinList.addAll(joinList);
        List containList = new ArrayList();
        String fromSql = "";
        while(delJoinList != null&&!delJoinList.isEmpty()){
            for(int i = 0;i < delJoinList.size();i++){
                //主表的表名与字段名
                String TemMasterTable = JoinHelper.delColumn(delJoinList.get(i).getMasterDriver());
                String MasterTable = contextManager.getMinTable(TemMasterTable);
                String TemMasterColumn = contextManager.getMinColumn(delJoinList.get(i).getMasterDriver());
                String MasterColumn = MasterTable + "." + TemMasterColumn;
                //从表的表名与字段名
                String TemSlaveTable = JoinHelper.delColumn(delJoinList.get(i).getSlaveDriver());
                String SlaveTable = contextManager.getMinTable(TemSlaveTable);
                String TemSlaveColumn = contextManager.getMinColumn(delJoinList.get(i).getSlaveDriver());
                String SlaveColumn = SlaveTable + "." + TemSlaveColumn;
                //连接规则
                String Join = delJoinList.get(i).getJoinModel();

                if(fromSql.length()==0){
                    fromSql += " FROM " + MasterTable + " " + Join + " " + SlaveTable + " ON " + MasterColumn + "=" + SlaveColumn;
                    containList.add(MasterTable);
                    containList.add(SlaveTable);
                    delJoinList.remove(i);
                    break;
                }
                if(containList.contains(MasterTable)&&containList.contains(SlaveTable)){
                    SlaveTable = SlaveTable + "_" + String.valueOf(i);
                    fromSql += " " + Join + " " + SlaveTable + " ON " + MasterColumn + "=" + SlaveColumn ;
                    delJoinList.remove(i);
                    break;
                }else if(containList.contains(MasterTable)){
                    fromSql += " " + Join + " " + SlaveTable + " ON " + MasterColumn + "=" + SlaveColumn;
                    containList.add(SlaveTable);
                    delJoinList.remove(i);
                    break;
                }else if(containList.contains(SlaveTable)){
                    String newJoin = JoinHelper.Transfer(Join);
                    fromSql = " " + newJoin + " " + MasterTable + " ON " + MasterColumn + "=" + SlaveColumn;
                    containList.add(MasterTable);
                    delJoinList.remove(i);
                    break;
                }

            }

        }
        return fromSql;
    }
}
