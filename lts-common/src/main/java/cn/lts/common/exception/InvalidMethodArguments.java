package cn.lts.common.exception;

/**
 * author: Zhang Li
 * created on 15-5-3.
 */
public class InvalidMethodArguments extends RuntimeException {
    private static final long serialVersionUID = -2207106216280883111L;

    public InvalidMethodArguments(String formater, Object... args) {
        this(String.format(formater, args));
    }

    public InvalidMethodArguments(String message) {
        super(message);
    }
}
