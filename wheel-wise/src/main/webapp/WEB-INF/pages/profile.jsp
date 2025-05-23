<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Profile Page</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css" />

<c:if test="${cookie.role.value == 'admin'}">
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/admin.css" />
</c:if>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body style="display: flex;">

	<!-- Admin sidebar outside of content div -->
	<c:if test="${cookie.role.value == 'admin'}">
		<jsp:include page="./admin/adminNavBar.jsp" />
	</c:if>

	<!-- Content wrapper -->
	<div class="content" style="width: 100%;">

		<!-- Default navbar inside content (for non-admins) -->
		<c:if test="${cookie.role.value != 'admin'}">
			<jsp:include page="navbar.jsp" />
		</c:if>

		<!-- Profile Section -->
		<div class="section">
			<div class="section profile-header"
				style="text-align: center; margin-bottom: 20px;">
				<c:if test="${not empty imagePath}">
					<img
						src="${pageContext.request.contextPath}/resources/images/users/${imagePath}"
						alt="Profile Image"
						style="width: 150px; height: 150px; border-radius: 50%; object-fit: cover;" />
				</c:if>
			</div>
			<h2>Edit Profile</h2>
			<form action="${pageContext.request.contextPath}/profile"
				method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="firstName">First Name</label>
					<input type="text" name="firstName" id="firstName"
						value="${firstName}" placeholder="Enter first name" required />
				</div>
				<div class="form-group">
					<label for="lastName">Last Name</label>
					<input type="text" name="lastName" id="lastName"
						value="${lastName}" placeholder="Enter last name" required />
				</div>
				<div class="form-group">
					<label for="username">Username</label>
					<input type="text" name="username" id="username"
						value="${username}" placeholder="Enter username" required />
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
					<input type="email" name="email" id="email" value="${email}"
						placeholder="Enter email address" required />
				</div>
				<div class="form-group">
					<label for="phoneNumber">Phone Number</label>
					<input type="tel" name="phoneNumber" id="phoneNumber"
						value="${phoneNumber}" placeholder="Enter phone number" required />
				</div>
				<div class="form-group">
					<label for="password">New Password</label>
					<input type="password" name="password" id="password"
						placeholder="Enter new password" />
				</div>
				<div class="form-group">
					<label for="profileImage">Upload New Profile Image</label>
					<input type="file" name="profileImage" id="profileImage"
						accept="image/*" />
				</div>
				<button type="submit" class="save-btn">Save Changes</button>
			</form>
		</div>

		<!-- Order History Section -->
		<div class="section">
			<h2>Order History</h2>
			<c:choose>
				<c:when test="${not empty orders}">
					<table class="order-table">
						<thead>
							<tr>
								<th>Order ID</th>
								<th>Date</th>
								<th>Status</th>
								<th>Total</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<td>${order.orderId}</td>
									<td>${order.orderDate}</td>
									<td>${order.status}</td>
									<td>$${order.total}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<p>No orders found.</p>
				</c:otherwise>
			</c:choose>
		</div>

	</div>
</body>
</html>
