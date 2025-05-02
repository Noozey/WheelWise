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

@WebServlet("/placeorder")
public class PlaceOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		Integer userId = (Integer) session.getAttribute("userId");

		if (cart == null || cart.isEmpty() || userId == null) {
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}

		String payment = request.getParameter("payment");
		double totalAmount = cart.stream().mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum();
		double discount = 0.0;
		double netAmount = totalAmount;

		try (Connection conn = DbConfig.getDbConnection()) {
			conn.setAutoCommit(false);

			// 1. Insert into invoice
			String invoiceSql = "INSERT INTO invoice (invoice_date, total_amount, discount_amount, net_amount, payment_type) VALUES (CURDATE(), ?, ?, ?, ?)";
			PreparedStatement invoiceStmt = conn.prepareStatement(invoiceSql, Statement.RETURN_GENERATED_KEYS);
			invoiceStmt.setDouble(1, totalAmount);
			invoiceStmt.setDouble(2, discount);
			invoiceStmt.setDouble(3, netAmount);
			invoiceStmt.setString(4, payment);
			invoiceStmt.executeUpdate();

			ResultSet invoiceRs = invoiceStmt.getGeneratedKeys();
			if (!invoiceRs.next())
				throw new SQLException("Invoice ID not generated.");
			int invoiceId = invoiceRs.getInt(1);

			// 2. Insert into order
			String orderSql = "INSERT INTO `order` (order_date, invoice_id, user_id) VALUES (CURDATE(), ?, ?)";
			PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
			orderStmt.setInt(1, invoiceId);
			orderStmt.setInt(2, userId);
			orderStmt.executeUpdate();

			ResultSet orderRs = orderStmt.getGeneratedKeys();
			if (!orderRs.next())
				throw new SQLException("Order ID not generated.");
			int orderId = orderRs.getInt(1);

			// 3. Insert into order_product
			String productSql = "INSERT INTO order_product (order_id, product_id, quantity) VALUES (?, ?, ?)";
			PreparedStatement itemStmt = conn.prepareStatement(productSql);
			for (CartItem item : cart) {
				itemStmt.setInt(1, orderId);
				itemStmt.setInt(2, item.getProduct().getId());
				itemStmt.setInt(3, item.getQuantity());
				itemStmt.addBatch();
			}
			itemStmt.executeBatch();

			conn.commit();
			session.removeAttribute("cart");

			request.setAttribute("orderId", orderId);
			request.getRequestDispatcher("/WEB-INF/pages/confirmation.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Order failed: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
		}
	}
}
