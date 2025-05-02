package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.ProductModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productInformation")
public class productInformationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public productInformationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String productIdStr = request.getParameter("id");

		if (productIdStr == null || productIdStr.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/product"); // Redirect if no ID is passed
			return;
		}

		try {
			int productId = Integer.parseInt(productIdStr);

			try (Connection conn = DbConfig.getDbConnection();
					PreparedStatement stmt = conn.prepareStatement("SELECT * FROM product WHERE product_id = ?")) {

				stmt.setInt(1, productId);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					ProductModel product = new ProductModel();
					product.setId(rs.getInt("product_id"));
					product.setName(rs.getString("product_name"));
					product.setBrand(rs.getString("brand"));
					product.setCategory(rs.getString("category"));
					product.setPrice(rs.getDouble("price"));
					product.setImageUrl(rs.getString("product_image"));

					request.setAttribute("product", product);
					request.getRequestDispatcher("WEB-INF/pages/productsInformation.jsp").forward(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + "/product"); // Redirect if product not found
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/product");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/product");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
