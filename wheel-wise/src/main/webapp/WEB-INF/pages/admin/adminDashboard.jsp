<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard - AutoParts Elite</title>
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
						<p class="stat-value">$24,780</p>
						<span class="stat-change positive">+12.5%</span>
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
						<p class="stat-value">1,482</p>
						<span class="stat-change positive">+8.2%</span>
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
						<p class="stat-value">284</p>
						<span class="stat-change positive">+4.8%</span>
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
						<h3>Orders Complete</h3>
						<p class="stat-value">942</p>
						<span class="stat-change negative">-2.4%</span>
					</div>
				</div>
			</div>

			<div class="dashboard-content">
				<div class="recent-orders">
					<div class="section-header">
						<h2>Recent Orders</h2>
						<button class="view-all"><a href="${pageContext.request.contextPath}/order">View All</a></button>
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
								<tr>
									<td>#ORD-7843</td>
									<td>John Smith</td>
									<td>Performance Brake Kit</td>
									<td>$799.99</td>
									<td>
										<span class="status-badge completed">Completed</span>
									</td>
									<td>2024-03-15</td>
								</tr>
								<tr>
									<td>#ORD-7842</td>
									<td>Emma Wilson</td>
									<td>LED Headlight Set</td>
									<td>$299.99</td>
									<td>
										<span class="status-badge pending">Pending</span>
									</td>
									<td>2024-03-15</td>
								</tr>
								<tr>
									<td>#ORD-7841</td>
									<td>Michael Brown</td>
									<td>Sport Air Filter</td>
									<td>$59.99</td>
									<td>
										<span class="status-badge processing">Processing</span>
									</td>
									<td>2024-03-14</td>
								</tr>
								<tr>
									<td>#ORD-7840</td>
									<td>Sarah Davis</td>
									<td>Performance Brake Kit</td>
									<td>$799.99</td>
									<td>
										<span class="status-badge completed">Completed</span>
									</td>
									<td>2024-03-14</td>
								</tr>
								<tr>
									<td>#ORD-7839</td>
									<td>James Johnson</td>
									<td>LED Headlight Set</td>
									<td>$299.99</td>
									<td>
										<span class="status-badge cancelled">Cancelled</span>
									</td>
									<td>2024-03-14</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="dashboard-bottom">
					<div class="top-products">
						<div class="section-header">
							<h2>Top Products</h2>
							<button class="view-all">View All</button>
						</div>
						<div class="product-list">
							<div class="product-item">
								<img
									src="https://images.unsplash.com/photo-1600191763437-4b7f2cae081c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3"
									alt="Performance Brake Kit">
								<div class="product-details">
									<h4>Performance Brake Kit</h4>
									<p>142 sales this month</p>
								</div>
								<span class="product-price">$799.99</span>
							</div>
							<div class="product-item">
								<img
									src="https://images.unsplash.com/photo-1486262715619-67b85e0b08d3?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3"
									alt="Sport Air Filter">
								<div class="product-details">
									<h4>Sport Air Filter</h4>
									<p>98 sales this month</p>
								</div>
								<span class="product-price">$59.99</span>
							</div>
							<div class="product-item">
								<img
									src="https://images.unsplash.com/photo-1601393689124-8b474b46d598?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3"
									alt="LED Headlight Set">
								<div class="product-details">
									<h4>LED Headlight Set</h4>
									<p>76 sales this month</p>
								</div>
								<span class="product-price">$299.99</span>
							</div>
						</div>
					</div>

					<div class="recent-activity">
						<div class="section-header">
							<h2>Recent Activity</h2>
							<button class="view-all">View All</button>
						</div>
						<div class="activity-list">
							<div class="activity-item">
								<div class="activity-icon"
									style="background-color: rgba(99, 102, 241, 0.1); color: rgb(99, 102, 241);">
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
										viewBox="0 0 24 24" fill="none" stroke="currentColor"
										stroke-width="2" stroke-linecap="round"
										stroke-linejoin="round" class="icon">
										<path d="M6 2 3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4Z" />
										<path d="M3 6h18" />
										<path d="M16 10a4 4 0 0 1-8 0" /></svg>
								</div>
								<div class="activity-details">
									<p>New order received</p>
									<span>2 minutes ago</span>
								</div>
							</div>
							<div class="activity-item">
								<div class="activity-icon"
									style="background-color: rgba(132, 204, 22, 0.1); color: rgb(132, 204, 22);">
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
										viewBox="0 0 24 24" fill="none" stroke="currentColor"
										stroke-width="2" stroke-linecap="round"
										stroke-linejoin="round" class="icon">
										<path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
										<circle cx="9" cy="7" r="4" />
										<path d="M22 21v-2a4 4 0 0 0-3-3.87" />
										<path d="M16 3.13a4 4 0 0 1 0 7.75" /></svg>
								</div>
								<div class="activity-details">
									<p>New customer registered</p>
									<span>15 minutes ago</span>
								</div>
							</div>
							<div class="activity-item">
								<div class="activity-icon"
									style="background-color: rgba(245, 158, 11, 0.1); color: rgb(245, 158, 11);">
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
										viewBox="0 0 24 24" fill="none" stroke="currentColor"
										stroke-width="2" stroke-linecap="round"
										stroke-linejoin="round" class="icon">
										<path d="M22 12h-4l-3 9L9 3l-3 9H2" /></svg>
								</div>
								<div class="activity-details">
									<p>Product stock updated</p>
									<span>45 minutes ago</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>
</body>
</html>