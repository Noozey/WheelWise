package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.Order;
import com.WheelWise.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class profileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public profileController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int userId = (int) request.getSession().getAttribute("userId");

		try (Connection dbConn = DbConfig.getDbConnection()) {
			if (dbConn == null) {
				System.err.println("Database connection is not available.");
				request.setAttribute("error", "Unable to connect to database.");
				request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
				return;
			}

			// Load profile info
			String selectQuery = "SELECT * FROM User WHERE user_id = ?";
			try (PreparedStatement selectStmt = dbConn.prepareStatement(selectQuery)) {
				selectStmt.setInt(1, userId);
				ResultSet rs = selectStmt.executeQuery();

				if (rs.next()) {
					request.setAttribute("firstName", rs.getString("first_name"));
					request.setAttribute("lastName", rs.getString("last_name"));
					request.setAttribute("username", rs.getString("username"));
					request.setAttribute("dob", rs.getDate("dob"));
					request.setAttribute("gender", rs.getString("gender"));
					request.setAttribute("email", rs.getString("email"));
					request.setAttribute("phoneNumber", rs.getString("number"));
					request.setAttribute("password", rs.getString("password"));
					request.setAttribute("imagePath", rs.getString("image_path"));
				} else {
					request.setAttribute("error", "User not found.");
				}
			}

			// Load user orders
			List<Order> orders = new ArrayList<>();

			String orderQuery = """
											SELECT
					  o.order_id,
					  u.first_name,
					  u.last_name,
					  o.order_date,
					  o.delivery_status AS status,
					  SUM(p.price * op.quantity) AS total
					FROM `order` o
					JOIN `user` u ON o.user_id = u.user_id
					JOIN order_product op ON o.order_id = op.order_id
					JOIN product p ON op.product_id = p.product_id
					WHERE o.user_id = ?
					GROUP BY o.order_id, u.first_name, u.last_name, o.order_date, o.delivery_status
					ORDER BY o.order_date DESC
										""";

			try (PreparedStatement orderStmt = dbConn.prepareStatement(orderQuery)) {
				orderStmt.setInt(1, userId);
				ResultSet orderRs = orderStmt.executeQuery();

				while (orderRs.next()) {
					Order order = new Order();
					order.setOrderId(orderRs.getInt("order_id"));
					order.setCustomerName(orderRs.getString("first_name") + " " + orderRs.getString("last_name"));
					order.setOrderDate(orderRs.getDate("order_date"));
					order.setStatus(orderRs.getString("status"));
					order.setTotal(orderRs.getDouble("total"));
					orders.add(order);
				}
				request.setAttribute("orders", orders);
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("error", "Error retrieving user data.");
		}

		request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");

		if (firstName == null || firstName.trim().isEmpty()) {
			request.setAttribute("error", "First name cannot be empty.");
			request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
			return;
		}

		if (password != null && !password.trim().isEmpty()) {
			password = PasswordUtil.encrypt(username, password);
		}

		int userId = (int) request.getSession().getAttribute("userId");

		try (Connection dbConn = DbConfig.getDbConnection()) {
			if (dbConn == null) {
				System.err.println("Database connection is not available.");
				request.setAttribute("error", "Unable to connect to database.");
				request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
				return;
			}

			String updateQuery = "UPDATE User SET first_name=?, last_name=?, username=?, dob=?, gender=?, email=?, number=?, password=? WHERE user_id=?";
			try (PreparedStatement updateStmt = dbConn.prepareStatement(updateQuery)) {
				updateStmt.setString(1, firstName);
				updateStmt.setString(2, lastName);
				updateStmt.setString(3, username);
				updateStmt.setString(4, dob);
				updateStmt.setString(5, gender);
				updateStmt.setString(6, email);
				updateStmt.setString(7, phoneNumber);
				updateStmt.setString(8, password);
				updateStmt.setInt(9, userId);

				int rowsAffected = updateStmt.executeUpdate();
				if (rowsAffected > 0) {
					request.setAttribute("message", "Profile updated successfully.");
				} else {
					request.setAttribute("error", "Failed to update profile.");
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("error", "Error updating user data.");
		}

		doGet(request, response); // show updated data
	}
}
