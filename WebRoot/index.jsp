<%@page import="com.cesar.wechat.service.WXHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	WXHelper wxHelper = new WXHelper();
	String uuid = wxHelper.getUUID();
	String imagePath = wxHelper.getQR(uuid);
%>
<html>
<script type='text/javascript' src='/WXApi/dwr/engine.js'></script>
<script type='text/javascript' src='/WXApi/dwr/interface/DWRHelper.js'></script>
<script type='text/javascript' src='/WXApi/dwr/interface/WXHelper.js'></script>
<script type='text/javascript' src='/WXApi/dwr/util.js'></script>

<head>
<base href="<%=basePath%>">
<title>Cesar的小项目</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<body>
	<br>
	<br>
	<br>
	<div align="center">
		<h2>
			<b>查看微信流失好友</b>
		</h2>
		<br> <b>请扫一扫登录</b>
	</div>
	<br>
	<br>
	<br>
	<br>
	<div align="center">
		<img alt="获取二维码失败，请刷新重试" src="<%=imagePath%>">
	</div>
	<div align="center">
		<form name="hidden_form" class="box">
			<input type="hidden" name="uuid" id="uuid" value="<%=uuid%>" /> <input
				type="text" name="info" id="info" value="请扫描" />
		</form>
	</div>
</body>
<script type="text/javascript">
	var uuid = document.getElementById("uuid").value;
	DWRHelper.waitForLogin(uuid, 1, function getResult(res) {
		if (res == 201) {
			document.getElementById("info").value = "扫描成功，请在手机上点击登陆";
			DWRHelper.waitForLogin(uuid, 0, function getResult(code) {
				if (code == 200) {
					DWRHelper.getRedirectUri(function getRes(res){
						var url1 = "second.jsp?url="+res+"&uuid="+uuid;
						window.location.href=url1;
					});
					document.getElementById("info").value = "点击成功，正在跳转";
				} else {
					document.getElementById("info").value = "失败，请重试";
				}
			});
		} else {
			document.getElementById("info").value = "扫描失败，请刷新重试";
		}
	});
</script>
</html>

