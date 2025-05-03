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

@WebServlet("/order")
public class orderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Order> orders = new ArrayList<>();

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

			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setCustomerName(rs.getString("customer_name"));
				order.setStatus(rs.getString("delivery_status"));
				order.setTotal(rs.getDouble("net_amount"));
				orders.add(order);
			}

			request.setAttribute("orders", orders);
			request.getRequestDispatcher("/WEB-INF/pages/admin/order.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving order data");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderId = request.getParameter("orderId");
		String newStatus = request.getParameter("newStatus");

		if (orderId != null && newStatus != null) {
			String sql = "UPDATE `order` SET delivery_status = ? WHERE order_id = ?";

			try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

				stmt.setString(1, newStatus);
				stmt.setInt(2, Integer.parseInt(orderId));

				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					// If status update is successful, redirect to the order page
					response.sendRedirect(request.getContextPath() + "/order");
				} else {
					// If no rows were affected, send an error response
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating order status");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating order status");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID or status");
		}
	}
}
