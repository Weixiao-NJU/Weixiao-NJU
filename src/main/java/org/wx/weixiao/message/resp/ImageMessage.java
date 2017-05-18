package org.wx.weixiao.message.resp;


/**
 * 图片消息
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class ImageMessage extends BaseMessage {
	// 图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}