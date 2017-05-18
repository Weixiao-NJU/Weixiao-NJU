package org.wx.weixiao.appConnection;

import java.util.Random;

public class Sign {
	private String from;
	private String nonce;
	private String timestamp;
	private String signature;
	
	public Sign(){
		from="jw";
		nonce=createNonce();
		timestamp=createTimestamp();
		signature="";
	}
	
	public String getFrom(){
		return from;
	}
	public String getNonce(){
		return nonce;
	}
	public String getTimestamp(){
		return timestamp;
	}
	public String getSignature(){
		return signature;
	}
	public void setSignature(String signature){
		this.signature=signature;
	}
	
	/**
	 * 生成长度不超过40的随机字符串
	 * @return
	 */
	private String createNonce(){
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
        // 生成随机长度���
        int stringLength = random.nextInt(40) + 1;  
        // 生成字符串��
        for (int j = 0; j < stringLength; j++) {  
        	int number = random.nextInt(base.length());     
        	sb.append(base.charAt(number));     
        }
        return sb.toString();
	}

	/**
	 * 生成时间戳
	 * @return
     */
	public String createTimestamp(){
		return System.currentTimeMillis()+"";
	}
	
	public void SigntoString(){
		System.out.println("from: "+from+" nonce: "+nonce+ " timestamp: "+timestamp+" signature: "+signature);
	}
}
