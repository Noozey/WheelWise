package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.ContactMessage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * adminMessageController handles requests to the /messages URL. It retrieves
 * all contact messages submitted through the website's contact form, and
 * forwards the data to the admin message JSP page for display.
 */
@SuppressWarnings("serial")
@WebServlet("/messages")
public class adminMessageController extends HttpServlet {

	/**
	 * Handles GET requests to the /messages endpoint. Connects to the database,
	 * retrieves all contact messages sorted by submission time in descending order,
	 * and forwards the messages to the admin JSP view for rendering.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Create a list to hold retrieved contact messages
		List<ContactMessage> messages = new ArrayList<>();

		try (Connection conn = DbConfig.getDbConnection()) {

			// Prepare SQL query to fetch all messages ordered by submission time (latest
			// first)
			String sql = "SELECT * FROM contact_messages ORDER BY submitted_at DESC";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			// Iterate through the result set and map each row to a ContactMessage object
			while (rs.next()) {
				ContactMessage msg = new ContactMessage();
				msg.setId(rs.getInt("id"));
				msg.setName(rs.getString("name"));
				msg.setEmail(rs.getString("email"));
				msg.setPhone(rs.getString("phone"));
				msg.setSubject(rs.getString("subject"));
				msg.setMessage(rs.getString("message"));
				msg.setSubmittedAt(rs.getString("submitted_at"));
				messages.add(msg);
			}

		} catch (SQLException e) {
			// Log SQL exceptions for debugging
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// Log class not found exception if DB driver is missing
			e1.printStackTrace();
		}

		// Set the list of contact messages as a request attribute for the JSP page
		request.setAttribute("messages", messages);

		// Forward the request to the admin message JSP page for rendering
		request.getRequestDispatcher("/WEB-INF/pages/admin/message.jsp").forward(request, response);
	}
}
