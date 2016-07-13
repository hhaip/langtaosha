package cn.lts.common.pojo;

import cn.lts.common.util.MessageUtil;

/**
 * 响应消息之图片消息
 * @author jianyong.zhou
 *
 */
public class ImageMessage extends BaseMessage {
	/**
	 * 图片
	 */
	private Image Image;

	public ImageMessage() {
		super();
		this.MsgType = MessageUtil.REQ_MESSAGE_TYPE_IMAGE;
	}

	public void setImage(Image image) {
		Image = image;
	}

	public Image getImage() {
		return Image;
	}
	
	@Override
	public String getMsgType() {
		return MessageUtil.REQ_MESSAGE_TYPE_IMAGE;
	}
}