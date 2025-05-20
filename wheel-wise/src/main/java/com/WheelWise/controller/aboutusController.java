package com.WheelWise.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * aboutusController is responsible for handling requests to the About Us page.
 * It simply forwards the request to the corresponding JSP view.
 */
@WebServlet("/about")
public class aboutusController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for aboutusController.
	 */
	public aboutusController() {
		super();
	}

	/**
	 * Handles GET requests to the About Us page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward the request to the aboutus.jsp view
		request.getRequestDispatcher("/WEB-INF/pages/aboutus.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests by delegating to doGet. This ensures that POST requests
	 * to /about behave the same as GET requests.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
