package com.cesar.wechat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.cesar.wechat.bean.*;
import com.cesar.wechat.json.*;
import com.cesar.wechat.util.CommonUtil;
import com.google.gson.Gson;

public class WXHelper {

	private final String appID = "wx782c26e4c19acffb";
	private final int chatRoomMaxCount = 35;
	private final List<String> specialUser = Arrays.asList("newsapp", "fmessage", "filehelper", "weibo", "qqmail",
			"fmessage", "tmessage", "qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote", "qqfriend",
			"readerapp", "blogapp", "facebookapp", "masssendapp", "meishiapp", "feedsapp", "voip", "blogappweixin",
			"weixin", "brandsessionholder", "weixinreminder", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c",
			"officialaccounts", "notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "wxitil",
			"userexperience_alarm", "notification_messages");

	/* 各个方法的公有变量 */
	private BaseRequest baseRequest;
	private LoginResponse loginResponse;
	private String uuid;
	private String redirectUri;

	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpGet httpGet;
	private HttpResponse response;
	private HttpEntity entity;

	private Gson gson;
	private Logger logger;

	/* 下面是统计字段 */
	private List<Contact> all; // 所有人(人，非服务号)
	private List<Contact> special; // 特殊账号（文件助手，邮箱等等）
	private List<Contact> service; // 公共号/服务号
	private List<Contact> chatRoom; // 群聊
	private Contact mine; // 自己
	private List<Contact> lost; // 将你删除的人

	public WXHelper() {
		httpClient = new DefaultHttpClient();
		baseRequest = new BaseRequest();
		loginResponse = new LoginResponse();
		gson = new Gson();
		logger = Logger.getLogger(WXHelper.class);

		all = new ArrayList<>();
		special = new ArrayList<>();
		service = new ArrayList<>();
		chatRoom = new ArrayList<>();
		lost = new ArrayList<>();
	}
	public WXHelper(String uuid, String redirectUri){
		this();
		this.uuid = uuid;
		this.redirectUri = redirectUri;
	}

