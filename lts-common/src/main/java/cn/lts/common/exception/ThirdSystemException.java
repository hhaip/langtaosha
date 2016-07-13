package cn.lts.common.exception;

/**
 * author: Zhang Li
 * created on 15-4-22.
 */
public class ThirdSystemException extends RuntimeException {
    private static final long serialVersionUID = -8757100780573466579L;
    private String code;
    private String msg;

    public ThirdSystemException(String code, String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
        this.code = code;
    }

    public ThirdSystemException(String code, String message) {
        super(message, null);
        this.msg = message;
        this.code = code;
    }

    public ThirdSystemException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public ThirdSystemException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
