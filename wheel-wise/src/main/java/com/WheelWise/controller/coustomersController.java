package com.WheelWise.controller;

import java.io.IOException;
import java.util.List;

import com.WheelWise.model.UserModel;
import com.WheelWise.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * coustomersController handles requests to view the list of all customers. It
 * fetches all users from the UserService and forwards the data to the customers
 * JSP page for display.
 */
@WebServlet("/coustomers")
public class coustomersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to the /coustomers URL. Retrieves all users/customers
	 * and sets them as a request attribute. Forwards the request to the admin
	 * coustomers JSP page to display the list.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Instantiate UserService to interact with the user data
		UserService userService = new UserService();

		// Retrieve the list of all users/customers
		List<UserModel> customers = userService.getAllUsers();

		// Set the retrieved list as a request attribute for JSP access
		request.setAttribute("customers", customers);

		// Forward the request and response to the admin coustomers JSP page
		request.getRequestDispatcher("WEB-INF/pages/admin/coustomers.jsp").forward(request, response);
	}
}
