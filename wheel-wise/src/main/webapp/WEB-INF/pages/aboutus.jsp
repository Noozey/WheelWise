<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>About Us</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
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
	overflow-x: hidden;
	line-height: 1.6;
}

.container {
	max-width: 1200px;
	margin: 3rem auto;
	padding: 0 2rem;
}

.hero {
	text-align: center;
	margin-bottom: 5rem;
}

.hero h1 {
	font-size: 3rem;
	font-weight: 700;
	color: var(--light);
	margin-bottom: 1.5rem;
}

.est-badge {
	display: inline-block;
	background: linear-gradient(135deg, var(--purple), var(--neon-purple));
	color: white;
	padding: 0.5rem 2rem;
	border-radius: 50px;
	font-weight: 600;
	margin: 2rem 0;
	animation: pulse 2s infinite;
	box-shadow: 0 0 20px rgba(138, 43, 226, 0.5);
}

@
keyframes pulse { 0% {
	transform: scale(1);
	box-shadow: 0 0 0 0 rgba(138, 43, 226, 0.5);
}

70
%
{
transform
:
scale(
1.05
);
box-shadow
:
0
0
0
15px
rgba(
138
,
43
,
226
,
0
);
}
100
%
{
transform
:
scale(
1
);
box-shadow
:
0
0
0
0
rgba(
138
,
43
,
226
,
0
);
}
}
.hero p {
	max-width: 800px;
	margin: 0 auto;
	font-size: 1.1rem;
	color: var(--light);
	opacity: 0.9;
}

.section {
	background-color: var(--dark);
	border-radius: 10px;
	padding: 3rem;
	margin-bottom: 3rem;
	border: 1px solid var(--gray);
}

.section h2 {
	font-size: 2rem;
	font-weight: 600;
	color: var(--light);
	margin-bottom: 2rem;
	position: relative;
}

.section h2:after {
	content: '';
	position: absolute;
	bottom: -10px;
	left: 0;
	width: 80px;
	height: 3px;
	background: linear-gradient(90deg, var(--purple), var(--neon-purple));
}

.grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
	gap: 2rem;
	margin-top: 2rem;
}

.card {
	background-color: rgba(30, 30, 30, 0.8);
	border-radius: 10px;
	padding: 2rem;
	border-top: 4px solid var(--purple);
	transition: all 0.3s ease;
}

.card:hover {
	transform: translateY(-10px);
	box-shadow: 0 10px 25px rgba(138, 43, 226, 0.3);
}

.card h3 {
	color: var(--light);
	font-size: 1.3rem;
	font-weight: 600;
	margin-bottom: 1rem;
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
}

.btn:hover {
	transform: translateY(-3px);
	box-shadow: 0 8px 25px rgba(138, 43, 226, 0.6);
}

.team-member {
	text-align: center;
}

.team-member img {
	width: 160px;
	height: 160px;
	border-radius: 50%;
	object-fit: cover;
	border: 3px solid var(--purple);
	margin-bottom: 1.5rem;
}

.team-member h3 {
	color: var(--light);
	font-weight: 600;
	margin-bottom: 0.5rem;
}

.team-member .title {
	color: var(--purple);
	font-weight: 600;
	margin-bottom: 1rem;
}

footer {
	background-color: var(--dark);
	padding: 3rem 0;
	text-align: center;
	border-top: 1px solid var(--gray);
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
}
</style>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css" />
</head>
<body>
	<!-- Navbar -->
	<jsp:include page="navbar.jsp" />

	<div class="container">
		<section class="hero">
			<h1>The Future of Auto Parts</h1>
			<div class="est-badge">EST. 2025</div>
			<p>WheelWise is revolutionizing the automotive parts industry
				with cutting-edge technology and unparalleled expertise. Founded in
				2025, we're building the next generation of auto parts distribution
				for enthusiasts and professionals alike.</p>
		</section>

		<section class="section">
			<h2>Our Vision</h2>
			<p>We're not just another auto parts company - we're redefining
				the entire experience from discovery to installation.</p>

			<div class="grid">
				<div class="card">
					<h3>Smart Matching</h3>
					<p>Our AI system learns your vehicle's needs and recommends the
						perfect parts with 99% accuracy.</p>
				</div>
				<div class="card">
					<h3>AR Guides</h3>
					<p>Interactive augmented reality makes installations easier
						than ever before.</p>
				</div>
				<div class="card">
					<h3>Fast Delivery</h3>
					<p>Same-day shipping to most locations with our distributed
						warehouse network.</p>
				</div>
			</div>
		</section>

		<section class="section">
			<h2>Our Team</h2>

			<div class="grid">
				<div class="team-member">
					<img src="${pageContext.request.contextPath}/resources/images/team/abhinav.jpeg">
					<h3>Abhinav Shakya</h3>
				</div>
				<div class="team-member">
					<img src="${pageContext.request.contextPath}/resources/images/team/rohan.jpeg">
					<h3>Rohan Upreti</h3>
				</div>
				<div class="team-member">
					<img src="${pageContext.request.contextPath}/resources/images/team/ramit.jpeg">
					<h3>Ramit Kharel</h3>
				</div>
				<div class="team-member">
					<img src="${pageContext.request.contextPath}/resources/images/team/Bigyan.jpeg">
					<h3>Bigyan Lama</h3>
				</div>
				<div class="team-member">
					<img src="${pageContext.request.contextPath}/resources/images/team/sahil1.jpeg">
					<h3>Sahil Sherchan</h3>
				</div>
			</div>
		</section>

		<section class="section" style="text-align: center;">
			<h2>Join The Revolution</h2>
			<p>We're just getting started. Be part of the future of auto
				parts.</p>
			<a href="${pageContext.request.contextPath}/contact" class="btn">Get
				in Touch</a>
		</section>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>