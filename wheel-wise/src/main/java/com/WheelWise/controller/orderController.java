package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * orderController handles HTTP requests related to orders. It supports
 * retrieving the list of orders (GET) and updating the delivery status of an
 * order (POST).
 */
@WebServlet("/order")
public class orderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to fetch all orders. Retrieves order details joined with
	 * customer and invoice info, then forwards the data to the order JSP page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// List to hold retrieved orders
		List<Order> orders = new ArrayList<>();

		// SQL query to fetch order info with customer name and invoice amount
		String sql = """
				    SELECT o.order_id, o.order_date, o.delivery_status,
				           CONCAT(u.first_name, ' ', u.last_name) AS customer_name,
				           i.net_amount
				    FROM `order` o
				    JOIN `user` u ON o.user_id = u.user_id
				    JOIN `invoice` i ON o.invoice_id = i.invoice_id
				    ORDER BY o.order_date DESC
				""";

		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			// Iterate over the result set and populate the orders list
			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setCustomerName(rs.getString("customer_name"));
				order.setStatus(rs.getString("delivery_status"));
				order.setTotal(rs.getDouble("net_amount"));
				orders.add(order);
			}

			// Set orders list as a request attribute and forward to JSP page for rendering
			request.setAttribute("orders", orders);
			request.getRequestDispatcher("/WEB-INF/pages/admin/order.jsp").forward(request, response);

		} catch (Exception e) {
			// Log exception and send error response if something goes wrong
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving order data");
		}
	}

	/**
	 * Handles POST requests to update the delivery status of an order. Expects
	 * 'orderId' and 'newStatus' parameters from the request.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String orderId = request.getParameter("orderId");
		String newStatus = request.getParameter("newStatus");

		// Validate parameters
		if (orderId != null && newStatus != null) {
			String sql = "UPDATE `order` SET delivery_status = ? WHERE order_id = ?";

			try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

				// Set parameters and execute update query
				stmt.setString(1, newStatus);
				stmt.setInt(2, Integer.parseInt(orderId));

				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					// On successful update, redirect back to the orders page
					response.sendRedirect(request.getContextPath() + "/order");
				} else {
					// If no rows were updated, send an internal server error response
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating order status");
				}
			} catch (Exception e) {
				// Log exception and send error response
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating order status");
			}
		} else {
			// If parameters are invalid, send bad request error
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID or status");
		}
	}
}
