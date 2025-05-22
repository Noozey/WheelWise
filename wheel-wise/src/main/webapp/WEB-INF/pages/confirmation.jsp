<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Order Confirmed</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>
	<div class="checkout-container">
		<h1>Order Successful!</h1>
		<p>Your order has been placed.</p>
		<p>
			<strong>Order ID:</strong>
			${orderId}
		</p>
		<a href="${pageContext.request.contextPath}/home" class="checkout-btn">Continue
			Shopping</a>
	</div>
</body>
</html>
