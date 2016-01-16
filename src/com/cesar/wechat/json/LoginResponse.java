package com.cesar.wechat.json;

/**
 * 该类是调用Login方法后返回的Xml的封装类
 * @author I325032
 *
 */
public class LoginResponse {
	
	private int ret;
	private String message;
	private String skey;
	private String wxsid;
	private Long wxuin;
	private String pass_ticket;
	private int isgrayscale;
	private String deviceID;

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public String getWxsid() {
		return wxsid;
	}

	public void setWxsid(String wxsid) {
		this.wxsid = wxsid;
	}

	public long getWxuin() {
		return wxuin;
	}

	public void setWxuin(long wxuin) {
		this.wxuin = wxuin;
	}

	public String getPass_ticket() {
		return pass_ticket;
	}

	public void setPass_ticket(String pass_ticket) {
		this.pass_ticket = pass_ticket;
	}

	public int getIsgrayscale() {
		return isgrayscale;
	}

	public void setIsgrayscale(int isgrayscale) {
		this.isgrayscale = isgrayscale;
	}
}
