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

/**
 * cartController handles all requests related to the shopping cart. It supports
 * adding, removing, updating cart items and immediate purchase ('buy-now'
 * action). It manages the cart stored in the user session.
 */
@WebServlet("/cart")
public class cartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests by forwarding to the cart.jsp page to display the cart
	 * contents.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for cart operations: - add: adds product(s) to the cart
	 * - remove: removes product from the cart - update: updates the quantity of a
	 * product in the cart - buy-now: adds product to cart and redirects immediately
	 * to checkout
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
		HttpSession session = request.getSession();

		// Retrieve current cart from session or create a new one if none exists
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<>();
		}

		try {
			int productId = Integer.parseInt(request.getParameter("productId"));

			if ("add".equals(action)) {
				// Handle adding product(s) to cart
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				try (Connection conn = DbConfig.getDbConnection()) {
					String sql = "SELECT * FROM product WHERE product_id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, productId);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						// Create product model from database data
						ProductModel p = new ProductModel();
						p.setId(productId);
						p.setName(rs.getString("product_name"));
						p.setBrand(rs.getString("brand"));
						p.setCategory(rs.getString("category"));
						p.setPrice(rs.getDouble("price"));
						p.setImageUrl(rs.getString("product_image"));

						// Check if product is already in cart, update quantity if yes
						boolean found = false;
						for (CartItem item : cart) {
							if (item.getProduct().getId() == productId) {
								item.setQuantity(item.getQuantity() + quantity);
								found = true;
								break;
							}
						}

						// If not found, add new cart item
						if (!found)
							cart.add(new CartItem(p, quantity));
					}
				}

			} else if ("remove".equals(action)) {
				// Remove the product from the cart
				cart.removeIf(item -> item.getProduct().getId() == productId);

			} else if ("update".equals(action)) {
				// Update the quantity of the specified product in the cart
				int newQuantity = Integer.parseInt(request.getParameter("quantity"));
				for (CartItem item : cart) {
					if (item.getProduct().getId() == productId) {
						item.setQuantity(newQuantity);
						break;
					}
				}

			} else if ("buy-now".equals(action)) {
				// Add product to cart and redirect directly to checkout page
				int quantity = 1; // Default quantity for 'buy-now' action
				try (Connection conn = DbConfig.getDbConnection()) {
					String sql = "SELECT * FROM product WHERE product_id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, productId);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						// Create product model from database data
						ProductModel p = new ProductModel();
						p.setId(productId);
						p.setName(rs.getString("product_name"));
						p.setBrand(rs.getString("brand"));
						p.setCategory(rs.getString("category"));
						p.setPrice(rs.getDouble("price"));
						p.setImageUrl(rs.getString("product_image"));

						// Add to cart or update quantity if already exists
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

						// Save updated cart in session and redirect to checkout
						session.setAttribute("cart", cart);
						response.sendRedirect(request.getContextPath() + "/checkout");
						return; // Stop further processing after redirect
					}
				}
			}

		} catch (Exception e) {
			// Log any exceptions for debugging
			e.printStackTrace();
		}

		// Update the cart in session after all actions and redirect back to the cart
		// page
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/cart");
	}
}
