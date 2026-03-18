<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>选择支付方式</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 500px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 30px; }
        .order-info { background: #f9f9f9; padding: 15px; border-radius: 4px; margin-bottom: 20px; }
        .order-info p { margin: 8px 0; color: #666; }
        .amount { font-size: 28px; color: #ff6a00; text-align: center; margin: 20px 0; }
        .amount::before { content: '¥'; font-size: 18px; }
        .pay-options { display: flex; flex-direction: column; gap: 15px; }
        .pay-option { display: flex; align-items: center; padding: 20px; border: 2px solid #eee; border-radius: 8px; cursor: pointer; transition: all 0.3s; }
        .pay-option:hover { border-color: #2896f3; background: #f0f7ff; }
        .pay-option img { width: 40px; height: 40px; margin-right: 15px; }
        .pay-option .name { font-size: 16px; font-weight: bold; }
        .pay-option .desc { font-size: 12px; color: #999; margin-top: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>选择支付方式</h2>
        <div class="order-info">
            <p><strong>预约信息</strong></p>
            <p>预约人：${appointment.name}</p>
            <p>就诊时间：${appointment.visittime}</p>
            <p>联系电话：${appointment.aphone}</p>
        </div>
        <div class="amount">${appointment.amount}</div>
        <div class="pay-options">
            <div class="pay-option" onclick="createOrder(1)">
                <img src="https://gw.alipayobjects.com/mdn/rms/afts/img/A*F6gvR4VdXrQAAAAAAAAAAAAAARQnAQ" alt="支付宝">
                <div>
                    <div class="name">支付宝</div>
                    <div class="desc">推荐使用支付宝扫码支付</div>
                </div>
            </div>
            <div class="pay-option" onclick="createOrder(2)">
                <img src="https://open.weixin.qq.com/zh_CN/htmledition/res/assets/res-design-download/icon64_appwx_logo.png" alt="微信支付">
                <div>
                    <div class="name">微信支付</div>
                    <div class="desc">推荐使用微信扫码支付</div>
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.2.min.js"></script>
    <script>
        function createOrder(payType) {
            $.post('${pageContext.request.contextPath}/payment/createOrder', {
                aid: ${appointment.aid},
                payType: payType
            }, function(res) {
                if (res.success) {
                    if (payType === 1) {
                        window.location.href = '${pageContext.request.contextPath}/payment/alipay?orderNo=' + res.orderNo;
                    } else {
                        window.location.href = '${pageContext.request.contextPath}/payment/wechat?orderNo=' + res.orderNo;
                    }
                } else {
                    alert(res.msg);
                }
            });
        }
    </script>
</body>
</html>
