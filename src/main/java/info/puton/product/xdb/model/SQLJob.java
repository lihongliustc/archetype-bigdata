package info.puton.product.xdb.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by taoyang on 3/30/16.
 */
public class SQLJob {

    private String databaseType;

    private String database;

    private String table;

    private String jdbcDriver;

    private String jdbcUrl;

    private String jdbcUsername;

    private String jdbcPassword;

    private String sql;

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public SQLJob() {
    }

    public SQLJob(String database) {
        this.database = database;
        this.databaseType = database.trim().toUpperCase().split("\\.")[0];

        try {
            InputStream in = this.getClass().getResourceAsStream("/xdb/datasource.properties");

            Properties p = new Properties();
            p.load(in);

            this.jdbcDriver=p.getProperty(database+".jdbc.driver");
            this.jdbcUrl=p.getProperty(database+".jdbc.url");
            this.jdbcUsername=p.getProperty(database+".jdbc.username");
            this.jdbcPassword=p.getProperty(database+".jdbc.password");

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "SQLJob{" +
                "databaseType='" + databaseType + '\'' +
                ", database='" + database + '\'' +
                ", table='" + table + '\'' +
                ", jdbcDriver='" + jdbcDriver + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", jdbcUsername='" + jdbcUsername + '\'' +
                ", jdbcPassword='" + jdbcPassword + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
