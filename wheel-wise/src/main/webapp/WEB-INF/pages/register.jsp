<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/signup.css" />
<title>Signup</title>
</head>
<body>
	<div class="container">
		<div class="form-container">
			<div class="form-section">
				<h1>Sign Up</h1>
				<c:if test="${not empty error}">
					<p class="error-message" style="color: red">${error}</p>
				</c:if>

				<!-- Display success message if available -->
				<c:if test="${not empty success}">
					<p class="success-message">${success}</p>
				</c:if>
				<form action="${pageContext.request.contextPath}/register"
					method="post" enctype="multipart/form-data">
					<div class="row">
						<div class="col">
							<label for="firstName">First Name</label>
							<input class="input"  id="firstName" name="firstName"
								 />
						</div>
						<div class="col">
							<label for="lastName">Last Name</label>
							<input class="input"  id="lastName" name="lastName"
								 />
						</div>
					</div>
					<div class="row">
						<div class="col">
							<label for="username">Username</label>
							<input class="input"  id="username" name="username"
								 />
						</div>
						<div class="col">
							<label for="birthday">Date of Birth</label>
							<input class="input" type="date" id="birthday" name="dob"
								 />
						</div>
					</div>
					<div class="row">
						<div class="col">
							<label for="gender">Gender</label>
							<select class="input" id="gender" name="gender" >
								<option value="male">Male</option>
								<option value="female">Female</option>
							</select>
						</div>
						<div class="col">
							<label for="email">Email</label>
							<input class="input"  id="email" name="email"
								 />
						</div>
					</div>
					<div class="row">
						<div class="col">
							<label for="phoneNumber">Phone Number</label>
							<input class="input" id="phoneNumber"
								name="phoneNumber"  />
						</div>
					</div>
					<div class="row">
						<div class="col">
							<label for="password">Password</label>
							<input class="input" id="password" type="password"
								name="password"  />
						</div>
						<div class="col">
							<label for="retypePassword">Retype Password</label>
							<input class="input" id="retypePassword" type="password"
								name="retypePassword"  />
						</div>
					</div>
					<div class="row">
						<div class="col">
							<label for="image">Profile Picture</label>
							<input class="input" id="image" name="image" type="file" />
						</div>
					</div>
					<button type="submit">Submit</button>
				</form>
			</div>
			<div class="image-section">
				<img
					src="https://images.unsplash.com/photo-1503376780353-7e6692767b70?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1740&q=80"
					alt="Porsche" />
			</div>
		</div>
	</div>
</body>
</html>
