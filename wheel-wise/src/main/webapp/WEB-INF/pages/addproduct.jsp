<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add Product</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f5f5f5;
			padding: 40px;
		}

		form {
			background-color: #ffffff;
			padding: 30px;
			border-radius: 10px;
			box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
			max-width: 500px;
			margin: auto;
		}

		form span {
			display: block;
			margin-bottom: 20px;
		}

		label {
			display: block;
			margin-bottom: 6px;
			font-weight: bold;
			color: #333333;
		}

		.input {
			width: 100%;
			padding: 10px;
			border: 1px solid #cccccc;
			border-radius: 6px;
			font-size: 16px;
			box-sizing: border-box;
		}

		.submit-button {
			background-color: #007bff;
			color: white;
			border: none;
			padding: 12px 20px;
			border-radius: 6px;
			font-size: 16px;
			cursor: pointer;
			transition: background-color 0.3s ease;
		}

		.submit-button:hover {
			background-color: #0056b3;
		}
	</style>
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