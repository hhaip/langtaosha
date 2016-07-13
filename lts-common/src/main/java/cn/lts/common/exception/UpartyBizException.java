package cn.lts.common.exception;

/**
 * author: Zhang Li
 * created on 15-4-28.
 */
public class UpartyBizException extends Exception {
    private static final long serialVersionUID = -4170100474387464070L;

    private ErrorCode errorCode;

    public UpartyBizException(ErrorCode errorCode) {
        super(errorCode.getCode() + "--" + errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
