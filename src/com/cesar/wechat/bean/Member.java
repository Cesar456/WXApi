package com.cesar.wechat.bean;

/**
 * 群里的Member
 * @author I325032
 */
public class Member {
	
	private long Uin;
	private String UserName;
	private String NickName;
	private long AttrStatus;
	private String PYInitial;
	private String PYQuanPin;
	private String RemarkPYInitial;
	private String RemarkPYQuanPin;
	private int MemberStatus;
	private String DisplayName;
	private String KeyWord;
	public long getUin() {
		return Uin;
	}
	public void setUin(long uin) {
		Uin = uin;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public long getAttrStatus() {
		return AttrStatus;
	}
	public void setAttrStatus(long attrStatus) {
		AttrStatus = attrStatus;
	}
	public String getPYInitial() {
		return PYInitial;
	}
	public void setPYInitial(String pYInitial) {
		PYInitial = pYInitial;
	}
	public String getPYQuanPin() {
		return PYQuanPin;
	}
	public void setPYQuanPin(String pYQuanPin) {
		PYQuanPin = pYQuanPin;
	}
	public String getRemarkPYInitial() {
		return RemarkPYInitial;
	}
	public void setRemarkPYInitial(String remarkPYInitial) {
		RemarkPYInitial = remarkPYInitial;
	}
	public String getRemarkPYQuanPin() {
		return RemarkPYQuanPin;
	}
	public void setRemarkPYQuanPin(String remarkPYQuanPin) {
		RemarkPYQuanPin = remarkPYQuanPin;
	}
	public int getMemberStatus() {
		return MemberStatus;
	}
	public void setMemberStatus(int memberStatus) {
		MemberStatus = memberStatus;
	}
	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	public String getKeyWord() {
		return KeyWord;
	}
	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}

}
