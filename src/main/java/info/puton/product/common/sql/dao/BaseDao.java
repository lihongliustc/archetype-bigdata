package info.puton.product.common.sql.dao;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by taoyang on 4/25/16.
 */
public class BaseDao {

    /**
     * 获取数据库连接
     * @return
     */
    public Connection getConnection() {

        Connection conn = null;

        try {
            InputStream in = this.getClass().getResourceAsStream("/xdb/jdbc.properties");
            Properties p = new Properties();
            p.load(in);

            String jdbcDriver = p.getProperty("xdb.jdbc.driver");
            String jdbcURL = p.getProperty("xdb.jdbc.url");
            String username = p.getProperty("xdb.jdbc.username");
            String password = p.getProperty("xdb.jdbc.password");

            DbUtils.loadDriver(jdbcDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // handle the exception
            e.printStackTrace();
        } finally {
//            DbUtils.closeQuietly(conn);
        }

        return conn;

    }

    /**
     * 查找多个对象
     * @param sqlString
     * @param clazz
     * @return
     */
    public List query(String sqlString,Class clazz) {
        List beans = null;
        Connection conn = null;
        try {
            conn = getConnection();
            QueryRunner qRunner = new QueryRunner();
            beans =
                    (List) qRunner.query(
                            conn,
                            sqlString,
                            new BeanListHandler(clazz));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return beans;
    }

    /**
     * 查找对象
     * @param sqlString
     * @param clazz
     * @return
     */
    public Object get(String sqlString,Class clazz) {
        List beans = null;
        Object obj = null;
        Connection conn = null;
        try {
            conn = getConnection();
            QueryRunner qRunner = new QueryRunner();
            beans =
                    (List) qRunner.query(
                            conn,
                            sqlString,
                            new BeanListHandler(clazz));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        if(beans!=null &&!beans.isEmpty()){ //注意这里
            obj=beans.get(0);

        }

        return obj;
    }

    /**
     * 执行更新的sql语句,插入,修改,删除
     * @param sqlString
     * @return
     */
    public boolean update(String sqlString) {
        Connection conn = null;
        boolean flag = false;
        try {
            conn = getConnection();
            QueryRunner qRunner = new QueryRunner();
            int i =qRunner.update(conn,sqlString);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return flag;
    }

}
