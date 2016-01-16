package com.cesar.wechat.bean;

public class BaseResponse {
	int Ret;
	String ErrMsg;
	public String getErrMsg() {
		return ErrMsg;
	}

	public int getRet() {
		return Ret;
	}

	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}

	public void setRet(int ret) {
		Ret = ret;
	}
}
