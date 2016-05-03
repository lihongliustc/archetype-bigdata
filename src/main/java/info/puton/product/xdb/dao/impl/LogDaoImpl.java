package info.puton.product.xdb.dao.impl;

import info.puton.product.common.sql.dao.BaseDao;
import info.puton.product.xdb.constant.Stage;
import info.puton.product.xdb.constant.StageStatus;
import info.puton.product.xdb.dao.ILogDao;
import info.puton.product.xdb.model.StageLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by taoyang on 4/25/16.
 */
public class LogDaoImpl extends BaseDao implements ILogDao {

    @Override
    public Boolean add(StageLog stageLog) {
        String sql = "INSERT INTO XBD_LOG" +
                "(JOB_ID, STAGE_ID, STAGE_STATUS, STAGE_DETAIL, STAGE_TIME)" +
                " VALUES" + "(" +
                "" + stageLog.getJobId() + "," +
                "" + stageLog.getStageId() + "," +
                "" + stageLog.getStageStatus() + "," +
                "'" + stageLog.getStageDetail() + "'," +
                "'" + stageLog.getStageTime() + "'" +
                ")";
        return this.update(sql);
    }

    public static void main(String[] args) {
        StageLog stageLog = new StageLog();
        stageLog.setJobId(123456);
        stageLog.setStageId(Stage.PARSE);
        stageLog.setStageStatus(StageStatus.PROCESSING);
        stageLog.setStageDetail("正在处理解析");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stageTime = sdf.format(date);
        stageLog.setStageTime(stageTime);

        ILogDao ld = new LogDaoImpl();
        ld.add(stageLog);

    }

}
