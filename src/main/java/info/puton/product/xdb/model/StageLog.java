package info.puton.product.xdb.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by taoyang on 4/25/16.
 */
public class StageLog {

    private Integer jobId;

    private Integer stageId;

    private Integer stageStatus;

    private String stageDetail;

    private String stageTime;

    public StageLog(Integer jobId, Integer stageId, Integer stageStatus, String stageDetail) {
        this.jobId = jobId;
        this.stageId = stageId;
        this.stageStatus = stageStatus;
        this.stageDetail = stageDetail;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.stageTime = sdf.format(date);
    }

    public StageLog() {
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Integer getStageStatus() {
        return stageStatus;
    }

    public void setStageStatus(Integer stageStatus) {
        this.stageStatus = stageStatus;
    }

    public String getStageDetail() {
        return stageDetail;
    }

    public void setStageDetail(String stageDetail) {
        this.stageDetail = stageDetail;
    }

    public String getStageTime() {
        return stageTime;
    }

    public void setStageTime(String stageTime) {
        this.stageTime = stageTime;
    }
}
