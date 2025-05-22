<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Products</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin.css">
<style>
/* Modal overlay */
.modal {
	position: fixed;
	z-index: 100;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.6);
	display: flex;
	justify-content: center;
	align-items: center;
	animation: fadeIn 0.3s ease-in-out;
}

/* Modal content box (ShadCN style) */
.modal-content {
	background-color: #121212; /* Dark background */
	color: #ffffff;
	padding: 24px;
	width: 480px;
	border-radius: 12px; /* Rounded corners */
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
	font-family: 'Segoe UI', sans-serif;
	animation: scaleIn 0.3s ease-in-out;
	transition: all 0.2s ease;
	max-height: 90vh; /* Ensure it doesn't overflow */
}

/* Header style */
.modal-content h2 {
	color: #00c3d1;
	margin-bottom: 20px;
	font-size: 22px;
}

/* Form field labels */
.modal-content label {
	display: block;
	margin-top: 10px;
	font-weight: 600;
	color: #a3aab3;
	font-size: 14px;
}

/* Input fields (smooth style) */
.modal-content input[type="text"], .modal-content input[type="number"],
	.modal-content input[type="hidden"] {
	width: 100%;
	padding: 12px;
	margin-top: 6px;
	background-color: #1e1e2f;
	color: #ffffff;
	border: 1px solid #444;
	border-radius: 8px;
	font-size: 14px;
	transition: border-color 0.3s;
}

/* Focus on input */
.modal-content input[type="text"]:focus, .modal-content input[type="number"]:focus
	{
	outline: none;
	border-color: #11daaa; /* Highlight focus */
}

/* Submit button */
.modal-content button[type="submit"] {
	margin-top: 20px;
	background-color: #11daaa;
	color: #000;
	border: none;
	padding: 14px;
	width: 100%;
	border-radius: 8px;
	cursor: pointer;
	font-weight: bold;
	font-size: 16px;
	transition: background-color 0.3s ease, transform 0.2s ease;
}

/* Hover effect on submit button */
.modal-content button[type="submit"]:hover {
	background-color: #0fb190;
	transform: scale(1.05);
}

/* Close button */
.close-btn {
	position: absolute;
	top: 16px;
	right: 16px;
	cursor: pointer;
	font-size: 28px;
	color: #ffffff;
	opacity: 0.7;
	transition: opacity 0.3s;
}

.close-btn:hover {
	opacity: 1;
}

/* Animations */
@
keyframes fadeIn {from { background:rgba(0, 0, 0, 0);
	
}

to {
	background: rgba(0, 0, 0, 0.6);
}

}
@
keyframes scaleIn {from { transform:scale(0.95);
	opacity: 0;
}

to {
	transform: scale(1);
	opacity: 1;
}
}
</style>
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
					<button class="add-product-btn">
						<a href="${pageContext.request.contextPath}/addproduct">Add
							Product</a>
					</button>
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
									<input type="number" class="stock-input"
										value="${product.stock}" min="0">
								</td>
								<td>
									<input type="hidden" class="product-id" value="${product.id}" />
									<button class="action-btn edit-btn" style="width: 75px;">Edit</button>
									<form method="post"
										action="${pageContext.request.contextPath}/products"
										style="margin-top: 5px;">
										<input type="hidden" name="action" value="delete" />
										<input type="hidden" name="id" value="${product.id}" />
										<button type="submit" class="action-btn delete-btn">Delete</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</main>
	</div>
	<!-- Edit Product Modal -->
	<div id="editModal" class="modal" style="display: none;">
		<div class="modal-content">
			<h2>Edit Product</h2>
			<form id="editProductForm"
				action="${pageContext.request.contextPath}/products" method="post"
				action="${pageContext.request.contextPath}/updateproduct">
				<input type="hidden" name="id" id="edit-id">
				<label>Name:</label>
				<input type="text" name="name" id="edit-name" required>
				<br>
				<label>Brand:</label>
				<input type="text" name="brand" id="edit-brand" required>
				<br>
				<label>Category:</label>
				<input type="text" name="category" id="edit-category" required>
				<br>
				<label>Price:</label>
				<input type="number" name="price" id="edit-price" step="0.01"
					required>
				<br>
				<label>Stock:</label>
				<input type="number" name="stock" id="edit-stock" required>
				<br>
				<button type="submit">Save Changes</button>
			</form>
		</div>
	</div>
</body>
<script>
const modal = document.getElementById("editModal");

// Function to close the modal
function closeModal() {
  modal.style.display = "none";
}

// Close the modal when clicking outside the modal content
modal.addEventListener("click", function(event) {
  // Check if the clicked target is the modal overlay (not the modal content)
  if (event.target === modal) {
    closeModal();
  }
});

  // Attach event listeners after DOM loads
  document.addEventListener("DOMContentLoaded", () => {
    const editButtons = document.querySelectorAll(".edit-btn");
    editButtons.forEach((button, index) => {
      button.addEventListener("click", () => {
        const row = button.closest("tr");
        const id = row.querySelector(".product-id").value;
        const name = row.children[1].innerText;
        const brand = row.children[2].innerText;
        const category = row.children[3].innerText;
        const price = row.children[4].innerText.replace('$', '');
        const stock = row.querySelector(".stock-input").value;

        document.getElementById("edit-id").value = id;
        document.getElementById("edit-name").value = name;
        document.getElementById("edit-brand").value = brand;
        document.getElementById("edit-category").value = category;
        document.getElementById("edit-price").value = price;
        document.getElementById("edit-stock").value = stock;

        document.getElementById("editModal").style.display = "block";
      });
    });
  });
</script>

</html>
