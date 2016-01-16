<%@page import="com.cesar.wechat.bean.Contact"%>
<%@page import="com.cesar.wechat.service.WXHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type='text/javascript' src='/WXApi/dwr/engine.js'></script>
<script type='text/javascript' src='/WXApi/dwr/interface/DWRHelper.js'></script>
<script type='text/javascript' src='/WXApi/dwr/interface/WXHelper.js'></script>
<script type='text/javascript' src='/WXApi/dwr/util.js'></script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String redictUrl = request.getParameter("url").replace("[cesar]",
			"&");//将&符号转义回来,原因是js自动截取了转发的第一个&，所以在dwrhelper类中将其转义，现在转义回来
	String uuid = request.getParameter("uuid");
	WXHelper wxHelper = new WXHelper(uuid, redictUrl);
	wxHelper.excute();
	Contact mine = wxHelper.getMine();
	List<Contact> all = wxHelper.getAll();
	List<Contact> del = wxHelper.getLost();
	double delPer;
	if (all.size() == 0) {
		delPer = 0;
	} else {
		delPer = (double) del.size() / (double) all.size();
	}
%>
<html>
<head>
<base href="<%=basePath%>">
<title>Result</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<body>

	<div align="center">
		<br> <br> <br> 你好, <b><%=mine.getNickName()%></b>, 你的如下好友已经将您删除：
	</div>
	<br>
	<br>
	<table border=1 align="center">
	<tr>
	<th>昵称</th>
	<th>备注</th>
	</tr>
	<% for(Contact contact:del){ %>
	<tr>
	<td><%=contact.getNickName() %></td>
	<td><%=contact.getRemarkName() %></td>
	</tr>
	<%} %>
	</table>
	<div align="center">
		<br> <br> <br> 你共有好友<b><%=all.size()%></b>位,其中<b><%=del.size()%></b>已经将您删除，
		<br>删除率为：<%=delPer%>
	</div>
</body>
</html>
