<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<title>AutoPartsPro - Quality Car Parts</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick-theme.min.css" />
<script src="https://unpkg.com/feather-icons"></script>
<script src="https://unpkg.com/scrollreveal"></script>
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
				<span>Looking</span>
				<br>
				For Car Parts
			</h1>
		</div>

		<div class="form-container">
			<form action="searchCars.jsp" method="get">
				<div class="input-box">
					<span>Location</span>
					<input type="text" name="location" placeholder="Search Places" />
				</div>
				<div class="input-box">
					<span>Pick-up Date</span>
					<input type="date" name="pickup_date" required />
				</div>
				<div class="input-box">
					<span>Return Date</span>
					<input type="date" name="return_date" required />
				</div>
				<input type="submit" class="btn" value="Search Cars" />
			</form>
		</div>
	</section>

	<!-- Slider -->
	<section id="slider" class="hero-slider">
		<div id="slider-1">
			<div>
				<h2>Premium Quality Car Parts</h2>
				<p>Top-rated accessories and components for your vehicle.</p>
				<a href="#">Shop Now</a>
			</div>
			<div>
				<h2>Fast Shipping, Trusted Support</h2>
				<p>Get your parts delivered quickly with full support.</p>
				<a href="#">Learn More</a>
			</div>
			<div>
				<h2>Gear Up for Performance</h2>
				<p>Upgrade your ride with high-end gear.</p>
				<a href="#">Buy Now</a>
			</div>
		</div>
	</section>

	<!-- Featured Products -->
	<section id="featured" class="section">
		<h2>Featured Products</h2>
		<div class="products">
			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/207a56c8-b777-42ee-a37b-f2673ce939ed.jpg"
					alt="Brake Pads" />
				<h3>Brake Pads</h3>
				<p>High-performance brake pads for maximum safety.</p>
			</div>
			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/207a56c8-b777-42ee-a37b-f2673ce939ed.jpg"
					alt="Spark Plug" />
				<h3>Spark Plug</h3>
				<p>Durable spark plugs to keep your engine running smooth.</p>
			</div>
			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/207a56c8-b777-42ee-a37b-f2673ce939ed.jpg"
					alt="Air Filter" />
				<h3>Air Filter</h3>
				<p>Ensure clean airflow for your engine with our top-grade
					filters.</p>
			</div>
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
			AutoPartsPro is your one-stop destination for high-quality car parts,
			accessories, and expert service. With years of experience and trusted
			partners, we bring you reliability on every road.</p>
	</section>

	<!-- Testimonials -->
	<section id="testimonials" class="testimonials-section">
		<h2>What Our Customers Say</h2>
		<div id="slider-2">
			<div class="testimonial-card">
				<span class="quote-mark">&#10077;</span>
				<p class="testimonial-text">AutoPartsPro delivers every time.
					Great prices and quick delivery. Couldn't ask for more.</p>
				<div class="testimonial-author">
					<img src="https://randomuser.me/api/portraits/men/15.jpg"
						class="testimonial-avatar" alt="User" />
					<div>
						<div class="author-name">Carlos Mendez</div>
						<div class="author-title">Fleet Manager, FastLine</div>
					</div>
				</div>
			</div>
			<div class="testimonial-card">
				<span class="quote-mark">&#10077;</span>
				<p class="testimonial-text">I got my brake pads delivered in 2
					days and they were a perfect fit. Will buy again!</p>
				<div class="testimonial-author">
					<img src="https://randomuser.me/api/portraits/women/25.jpg"
						class="testimonial-avatar" alt="User" />
					<div>
						<div class="author-name">Leena Patel</div>
						<div class="author-title">Mechanic, AutoFix</div>
					</div>
				</div>
			</div>
		</div>
	</section>
<jsp:include page="footer.jsp" />


	<script>
		$(document).ready(function() {
			$('#slider-1').slick({
				autoplay : true,
				dots : true,
				arrows : false
			});
			$('#slider-2').slick({
				autoplay : true,
				dots : true,
				arrows : false
			});
			feather.replace();
		});

		function toggleMenu() {
			document.getElementById("nav-menu").classList.toggle("active");
		}

		ScrollReveal({
			distance : '60px',
			duration : 2500,
			delay : 400,
			reset : true
		});
		ScrollReveal().reveal('.text', {
			origin : 'top'
		});
		ScrollReveal().reveal('.form-container form', {
			origin : 'left'
		});
	</script>
</body>
</html>
