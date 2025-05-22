<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.WheelWise.model.CartItem"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Checkout</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>
	<div class="checkout-container">
		<h1>Checkout</h1>
		<form method="post"
			action="${pageContext.request.contextPath}/placeorder">
			<div class="delivery-section">
				<h2>Delivery Info</h2>
				<label>Full Name</label>
				<input type="text" name="name" required>
				<label>Address</label>
				<textarea name="address" rows="2" required></textarea>
				<label>City</label>
				<input type="text" name="city" required>
				<label>Postal Code</label>
				<input type="text" name="postal" required>
				<label>Phone</label>
				<input type="text" name="phone" required>
			</div>

			<div class="payment-section">
				<h2>Payment</h2>
				<label>
					<input type="radio" name="payment" value="cod" checked>
					Cash on Delivery
				</label>
			</div>

			<div class="order-summary">
				<h2>Summary</h2>
				<ul>
					<c:forEach var="item" items="${sessionScope.cart}">
						<li>${item.product.name}x ${item.quantity} = $${item.quantity
							* item.product.price}</li>
					</c:forEach>
				</ul>
			</div>

			<button type="submit" class="checkout-btn">Place Order</button>
		</form>
	</div>
</body>
</html>
