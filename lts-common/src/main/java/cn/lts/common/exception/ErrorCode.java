package cn.lts.common.exception;

import java.io.Serializable;

/**
 * author: Zhang Li
 * created on 15-4-28.
 */
public class ErrorCode implements Serializable {
    private static final long serialVersionUID = -4977448850573113528L;

    private String code;

    private String msg;

    public ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
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
