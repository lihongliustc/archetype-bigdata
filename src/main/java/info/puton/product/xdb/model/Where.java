package info.puton.product.xdb.model;

import info.puton.product.xdb.exception.XDBException;

/**
 * Created by taoyang on 3/22/16.
 */
public class Where {

    private String paramAtom;

    private String paramValue;

    private String operator;

    public String getParamAtom() {
        return paramAtom;
    }

    public void setParamAtom(String paramAtom) {
        this.paramAtom = paramAtom;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Where(String paramAtom, String paramValue, String operator) {
        this.paramAtom = paramAtom;
        this.paramValue = paramValue;
        this.operator = operator;
    }

    public Where() {
    }

    @Override
    public String toString() {
        return "Where{" +
                "paramAtom='" + paramAtom + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }

    public String generateWhere() throws XDBException {

        DataSource dataSource = new DataSource(getParamAtom());

        //TODO deal with different databaseType
        String databaseType = dataSource.getDatabaseType();

        String str;

        switch (getOperator()){

            case "in":

                str= " " + dataSource.getFieldName() + " IN " + " ( " + getParamValue() + " ) ";

                return str;

            case "between and":

                String[] value = getParamValue().trim().split(" ");

                str = " " + dataSource.getFieldName() + " BETWEEN " + value[0] + " AND " + value[1];

                return str;

            default:

                throw new XDBException("unrecognized operator in where");

        }

    }

}
