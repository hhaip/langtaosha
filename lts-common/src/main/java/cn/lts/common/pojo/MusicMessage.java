package cn.lts.common.pojo;

import cn.lts.common.util.MessageUtil;

/**
 * 响应消息之音乐消息
 *
 */
public class MusicMessage extends BaseMessage {
	/**
	 * 音乐  
	 */
    private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}  
    
	@Override
	public String getMsgType() {
		return MessageUtil.RESP_MESSAGE_TYPE_MUSIC;
	}
    
}
