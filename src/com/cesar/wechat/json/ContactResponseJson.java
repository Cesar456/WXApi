package com.cesar.wechat.json;

import java.util.List;

import com.cesar.wechat.bean.Contact;
import com.cesar.wechat.bean.BaseResponse;

public class ContactResponseJson {
	BaseResponse BaseResponse;
	int MemberCount;
	List<Contact> MemberList;
	public BaseResponse getBaseResponse() {
		return BaseResponse;
	}
	public int getMemberCount() {
		return MemberCount;
	}

	public List<Contact> getMemberList() {
		return MemberList;
	}

	public void setBaseResponse(BaseResponse BaseResponse) {
		this.BaseResponse = BaseResponse;
	}

	public void setMemberCount(int MemberCount) {
		this.MemberCount = MemberCount;
	}

	public void setMemberList(List<Contact> MemberList) {
		this.MemberList = MemberList;
	}

}
