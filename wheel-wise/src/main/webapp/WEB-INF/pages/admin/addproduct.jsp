<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add Product</title>
	<style>

body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background-color: #0e0e0e;
	padding: 40px;
	color: #ffffff;
}

form {
	background-color: #1e1e1e;
	padding: 40px;
	border-radius: 12px;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.3);
	max-width: 900px;
	margin: auto;
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 20px;
}

form h2 {
	grid-column: span 2;
	color: #ffffff;
	font-size: 28px;
	margin-bottom: 20px;
}

form span {
	display: block;
	grid-column: span 2;
	margin-bottom: 20px;
	color: #cccccc;
}

label {
	display: block;
	margin-bottom: 6px;
	font-weight: 600;
	color: #dddddd;
}

.input {
	width: 100%;
	padding: 12px;
	border: 1px solid #444444;
	border-radius: 6px;
	background-color: #2a2a2a;
	color: #ffffff;
	font-size: 15px;
	box-sizing: border-box;
}

.input:focus {
	border-color: #8a2be2;
	outline: none;
	box-shadow: 0 0 6px rgba(138, 43, 226, 0.5);
}

.submit-button {
	grid-column: span 2;
	background-color: #8a2be2;
	color: white;
	border: none;
	padding: 18px 0;
	border-radius: 10px;
	font-size: 17px;
	cursor: pointer;
	transition: background-color 0.3s ease;
    width: 80%;
    justify-self: center;
}

.submit-button:hover {
	background-color: #6a1bbf;
}

input[type="file"] {
	background-color: transparent;
	color: #ffffff;
}

::file-selector-button {
	background-color: #8a2be2;
	color: white;
	border: none;
	padding: 8px 16px;
	border-radius: 6px;
	cursor: pointer;
	margin-right: 10px;
	transition: background-color 0.3s ease;
}

::file-selector-button:hover {
	background-color: #6a1bbf;
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