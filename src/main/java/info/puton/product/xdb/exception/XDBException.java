package info.puton.product.xdb.exception;

/**
 * Created by taoyang on 3/14/16.
 */
public class XDBException extends Exception {

    private String msgId;
    private String msgDesc;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc;
    }

    public XDBException(String msgDesc) {
        super(msgDesc);
        this.msgDesc = msgDesc;
    }

    public XDBException(String msgId, String msgDesc) {
        super(msgDesc);
        this.msgId = msgId;
        this.msgDesc = msgDesc;
    }

}
