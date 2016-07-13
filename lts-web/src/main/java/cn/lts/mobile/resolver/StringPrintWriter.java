package cn.lts.mobile.resolver;

/** 
 * @author zhoujy 
 * @date 创建时间：2015年7月1日 下午3:45:38 
 * @see  
 */
import java.io.PrintWriter;
import java.io.StringWriter;

public class StringPrintWriter extends PrintWriter {

	public StringPrintWriter() {
		super(new StringWriter());
	}

	public StringPrintWriter(int initialSize) {
		super(new StringWriter(initialSize));
	}

	public String getString() {
		flush();
		return ((StringWriter) this.out).toString();
	}

	@Override
	public String toString() {
		return getString();
	}
}
