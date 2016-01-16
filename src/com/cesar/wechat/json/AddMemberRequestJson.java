package com.cesar.wechat.json;

import com.cesar.wechat.bean.BaseRequest;

/**
 * 在调用为群聊添加成员的接口时用来做Json的参数
 * @author I325032
 */
public class AddMemberRequestJson {
	private BaseRequest BaseRequest;
	private String ChatRoomName;
	private String AddMemberList;

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

	public String getAddMemberList() {
		return AddMemberList;
	}

	public void setAddMemberList(String addMemberList) {
		AddMemberList = addMemberList;
	}

}
