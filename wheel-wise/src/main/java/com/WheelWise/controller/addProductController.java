package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.WheelWise.config.DbConfig;
import com.WheelWise.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * addProductController handles requests to add a new product. It supports image
 * upload, input validation, and inserts data into the database.
 */
@WebServlet("/addproduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold before file is written to disk
		maxFileSize = 1024 * 1024 * 10, // Maximum file size of 10MB
		maxRequestSize = 1024 * 1024 * 50 // Maximum request size of 50MB
)
public class addProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ImageUtil imageUtil = new ImageUtil();

	/**
	 * Handles GET requests and displays the add product form.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to add a new product. It performs validation, uploads
	 * image, and saves product info to the database.
	 *
	 * @param req      HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		// Extract form parameters
		String productName = req.getParameter("productname");
		String category = req.getParameter("category");
		String priceStr = req.getParameter("price");
		String brand = req.getParameter("brand");
		Part productImage = req.getPart("product_image");

		double price = 0.0;

		// Validate price input
		if (priceStr != null && !priceStr.trim().isEmpty()) {
			try {
				price = Double.parseDouble(priceStr.trim());
			} catch (NumberFormatException e) {
				req.setAttribute("message", "Invalid price format.");
				req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, response);
				return;
			}
		} else {
			req.setAttribute("message", "Price is required.");
			req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, response);
			return;
		}

		// Handle image upload using ImageUtil
		String imagePath = imageUtil.getImageNameFromPart(productImage);
		boolean isUploaded = imageUtil.uploadImage(productImage,
				"/Users/nooze/eclipse-workspace/wheel-wise/src/main/webapp", "product");

		if (!isUploaded) {
			req.setAttribute("message", "Failed to upload product image.");
			req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, response);
			return;
		}

		// Prepare SQL statement to insert product data
		String sql = "INSERT INTO product (product_name, category, price, brand, product_image, stock) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, productName);
			stmt.setString(2, category);
			stmt.setDouble(3, price);
			stmt.setString(4, brand);
			stmt.setString(5, imagePath);
			stmt.setInt(6, 5); // Default stock value set to 5

			int rows = stmt.executeUpdate();

			if (rows > 0) {
				req.setAttribute("message", "Product added successfully!");
			} else {
				req.setAttribute("message", "Failed to add product.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "Database error: " + e.getMessage());
		}

		// Forward back to the add product form with a message
		req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, response);
	}
}
