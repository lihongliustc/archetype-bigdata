package info.puton.product.xdb.context;

import info.puton.product.xdb.exception.XDBException;
import info.puton.product.xdb.model.DataSource;

/**
 * Created by taoyang on 4/12/16.
 */
public class ContextManager {

    Store store = new Store();

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ContextManager(Store store) {
        this.store = store;
    }

    public String getMinTable(String fullTable) throws XDBException {

        DataSource ds= new DataSource(fullTable);

        String minTable = store.getMinPrefix(ds.getFullTableName());

        return minTable;
    }

    public String getFullTable(String minTable){

        String fullTable = store.getFullPrefix(minTable.trim().toUpperCase());

        return fullTable;
    }

    public String getMinColumn(String fullColumn) throws XDBException {

        DataSource ds= new DataSource(fullColumn);

        String minColumn = store.getMinPrefix(ds.getFullTableName()) +
                "_" + ds.getFieldName();

        return minColumn;
    }

    public String getFullColumn(String minColumn){

        String[] elements = minColumn.trim().toUpperCase().split("_", 2);

        String fullColumn = store.getFullPrefix(elements[0]) + "." + elements[1];

        return fullColumn;
    }

    public static void main(String[] args) throws XDBException {

        ContextManager contextManager = new ContextManager(new Store());

        System.out.println(contextManager.getMinColumn("TD.db_alpha.TB_USER.USERID_AA_BB"));

        System.out.println(contextManager.getMinColumn("TD.db_alpha.TB_USER.PASSWD"));

        System.out.println(contextManager.getMinTable("TD.db_alpha.TB_USER"));

        System.out.println(contextManager.getMinColumn("TD.db_alpha.TB_USERINFO.USERID"));

        System.out.println(contextManager.getFullColumn(contextManager.getMinColumn("TD.db_alpha.TB_USERINFO.USERID_AA_BB")));

        System.out.println(contextManager.getFullTable(contextManager.getMinTable("TD.db_alpha.TB_USERINFO")));

    }

}
