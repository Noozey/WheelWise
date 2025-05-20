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
import com.WheelWise.util.ImageUtil;
import com.WheelWise.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * profileController manages the user profile operations including viewing and
 * updating profile information and displaying user orders. It handles file
 * uploads for profile images.
 */
@WebServlet("/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB before writing file to disk
		maxFileSize = 1024 * 1024 * 10, // Maximum allowed file size (10MB)
		maxRequestSize = 1024 * 1024 * 50 // Maximum request size (50MB)
)
public class profileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ImageUtil imageUtil = new ImageUtil();

	/**
	 * Handles GET requests to display the user profile page. Retrieves user details
	 * and user's orders from the database and forwards to profile.jsp.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve current user's ID from session
		int userId = (int) request.getSession().getAttribute("userId");

		try (Connection dbConn = DbConfig.getDbConnection()) {
			if (dbConn == null) {
				// Handle DB connection failure
				request.setAttribute("error", "Unable to connect to database.");
				request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
				return;
			}

			// Query to fetch user details based on user ID
			String selectQuery = "SELECT * FROM User WHERE user_id = ?";
			try (PreparedStatement selectStmt = dbConn.prepareStatement(selectQuery)) {
				selectStmt.setInt(1, userId);
				ResultSet rs = selectStmt.executeQuery();

				if (rs.next()) {
					// Set user details as request attributes for the JSP
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
					// If no user found, set error message
					request.setAttribute("error", "User not found.");
				}
			}

			// Prepare to fetch orders placed by the user
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

				// Loop through orders and create Order model objects
				while (orderRs.next()) {
					Order order = new Order();
					order.setOrderId(orderRs.getInt("order_id"));
					order.setCustomerName(orderRs.getString("first_name") + " " + orderRs.getString("last_name"));
					order.setOrderDate(orderRs.getDate("order_date"));
					order.setStatus(orderRs.getString("status"));
					order.setTotal(orderRs.getDouble("total"));
					orders.add(order);
				}
				// Set orders as request attribute
				request.setAttribute("orders", orders);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("error", "Error retrieving user data.");
		}

		// Forward to profile JSP page to display data
		request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to update the user profile. Validates input, handles
	 * optional password encryption, processes profile image upload, and updates
	 * user details in the database.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Ensure UTF-8 encoding for proper character handling
		request.setCharacterEncoding("UTF-8");

		// Retrieve form parameters for profile update
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");

		// Basic validation: first name must not be empty
		if (firstName == null || firstName.trim().isEmpty()) {
			request.setAttribute("error", "First name cannot be empty.");
			request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
			return;
		}

		// Encrypt password if provided, else keep null to preserve existing password
		if (password != null && !password.trim().isEmpty()) {
			password = PasswordUtil.encrypt(username, password);
		} else {
			password = null;
		}

		// Retrieve current user's ID from session
		int userId = (int) request.getSession().getAttribute("userId");

		// Handle profile image upload
		Part filePart = request.getPart("profileImage");
		String imageName = null;

		if (filePart != null && filePart.getSize() > 0) {
			String rootPath = getServletContext().getRealPath("");
			// Upload image using ImageUtil helper
			boolean uploaded = imageUtil.uploadImage(filePart, rootPath, "users");
			if (uploaded) {
				// Get saved image file name
				imageName = imageUtil.getImageNameFromPart(filePart);
			} else {
				// Handle image upload failure
				request.setAttribute("error", "Failed to upload image.");
				request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
				return;
			}
		}

		try (Connection dbConn = DbConfig.getDbConnection()) {
			if (dbConn == null) {
				// Handle DB connection failure
				request.setAttribute("error", "Unable to connect to database.");
				request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
				return;
			}

			// If password was not changed, fetch existing password from DB to keep it
			// unchanged
			if (password == null) {
				String pwdQuery = "SELECT password FROM User WHERE user_id = ?";
				try (PreparedStatement pwdStmt = dbConn.prepareStatement(pwdQuery)) {
					pwdStmt.setInt(1, userId);
					ResultSet rs = pwdStmt.executeQuery();
					if (rs.next()) {
						password = rs.getString("password");
					} else {
						request.setAttribute("error", "User not found.");
						request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
						return;
					}
				}
			}

			// Build SQL UPDATE query with optional image path update
			StringBuilder updateQuery = new StringBuilder(
					"UPDATE User SET first_name=?, last_name=?, username=?, dob=?, gender=?, email=?, number=?, password=?");

			if (imageName != null) {
				updateQuery.append(", image_path=?");
			}
			updateQuery.append(" WHERE user_id=?");

			try (PreparedStatement updateStmt = dbConn.prepareStatement(updateQuery.toString())) {
				updateStmt.setString(1, firstName);
				updateStmt.setString(2, lastName);
				updateStmt.setString(3, username);
				updateStmt.setString(4, dob);
				updateStmt.setString(5, gender);
				updateStmt.setString(6, email);
				updateStmt.setString(7, phoneNumber);
				updateStmt.setString(8, password);

				if (imageName != null) {
					updateStmt.setString(9, imageName);
					updateStmt.setInt(10, userId);
				} else {
					updateStmt.setInt(9, userId);
				}

				// Execute update and check if successful
				int rowsUpdated = updateStmt.executeUpdate();
				if (rowsUpdated > 0) {
					request.setAttribute("success", "Profile updated successfully.");
				} else {
					request.setAttribute("error", "Failed to update profile.");
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("error", "Database error while updating profile.");
		}

		// After processing, reload profile page with updated data
		doGet(request, response);
	}
}