	/**
	 * @return uuid
	 */
	public String getUUID(){
		String url = "https://login.weixin.qq.com/jslogin?appid=%s&fun=new&lang=zh-CN&_=%s";
		url = String.format(url, appID,System.currentTimeMillis());
		httpGet = new HttpGet(url);
		try {
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			logger.debug(result);
			String[] res = result.split(";");
			if (res[0].replace("window.QRLogin.code = ", "").equals("200")) {
				uuid = res[1].replace(" window.QRLogin.uuid = ", "").replace("\"", "");
				return uuid;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @return 二维码图片在线地址
	 */
	public String getQR(String uuid) {
		if (uuid == null || "".equals(uuid)) {
			return null;
		}
		String QRurl = "http://login.weixin.qq.com/qrcode/" + uuid;
		logger.debug(QRurl);
		return QRurl;
		// 同时提供使其变为本地图片的方法
		// httpGet = new HttpGet(QRurl);
		// response = httpClient.execute(httpGet);
		// entity = response.getEntity();
		// InputStream in = entity.getContent();
		// //注意这里要对filepath赋值
		// OutputStream out = new FileOutputStream(new File("FilePath"+".png"));
		// byte[] b = new byte[1024];
		// int t;
		// while((t=in.read())!=-1){
		// out.write(b, 0, t);
		// }
		// out.flush();
		// in.close();
		// out.close();
	}
	
	/**
	 * 生成二维码之后等待用户扫描并在手机上验证 所以要轮询该方法 验证成功后会生成一个redirectUri，该地址是用于登录微信的地址，保存在公有变量
	 * @return 状态码200 表示验证成功 201表示扫描成功等待验证，异常返回-1
	 */
	public int waitForLogin(String uuid, int tip) {
		String urlString = "http://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?tip=%s&uuid=%s&_=%s";
		urlString = String.format(urlString, tip, uuid, System.currentTimeMillis());
		httpGet = new HttpGet(urlString);
		try {
			response = httpClient.execute(httpGet);
			String re = EntityUtils.toString(response.getEntity());
			String[] result = re.split(";");
			logger.debug(re);
			if (result[0].replace("window.code=", "").equals("201")) {
				tip = 0;
				return 201;
			} else if (result[0].replace("window.code=", "").equals("200")) {
				redirectUri = (result[1].replace("window.redirect_uri=", "").replace("\"", "") + "&fun=new").trim();
				return 200;
			} else {
				return 400;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//在login以后，开始进行
	public boolean excute(){
		login();
		initWX();
		getContact();
		if(all.size()==0){
			logger.error("没有得到好友列表");
			return false;
		}
		int count = all.size()/chatRoomMaxCount+1;
		List<Contact> cache = new ArrayList<>();
		String chatRoomName = "";
		for(int i =0;i<count;i++){
			int begin = i*chatRoomMaxCount;
			int end = Math.min(all.size(), (i+1)*chatRoomMaxCount);
			for(;begin<end;begin++){
				cache.add(all.get(begin));
			}
			if(i==0){
				chatRoomName = createChatRoom(cache);
				if(chatRoomName==null||"".equals(chatRoomName)){
					logger.error("建群失败");
					return false;
				}
			} else {
				addMember(cache, chatRoomName);
			}
			delMember(cache, chatRoomName);
			cache.clear();
		}
		
		for(Contact contact:lost){
			logger.debug(contact);
		}
		return true;
	}

	private boolean login() {
		String url = redirectUri;
		httpGet = new HttpGet(url);
		try {
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			String data = EntityUtils.toString(entity);
			logger.debug(data);
			loginResponse = CommonUtil.parseLoginResult(data);
			baseRequest = new BaseRequest(loginResponse.getWxuin(), loginResponse.getWxsid(), loginResponse.getSkey(),
					loginResponse.getDeviceID());
			return true;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void initWX() {

		String url = String.format("http://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?pass_ticket=%s&skey=%s&r=%s",
				loginResponse.getPass_ticket(), loginResponse.getSkey(), System.currentTimeMillis());
		InitRequestJson initRequestJson = new InitRequestJson(baseRequest);

		String re = getResponse(url, gson.toJson(initRequestJson));
		InitResponseJson initResponseJson = gson.fromJson(re, InitResponseJson.class);
		mine = initResponseJson.getUser();// 获取当前用户信息
	}

	private void getContact() {
		String url = String.format("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?pass_ticket=%s&skey=%s&r=%s",
				loginResponse.getPass_ticket(), loginResponse.getSkey(), System.currentTimeMillis());
		String re = getResponse(url, "");
		ContactResponseJson contactResponse = gson.fromJson(re, ContactResponseJson.class);
		for (Contact contact : contactResponse.getMemberList()) {
			if ((contact.getVerifyFlag() & 8) != 0) {
				service.add(contact);
			} else if (specialUser.contains(contact.getUserName())) {
				special.add(contact);
			} else if (contact.getUserName().contains("@@")) {
				chatRoom.add(contact);
			} else if (contact.getUserName().equals(mine.getUserName())) {
			} else {
				all.add(contact);
			}
		}
	}

	/**
	 * 创建聊天室，小伙伴被拉入群，如果MemberStatus=4，那么说明拉入失败，他将你删掉了
	 * @param contact，聊天室成员
	 * @return 创建的群聊的名字
	 */
	private String createChatRoom(List<Contact> contacts) {
		String url = String.format("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxcreatechatroom?pass_ticket=%s&r=%s'",
				loginResponse.getPass_ticket(), System.currentTimeMillis());
		List<UserName> userNames = new ArrayList<>();
		for (Contact contact : contacts) {
			UserName userName = new UserName();
			userName.setUserName(contact.getUserName());
			userNames.add(userName);
		}
		CreateChatRoomRequestJson createChatRoomRequestJson = new CreateChatRoomRequestJson(userNames.size(), userNames,
				"", baseRequest);
		String re = getResponse(url, gson.toJson(createChatRoomRequestJson));
		CreateOrAddChatRoomResposeJson chatRoomResposeJson = gson.fromJson(re, CreateOrAddChatRoomResposeJson.class);

		if (chatRoomResposeJson.getBaseResponse().getRet() == 0) {
			addLose(chatRoomResposeJson.getMemberList());
			return chatRoomResposeJson.getChatRoomName();
		}
		return null;
	}

	private boolean delMember(List<Contact> contacts, String chatRoomName) {
		String url = String.format(
				"https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxupdatechatroom?fun=delmember&pass_ticket=%s",
				loginResponse.getPass_ticket());
		DelMemberRequestJson delMemberRequestJson = new DelMemberRequestJson();
		delMemberRequestJson.setBaseRequest(baseRequest);
		delMemberRequestJson.setChatRoomName(chatRoomName);

		String s = "";
		for (Contact contact : contacts) {
			s = s + contact.getUserName() + ",";
		}
		s = s.substring(0, s.length() - 1);
		delMemberRequestJson.setDelMemberList(s);

		String re = getResponse(url, gson.toJson(delMemberRequestJson));
		BaseResponse baseResponse = gson.fromJson(re, BaseResponse.class);
		if (baseResponse.getRet() == 0) {
			return true;
		}
		return false;
	}

	private boolean addMember(List<Contact> content, String chatRoomName) {
		String url = String.format(
				"https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxupdatechatroom?fun=addmember&pass_ticket=%s",
				loginResponse.getPass_ticket());
		AddMemberRequestJson addMemberRequestJson = new AddMemberRequestJson();
		addMemberRequestJson.setBaseRequest(baseRequest);
		addMemberRequestJson.setChatRoomName(chatRoomName);
		String s = "";
		for (Contact contact : content) {
			s = s + contact.getUserName() + ",";
		}
		s = s.substring(0, s.length());
		addMemberRequestJson.setAddMemberList(s);
		String re = getResponse(url, gson.toJson(addMemberRequestJson));
		
		CreateOrAddChatRoomResposeJson addChatRoomRespose = gson.fromJson(re, CreateOrAddChatRoomResposeJson.class);
		BaseResponse baseResponse = addChatRoomRespose.getBaseResponse();
		if (baseResponse.getRet() == 0) {
			addLose(addChatRoomRespose.getMemberList());
			return true;
		}
		return false;
	}

	/**
	 * 遍历判断是否被删除
	 * @param members
	 */
	private void addLose(List<Member> members) {
		for (Member member : members) {
			if (member.getMemberStatus() == 4) {
				for (Contact contact : all) {
					if (contact.getUserName().equals(member.getUserName())) {
						lost.add(contact);
						break;
					}
				}
			}
		}
	}

	private String getResponse(String url, String content) {
		httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
		try {
			if (content != null && !"".equals(content)) {
				StringEntity se = new StringEntity(content);
				httpPost.setEntity(se);
			}
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			String re = EntityUtils.toString(entity, "UTF-8");
			logger.debug(re);
			return re;
		} catch (ParseException e) {
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
		return null;
	}

	public List<Contact> getAll() {
		return all;
	}

	public List<Contact> getSpecial() {
		return special;
	}

	public List<Contact> getService() {
		return service;
	}

	public List<Contact> getChatRoom() {
		return chatRoom;
	}

	public Contact getMine() {
		return mine;
	}

	public List<Contact> getLost() {
		return lost;
	}
	
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
