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

@WebServlet("/products")
public class adminProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sql = "SELECT * FROM product"; // Select all products

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();

			List<ProductModel> productList = new ArrayList<>();
			while (rs.next()) {
				ProductModel product = new ProductModel();
				product.setId(rs.getInt("product_id"));
				product.setName(rs.getString("product_name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getDouble("price"));
				product.setBrand(rs.getString("brand"));
				product.setImageUrl(rs.getString("product_image"));
				product.setStock(rs.getInt("stock"));

				productList.add(product);
			}

			// Pass the list of products to the request scope
			request.setAttribute("productList", productList);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Database error: " + e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("delete".equals(action)) {
			// If action is "delete", call deleteProduct method
			deleteProduct(request, response);
		} else {
			// If action is not "delete", handle product update
			updateProduct(request, response);
		}
	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		// Start by deleting the dependent records in order_product
		String deleteOrderProductSql = "DELETE FROM order_product WHERE product_id = ?";
		String deleteProductSql = "DELETE FROM product WHERE product_id = ?";

		try (Connection conn = DbConfig.getDbConnection()) {
			// Delete dependent records from order_product first
			try (PreparedStatement stmt1 = conn.prepareStatement(deleteOrderProductSql)) {
				stmt1.setInt(1, id);
				stmt1.executeUpdate();
			}

			// Now delete the product from the product table
			try (PreparedStatement stmt2 = conn.prepareStatement(deleteProductSql)) {
				stmt2.setInt(1, id);
				int rowsDeleted = stmt2.executeUpdate();
				if (rowsDeleted > 0) {
					// Redirect to products page or reload the product list
					response.sendRedirect(request.getContextPath() + "/products");
				} else {
					// Show an error message if deletion fails
					request.setAttribute("message", "Product deletion failed.");
					request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Database error: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
		}
	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String brand = request.getParameter("brand");
		String category = request.getParameter("category");
		double price = Double.parseDouble(request.getParameter("price"));
		int stock = Integer.parseInt(request.getParameter("stock"));

		String sql = "UPDATE product SET product_name = ?, brand = ?, category = ?, price = ?, stock = ? WHERE product_id = ?";

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, name);
			stmt.setString(2, brand);
			stmt.setString(3, category);
			stmt.setDouble(4, price);
			stmt.setInt(5, stock);
			stmt.setInt(6, id);

			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				// Redirect to products page or reload the product list
				response.sendRedirect(request.getContextPath() + "/products");
			} else {
				request.setAttribute("message", "Product update failed.");
				request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Database error: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
		}
	}
}
