package com.cesar.wechat.json;

import java.util.List;

import com.cesar.wechat.bean.BaseResponse;
import com.cesar.wechat.bean.Member;

public class CreateOrAddChatRoomResposeJson {
	private BaseResponse BaseResponse;
	private String ChatRoomName;
	private List<Member> MemberList;

	public BaseResponse getBaseResponse() {
		return BaseResponse;
	}

	public String getChatRoomName() {
		return ChatRoomName;
	}

	public List<Member> getMemberList() {
		return MemberList;
	}

	public void setMemberList(List<Member> memberList) {
		MemberList = memberList;
	}

	public void setBaseResponse(BaseResponse baseResponse) {
		BaseResponse = baseResponse;
	}

	public void setChatRoomName(String chatRoomName) {
		ChatRoomName = chatRoomName;
	}
}
