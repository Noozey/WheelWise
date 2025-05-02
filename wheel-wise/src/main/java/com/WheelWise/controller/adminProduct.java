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
 * Servlet implementation class adminProduct
 */
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

				productList.add(product);
			}

			// Pass the list of products to the request scope
			request.setAttribute("productList", productList);
			System.out.print("hello" + productList);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Database error: " + e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/pages/admin/products.jsp").forward(request, response);
	}
}
