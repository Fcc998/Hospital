<%@page contentType="text/html; UTF-8"  pageEncoding="UTF-8" language="java"  %>
<%@include file="common.jsp"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <base href="<%=basePath%>" >
    <meta charset="UTF-8">
    <title>支付成功</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .success-container {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        .success-icon {
            font-size: 80px;
            color: #52c41a;
            margin-bottom: 20px;
        }
        .success-title {
            color: #333;
            margin-bottom: 30px;
        }
        .order-info {
            text-align: left;
            background: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin: 25px 0;
        }
        .order-info p {
            margin: 10px 0;
            color: #666;
        }
        .sms-notice {
            background: #e6f7ff;
            border: 1px solid #91d5ff;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            color: #1890ff;
        }
        .btn-group {
            margin-top: 30px;
        }
        .btn {
            display: inline-block;
            padding: 12px 25px;
            background: #0c81f8;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            margin: 0 10px;
            transition: background 0.3s;
        }
        .btn:hover {
            background: #0a6bd6;
        }
        .btn-home {
            background: #52c41a;
        }
        .btn-home:hover {
            background: #389e0d;
        }
    </style>
</head>
<body>
    <div class="success-container">
        <div class="success-icon">✓</div>
        <h2 class="success-title">支付成功！</h2>
        <p style="color: #666; font-size: 16px;">您的挂号订单已支付成功，短信通知已发送至您的手机</p>
        
        <div class="sms-notice">
            <p>短信提醒：【医者天下】您已成功预约医生，就诊时间：${appointment.visittime}，请按时就诊！订单号：${appointment.aid}</p>
        </div>
        
        <div class="order-info">
            <h3 style="margin-top: 0; color: #333;">订单详情</h3>
            <p>订单号：${appointment.aid}</p>
            <p>就诊人：${appointment.name}</p>
            <p>就诊时间：${appointment.visittime}</p>
            <p>联系电话：${appointment.aphone}</p>
            <p>支付方式：
                <c:choose>
                    <c:when test="${appointment.payType == 'alipay'}">支付宝</c:when>
                    <c:when test="${appointment.payType == 'wechat'}">微信支付</c:when>
                    <c:otherwise>其他</c:otherwise>
                </c:choose>
            </p>
            <p>支付金额：¥15.00</p>
        </div>
        
        <div class="btn-group">
            <a href="" class="btn btn-home">返回首页</a>
            <a href="javascript:history.back(-3)" class="btn">查看医生详情</a>
        </div>
    </div>
</body>
</html>
