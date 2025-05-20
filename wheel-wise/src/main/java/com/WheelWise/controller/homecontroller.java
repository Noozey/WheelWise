package com.WheelWise.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * homecontroller handles requests to the home page. It supports both "/" and
 * "/home" URLs and forwards requests to the home.jsp view.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home", "/" })
public class homecontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public homecontroller() {
		super();
		// Default constructor stub
	}

	/**
	 * Handles GET requests to the home page URLs. Forwards the request to the
	 * home.jsp page for rendering.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests by delegating to doGet method.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
