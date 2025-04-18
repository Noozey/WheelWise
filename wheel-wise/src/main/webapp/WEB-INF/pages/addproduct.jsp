<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Product</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/addproduct" method="post" enctype="multipart/form-data">
		<span>
			<label>Product-Name</label>
			<input type="text" placeholder="air-filter" class="input"
				id="productname" name="productname" required>
		</span>

		<span>
			<label>Category</label>
			<input type="text" placeholder="engine-parts" class="input"
				id="category" name="category" required>
		</span>

		<span>
			<label>Price</label>
			<input type="number" placeholder="100" class="input" id="price"
				name="price" required>
		</span>

		<span>
			<label>Brand</label>
			<input type="text" placeholder="Bosch" class="input" id="brand"
				name="brand" required>
		</span>

		<span>
			<label>Product Image</label>
			<input type="file" class="input" id="product_image"
				name="product_image" accept="image/*" required>
		</span>
		<span>
			<button type="submit" class="submit-button">Submit</button>
		</span>

	</form>
</body>
</html>