<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Profile Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navbar.css" />
</head>
<body>
	<!-- Navbar -->
	<jsp:include page="navbar.jsp" />

	<div class="content">
		
		<div class="section">
		<div class="section profile-header" style="text-align: center; margin-bottom: 20px;">
			<c:if test="${not empty imagePath}">
				<img src="${pageContext.request.contextPath}/resources/images/users/${imagePath}" alt="Profile Image" style="width: 150px; height: 150px; border-radius: 50%; object-fit: cover;" />
			</c:if>
		</div>
			<h2>Edit Profile</h2>
			<form action="${pageContext.request.contextPath}/profile" method="post">
				<div class="form-group">
					<label for="firstName">First Name</label>
					<input type="text" name="firstName" id="firstName" value="${firstName}" placeholder="Enter first name" required />
				</div>
				<div class="form-group">
					<label for="lastName">Last Name</label>
					<input type="text" name="lastName" id="lastName" value="${lastName}" placeholder="Enter last name" required />
				</div>
				<div class="form-group">
					<label for="username">Username</label>
					<input type="text" name="username" id="username" value="${username}" placeholder="Enter username" required />
				</div>
				<div class="form-group">
					<label for="dob">Date of Birth</label>
					<input type="date" name="dob" id="dob" value="${dob}" required />
				</div>
				<div class="form-group">
					<label for="gender">Gender</label>
					<select name="gender" id="gender" required>
						<option value="">--Select--</option>
						<option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option>
						<option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
					</select>
				</div>
				<div class="form-group">
					<label for="email">Email Address</label>
					<input type="email" name="email" id="email" value="${email}" placeholder="Enter email address" required />
				</div>
				<div class="form-group">
					<label for="phoneNumber">Phone Number</label>
					<input type="tel" name="phoneNumber" id="phoneNumber" value="${phoneNumber}" placeholder="Enter phone number" required />
				</div>
				<div class="form-group">
					<label for="password">New Password</label>
					<input type="password" name="password" id="password" placeholder="Enter new password" />
				</div>
				<button type="submit" class="save-btn">Save Changes</button>
			</form>
		</div>
	</div>
</body>
</html>
