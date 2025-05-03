package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.Order;
import com.WheelWise.service.AdminDashBoardInformation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admindashboard")
public class adminDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public adminDashboardController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AdminDashBoardInformation service = new AdminDashBoardInformation();

		// Dashboard stats
		int totalOrders = service.getTotalOrders();
		int totalCustomers = service.getTotalCustomers();
		int totalProducts = service.getTotalProducts();
		double totalSales = service.getTotalSales();

		// Set attributes for dashboard stats
		request.setAttribute("totalOrders", totalOrders);
		request.setAttribute("totalCustomers", totalCustomers);
		request.setAttribute("totalProducts", totalProducts);
		request.setAttribute("totalSales", totalSales);

		// Fetch recent 5 orders
		List<Order> recentOrders = new ArrayList<>();
		String sqlRecentOrders = """
				   SELECT o.order_id, o.order_date, o.delivery_status,
				          CONCAT(u.first_name, ' ', u.last_name) AS customer_name,
				          i.net_amount,
				          p.product_name
				   FROM `order` o
				   JOIN `user` u ON o.user_id = u.user_id
				   JOIN `invoice` i ON o.invoice_id = i.invoice_id
				   JOIN `order_product` op ON o.order_id = op.order_id
				   JOIN `product` p ON op.product_id = p.product_id
				   ORDER BY o.order_date DESC
				   LIMIT 5
				""";

		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlRecentOrders);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setCustomerName(rs.getString("customer_name"));
				order.setStatus(rs.getString("delivery_status"));
				order.setTotal(rs.getDouble("net_amount"));
				order.setProductName(rs.getString("product_name"));

				recentOrders.add(order);
			}

			request.setAttribute("recentOrders", recentOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Forward to JSP
		request.getRequestDispatcher("WEB-INF/pages/admin/adminDashboard.jsp").forward(request, response);
	}
}
