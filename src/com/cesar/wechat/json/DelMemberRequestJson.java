package com.cesar.wechat.json;

import com.cesar.wechat.bean.BaseRequest;

public class DelMemberRequestJson {

	private BaseRequest BaseRequest;
	private String ChatRoomName;
	private String DelMemberList;
	
	public DelMemberRequestJson() {
	}

	public DelMemberRequestJson(com.cesar.wechat.bean.BaseRequest baseRequest, String chatRoomName,
			String delMemberList) {
		BaseRequest = baseRequest;
		ChatRoomName = chatRoomName;
		DelMemberList = delMemberList;
	}

	public BaseRequest getBaseRequest() {
		return BaseRequest;
	}

	public void setBaseRequest(BaseRequest baseRequest) {
		BaseRequest = baseRequest;
	}

	public String getChatRoomName() {
		return ChatRoomName;
	}

	public void setChatRoomName(String chatRoomName) {
		ChatRoomName = chatRoomName;
	}

	public String getDelMemberList() {
		return DelMemberList;
	}

	public void setDelMemberList(String delMemberList) {
		DelMemberList = delMemberList;
	}
}
