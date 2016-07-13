package cn.lts.common.exception;

/**
 * author: Zhang Li
 * created on 15-5-3.
 */
public class UpartySysException extends RuntimeException {
    private static final long serialVersionUID = 6130729788194644878L;

    private ErrorCode errorCode;

    public UpartySysException(ErrorCode errorCode) {
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
