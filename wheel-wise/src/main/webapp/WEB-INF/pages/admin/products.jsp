<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Products - AutoParts Elite Admin</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css" />
</head>
<body>
	<div class="admin-container">
	<jsp:include page="adminNavBar.jsp" />

		<main class="main-content">
			<header class="admin-header">
				<div class="header-left">
					<h1>Products</h1>
				</div>
				<div class="header-right">
					<button class="add-product-btn" onclick="openAddProductModal()">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<path d="M5 12h14" />
							<path d="M12 5v14" /></svg>
						Add Product
					</button>
					<div class="search-box">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="icon">
							<circle cx="11" cy="11" r="8" />
							<path d="m21 21-4.3-4.3" /></svg>
						<input type="text" placeholder="Search products...">
					</div>
				</div>
			</header>

			<div class="table-container">
				<table>
					<thead>
						<tr>
							<th>Image</th>
							<th>Name</th>
							<th>Brand</th>
							<th>Category</th>
							<th>Price</th>
							<th>Stock</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="product" items="${productList}">
							<tr>
								<td>
									<img
										src="${pageContext.request.contextPath}/resources/images/product/${product.imageUrl}"
										style="height: 100px;" alt="Product" class="product-thumbnail">
								</td>
								<td>${product.name}</td>
								<td>${product.brand}</td>
								<td>${product.category}</td>
								<td>$${product.price}</td>
								<td>
									<input type="number" class="stock-input" value="15" min="0">
								</td>
								<td>
									<button class="action-btn edit-btn">Edit</button>
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
