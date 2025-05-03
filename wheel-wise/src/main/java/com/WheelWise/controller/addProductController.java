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

@WebServlet("/addproduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class addProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ImageUtil imageUtil = new ImageUtil();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String productName = req.getParameter("productname");
		String category = req.getParameter("category");
		String priceStr = req.getParameter("price");
		String brand = req.getParameter("brand");
		Part productImage = req.getPart("product_image");

		double price = 0.0;

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

		// Upload image using ImageUtil
		String imagePath = imageUtil.getImageNameFromPart(productImage);
		boolean isUploaded = imageUtil.uploadImage(productImage,
				"/Users/nooze/eclipse-workspace/wheel-wise/src/main/webapp", "product");

		if (!isUploaded) {
			req.setAttribute("message", "Failed to upload product image.");
			req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, response);
			return;
		}

		// Insert product into database
		String sql = "INSERT INTO product (product_name, category, price, brand, product_image) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, productName);
			stmt.setString(2, category);
			stmt.setDouble(3, price);
			stmt.setString(4, brand);
			stmt.setString(5, imagePath);

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

		req.getRequestDispatcher("/WEB-INF/pages/admin/addproduct.jsp").forward(req, response);
	}
}
