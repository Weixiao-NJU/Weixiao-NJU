package org.wx.weixiao.message.req;

/**
 * 图片消息
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}