package cn.lts.mobile.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/** 
 * @author zhoujy 
 * @date 创建时间：2015年7月1日 下午3:41:09 
 * @see  
 */
public class MyExceptionResolver implements HandlerExceptionResolver {
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//把漏网的异常信息记入日志 
		logger.error("Catch Exception: ", ex);
        Map<String,Object> map = new HashMap<String,Object>();
        StringPrintWriter strintPrintWriter = new StringPrintWriter();
        ex.printStackTrace(strintPrintWriter);
        //将错误信息传递给view
        //决定是否显示错误信息
        map.put("showStackTrace", Boolean.parseBoolean(request.getParameter("showStackTrace")));
        map.put("errMsg", "系统开小差，请稍后重试");
        //详细的错误信息
        map.put("stackTrace", strintPrintWriter.getString());
        return new ModelAndView("error_page", map);
	}
}