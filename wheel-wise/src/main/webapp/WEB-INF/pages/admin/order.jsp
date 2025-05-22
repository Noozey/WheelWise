<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Orders</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
	<div class="admin-container">

		<jsp:include page="adminNavBar.jsp" />
		<main class="main-content">
			<header class="admin-header">
				<div class="header-left">
					<h1>Orders</h1>
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
						</tr>
					</thead>
					<tbody>
						<c:forEach var="order" items="${orders}">
							<tr>
								<td>#ORD-${order.orderId}</td>
								<td>${order.customerName}</td>
								<td>View in detail</td>
								<!-- Replace with product summary if needed -->
								<td>$${order.total}</td>
								<td>${order.orderDate}</td>
								<td>
									<form action="${pageContext.request.contextPath}/order"
										method="POST">
										<input type="hidden" name="orderId" value="${order.orderId}">
										<select class="status-select" name="newStatus"
											onchange="this.form.submit()">
											<option value="pending"
												${order.status == 'pending' ? 'selected' : ''}>Pending</option>
											<option value="processing"
												${order.status == 'processing' ? 'selected' : ''}>Processing</option>
											<option value="completed"
												${order.status == 'completed' ? 'selected' : ''}>Completed</option>
											<option value="cancelled"
												${order.status == 'cancelled' ? 'selected' : ''}>Cancelled</option>
										</select>
									</form>
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
