<%@page pageEncoding="UTF-8"  contentType="text/html; UTF-8" language="java" %>
<%@include file="common.jsp"%>
<html>
<head >
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>用户注册 - 医者天下</title>
    <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
    <link rel="stylesheet" href="css/all.css"/>
    <script src="js/my.js"></script>
    <style>
        .verify-box { display: flex; align-items: center; }
        .verify-box input { flex: 1; }
        .verify-btn { 
            margin-left: 10px; 
            padding: 8px 15px; 
            background: #2896f3; 
            color: #fff; 
            border: none; 
            border-radius: 4px; 
            cursor: pointer;
            white-space: nowrap;
        }
        .verify-btn:disabled { background: #ccc; cursor: not-allowed; }
        .verify-type { margin: 10px 0; }
        .verify-type label { margin-right: 20px; cursor: pointer; }
        .box { position: relative; }
    </style>
    <script>
        var countdown = 0;
        
        function startCountdown(btn, type) {
            var target = type === 1 ? $('#email').val() : $('#mobile').val();
            if (!target) {
                alert(type === 1 ? '请先输入邮箱' : '请先输入手机号');
                return;
            }
            
            var url = type === 1 ? 'verify/sendEmailCode' : 'verify/sendPhoneCode';
            var param = type === 1 ? {email: target} : {phone: target};
            
            $.post('${pageContext.request.contextPath}/' + url, param, function(res) {
                if (res.success) {
                    alert('验证码已发送');
                    countdown = 60;
                    var timer = setInterval(function() {
                        if (countdown <= 0) {
                            clearInterval(timer);
                            btn.prop('disabled', false).text('获取验证码');
                        } else {
                            btn.prop('disabled', true).text(countdown + '秒后重发');
                            countdown--;
                        }
                    }, 1000);
                } else {
                    alert(res.msg);
                }
            });
        }
        
        $(function() {
            $('#btn').click(function(e) {
                var verifyType = $('input[name="verifyType"]:checked').val();
                var verifyCode = $('#verifyCode').val();
                
                if (!verifyCode) {
                    e.preventDefault();
                    alert('请输入验证码');
                    return false;
                }
                
                if (!$('#ck').is(':checked')) {
                    e.preventDefault();
                    alert('请阅读并同意用户协议');
                    return false;
                }
                
                var pwd = $('#pwd').val();
                var pwd2 = $('#pwd2').val();
                if (pwd !== pwd2) {
                    e.preventDefault();
                    alert('两次密码输入不一致');
                    return false;
                }
            });
        });
    </script>
</head>
<body>
<div class="header">
    <a class="logo" href=""><img src="images/logo.jpg"></a>
    <div class="desc" style="margin-left: 120px;font-size: 25px">欢迎注册</div>
</div>
<div class="container">
    <div class="register">
        <form action="${pageContext.request.contextPath}/user/register" method="post" id="registerForm">
            <div class="register-box">
                <div class="box default">
                    <label for="user">账&nbsp;户&nbsp;名：</label>
                    <input name="user" type="text" id="user" placeholder="您的账户名" />
                    <i></i>
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <div class="register-box">
                <div class="box default">
                    <label for="name">姓&nbsp;名&nbsp;：</label>
                    <input name="name" type="text" id="name" placeholder="您的姓名" />
                    <i></i>
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <div class="register-box">
                <div class="box default">
                    <label for="name">性&nbsp;别&nbsp;：</label>
                    <input style="width: 50px" name="gender" value="男" type="radio" checked />男
                    <input style="width: 50px" name="gender" value="女" type="radio"  />女
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <div class="register-box">
                <div class="box default">
                    <label for="pwd">设 置 密 码：</label>
                    <input name="pwd" type="password" id="pwd" placeholder="建议至少两种字符组合" />
                    <i></i>
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <div class="register-box">
                <div class="box default">
                    <label for="pwd2">确 认 密 码：</label>
                    <input type="password" id="pwd2" placeholder="请再次输入密码" />
                    <i></i>
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <div class="register-box">
                <div class="box default">
                    <label for="email">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</label>
                    <input name="email" type="text" id="email" placeholder="请输入邮箱" />
                    <i></i>
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <div class="register-box">
                <div class="box default">
                    <label for="mobile">手 机 号 码：</label>
                    <input name="phone" type="text" id="mobile" placeholder="请输入手机号" />
                    <i></i>
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <div class="register-box">
                <div class="box default">
                    <label>验证方式：</label>
                    <div class="verify-type">
                        <label><input type="radio" name="verifyType" value="1" checked />邮箱验证</label>
                        <label><input type="radio" name="verifyType" value="2" />手机验证</label>
                    </div>
                </div>
            </div>
            <div class="register-box">
                <div class="box default">
                    <label for="verifyCode">验&nbsp;证&nbsp;码：</label>
                    <div class="verify-box">
                        <input name="verifyCode" type="text" id="verifyCode" placeholder="请输入验证码" />
                        <button type="button" class="verify-btn" id="sendCodeBtn" onclick="sendVerifyCode()">获取验证码</button>
                    </div>
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <div class="register-box xieyi">
                <div class="box default">
                    <input type="checkbox" id="ck" />
                    <span>我已阅读并同意<a href="##">《医者天下用户注册协议》</a></span>
                </div>
                <div class="tip">
                    <i></i>
                    <span></span>
                </div>
            </div>
            <button id="btn" type="submit">注册</button>
        </form>
    </div>
</div>
<script>
    function sendVerifyCode() {
        var type = parseInt($('input[name="verifyType"]:checked').val());
        var btn = $('#sendCodeBtn');
        
        if (countdown > 0) return;
        
        if (type === 1) {
            var email = $('#email').val();
            if (!email) {
                alert('请先输入邮箱');
                return;
            }
            if (!/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(email)) {
                alert('邮箱格式不正确');
                return;
            }
            startCountdown(btn, type);
        } else {
            var phone = $('#mobile').val();
            if (!phone) {
                alert('请先输入手机号');
                return;
            }
            if (!/^1[3-9]\d{9}$/.test(phone)) {
                alert('手机号格式不正确');
                return;
            }
            startCountdown(btn, type);
        }
    }
</script>
</body>
</html>
