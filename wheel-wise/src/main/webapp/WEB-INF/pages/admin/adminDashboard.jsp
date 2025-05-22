<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard</title>
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
					<h1>Dashboard</h1>
				</div>
				<div class="header-right">
					<button class="notification-btn">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M6 8a6 6 0 0 1 12 0c0 7 3 9 3 9H3s3-2 3-9" />
							<path d="M10.3 21a1.94 1.94 0 0 0 3.4 0" /></svg>
						<span class="notification-badge">3</span>
					</button>
				</div>
			</header>

			<div class="dashboard-grid">
				<div class="stat-card">
					<div class="stat-icon"
						style="background-color: rgba(132, 204, 22, 0.1); color: rgb(132, 204, 22);">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M22 12h-4l-3 9L9 3l-3 9H2" /></svg>
					</div>
					<div class="stat-info">
						<h3>Total Sales</h3>
						<p class="stat-value">$${totalSales}</p>
					</div>
				</div>

				<div class="stat-card">
					<div class="stat-icon"
						style="background-color: rgba(99, 102, 241, 0.1); color: rgb(99, 102, 241);">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
							<circle cx="9" cy="7" r="4" />
							<path d="M22 21v-2a4 4 0 0 0-3-3.87" />
							<path d="M16 3.13a4 4 0 0 1 0 7.75" /></svg>
					</div>
					<div class="stat-info">
						<h3>Total Customers</h3>
						<p class="stat-value">${totalCustomers}</p>
					</div>
				</div>

				<div class="stat-card">
					<div class="stat-icon"
						style="background-color: rgba(245, 158, 11, 0.1); color: rgb(245, 158, 11);">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M6 2 3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4Z" />
							<path d="M3 6h18" />
							<path d="M16 10a4 4 0 0 1-8 0" /></svg>
					</div>
					<div class="stat-info">
						<h3>Total Products</h3>
						<p class="stat-value">${totalProducts}</p>
					</div>
				</div>

				<div class="stat-card">
					<div class="stat-icon"
						style="background-color: rgba(239, 68, 68, 0.1); color: rgb(239, 68, 68);">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path
								d="M3.85 8.62a4 4 0 0 1 4.78-4.77 4 4 0 0 1 6.74 0 4 4 0 0 1 4.78 4.78 4 4 0 0 1 0 6.74 4 4 0 0 1-4.77 4.78 4 4 0 0 1-6.75 0 4 4 0 0 1-4.78-4.77 4 4 0 0 1 0-6.76Z" />
							<path d="m9 12 2 2 4-4" /></svg>
					</div>
					<div class="stat-info">
						<h3>Total Orders</h3>
						<p class="stat-value">${totalOrders}</p>
					</div>
				</div>
			</div>

			<div class="dashboard-content">
				<div class="recent-orders">
					<div class="section-header">
						<h2>Recent Orders</h2>
						<button class="view-all">
							<a href="${pageContext.request.contextPath}/order">View All</a>
						</button>
					</div>
					<div class="table-container">
						<table>
							<thead>
								<tr>
									<th>Order ID</th>
									<th>Customer</th>
									<th>Product</th>
									<th>Amount</th>
									<th>Status</th>
									<th>Date</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="order" items="${recentOrders}">
									<tr>
										<td>#ORD-${order.orderId}</td>
										<td>${order.customerName}</td>
										<td>${order.productName}</td>
										<td>$${order.total}</td>
										<td>
											<span class="status-badge ${order.status.toLowerCase()}">${order.status}</span>
										</td>
										<td>${order.orderDate}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</main>
	</div>
</body>
</html>