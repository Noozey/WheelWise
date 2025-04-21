<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contact Us - WheelWise | Premium Car Parts</title>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
<style>
:root {
	--black: #000000;
	--dark: #111111;
	--purple: #8A2BE2;
	--neon-purple: #C724B1;
	--light: #E6E6E6;
	--gray: #333333;
}

body {
	font-family: 'Poppins', sans-serif;
	background-color: var(--black);
	color: var(--light);
	margin: 0;
	padding: 0;
	line-height: 1.6;
	display: flex;
	flex-direction: column;
	min-height: 100vh;
}
.main-content {
	flex: 1;
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 100%;
}

.container {
	max-width: 1200px;
	width: 100%;
	margin: 3rem auto;
	padding: 0 2rem;
	box-sizing: border-box;
}

.contact-hero {
	text-align: center;
	margin-bottom: 3rem;
	width: 100%;
}

.contact-hero h1 {
	font-size: 2.8rem;
	font-weight: 700;
	color: var(--light);
	margin: 0 auto 1rem;
	max-width: 800px;
}

.contact-hero p {
	max-width: 700px;
	margin: 0 auto;
	font-size: 1.1rem;
	color: var(--light);
	opacity: 0.9;
	line-height: 1.8;
}

.contact-section {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
	gap: 3rem;
	margin-bottom: 4rem;
	align-items: stretch;
	width: 100%;
}

.contact-info, .contact-form {
	background-color: var(--dark);
	border-radius: 10px;
	padding: 2.5rem;
	border: 1px solid var(--gray);
	height: 100%;
	box-sizing: border-box;
	margin: 0 auto;
	max-width: 550px;
	width: 100%;
}

.contact-info h2, .contact-form h2 {
	font-size: 1.8rem;
	font-weight: 600;
	color: var(--light);
	margin: 0 0 1.5rem 0;
	position: relative;
	text-align: center;
}

.contact-info h2:after, .contact-form h2:after {
	content: '';
	position: absolute;
	bottom: -10px;
	left: 50%;
	transform: translateX(-50%);
	width: 60px;
	height: 3px;
	background: linear-gradient(90deg, var(--purple), var(--neon-purple));
}

.info-item {
	margin-bottom: 1.5rem;
	display: flex;
	align-items: flex-start;
	gap: 1rem;
}

.info-icon {
	color: var(--purple);
	font-size: 1.5rem;
	margin-top: 0.2rem;
	flex-shrink: 0;
}

.info-content {
	text-align: left;
	flex-grow: 1;
}

.info-content h3 {
	color: var(--light);
	font-weight: 600;
	margin-bottom: 0.3rem;
	text-align: left;
}

.info-content p, .info-content a {
	color: var(--light);
	opacity: 0.9;
	text-decoration: none;
	transition: all 0.3s ease;
	margin: 0;
	text-align: left;
	/
}

.info-content a:hover {
	color: var(--purple);
}

.form-group {
	margin-bottom: 1.5rem;
}

.form-group label {
	display: block;
	color: var(--light);
	margin-bottom: 0.5rem;
	font-weight: 500;
	text-align: left;
}

.form-control {
	width: 100%;
	padding: 0.8rem 1rem;
	border-radius: 5px;
	border: 1px solid var(--gray);
	background-color: rgba(50, 50, 50, 0.5);
	color: var(--light);
	font-family: 'Poppins', sans-serif;
	transition: all 0.3s ease;
	box-sizing: border-box;
}

.form-control:focus {
	outline: none;
	border-color: var(--purple);
	box-shadow: 0 0 0 2px rgba(138, 43, 226, 0.3);
}

textarea.form-control {
	min-height: 150px;
	resize: vertical;
}

.btn-container {
	text-align: center;
	margin-top: 1.5rem;
}

.btn {
	display: inline-block;
	background: linear-gradient(135deg, var(--purple), var(--neon-purple));
	color: white;
	padding: 1rem 2.5rem;
	border-radius: 50px;
	text-decoration: none;
	font-weight: 600;
	transition: all 0.3s ease;
	border: none;
	cursor: pointer;
	box-shadow: 0 5px 15px rgba(138, 43, 226, 0.4);
	margin: 0 auto;
}

