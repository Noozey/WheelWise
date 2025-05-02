package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.CartItem;
import com.WheelWise.model.ProductModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class cartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

		if (cart == null) {
			cart = new ArrayList<>();
		}

		try {
			int productId = Integer.parseInt(request.getParameter("productId"));

			if ("add".equals(action)) {
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				try (Connection conn = DbConfig.getDbConnection()) {
					String sql = "SELECT * FROM product WHERE product_id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, productId);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						ProductModel p = new ProductModel();
						p.setId(productId);
						p.setName(rs.getString("product_name"));
						p.setBrand(rs.getString("brand"));
						p.setCategory(rs.getString("category"));
						p.setPrice(rs.getDouble("price"));
						p.setImageUrl(rs.getString("product_image"));

						boolean found = false;
						for (CartItem item : cart) {
							if (item.getProduct().getId() == productId) {
								item.setQuantity(item.getQuantity() + quantity);
								found = true;
								break;
							}
						}

						if (!found)
							cart.add(new CartItem(p, quantity));
					}
				}

			} else if ("remove".equals(action)) {
				cart.removeIf(item -> item.getProduct().getId() == productId);

			} else if ("update".equals(action)) {
				int newQuantity = Integer.parseInt(request.getParameter("quantity"));
				for (CartItem item : cart) {
					if (item.getProduct().getId() == productId) {
						item.setQuantity(newQuantity);
						break;
					}
				}

			} else if ("buy-now".equals(action)) {
				// When 'Buy Now' is pressed, add the product to the cart and redirect to
				// checkout
				int quantity = 1; // You can set the quantity to 1 or get it from the request if needed
				try (Connection conn = DbConfig.getDbConnection()) {
					String sql = "SELECT * FROM product WHERE product_id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, productId);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						ProductModel p = new ProductModel();
						p.setId(productId);
						p.setName(rs.getString("product_name"));
						p.setBrand(rs.getString("brand"));
						p.setCategory(rs.getString("category"));
						p.setPrice(rs.getDouble("price"));
						p.setImageUrl(rs.getString("product_image"));

						// Add the product to the cart (if not already in cart)
						boolean found = false;
						for (CartItem item : cart) {
							if (item.getProduct().getId() == productId) {
								item.setQuantity(item.getQuantity() + quantity);
								found = true;
								break;
							}
						}

						if (!found)
							cart.add(new CartItem(p, quantity));

						// Proceed to checkout page
						session.setAttribute("cart", cart);
						response.sendRedirect(request.getContextPath() + "/checkout");
						return; // Prevent further processing
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Update the session cart after all actions
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/cart");
	}
}