package com.WheelWise.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * checkout servlet handles requests to the checkout page. It ensures that a
 * cart exists in the user session before forwarding to checkout.jsp. If no cart
 * is found, it redirects the user back to the cart page.
 */
@WebServlet("/checkout")
public class checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests for the checkout page. Checks if the session and cart
	 * exist; if not, redirects to the cart page. Otherwise, forwards the request to
	 * the checkout JSP.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// Redirect to cart if session is invalid or cart attribute is missing
		if (session == null || session.getAttribute("cart") == null) {
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}

		// Forward to the checkout JSP page to display checkout details
		request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
	}
}