.btn:hover {
	transform: translateY(-3px);
	box-shadow: 0 8px 25px rgba(138, 43, 226, 0.6);
}

.map-container {
	margin: 3rem auto 0;
	border-radius: 10px;
	overflow: hidden;
	border: 1px solid var(--gray);
	max-width: 1000px;
	width: 100%;
}

.map-container iframe {
	width: 100%;
	height: 400px;
	border: none;
}

footer {
	background-color: var(--dark);
	padding: 3rem 0;
	text-align: center;
	border-top: 1px solid var(--gray);
	width: 100%;
	margin-top: auto;
}

.footer-logo {
	font-size: 1.8rem;
	font-weight: 700;
	color: var(--light);
	margin-bottom: 1rem;
}

.footer-logo span {
	background: linear-gradient(90deg, var(--purple), var(--neon-purple));
	-webkit-background-clip: text;
	background-clip: text;
	color: transparent;
}

.copyright {
	color: var(--light);
	opacity: 0.7;
	font-size: 0.9rem;
	margin: 0;
}
</style>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navbar.css" />
</head>
<body>
<!-- Navbar -->
  <jsp:include page="navbar.jsp" />
  
	<main class="main-content">
		<div class="container">
			<section class="contact-hero">
				<h1>Get In Touch</h1>
				<p>Have questions about our products or services? Our team is
					ready to help you with any inquiries you may have.</p>
			</section>

			<section class="contact-section">
				<div class="contact-info">
					<h2>Contact Information</h2>

					<div class="info-item">
						<div class="info-icon">
							<i class="fas fa-map-marker-alt"></i>
						</div>
						<div class="info-content">
							<h3>Our Headquarters</h3>
							<p>
								Islington College
								<br>
								Kathmandu
							</p>
						</div>
					</div>

					<div class="info-item">
						<div class="info-icon">
							<i class="fas fa-phone-alt"></i>
						</div>
						<div class="info-content">
							<h3>Phone</h3>
							<p>
								<a href="tel:+18005551414">9801010000</a>
							</p>
						</div>
					</div>

					<div class="info-item">
						<div class="info-icon">
							<i class="fas fa-envelope"></i>
						</div>
						<div class="info-content">
							<h3>Email</h3>
							<p>
								<a href="mailto:info@wheelwise.com">info@wheelwise.com</a>
							</p>
						</div>
					</div>

				</div>

				<div class="contact-form">
					<h2>Send Us a Message</h2>
					<form>
						<div class="form-group">
							<label for="name">Full Name</label>
							<input type="text" id="name" name="name" class="form-control"
								required>
						</div>

						<div class="form-group">
							<label for="email">Email Address</label>
							<input type="email" id="email" name="email" class="form-control"
								required>
						</div>

						<div class="form-group">
							<label for="phone">Phone Number</label>
							<input type="tel" id="phone" name="phone" class="form-control">
						</div>

						<div class="form-group">
							<label for="subject">Subject</label>
							<select id="subject" name="subject" class="form-control" required>
								<option value="">Select a subject</option>
								<option value="product">Product Inquiry</option>
								<option value="order">Order Status</option>
								<option value="technical">Technical Support</option>
								<option value="business">Business Partnership</option>
								<option value="other">Other</option>
							</select>
						</div>

						<div class="form-group">
							<label for="message">Your Message</label>
							<textarea id="message" name="message" class="form-control"
								required></textarea>
						</div>

						<div class="btn-container">
							<button type="submit" class="btn">Send Message</button>
						</div>
					</form>
				</div>
			</section>

			<div class="map-container">
				<iframe
					src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3532.3118401898214!2d85.3252364!3d27.7076565!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x39eb1908434cb3c5%3A0x1fdf1a6d41d2512d!2sIslington%20College!5e0!3m2!1sen!2snp!4v1744952195458!5m2!1sen!2snp"
					width="600" height="450" style="border: 0;" allowfullscreen=""
					loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
			</div>
	</main>
</body>
</html>
