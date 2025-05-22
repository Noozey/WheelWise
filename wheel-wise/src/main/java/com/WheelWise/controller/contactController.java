package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.WheelWise.config.DbConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * contactController handles requests related to the contact page. It supports
 * displaying the contact form (GET) and processing form submissions (POST) by
 * saving the submitted data into the database.
 */
@WebServlet("/contact")
public class contactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for the contactController servlet.
	 */
	public contactController() {
		super();
	}

	/**
	 * Handles GET requests to display the contact form. Forwards the request to the
	 * contact JSP page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward the request to the contact.jsp page
		request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for form submission from the contact page. Retrieves
	 * form data, inserts it into the contact_messages table, and forwards back to
	 * the contact page with a success or error message.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get form data from the request
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");

		// Insert the form data into the database
		try (Connection conn = DbConfig.getDbConnection()) {
			String sql = "INSERT INTO contact_messages (name, email, phone, subject, message) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, phone);
			stmt.setString(4, subject);
			stmt.setString(5, message);

			int rowsInserted = stmt.executeUpdate();

			// Set success or error message based on the result
			if (rowsInserted > 0) {
				request.setAttribute("success", "Thank you! Your message has been sent.");
			} else {
				request.setAttribute("error", "Oops! Something went wrong.");
			}
		} catch (SQLException e) {
			// Handle SQL exceptions and provide feedback to the user
			e.printStackTrace();
			request.setAttribute("error", "Database error: " + e.getMessage());
		} catch (ClassNotFoundException e1) {
			// Handle missing JDBC driver exception
			e1.printStackTrace();
		}

		// Forward back to the contact.jsp page with a response message
		request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
	}
}
