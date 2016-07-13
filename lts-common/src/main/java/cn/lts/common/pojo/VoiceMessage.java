package cn.lts.common.pojo;

import cn.lts.common.util.MessageUtil;

/**
 * 响应消息之语音消息
 * @author jianyong.zhou
 *
 */
public class VoiceMessage extends BaseMessage{
	/**
	 * 语音
	 */
	private Voice Voice;
	
	public void setVoice(Voice voice) {
		Voice = voice;
	}
	
	public Voice getVoice() {
		return Voice;
	}
	@Override
	public String getMsgType() {
		return MessageUtil.REQ_MESSAGE_TYPE_VOICE;
	}
}