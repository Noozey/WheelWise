<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Shopping Cart - AutoParts Elite</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
	<main class="cart-container">
		<div class="cart-header">
			<h1>Shopping Cart</h1>
			<span class="cart-count">3 Items</span>
		</div>

		<div class="cart-content">
			<div class="cart-items">
				<div class="cart-item">
					<img
						src="https://images.unsplash.com/photo-1600191763437-4b7f2cae081c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3"
						alt="Performance Brake Kit">
					<div class="item-details">
						<h3>Performance Brake Kit</h3>
						<p class="item-brand">Brembo</p>
					</div>
					<div class="quantity-controls">
						<button class="quantity-btn">-</button>
						<input type="number" value="1" min="1" class="quantity-input">
						<button class="quantity-btn">+</button>
					</div>
					<div class="item-price">$799.99</div>
					<button class="remove-item">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M3 6h18" />
							<path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
							<path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" /></svg>
					</button>
				</div>

				<div class="cart-item">
					<img
						src="https://images.unsplash.com/photo-1486262715619-67b85e0b08d3?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3"
						alt="Sport Air Filter">
					<div class="item-details">
						<h3>Sport Air Filter</h3>
						<p class="item-brand">K&N</p>
					</div>
					<div class="quantity-controls">
						<button class="quantity-btn">-</button>
						<input type="number" value="2" min="1" class="quantity-input">
						<button class="quantity-btn">+</button>
					</div>
					<div class="item-price">$119.98</div>
					<button class="remove-item">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M3 6h18" />
							<path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
							<path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" /></svg>
					</button>
				</div>

				<div class="cart-item">
					<img
						src="https://images.unsplash.com/photo-1601393689124-8b474b46d598?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3"
						alt="LED Headlight Set">
					<div class="item-details">
						<h3>LED Headlight Set</h3>
						<p class="item-brand">Philips</p>
					</div>
					<div class="quantity-controls">
						<button class="quantity-btn">-</button>
						<input type="number" value="1" min="1" class="quantity-input">
						<button class="quantity-btn">+</button>
					</div>
					<div class="item-price">$299.99</div>
					<button class="remove-item">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M3 6h18" />
							<path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
							<path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" /></svg>
					</button>
				</div>
			</div>

			<div class="cart-summary">
				<h2>Order Summary</h2>
				<div class="summary-row">
					<span>Subtotal</span>
					<span>$1,219.96</span>
				</div>
				<div class="summary-row">
					<span>Shipping</span>
					<span>$25.00</span>
				</div>
				<div class="summary-row">
					<span>Tax</span>
					<span>$97.60</span>
				</div>
				<div class="promo-code">
					<input type="text" placeholder="Enter promo code">
					<button class="apply-btn">Apply</button>
				</div>
				<div class="summary-total">
					<span>Total</span>
					<span>$1,342.56</span>
				</div>
				<button class="checkout-btn">
					Proceed to Checkout
					<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
						viewBox="0 0 24 24" fill="none" stroke="currentColor"
						stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
						class="icon">
						<path d="M5 12h14" />
						<path d="m12 5 7 7-7 7" /></svg>
				</button>
			</div>
		</div>
	</main>
</body>
</html>