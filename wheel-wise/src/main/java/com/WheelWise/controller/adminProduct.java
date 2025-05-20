package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.ProductModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * adminProduct handles requests related to viewing, updating, and deleting
 * products in the admin dashboard.
 */
@WebServlet("/products")
public class adminProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to display all products in the admin dashboard.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sql = "SELECT * FROM product"; // SQL query to fetch all products

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();

			List<ProductModel> productList = new ArrayList<>();

			// Loop through result set and create ProductModel objects for each product
			while (rs.next()) {
				ProductModel product = new ProductModel();
				product.setId(rs.getInt("product_id"));
				product.setName(rs.getString("product_name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getDouble("price"));
				product.setBrand(rs.getString("brand"));
				product.setImageUrl(rs.getString("product_image"));
				product.setStock(rs.getInt("stock"));

				productList.add(product); // Add each product to the list
			}

			// Pass the product list to the request scope to be used in the JSP
			request.setAttribute("productList", productList);

		} catch (Exception e) {
			// Handle and log database errors
			e.printStackTrace();
			request.setAttribute("message", "Database error: " + e.getMessage());
		}

		// Forward the request to the admin product listing JSP page
		request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for updating or deleting a product. It checks the
	 * "action" parameter to determine the desired operation.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("delete".equals(action)) {
			// If action is "delete", perform deletion
			deleteProduct(request, response);
		} else {
			// Otherwise, treat it as an update
			updateProduct(request, response);
		}
	}

	/**
	 * Deletes a product from the database. This includes first deleting related
	 * entries in the order_product table to maintain referential integrity.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		String deleteOrderProductSql = "DELETE FROM order_product WHERE product_id = ?";
		String deleteProductSql = "DELETE FROM product WHERE product_id = ?";

		try (Connection conn = DbConfig.getDbConnection()) {

			// Step 1: Delete product references in order_product table
			try (PreparedStatement stmt1 = conn.prepareStatement(deleteOrderProductSql)) {
				stmt1.setInt(1, id);
				stmt1.executeUpdate();
			}

			// Step 2: Delete the actual product
			try (PreparedStatement stmt2 = conn.prepareStatement(deleteProductSql)) {
				stmt2.setInt(1, id);
				int rowsDeleted = stmt2.executeUpdate();

				if (rowsDeleted > 0) {
					// Product deleted successfully, redirect back to product list
					response.sendRedirect(request.getContextPath() + "/products");
				} else {
					// No rows affected, product deletion failed
					request.setAttribute("message", "Product deletion failed.");
					request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
				}
			}

		} catch (Exception e) {
			// Handle exceptions and display error message
			e.printStackTrace();
			request.setAttribute("message", "Database error: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
		}
	}

	/**
	 * Updates product details in the database with the submitted form values.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void updateProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Retrieve form inputs
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String brand = request.getParameter("brand");
		String category = request.getParameter("category");
		double price = Double.parseDouble(request.getParameter("price"));
		int stock = Integer.parseInt(request.getParameter("stock"));

		// SQL query to update the product details
		String sql = "UPDATE product SET product_name = ?, brand = ?, category = ?, price = ?, stock = ? WHERE product_id = ?";

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			// Set the new product data in the query
			stmt.setString(1, name);
			stmt.setString(2, brand);
			stmt.setString(3, category);
			stmt.setDouble(4, price);
			stmt.setInt(5, stock);
			stmt.setInt(6, id);

			int rowsUpdated = stmt.executeUpdate();

			if (rowsUpdated > 0) {
				// Update successful, redirect to product list
				response.sendRedirect(request.getContextPath() + "/products");
			} else {
				// Update failed, show error message
				request.setAttribute("message", "Product update failed.");
				request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// Handle and log any exceptions
			e.printStackTrace();
			request.setAttribute("message", "Database error: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
		}
	}
}
