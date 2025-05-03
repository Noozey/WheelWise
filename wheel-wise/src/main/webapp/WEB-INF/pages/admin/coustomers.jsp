<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customers - AutoParts Elite Admin</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
	<div class="admin-container">
		<jsp:include page="adminNavBar.jsp" />

		<main class="main-content">
			<header class="admin-header">
				<div class="header-left">
					<h1>Customers</h1>
				</div>
				<div class="header-right">
					<div class="search-box">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<circle cx="11" cy="11" r="8" />
							<path d="m21 21-4.3-4.3" /></svg>
						<input type="text" placeholder="Search customers...">
					</div>
				</div>
			</header>

			<div class="table-container">
				<table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Image</th>
							<th>Name</th>
							<th>Email</th>
							<th>Username</th>
							<th>Status</th>
							<th>Number</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${customers}">
							<tr>
								<td>${user.id}</td>
								<td>
									<img
										src="${pageContext.request.contextPath}/resources/images/users/${user.imagePath}"
										alt="${user.firstName} ${user.lastName}"
										class="product-thumbnail" style="height: 60px;">

								</td>
								<td>${user.firstName}${user.lastName}</td>
								<td>${user.email}</td>
								<td>${user.userName}</td>
								<td>
									<span class="status-badge completed">Active</span>
								</td>
								<td>${user.number}</td>
							</tr>
						</c:forEach>
						<c:if test="${empty customers}">
							<tr>
								<td colspan="6">No customers found.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</main>
	</div>
</body>
</html>
