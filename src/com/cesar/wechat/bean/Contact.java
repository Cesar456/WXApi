package com.cesar.wechat.bean;

import java.util.List;

public class Contact {
	private long Uin;
	private String UserName;
	private String NickName;
	private String HeadImgUrl;
	private int ContactFlag;
	private int MemberCount;
	private List<Member> MemberList;
	private String RemarkName;
	private int HideInputBarFlag;
	private int Sex;
	private String Signature;
	private int VerifyFlag;
	private long OwnerUin;
	private String PYInitial;
	private String PYQuanPin;
	private String RemarkPYInitial;
	private String RemarkPYQuanPin;
	private int StarFriend;
	private int AppAccountFlag;
	private int Statues;
	private int AttrStatus;
	private String Province;
	private String City;
	private String Alias;
	private int SnsFlag;
	private int UniFriend;
	private String DisplayName;
	private int ChatRoomId;
	private String KeyWord;
	private String EncryChatRoomId;
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
	public String getHeadImgUrl() {
		return HeadImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		HeadImgUrl = headImgUrl;
	}
	public int getContactFlag() {
		return ContactFlag;
	}
	public void setContactFlag(int contactFlag) {
		ContactFlag = contactFlag;
	}
	public int getMemberCount() {
		return MemberCount;
	}
	public void setMemberCount(int memberCount) {
		MemberCount = memberCount;
	}
	public List<Member> getMemberList() {
		return MemberList;
	}
	public void setMemberList(List<Member> memberList) {
		MemberList = memberList;
	}
	public String getRemarkName() {
		return RemarkName;
	}
	public void setRemarkName(String remarkName) {
		RemarkName = remarkName;
	}
	public int getHideInputBarFlag() {
		return HideInputBarFlag;
	}
	public void setHideInputBarFlag(int hideInputBarFlag) {
		HideInputBarFlag = hideInputBarFlag;
	}
	public int getSex() {
		return Sex;
	}
	public void setSex(int sex) {
		Sex = sex;
	}
	public String getSignature() {
		return Signature;
	}
	public void setSignature(String signature) {
		Signature = signature;
	}
	public int getVerifyFlag() {
		return VerifyFlag;
	}
	public void setVerifyFlag(int verifyFlag) {
		VerifyFlag = verifyFlag;
	}
	
	public long getUin() {
		return Uin;
	}
	public void setUin(long uin) {
		Uin = uin;
	}
	public long getOwnerUin() {
		return OwnerUin;
	}
	public void setOwnerUin(long ownerUin) {
		OwnerUin = ownerUin;
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
	public int getStarFriend() {
		return StarFriend;
	}
	public void setStarFriend(int starFriend) {
		StarFriend = starFriend;
	}
	public int getAppAccountFlag() {
		return AppAccountFlag;
	}
	public void setAppAccountFlag(int appAccountFlag) {
		AppAccountFlag = appAccountFlag;
	}
	public int getStatues() {
		return Statues;
	}
	public void setStatues(int statues) {
		Statues = statues;
	}
	public int getAttrStatus() {
		return AttrStatus;
	}
	public void setAttrStatus(int attrStatus) {
		AttrStatus = attrStatus;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getAlias() {
		return Alias;
	}
	public void setAlias(String alias) {
		Alias = alias;
	}
	public int getSnsFlag() {
		return SnsFlag;
	}
	public void setSnsFlag(int snsFlag) {
		SnsFlag = snsFlag;
	}
	public int getUniFriend() {
		return UniFriend;
	}
	public void setUniFriend(int uniFriend) {
		UniFriend = uniFriend;
	}
	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	public int getChatRoomId() {
		return ChatRoomId;
	}
	public void setChatRoomId(int chatRoomId) {
		ChatRoomId = chatRoomId;
	}
	public String getKeyWord() {
		return KeyWord;
	}
	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}
	public String getEncryChatRoomId() {
		return EncryChatRoomId;
	}
	public void setEncryChatRoomId(String encryChatRoomId) {
		EncryChatRoomId = encryChatRoomId;
	}
	@Override
	public String toString() {
		return "Member [Uin=" + Uin + ", UserName=" + UserName + ", NickName="
				+ NickName + ", HeadImgUrl=" + HeadImgUrl + ", ContactFlag="
				+ ContactFlag + ", MemberCount=" + MemberCount
				+ ", MemberList=" + MemberList + ", RemarkName=" + RemarkName
				+ ", HideInputBarFlag=" + HideInputBarFlag + ", Sex=" + Sex
				+ ", Signature=" + Signature + ", VerifyFlag=" + VerifyFlag
				+ ", OwnerUin=" + OwnerUin + ", PYInitial=" + PYInitial
				+ ", PYQuanPin=" + PYQuanPin + ", RemarkPYInitial="
				+ RemarkPYInitial + ", RemarkPYQuanPin=" + RemarkPYQuanPin
				+ ", StarFriend=" + StarFriend + ", AppAccountFlag="
				+ AppAccountFlag + ", Statues=" + Statues + ", AttrStatus="
				+ AttrStatus + ", Province=" + Province + ", City=" + City
				+ ", Alias=" + Alias + ", SnsFlag=" + SnsFlag + ", UniFriend="
				+ UniFriend + ", DisplayName=" + DisplayName + ", ChatRoomId="
				+ ChatRoomId + ", KeyWord=" + KeyWord + ", EncryChatRoomId="
				+ EncryChatRoomId + "]";
	}
	

}
