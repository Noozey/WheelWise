package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * PlaceOrderController handles placing an order by the user. It processes the
 * cart items stored in session, creates invoice and order records, inserts
 * order products, and commits the transaction.
 */
@WebServlet("/placeorder")
public class PlaceOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles POST requests for placing an order. Validates cart and user session,
	 * calculates total, creates invoice and order entries, inserts products,
	 * commits transaction, and forwards to confirmation page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Retrieve cart and userId from session
		HttpSession session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		Integer userId = (Integer) session.getAttribute("userId");

		// Redirect to cart page if cart is empty or user is not logged in
		if (cart == null || cart.isEmpty() || userId == null) {
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}

		// Get payment method from request
		String payment = request.getParameter("payment");

		// Calculate total amount from cart items (price * quantity)
		double totalAmount = cart.stream().mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum();

		// For simplicity, assume no discount currently
		double discount = 0.0;
		double netAmount = totalAmount - discount;

		try (Connection conn = DbConfig.getDbConnection()) {
			// Disable auto-commit for transaction management
			conn.setAutoCommit(false);

			// 1. Insert invoice record with total, discount, net amount, and payment type
			String invoiceSql = "INSERT INTO invoice (invoice_date, total_amount, discount_amount, net_amount, payment_type) VALUES (CURDATE(), ?, ?, ?, ?)";
			PreparedStatement invoiceStmt = conn.prepareStatement(invoiceSql, Statement.RETURN_GENERATED_KEYS);
			invoiceStmt.setDouble(1, totalAmount);
			invoiceStmt.setDouble(2, discount);
			invoiceStmt.setDouble(3, netAmount);
			invoiceStmt.setString(4, payment);
			invoiceStmt.executeUpdate();

			// Retrieve generated invoice ID
			ResultSet invoiceRs = invoiceStmt.getGeneratedKeys();
			if (!invoiceRs.next()) {
				throw new SQLException("Invoice ID not generated.");
			}
			int invoiceId = invoiceRs.getInt(1);

			// 2. Insert order record linked to invoice and user
			String orderSql = "INSERT INTO `order` (order_date, invoice_id, user_id) VALUES (CURDATE(), ?, ?)";
			PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
			orderStmt.setInt(1, invoiceId);
			orderStmt.setInt(2, userId);
			orderStmt.executeUpdate();

			// Retrieve generated order ID
			ResultSet orderRs = orderStmt.getGeneratedKeys();
			if (!orderRs.next()) {
				throw new SQLException("Order ID not generated.");
			}
			int orderId = orderRs.getInt(1);

			// 3. Insert each cart item into order_product linking order and product with
			// quantity
			String productSql = "INSERT INTO order_product (order_id, product_id, quantity) VALUES (?, ?, ?)";
			PreparedStatement itemStmt = conn.prepareStatement(productSql);
			for (CartItem item : cart) {
				itemStmt.setInt(1, orderId);
				itemStmt.setInt(2, item.getProduct().getId());
				itemStmt.setInt(3, item.getQuantity());
				itemStmt.addBatch();
			}
			itemStmt.executeBatch();

			// Commit transaction after successful inserts
			conn.commit();

			// Clear cart from session after order placement
			session.removeAttribute("cart");

			// Forward to confirmation page with orderId attribute
			request.setAttribute("orderId", orderId);
			request.getRequestDispatcher("/WEB-INF/pages/confirmation.jsp").forward(request, response);

		} catch (Exception e) {
			// Handle exceptions by logging and forwarding back to checkout page with error
			// message
			e.printStackTrace();
			request.setAttribute("error", "Order failed: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
		}
	}
}
