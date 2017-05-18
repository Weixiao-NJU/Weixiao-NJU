package org.wx.weixiao.message.event;

/**
 * 自定义菜单事件
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class MenuEvent extends BaseEvent {
	// 事件KEY值，与自定义菜单接口中KEY值对应
	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
