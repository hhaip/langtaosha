package cn.lts.common.pojo;

import cn.lts.common.util.MessageUtil;

/**
 * 响应消息之视频消息
 * @author jianyong.zhou
 *
 */
public class VideoMessage extends BaseMessage{
	/**
	 * 视频
	 */
	private Video video;
	
	public void setVideo(Video video) {
		this.video = video;
	}
	
	public Video getVideo() {
		return video;
	}
	
	@Override
	public String getMsgType() {
		return MessageUtil.REQ_MESSAGE_TYPE_VIDEO;
	}
}