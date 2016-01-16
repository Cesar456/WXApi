/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cesar.wechat.json;

import com.cesar.wechat.bean.BaseRequest;

public class InitRequestJson {
	private BaseRequest BaseRequest;

	public InitRequestJson(BaseRequest BaseRequest) {
		this.BaseRequest = BaseRequest;
	}

	public BaseRequest getBaseRequest() {
		return BaseRequest;
	}

	public void setBaseRequest(BaseRequest baseRequest) {
		BaseRequest = baseRequest;
	}
}
