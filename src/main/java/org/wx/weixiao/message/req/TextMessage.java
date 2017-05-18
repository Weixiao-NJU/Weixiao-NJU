package org.wx.weixiao.message.req;

/**
 * 文本消息
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
