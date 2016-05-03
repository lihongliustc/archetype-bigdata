package info.puton.product.xdb.model;

import info.puton.product.xdb.exception.XDBException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by taoyang on 3/14/16.
 */
public class DataSource {

    private String databaseType;

    private String databaseName;

    private String tableName;

    private String fieldName;

    public DataSource(String databaseType, String databaseName, String tableName, String fieldName) {
        this.databaseType = databaseType;
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public DataSource() {
    }

    public DataSource(String jsonStr) throws XDBException {

//        System.out.println(StringUtils.countMatches(dsStr, "."));

        if(StringUtils.countMatches(jsonStr, ".") == 3){

            String[] elements = jsonStr.trim().toUpperCase().split("\\.");

            this.databaseType = elements[0];
            this.databaseName = elements[1];
            this.tableName = elements[2];
            this.fieldName = elements[3];

        } else if (StringUtils.countMatches(jsonStr, ".") == 2){

            String[] elements = jsonStr.trim().toUpperCase().split("\\.");

            this.databaseType = elements[0];
            this.databaseName = elements[1];
            this.tableName = elements[2];

        } else {

            throw new XDBException("Error occurred while identifying the DataSource.");

        }

    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "databaseType='" + databaseType + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }

    public String getFullTableName() {
        return getDatabaseType()+"."+getDatabaseName()+"."+getTableName();
    }

}
