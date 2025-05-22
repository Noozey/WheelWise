<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page
	import="com.WheelWise.model.CartItem, com.WheelWise.model.ProductModel"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Shopping Cart</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/cart.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css" />
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<main class="cart-container">
		<div class="cart-header">
			<h1>Shopping Cart</h1>
			<span class="cart-count">
				<%=(session.getAttribute("cart") != null) ? ((List<?>) session.getAttribute("cart")).size() + " Items"
		: "0 Items"%>
			</span>
		</div>

		<div class="cart-content">
			<div class="cart-items">
				<%
				List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
				if (cart != null && !cart.isEmpty()) {
					double subtotal = 0;
					for (CartItem item : cart) {
						ProductModel p = item.getProduct();
						double total = item.getTotalPrice();
						subtotal += total;
				%>
				<div class="cart-item">
					<img
						src="${pageContext.request.contextPath}/resources/images/product/<%= p.getImageUrl() %>"
						alt="<%= p.getName() %>">
					<div class="item-details">
						<h3><%=p.getName()%></h3>
						<p class="item-brand"><%=p.getBrand()%></p>
					</div>
					<div class="quantity-controls">
						<form method="post" action="cart">
							<input type="hidden" name="action" value="update" />
							<input type="hidden" name="productId" value="<%=p.getId()%>" />
							<input type="number" name="quantity" min="1"
								value="<%=item.getQuantity()%>" class="quantity-input"
								onchange="this.form.submit()" />
						</form>
					</div>
					<div class="item-price">
						$<%=String.format("%.2f", total)%></div>
					<form method="post" action="cart">
						<input type="hidden" name="action" value="remove" />
						<input type="hidden" name="productId" value="<%=p.getId()%>" />
						<button type="submit" class="remove-item">X</button>
					</form>
				</div>
				<%
				}
				%>
			</div>

			<div class="cart-summary">
				<h2>Order Summary</h2>
				<div class="summary-row">
					<span>Subtotal</span>
					<span>
						$<%=String.format("%.2f", subtotal)%></span>
				</div>
				<div class="summary-row">
					<span>Shipping</span>
					<span>$25.00</span>
				</div>
				<div class="summary-row">
					<span>Tax</span>
					<span>
						$<%=String.format("%.2f", subtotal * 0.08)%></span>
				</div>

				<div class="summary-total">
					<span>Total</span>
					<span>
						$<%=String.format("%.2f", subtotal + 25 + subtotal * 0.08)%></span>
				</div>
				<form action="${pageContext.request.contextPath}/checkout"
					method="get">
					<button type="submit" class="checkout-btn">Proceed to
						Checkout</button>
				</form>


			</div>
			<%
			} else {
			%>
			<p style="color: gray;">Your cart is empty.</p>
			<%
			}
			%>
		</div>
	</main>
</body>
</html>
