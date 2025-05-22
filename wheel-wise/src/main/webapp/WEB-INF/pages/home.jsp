<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<title>Home</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/home.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css" />
</head>

<body>
	<!-- Navbar -->
	<jsp:include page="navbar.jsp" />

	<!-- HOME SECTION -->
	<section class="home" id="home">
		<div class="text">
			<h1 style="color: white;">
				For All
				<br>
				YourCar Needs
				<br>
				<span>WheelWise</span>
			</h1>
		</div>
	</section>

	<!-- Featured Products -->
	<section id="featured" class="section">
		<h2>Featured Products</h2>
		<div class="products">
			<a class="product-card"
				style="text-decoration: inherit; color: inherit;"
				href="${pageContext.request.contextPath}/productInformation?id=5">
				<img
					src="${pageContext.request.contextPath}/resources/images/product/brakes.png"
					alt="Brake Pads" />
				<h3>Brake Pads</h3>
				<p>High-performance brake pads for maximum safety.</p>
			</a>
			<a class="product-card"
				style="text-decoration: inherit; color: inherit;"
				href="${pageContext.request.contextPath}/productInformation?id=2">
				<img
					src="${pageContext.request.contextPath}/resources/images/product/disk.png"
					alt="Spark Plug" />
				<h3>Spark Plug</h3>
				<p>Durable spark plugs to keep your engine running smooth.</p>
			</a>
			<a class="product-card"
				style="text-decoration: inherit; color: inherit;"
				href="${pageContext.request.contextPath}/productInformation?id=6">

				<img
					src="${pageContext.request.contextPath}/resources/images/product/airfilter.png"
					alt="Air Filter" />
				<h3>Air Filter</h3>
				<p>Ensure clean airflow for your engine with our top-grade
					filters.</p>
			</a>
		</div>
	</section>

	<!-- Services Section -->
	<section id="services" class="section">
		<h2>Our Services</h2>
		<div class="products">
			<div class="product-card">
				<h3>Car Diagnostics</h3>
				<p>Advanced diagnostics to identify and solve vehicle issues
					quickly.</p>
			</div>
			<div class="product-card">
				<h3>Installation Support</h3>
				<p>Professional support to install any purchased part or
					accessory.</p>
			</div>
			<div class="product-card">
				<h3>Warranty & Replacement</h3>
				<p>We provide warranty-backed replacements on eligible items.</p>
			</div>
		</div>
	</section>

	<!-- About Us Section -->
	<section id="aboutus" class="section">
		<h2>About Us</h2>
		<p style="max-width: 700px; margin: auto; text-align: center;">
			WheelWise is your one-stop destination for high-quality car parts,
			accessories, and expert service. With years of experience and trusted
			partners, we bring you reliability on every road.</p>
	</section>
	<jsp:include page="footer.jsp" />
</body>
</html>
