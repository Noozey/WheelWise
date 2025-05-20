package com.WheelWise.controller;

import java.io.IOException;

import com.WheelWise.util.CookieUtil;
import com.WheelWise.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * logoutController handles user logout requests by invalidating the session and
 * clearing all cookies, then redirects the user to the home page.
 */
@WebServlet("/logout")
public class logoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public logoutController() {
		super();
	}

	/**
	 * Handles GET requests for logout. Invalidates the current session and removes
	 * all cookies, then redirects to the home page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Invalidate the current user session
		SessionUtil.invalidateSession(request);

		// Retrieve all cookies from the request and delete them
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				CookieUtil.deleteCookie(response, cookie.getName());
			}
		}

		// Redirect the user to the home page after logout
		response.sendRedirect(request.getContextPath() + "/home");
	}

	/**
	 * Handles POST requests by forwarding to doGet.
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
