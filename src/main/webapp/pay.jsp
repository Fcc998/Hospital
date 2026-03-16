<%@page contentType="text/html; UTF-8"  pageEncoding="UTF-8" language="java"  %>
<%@include file="common.jsp"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <base href="<%=basePath%>" >
    <meta charset="UTF-8">
    <title>在线支付</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .pay-container {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .pay-title {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .order-info {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 25px;
        }
        .order-info p {
            margin: 8px 0;
            color: #666;
        }
        .pay-methods {
            margin-bottom: 30px;
        }
        .pay-method {
            display: flex;
            align-items: center;
            padding: 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            margin-bottom: 15px;
            cursor: pointer;
            transition: all 0.3s;
        }
        .pay-method:hover {
            border-color: #0c81f8;
            background: #f0f7ff;
        }
        .pay-method.selected {
            border-color: #0c81f8;
            background: #f0f7ff;
        }
        .pay-icon {
            width: 50px;
            height: 50px;
            margin-right: 15px;
            font-size: 40px;
        }
        .alipay-icon {
            color: #1678ff;
        }
        .wechat-icon {
            color: #00c800;
        }
        .pay-name {
            font-size: 18px;
            font-weight: bold;
        }
        .pay-amount {
            text-align: center;
            font-size: 24px;
            color: #ff4400;
            font-weight: bold;
            margin: 20px 0;
        }
        .pay-btn {
            display: block;
            width: 100%;
            padding: 15px;
            background: #0c81f8;
            color: #fff;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            cursor: pointer;
            transition: background 0.3s;
        }
        .pay-btn:hover {
            background: #0a6bd6;
        }
    </style>
</head>
<body>
    <div class="pay-container">
        <h2 class="pay-title">在线支付</h2>
        <div class="order-info">
            <p>订单号：${appointment.aid}</p>
            <p>就诊人：${appointment.name}</p>
            <p>就诊时间：${appointment.visittime}</p>
            <p>联系电话：${appointment.aphone}</p>
        </div>
        <div class="pay-amount">
            支付金额：<span>¥15.00</span>
        </div>
        <div class="pay-methods">
            <h3 style="margin-bottom: 15px; color: #333;">选择支付方式</h3>
            <div class="pay-method selected" data-type="alipay">
                <div class="pay-icon alipay-icon">
                    <span>支</span>
                </div>
                <div class="pay-name">支付宝支付</div>
            </div>
            <div class="pay-method" data-type="wechat">
                <div class="pay-icon wechat-icon">
                    <span>微</span>
                </div>
                <div class="pay-name">微信支付</div>
            </div>
        </div>
        <button class="pay-btn" id="payBtn">立即支付</button>
    </div>

    <script src="js/jquery-1.11.2.min.js"></script>
    <script>
        $(function(){
            $('.pay-method').click(function(){
                $('.pay-method').removeClass('selected');
                $(this).addClass('selected');
            });

            $('#payBtn').click(function(){
                var payType = $('.pay-method.selected').data('type');
                var aid = '${appointment.aid}';
                
                $.post('appointment/processPay', {aid: aid, payType: payType}, function(data){
                    if(data > 0) {
                        alert('支付成功！');
                        location.href = 'appointment/paySuccess/' + data;
                    } else {
                        alert('支付失败，请重试！');
                    }
                });
            });
        });
    </script>
</body>
</html>
