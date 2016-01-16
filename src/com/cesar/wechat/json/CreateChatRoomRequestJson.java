package com.cesar.wechat.json;

import java.util.List;
import com.cesar.wechat.bean.BaseRequest;
import com.cesar.wechat.bean.UserName;

public class CreateChatRoomRequestJson {
	private int MemberCount;
	private List<UserName> MemberList;
	private String Topic;
	private BaseRequest BaseRequest;
	
	public CreateChatRoomRequestJson() {
	}

	public CreateChatRoomRequestJson(int memberCount, List<UserName> memberList, String topic,
			com.cesar.wechat.bean.BaseRequest baseRequest) {
		MemberCount = memberCount;
		MemberList = memberList;
		Topic = topic;
		BaseRequest = baseRequest;
	}


	public BaseRequest getBaseRequest() {
		return BaseRequest;
	}

	public void setBaseRequest(BaseRequest baseRequest) {
		BaseRequest = baseRequest;
	}

	public int getMemberCount() {
		return MemberCount;
	}

	public void setMemberCount(int memberCount) {
		MemberCount = memberCount;
	}

	public List<UserName> getMemberList() {
		return MemberList;
	}

	public void setMemberList(List<UserName> memberList) {
		MemberList = memberList;
	}

	public String getTopic() {
		return Topic;
	}

	public void setTopic(String topic) {
		Topic = topic;
	}
}
