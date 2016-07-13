package cn.lts.common.pojo;

/**
 * 语音消息中的Voice定义
 * @author jianyong.zhou
 *
 */
public class Voice {
	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}
	
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}