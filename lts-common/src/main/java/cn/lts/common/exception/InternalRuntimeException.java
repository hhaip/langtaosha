package cn.lts.common.exception;

/**
 * 内部的RuntimeException，用于识别log已被起源处catch打了，左边界处不需要再打log
 * 
 * @author zhoufeng
 *
 */
public class InternalRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 3681127260069300492L;

	public InternalRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
	
	/**
	 * 为什么此处不打log：因为new InternalRuntimeException(msg)处时，是没有堆栈信息的，log不出足够的堆栈信息给参考
	 * @param message
	 */
	public InternalRuntimeException(String message) {
        super(message);
    }
}
