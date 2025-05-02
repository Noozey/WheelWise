<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${product.name}-AutoPartsElite</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/productsInformation.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<main class="product-container">
		<div class="product-content">
			<div class="product-gallery">
				<div class="main-image">
					<img
						src="${pageContext.request.contextPath}/resources/images/product/${product.imageUrl}"
						alt="${product.name}" id="mainImage">
				</div>
			</div>

			<div class="product-info">
				<div class="product-header">
					<h1>${product.name}</h1>
					<div class="product-meta">
						<span class="brand">${product.brand}</span>
					</div>
					<div class="product-rating">
						<div class="stars">
							<c:forEach begin="1" end="5">
								<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
									viewBox="0 0 24 24" fill="currentColor" stroke="none">
                                    <polygon
										points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
                                </svg>
							</c:forEach>
						</div>
					</div>
				</div>

				<div class="product-price">
					<span class="current-price">$${product.price}</span>
				</div>

				<div class="product-description">
					<p>High-performance brake kit designed for maximum stopping
						power and durability. Features precision-engineered components and
						advanced materials for superior braking performance.</p>
					<ul class="feature-list">
						<li>Premium quality brake rotors</li>
						<li>High-friction brake pads</li>
						<li>Stainless steel hardware</li>
						<li>Professional installation recommended</li>
						<li>2-year warranty</li>
						<li>Compatible with multiple vehicles</li>
					</ul>
				</div>

				<div class="product-actions">
					<div class="quantity-selector">
						<button class="quantity-btn" onclick="updateQuantity(-1)">-</button>
						<input type="number" value="1" min="1" class="quantity-input"
							id="quantity">
						<button class="quantity-btn" onclick="updateQuantity(1)">+</button>
					</div>
					<div class="action-buttons">
						<form action="${pageContext.request.contextPath}/cart"
							method="post" class="cart-form">
							<input type="hidden" name="action" value="add" />
							<input type="hidden" name="productId" value="${product.id}" />
							<input type="hidden" name="quantity" value="1" />
							<button type="submit" class="add-to-cart-btn">
								Add to Cart
								<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
									viewBox="0 0 24 24" fill="none" stroke="currentColor"
									stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
									class="icon">
                                    <circle cx="8" cy="21" r="1" />
                                    <circle cx="19" cy="21" r="1" />
                                    <path
										d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12" />
                                </svg>
							</button>
						</form>
						<form action="${pageContext.request.contextPath}/cart"
							method="post" class="buy-now-form">
							<input type="hidden" name="action" value="buy-now" />
							<input type="hidden" name="productId" value="${product.id}" />
							<input type="hidden" name="quantity" value="1" />
							<button type="submit" class="buy-now-btn">
								Buy Now
								<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
									viewBox="0 0 24 24" fill="none" stroke="currentColor"
									stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
									class="icon">
            <path d="M5 12h14" />
            <path d="m12 5 7 7-7 7" />
        </svg>
							</button>
						</form>
					</div>
				</div>

				<div class="product-details">
					<div class="detail-section">
						<h3>Specifications</h3>
						<table class="specs-table">
							<tr>
								<td>Brand</td>
								<td>${product.brand}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</main>
</body>
</html>
