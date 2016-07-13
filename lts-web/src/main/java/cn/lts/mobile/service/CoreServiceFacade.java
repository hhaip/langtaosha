package cn.lts.mobile.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.lts.common.util.MessageUtil;

/**
 * 处理微信发来的信息
 * @author huanghaiping
 * 2016年7月13日-下午9:54:24
 */
@Component
public class CoreServiceFacade {
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*@Autowired
	private EventHandle eventHandle;

	@Autowired
	private MsgHandle msgHandle;*/

	/**
	 * 消息处理
	 * @param appContext 方便获取各种bean进行业务处理
	 * @param msg
	 * @return
	 */
	public String processRequest(String msg) {
		String respMessage = null;
		
		return respMessage;
	}

	public String handle(String msg) {
		String content = "";
		try {
			Map<String, String> values = MessageUtil.parseXml(msg);
			// 消息类型
			String msgType = values.get("MsgType");
			if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
				//content = eventHandle.handle(values);
			} else {
				//content = msgHandle.handle(values);
			}
		} catch (Exception e) {
			logger.error("微信消息处理异常:{},微信请求内容-[{}]", e.getMessage(), msg, e);
		}
		return StringUtils.defaultString(content, "");
	}

}