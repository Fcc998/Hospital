<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>支付宝支付</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 400px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .logo { text-align: center; margin-bottom: 20px; }
        .logo img { width: 120px; }
        .amount { text-align: center; font-size: 36px; color: #ff6a00; margin: 20px 0; }
        .amount::before { content: '¥'; font-size: 20px; }
        .order-info { background: #f9f9f9; padding: 15px; border-radius: 4px; margin: 15px 0; }
        .order-info p { margin: 5px 0; color: #666; }
        .btn-pay { width: 100%; padding: 15px; background: #1677ff; color: #fff; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .btn-pay:hover { background: #0958d9; }
        .qrcode { text-align: center; margin: 20px 0; }
        .qrcode img { width: 200px; height: 200px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">
            <img src="https://gw.alipayobjects.com/mdn/rms/afts/img/A*F6gvR4VdXrQAAAAAAAAAAAAAARQnAQ" alt="支付宝">
        </div>
        <div class="amount">${amount}</div>
        <div class="order-info">
            <p>订单号：${orderNo}</p>
            <p>商品：医院挂号费用</p>
        </div>
        <div class="qrcode">
            <img src="https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${orderNo}" alt="支付二维码">
            <p style="color:#999;font-size:12px;">请使用支付宝扫码支付</p>
        </div>
        <button class="btn-pay" onclick="simulatePay()">模拟支付成功</button>
        <p style="text-align:center;margin-top:15px;color:#999;font-size:12px;">（演示环境，点击按钮模拟支付）</p>
    </div>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.2.min.js"></script>
    <script>
        function simulatePay() {
            $.post('${pageContext.request.contextPath}/payment/simulate', {orderNo: '${orderNo}'}, function(res) {
                if (res.success) {
                    alert('支付成功！');
                    window.location.href = '${pageContext.request.contextPath}/';
                } else {
                    alert('支付失败：' + res.msg);
                }
            });
        }
    </script>
</body>
</html>
