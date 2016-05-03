package info.puton.product.xdb.model;

import java.util.List;

/**
 * Created by taoyang on 3/14/16.
 */
public class SQLResult {

    private List<String> resultList;

    private List<Join> joinList;

    private List<Where> paramList;

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }

    public List<Join> getJoinList() {
        return joinList;
    }

    public void setJoinList(List<Join> joinList) {
        this.joinList = joinList;
    }

    public List<Where> getParamList() {
        return paramList;
    }

    public void setParamList(List<Where> paramList) {
        this.paramList = paramList;
    }
}
