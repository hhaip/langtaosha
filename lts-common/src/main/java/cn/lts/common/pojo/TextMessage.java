package cn.lts.common.pojo;

import java.util.Map;

import cn.lts.common.util.MessageUtil;

/**
 * 响应消息之文本消息
 *
 */
public class TextMessage extends BaseMessage {
	/**
	 *  回复的消息内容  
	 */
	private String Content;

	public TextMessage() {
		super();
		this.MsgType = MessageUtil.REQ_MESSAGE_TYPE_TEXT;
	}

	public TextMessage(Map<String, String> values) {
		super(values);
		this.Content = values.get("Content");
	}

	public TextMessage(String content) {
		this();
		this.Content = content;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	@Override
	public String getMsgType() {
		return MessageUtil.REQ_MESSAGE_TYPE_TEXT;
	}
}