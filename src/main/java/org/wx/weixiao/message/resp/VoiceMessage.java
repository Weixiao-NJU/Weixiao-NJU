package org.wx.weixiao.message.resp;

/**
 * 语音消息
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class VoiceMessage extends BaseMessage {
	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}