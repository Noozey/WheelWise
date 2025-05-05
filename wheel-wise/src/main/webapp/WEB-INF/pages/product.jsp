<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Car Parts Product Page with Filters</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/products.css">

</head>
<body>
	<!-- Navbar -->
	<jsp:include page="navbar.jsp" />

	<div class="container">
		<header class="header">
			<div class="header-content">
				<div class="search-container">
					<input type="text" id="search" placeholder="Search parts...">
				</div>
			</div>
		</header>

		<main>
			<form method="get"
				action="${pageContext.request.contextPath}/product">
				<aside class="filters">
					<div class="filters-header">
						<h2>Filters</h2>
					</div>

					<div class="filter-section">
						<h3>Sort By</h3>
						<div class="sort-options">
							<select name="sort" onchange="this.form.submit()">
								<option value="featured">Featured</option>
								<option value="price-high"
									${param.sort == 'price-high' ? 'selected' : ''}>Price:
									High to Low</option>
								<option value="price-low"
									${param.sort == 'price-low' ? 'selected' : ''}>Price:
									Low to High</option>
								<option value="name-asc"
									${param.sort == 'name-asc' ? 'selected' : ''}>Name: A
									to Z</option>
								<option value="name-desc"
									${param.sort == 'name-desc' ? 'selected' : ''}>Name: Z
									to A</option>
							</select>
						</div>
					</div>

					<div class="filter-section">
						<h3>Price Range</h3>
						<div class="price-inputs">
							<input type="range" name="priceMax" min="0" max="1000"
								value="${param.priceMax != null ? param.priceMax : 1000}"
								onchange="this.form.submit()">
							<div class="price-values">
								<span>$0</span>
								<span id="price-value">$${param.priceMax != null ?
									param.priceMax : '1000'}</span>
								<span>$1000</span>
							</div>
						</div>
					</div>

					<div class="filter-section">
						<h3>Brands</h3>
						<div class="checkbox-group">
							<label>
								<input type="checkbox" name="brands" value="Brembo"
									onchange="this.form.submit()"
									${fn:contains(param.brands, 'Brembo') ? 'checked' : ''}>
								Brembo
							</label>
							<label>
								<input type="checkbox" name="brands" value="K&N"
									onchange="this.form.submit()"
									${fn:contains(param.brands, 'K&N') ? 'checked' : ''}>
								K&N
							</label>
							<label>
								<input type="checkbox" name="brands" value="Philips"
									onchange="this.form.submit()"
									${fn:contains(param.brands, 'Philips') ? 'checked' : ''}>
								Philips
							</label>
							<label>
								<input type="checkbox" name="brands" value="Bosch"
									onchange="this.form.submit()"
									${fn:contains(param.brands, 'Bosch') ? 'checked' : ''}>
								Bosch
							</label>
							<label>
								<input type="checkbox" name="brands" value="NGK"
									onchange="this.form.submit()"
									${fn:contains(param.brands, 'NGK') ? 'checked' : ''}>
								NGK
							</label>
						</div>
					</div>

					<div class="filter-section">
						<h3>Categories</h3>
						<div class="checkbox-group">
							<label>
								<input type="checkbox" name="categories" value="Brakes"
									onchange="this.form.submit()"
									${fn:contains(param.categories, 'Brakes') ? 'checked' : ''}>
								Brakes
							</label>
							<label>
								<input type="checkbox" name="categories" value="Air Intake"
									onchange="this.form.submit()"
									${fn:contains(param.categories, 'Air Intake') ? 'checked' : ''}>
								Air Intake
							</label>
							<label>
								<input type="checkbox" name="categories" value="Lighting"
									onchange="this.form.submit()"
									${fn:contains(param.categories, 'Lighting') ? 'checked' : ''}>
								Lighting
							</label>
							<label>
								<input type="checkbox" name="categories" value="Engine"
									onchange="this.form.submit()"
									${fn:contains(param.categories, 'Engine') ? 'checked' : ''}>
								Engine
							</label>
							<label>
								<input type="checkbox" name="categories" value="Suspension"
									onchange="this.form.submit()"
									${fn:contains(param.categories, 'Suspension') ? 'checked' : ''}>
								Suspension
							</label>
						</div>
					</div>
				</aside>
			</form>

			<section class="products">
				<c:forEach var="product" items="${products}">
					<a
						href="${pageContext.request.contextPath}/productInformation?id=${product.id}"
						class="product-link">
						<div class="product-card">
							<img
								src="${pageContext.request.contextPath}/resources/images/product/${product.imageUrl}">
							<div class="product-info">
								<h3>${product.name}</h3>
								<p class="brand">${product.brand}</p>
								<p class="category">${product.category}</p>
								<div class="product-footer">
									<span class="price">${product.price}</span>
									<form action="${pageContext.request.contextPath}/cart"
										method="post" class="cart-form">
										<input type="hidden" name="action" value="add" />
										<input type="hidden" name="productId" value="${product.id}" />
										<input type="hidden" name="quantity" value="1" />
										<button type="submit" class="add-to-cart">Add to Cart</button>
									</form>
								</div>

							</div>

						</div>

					</a>



				</c:forEach>
			</section>

		</main>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
