package info.puton.product.xdb.model;

import info.puton.product.xdb.exception.XDBException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by taoyang on 3/21/16.
 */
public class Join {

    private String masterDriver;

    private String slaveDriver;

    private String joinModel;

    public String getMasterDriver() {
        return masterDriver;
    }

    public void setMasterDriver(String masterDriver) {
        this.masterDriver = masterDriver;
    }

    public String getSlaveDriver() {
        return slaveDriver;
    }

    public void setSlaveDriver(String slaveDriver) {
        this.slaveDriver = slaveDriver;
    }

    public String getJoinModel() {
        return joinModel;
    }

    public void setJoinModel(String joinModel) {
        this.joinModel = joinModel;
    }

    public Join(String masterDriver, String slaveDriver, String joinModel) {
        this.masterDriver = masterDriver;
        this.slaveDriver = slaveDriver;
        this.joinModel = joinModel;
    }

    public Join() {
    }


    @Override
    public String toString() {
        return "Join{" +
                "masterDriver='" + masterDriver + '\'' +
                ", slaveDriver='" + slaveDriver + '\'' +
                ", joinModel='" + joinModel + '\'' +
                '}';
    }
}
