# WXApi

---

## 查看微信删除好友的web项目

---

---

## 项目作用

访问项目的网页，扫一扫网页上的二维码，就会显示你的微信好友中将你删除的人的列表。

---

## 项目原理
在微信中，将你删掉的好友是无法加入你创建的群聊的，而微信网页版也可以创建群聊，所以使用微信网页版的接口可以实现分辨一个好友是不是将你删除了。

---

## 流程和Java实现

### 1. 获取UUID
微信在生成二维码之前，会先生成一个UUID，作为一个识别的标记，携带这个UUID访问微信的接口就可以获取到二维码。同时也是查看二维码是否被扫描的一个重要参数。
参数列表如下:
- appid (可写死，wx782c26e4c19acffb)
- fun : new
- lang : zh-CN (中国地区)
- _ : 时间戳

```
// 参考代码：
// Java版本
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
```
``` python
# python版本
def getuuid():
    global uuid
    url = 'https://login.weixin.qq.com/jslogin'
    params = {
        'appid': 'wx782c26e4c19acffb',
        'fun': 'new',
        'lang': 'zh_CN',
        '_': int(time.time()),
    }
    request = urllib2.Request(url=url, data=urllib.urlencode(params))
    response = urllib2.urlopen(request)
    data = response.read()

    regx = r'window.QRLogin.code = (\d+); window.QRLogin.uuid = "(\S+?)"'
    pm = re.search(regx, data)

    code = pm.group(1)
    uuid = pm.group(2)

    if code == '200':
        return True

    return False
```

### 2. 获取二维码
将uuid放在url中然后使用get请求，会收到一个一张二维码的图片.当然，如果在网页中使用<img>的标签可以直接将这个URL放进去，就可以直接显示一张二维码。
参数列表如下：
- uuid：也就是上面所获取的UUID

``` java
//java版本
// 如果忽略注释直接返回获取图片的url放在网页中的<img>的标签下可以直接显示，如果使用注释中的内容会将其下载为本地图片
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
```

```
# Python版本
def showQRImage():
    global tip
    url = 'https://login.weixin.qq.com/qrcode/' + uuid
    request = urllib2.Request(url=url)
    response = urllib2.urlopen(request)
    f = open(QRImagePath, 'wb')
    f.write(response.read())
    f.close()  # 保存到本地
```


### 3. 获取用户登录状态
登陆状态主要是两种，一种是用户已经扫描，一种是用户扫描后在手机端已经点击确认了。这两种状态的获取访问的url是一样的，区别是一个叫做tip的参数，当tip=1的时候，如果没有扫描，服务端会一直等待，如果已经扫描，服务端会返回代买201.当tip=0的时候，如果用户没有点击确定，那么就会一直等待，直到用户点击确定后返回200.所以问题来了，如果不改变tip让他一直为1也是可以的，但是就需要不断的轮询，而如果改变tip的话，就可以while的循环。
参数如下：
- uuid : 就是之前获得的uuid
- _ : 时间戳
- tip ： 判断是要获得点击状态还是扫描状态
- 状态=200时，返回值是redirect_url:该返回值是一个url，访问该url就算是正式的登陆。

```
//java版本
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
```
```
# python版本
def waitForLogin():
    global tip, base_uri, redirect_uri
    url = 'https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?tip=%s&uuid=%s&_=%s' % (tip, uuid, int(time.time()))
    request = urllib2.Request(url = url)
    response = urllib2.urlopen(request)
    data = response.read()
    regx = r'window.code=(\d+);'
    pm = re.search(regx, data)
    code = pm.group(1)
    if code == '201': #已扫描
        print '成功扫描,请在手机上点击确认以登录'
        tip = 0
    elif code == '200': #已登录
        regx = r'window.redirect_uri="(\S+?)";'
        pm = re.search(regx, data)
        redirect_uri = pm.group(1) + '&fun=new'
        base_uri = redirect_uri[:redirect_uri.rfind('/')]
    elif code == '408': #超时
        pass
    return code
```

### 4. 正式登陆
手机端已经授权通过，上一步会返回一个Redirect_Url，这是一个真正的登陆url，使用get方法访问该url会返回一个xml格式的字符串，其中的属性将是接下来动作的重要参数。解析该字符串有如下的属性：

