package info.puton.product.xdb.fetcher;

import info.puton.product.xdb.converter.KeyConverter;
import info.puton.product.xdb.model.SQLJob;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by taoyang on 3/30/16.
 */
public class TableThread implements Callable {

    private List<SQLJob> sqlJobs;

    private Integer index;

    private JavaSparkContext sc;

    public TableThread(List<SQLJob> sqlJobs, Integer index,JavaSparkContext sc) {

        this.sqlJobs = sqlJobs;
        this.index = index;
        this.sc = sc;

    }

    public List<SQLJob> getSqlJobs() {
        return sqlJobs;
    }

    public void setSqlJobs(List<SQLJob> sqlJobs) {
        this.sqlJobs = sqlJobs;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public Object call() throws Exception {

//        Thread.sleep(5000);

        SQLJob sqlJob = sqlJobs.get(index);

        TableFetcher tableFetcher = new TableFetcher(sc);

        Map<String,String> options = new HashMap();
        options.put("driver", sqlJob.getJdbcDriver());
        options.put("url", sqlJob.getJdbcUrl());
//        options.put("dbtable","TEST_CALENDAR");
        options.put("dbtable", "(" + sqlJob.getSql() + ") AS " + KeyConverter.toUnderline(sqlJob.getTable()));
        options.put("user", sqlJob.getJdbcUsername());
        options.put("password", sqlJob.getJdbcPassword());

        DataFrame dataFrame = tableFetcher.getDataframe(options);

//        df.show();

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("sqlJob",sqlJob);
        resultMap.put("dataFrame",dataFrame);

        return resultMap;
    }
}
