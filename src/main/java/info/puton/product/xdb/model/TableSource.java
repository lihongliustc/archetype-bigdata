package info.puton.product.xdb.model;

import java.util.List;

/**
 * Created by taoyang on 3/14/16.
 */
public class TableSource {

    private String tableName;

    private List<String> columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public TableSource(String tableName, List<String> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public TableSource() {
    }
}
