package cn.lts.common.pojo;

/**
 * 视频消息中的Video定义
 * @author jianyong.zhou
 *
 */
public class Video {
	/**
	 * 通过上传多媒体文件，得到的id
	 */
	private String MediaId;
	
	/**
	 * 缩略媒体文件id
	 */
	private String thumbMediaId;
	
	/**
	 * 标题
	 */
	private String Title;
	
	/**
	 * 描述
	 */
	private String Description;

	public String getMediaId() {
		return MediaId;
	}
	
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	public String getTitle() {
		return Title;
	}
	
	public void setTitle(String title) {
		Title = title;
	}
	
	public String getDescription() {
		return Description;
	}
	
	public void setDescription(String description) {
		Description = description;
	}
}