package org.wx.weixiao.appConnection;

public class ConnectionInfo {
	private String userId;
	private String userName;
	private Sign sign;
	
	public ConnectionInfo(String userId,String userName){
		this.userId=userId;
		this.userName=userName;
		sign=new Sign();
	}
	public Sign getSign() {
		return sign;
	}
	public void setSign(Sign sign) {
		this.sign = sign;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
