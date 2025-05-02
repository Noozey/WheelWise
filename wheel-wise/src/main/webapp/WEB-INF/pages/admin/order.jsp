<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Orders - AutoParts Elite Admin</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
	<div class="admin-container">

		<jsp:include page="adminNavBar.jsp" />
		<main class="main-content">
			<header class="admin-header">
				<div class="header-left">
					<h1>Orders</h1>
				</div>
				<div class="header-right">
					<div class="search-box">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<circle cx="11" cy="11" r="8" />
							<path d="m21 21-4.3-4.3" /></svg>
						<input type="text" placeholder="Search orders...">
					</div>
					<button class="notification-btn">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M6 8a6 6 0 0 1 12 0c0 7 3 9 3 9H3s3-2 3-9" />
							<path d="M10.3 21a1.94 1.94 0 0 0 3.4 0" /></svg>
						<span class="notification-badge">3</span>
					</button>
					<div class="admin-profile">
						<img
							src="https://images.unsplash.com/photo-1633332755192-727a05c4013d?w=120&auto=format&fit=crop&q=60"
							alt="Admin Profile">
						<span>Admin User</span>
					</div>
				</div>
			</header>

			<div class="table-container">
				<table>
					<thead>
						<tr>
							<th>Order ID</th>
							<th>Customer</th>
							<th>Products</th>
							<th>Total</th>
							<th>Date</th>
							<th>Status</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="order" items="${orders}">
							<tr>
								<td>#ORD-${order.orderId}</td>
								<td>${order.customerName}</td>
								<td>View in detail</td> <!-- Replace with product summary if needed -->
								<td>$${order.total}</td>
								<td>${order.orderDate}</td>
								<td>
									<select class="status-select">
										<option value="pending" ${order.status == 'pending' ? 'selected' : ''}>Pending</option>
										<option value="processing" ${order.status == 'processing' ? 'selected' : ''}>Processing</option>
										<option value="completed" ${order.status == 'completed' ? 'selected' : ''}>Completed</option>
										<option value="cancelled" ${order.status == 'cancelled' ? 'selected' : ''}>Cancelled</option>
									</select>
								</td>
								<td>
									<button class="action-btn delete-btn">Delete</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</main>
	</div>
</body>
</html>
