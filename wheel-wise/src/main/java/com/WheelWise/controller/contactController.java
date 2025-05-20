package com.WheelWise.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * contactController handles requests for the Contact Us page. It serves the
 * contact.jsp page on GET requests. Currently, POST requests are forwarded to
 * the GET handler.
 */
@WebServlet("/contact")
public class contactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public contactController() {
		super();
		// Constructor stub - no additional initialization required.
	}

	/**
	 * Handles GET requests to the contact page. Forwards the request to the
	 * contact.jsp page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward the request to the contact.jsp view page
		request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests by delegating to the doGet method. This means form
	 * submissions or other POST requests will simply display the contact page
	 * again.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Delegate POST handling to doGet to show the same contact page
		doGet(request, response);
	}
}
