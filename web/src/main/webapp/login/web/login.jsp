<%--
  Created by IntelliJ IDEA.
  User: rui
  Date: 2017/10/19
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/common.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>电可托-您的智慧电管家</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link type="text/css" rel="stylesheet" href="${path}/resource/login/style.css"/>
    <link href="${path}/resource/login/login.css" type="text/css"
          rel="stylesheet"/>
    <script type="text/javascript" src="${path}/resource/md5/md5.min.js"></script>
    <script src="${path}/resource/form/src/jquery.min.js" type="text/javascript"></script>
    <script src="${path}/resource/login/jquery-ui-1.7.3.js" type="text/javascript"></script>
    <%--<%@ include file="/common/new_header.jsp" %>--%>
    <script type="text/javascript">
        function setScreenInfo() {
            document.getElementById('screenInfo').value = screen.width + " * " + screen.height;
        }
    </script>


</head>
<body id="login">

<div class="wrapper-login">
    <div class="header-logo">
        <div class="w1">
            <h1 style="margin: 14px" class="logo fl"><img src="${path}/resource/login/images/logo1.png" height="35" alt="">
            </h1>
        </div>
    </div>

    <div class="login-bg">
        <img src="${path}/resource/login/images/loginbg4.png" height="" alt=""/>
    </div>

    <div class="login-box">
        <div class="login">
            <!-- 用户登录信息 start-->
            <div class="login-form">
                <%--<form method="post" action='${path}/cas/login'method="post" onsubmit="return doLocalLogin();">--%>
                <form method="post"  method="post">
                    <input type="hidden" id="screenInfo" name="screenInfo" >
                    <input type="hidden" id="loginType" name="loginType" value="portal"/>
                    <!-- login title -->
                    <ul class="loginCont">
                        <%--<li>扫码登录</li>--%>
                        <!-- <li>账号登录</li> -->
                        <li></li>
                    </ul>
                    <%--<div class="qrCode">--%>
                    <%--<img src="${path}/login/qrcode.png" width="210" alt=""/>--%>
                    <%--<p class="phoneEle">打开 <span>手机电可托</span> 扫码登录</p>--%>
                    <%--</div>--%>
                    <div class="accountIn">
                        <!-- login 表单数据 -->
                        <div class="errormsg">
                            <!-- 放置错误提示信息 -->
                            <!--您输入的用户名或密码不正确。-->
                            <span id="tips">${loginMessage}</span>
                        </div>

                        <div class="input-group">
                            <label><img src="${path}/resource/login/images/icon-username.png" height="20px" width="20px">
                            </label>
                            <%--onblur="this.className='txtbox'
                            onfocus="this.className='txtbox1'"
                            --%>
                            <input type="text" placeholder="请输入账号" ; class="txtbox" style="margin-bottom: 2px;color: #fff;"
                                   value="${j_username}"
                            <%--onblur="this.className='txtbox';getType()"--%>
                                   onblur="this.className='txtbox'"
                                   color: #fff;      onfocus="this.className='txtbox'"
                                   id="j_username" name="j_username"/>
                        </div>
                        <div class="input-group">
                            <label><img src="${path}/resource/login/images/icon-password.png" height="20px" width="20px">
                            </label>
                            <input type="password" placeholder="请输入密码"; class="txtbox" style="margin-bottom: 2px;color: #fff;"
                                   onfocus="this.className='txtbox1'"
                                   onblur="this.className='txtbox'" id="j_password" name="j_password"/>
                        </div>
                        <input type="hidden" id="j_ip" name="j_ip" />
                        <input type="hidden" id="j_cname" name="j_cname" />

                        <div class="input-group hide">
                            <label>
                                用户类型：</label>
                            <select id="userType">
                                <option value="系统管理员">系统管理员</option>
                                <option value="个人">个人</option>
                            </select>
                        </div>

                        <div class="input-group ml-50 mr-30" style="display: inline-block;">
                            <a href="${path}/login/findPassword.jsp" target="_blank" class="btn btn-link f-l ml-20 password">忘记密码？</a>
                            <a href="${path}/login/register.jsp" target="_blank" class="btn btn-link f-r password">注册用户</a>
                            <%--用户类型<input id="accType" type="hidden" value=""/>--%>
                        </div>
                        <!-- 用户登录信息 end-->
                        <p style="text-align: center; margin:0 0 8px -12px;">
                            <a href="javascript:void(0);" onkeydown="keyFunction();" onclick="doAllLogin();" class="loginBtn">登录</a></p>

                        <!--<div class="weChat">微信登录</div>-->
                        <div class="weChat2">二维码登录</div>
                    </div>


                </form>
            </div>
        </div>
    </div>
    <div class="footerIn">
        <div class="footerBody w1">
            <div style="float: left;margin-top: 30px;">
                <ul>
                    <li>
                            <span style="text-align: left;"><img src="${path}/resource/login/images/footer_icon1.png" alt=""
                                                                 style="margin-right: 10px"> 020-38038377</span>
                    </li>
                    <li>
                            <span style="text-align: left;"><img src="${path}/resource/login/images/footer_icon2.png" alt=""
                                                                 style="margin-right: 10px">www.kexin.com.cn</span>
                    </li>
                    <li>
                            <span style="text-align: left;"><img src="${path}/resource/login/images/footer_icon3.png" alt=""
                                                                 style="margin-right: 10px"> 广州天河区马场路16号富力盈盛广场B1705室</span>
                    </li>
                </ul>
            </div>
            <div style="float: left;">
                <p><img src="${path}/resource/login/images/footer1.png" alt=""
                        style="margin: 10px">
                </p>
                <p style="margin-left: -80px;"><img src="${path}/resource/login/images/footer2.png" alt=""
                                                    style="margin: 10px">
                </p>
                <p class="footerCopyright ">© 版权所有：广东可信电力股份有限公司 备案号（粤ICP备16062448号）</p>
            </div>
            <div style="float: left;text-align: right;margin-top: 16px;">


                <div style="float: right;padding-left: 30px;" >
                    <p><img src="${path}/resource/login/images/ewm1.png" alt=""></p>
                    <p>可信电力</p>
                </div>
                <div style="float:right;">
                    <p><img src="${path}/resource/login/images/ewm2.png" alt=""></p>
                    <p>电可托</p>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>


<%--<div class="wechatIn2">--%>
    <%--<div class="wechatLogin">--%>
        <%--<p class="closeLogin2">关闭</p>--%>
        <%--<p style="margin-top: 16px;display: inherit;">--%>
            <%--<img id="qrcode" src="" width="240px" height="240px"/>--%>
        <%--</p>--%>
        <%--<p style="text-align: center;margin-top: 18px;">请使用可信APP扫码登录</p>--%>
    <%--</div>--%>
<%--</div>--%>


<%--<div class="wechatIn">--%>
    <%--<div class="wechatLogin">--%>
        <%--<p class="closeLogin">关闭</p>--%>
        <%--<p style="margin-top: 16px;display: inherit;">--%>
            <%--<img src="${path}/resource/login/images/qrcode.png" width="210" alt=""/>--%>
        <%--</p>--%>
        <%--<p style="text-align: center;margin-top: 18px;">请使用微信扫码登录</p>--%>
    <%--</div>--%>
<%--</div>--%>


</body>
</html>
