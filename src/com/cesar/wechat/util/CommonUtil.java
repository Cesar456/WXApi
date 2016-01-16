package com.cesar.wechat.util;

import java.io.IOException;
import java.io.StringReader;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.cesar.wechat.json.LoginResponse;

public class CommonUtil {
	
	private static Logger logger = Logger.getLogger(CommonUtil.class);

	public static LoginResponse parseLoginResult(String xmlDoc) {
		System.out.println(xmlDoc);
		LoginResponse loginResponse = new LoginResponse();
		StringReader reader = new StringReader(xmlDoc);
		InputSource source = new InputSource(reader);
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			Document document = saxBuilder.build(source);
			Element root = document.getRootElement();
			int ret = Integer.parseInt(root.getChild("ret").getText());
			String message = root.getChildText("message");
			String skey = root.getChildText("skey");
			String wxsid = root.getChildText("wxsid");
			long wxuin = Long.parseLong(root.getChildText("wxuin"));
			String pass_ticket = root.getChildText("pass_ticket");
			int isgrayscale = Integer.parseInt(root.getChildText("isgrayscale"));
			loginResponse.setRet(ret);
			loginResponse.setMessage(message);
			loginResponse.setSkey(skey);
			loginResponse.setWxsid(wxsid);
			loginResponse.setWxuin(wxuin);
			loginResponse.setPass_ticket(pass_ticket);
			loginResponse.setIsgrayscale(isgrayscale);
			loginResponse.setDeviceID("e000000000000012");
		} catch (JDOMException e) {
			logger.error("Jdom解析失败\n"+e.getStackTrace());
			return null;
		} catch (IOException e) {
			logger.error(e.getStackTrace());
			return null;
		}
		return loginResponse;
	}
	
	
	
}
