package org.wx.weixiao.appConnection;

import java.util.Arrays;

public class AppConnection {
	private final String SecurityKey="wechat_jw_security#12344@sudytech";
	
	public String createConnection(ConnectionInfo info){
		Sign sign=info.getSign();
		//第一步，整合字符串array
		String[] array = new String[] { info.getUserId(), sign.getFrom(), sign.getNonce(),sign.getTimestamp(), SecurityKey};
		//第二步，字符串排序
		Arrays.sort(array);
		//第三步，拼接成一个字符串
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			sb.append(array[i]);
		}
		String str = sb.toString();
		//第四步，sha1加密
		String signature=Sha1.toSha1(str);
		//第五步，整合成参数
		sign.setSignature(signature);
		String postStr="userId="+info.getUserId()+"&userName="+info.getUserName()+"&sign.from="+sign.getFrom()
				+"&sign.nonce=" +sign.getNonce() +"&sign.timestamp="+sign.getTimestamp()+"&sign.signature="+signature;
		return postStr;
	}

	//TEST
	public static void main(String[] args) {
		ConnectionInfo info=new ConnectionInfo("141250207","周赛");
		AppConnection connection=new AppConnection();
		String result=connection.createConnection(info);
		System.out.println(result);
	}
}
