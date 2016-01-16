package com.cesar.wechat.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class DWRHelper {

	private String redirectUri;

	/**
	 * 生成二维码之后等待用户扫描并在手机上验证 所以要轮询该方法 验证成功后会生成一个redirectUri，该地址是用于登录微信的地址，保存在公有变量
	 * 
	 * @return 状态码200 表示验证成功 201表示扫描成功等待验证，异常返回-1
	 */
	public String waitForLogin(String uuid, int tip) {
		HttpClient httpClient = new DefaultHttpClient();
		String urlString = "http://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?tip=%s&uuid=%s&_=%s";
		urlString = String.format(urlString, tip, uuid,
				System.currentTimeMillis());
		HttpGet httpGet = new HttpGet(urlString);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String re = EntityUtils.toString(response.getEntity());
			String[] result = re.split(";");
			if (result[0].replace("window.code=", "").equals("201")) {
				return "201";
			} else if (result[0].replace("window.code=", "").equals("200")) {
				// 由于js在转发参数时默认截取了&符号，所以先将其转制一下
				redirectUri = (result[1].replace("window.redirect_uri=", "")
						.replace("\"", "") + "&fun=new")
						.replace("&", "[cesar]").trim();
				return "200";
			} else {
				return "400";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "-1";
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
}
