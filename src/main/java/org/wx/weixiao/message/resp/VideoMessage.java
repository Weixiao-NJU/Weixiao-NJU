package org.wx.weixiao.message.resp;

/**
 * 视频消息
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class VideoMessage extends BaseMessage {
	// 视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}