- int ret;//返回值为0时表示本次请求成功
- String message;//一些信息（比如失败原因等）
- String skey;//后面请求会用到的参数
- String wxsid;//同上
- long wxuin;// 本人编码
- String pass_ticket;//重要！！后面很多请求都会用到这张通行证
- int isgrayscale;//不明


代码如下：
```
//java
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
```
```
#python版本
def login():
    global skey, wxsid, wxuin, pass_ticket, BaseRequest
    request = urllib2.Request(url = redirect_uri)
    response = urllib2.urlopen(request)
    data = response.read()
    doc = xml.dom.minidom.parseString(data)
    root = doc.documentElement
    for node in root.childNodes:
        if node.nodeName == 'skey':
            skey = node.childNodes[0].data
        elif node.nodeName == 'wxsid':
            wxsid = node.childNodes[0].data
        elif node.nodeName == 'wxuin':
            wxuin = node.childNodes[0].data
        elif node.nodeName == 'pass_ticket':
            pass_ticket = node.childNodes[0].data
    if skey == '' or wxsid == '' or wxuin == '' or pass_ticket == '':
        return False
    BaseRequest = {
        'Uin': int(wxuin),
        'Sid': wxsid,
        'Skey': skey,
        'DeviceID': deviceId,
    }
    return True
```

### 5. init初始化
该方法可有可无，作用主要是初始化几个联系人，可能是最近联系人还是怎样，并且能获得的是登陆人的信息。如果不需要获取这些东西就可以跳过这一步。该方法是post方法，但在url中也可以放几个值
主要参数：
url中:
- pass_ticket
- skey  这两个参数都是login时的返回值之一
- r 时间戳

post 文中携带：BaseRequst=Json格式的BaseRequest，BaseRequest类中有如下参数：、
- long Uin;
- String Sid;
- String Skey;
- String DeviceID; DeviceID是一串e开头的随机数，随便填就可以。

```java
//java
private void initWX() {

		String url = String.format("http://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?pass_ticket=%s&skey=%s&r=%s",
				loginResponse.getPass_ticket(), loginResponse.getSkey(), System.currentTimeMillis());
		InitRequestJson initRequestJson = new InitRequestJson(baseRequest);//Java中包含了BaseRequest的包装类
		String re = getResponse(url, gson.toJson(initRequestJson));//这是自己写的一个公有方法，可以直接看源码
		InitResponseJson initResponseJson = gson.fromJson(re, InitResponseJson.class);
		mine = initResponseJson.getUser();// 获取当前用户信息
	}
```
``` python
def webwxinit():
    url = base_uri + '/webwxinit?pass_ticket=%s&skey=%s&r=%s' % (pass_ticket, skey, int(time.time()))
    params = {
        'BaseRequest': json.dumps(BaseRequest)
    }
    request = urllib2.Request(url=url, data=json.dumps(params))
    request.add_header('ContentType', 'application/json; charset=UTF-8')
    response = urllib2.urlopen(request)
    data = response.read()
    global ContactList, My
    dic = json.loads(data)
    ContactList = dic['ContactList']
    My = dic['User']

    ErrMsg = dic['BaseResponse']['ErrMsg']
    if len(ErrMsg) > 0:
        print ErrMsg

    Ret = dic['BaseResponse']['Ret']
    if Ret != 0:
        return False
    return True

```

### 6. 后面部分
由于登陆成功后后面部分基本就是调用接口了，难点基本没有，可以直接看源码，我在这里贴上操作步骤

- 获取所有的用户
通过post方法访问一个url(源码中可以看)，就可以获取所有的用户列表。
- 创建聊天室
注意一次最多40人否则会出现问题
- 删除聊天室的成员
- 为聊天室添加成员
微信会返回该成员的一个状态，如果状态等于4，那么添加失败，就可以判断该用户已经删除了登陆用户。

---

## 封装为网页
1. 得到uuid，并将其包装直接插入<img>标签中就可以在网页中显示该二维码
2. 使用AJAX请求，请求waitforlogging()方法，当返回值为200时成功，此时遍历该用户每一个好友，判断其是否删除了该用户。
3. 显示

---
## 参考文档
1. [该功能的python实现](https://github.com/Cesar456/wechat-deleted-friends)
2. [网页微信登录原理](http://blog.csdn.net/killer000777/article/details/9188741)

---
## 项目源码
[项目源码](https://github.com/Cesar456/WXApi)
