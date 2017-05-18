package org.wx.weixiao.appConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1 {
	/**
	 * 将str通过sha1方法加密
	 * @param str
	 * @return
     */
	public static String toSha1(String str){
		MessageDigest sha1 = null; 
		try {
			sha1 = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		sha1.update(str.getBytes());
		byte[] digest = sha1.digest(); 
		//转化为16进制
		String hex = toHex(digest); 
		return hex;
	}
	
	/** 
	  * 转换成16进制
	  * @param digest 
	  * @return 
	  */
    private static String toHex(byte[] digest) {
	    StringBuilder sb = new StringBuilder();
	    int len = digest.length;
	    String out = null;
	    for (int i = 0; i < len; i++) {
            out = Integer.toHexString(0xFF & digest[i]);
            if (out.length() == 1) {
                sb.append("0");
            }
            sb.append(out);
	    }
	    return sb.toString().toUpperCase();
    }
}
