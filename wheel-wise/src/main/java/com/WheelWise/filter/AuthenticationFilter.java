package com.WheelWise.filter;

import java.io.IOException;

import com.WheelWise.util.CookieUtil;
import com.WheelWise.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String ADMIN_DASHBOARD = "/admindashboard";
	private static final String PRODUCT = "/product";
	private static final String ADMINPRODUCT = "/products";
	private static final String ABOUTUS = "/about";
	private static final String CONTACT = "/contact";
	private static final String order = "/order";
	private static final String ADDPRODUCT = "/addproduct";
	private static final String COUSTOMERS = "/coustomers";
	private static final String ProductDetails = "/productInformation";
	private static final String MESSAGE = "/messages";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization logic, if needed
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length());

		boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
		Cookie roleCookie = CookieUtil.getCookie(req, "role");
		String role = roleCookie != null ? roleCookie.getValue() : null;

		// Allow static resources
		if (path.matches(".*\\.(css|js|png|jpg|jpeg|woff2?|ttf|svg)$")) {
			chain.doFilter(request, response);
			return;
		}

		// Public pages
		boolean isPublic = path.equals(LOGIN) || path.equals(REGISTER) || path.equals(HOME) || path.equals(ROOT)
				|| path.equals(PRODUCT) || path.equals(ABOUTUS) || path.equals(CONTACT) || path.equals(ProductDetails);

		// Admin-only pages
		boolean isAdminOnly = path.equals(ADMIN_DASHBOARD) || path.equals(order) || path.equals(ADDPRODUCT)
				|| path.equals(COUSTOMERS) || path.equals(ADMINPRODUCT) || path.equals(MESSAGE)
				|| path.startsWith("/admin");

		// Redirect logged-in users trying to access login or register
		if (isLoggedIn && (path.equals(LOGIN) || path.equals(REGISTER))) {
			res.sendRedirect(contextPath + (role.equals("admin") ? ADMIN_DASHBOARD : HOME));
			return;
		}

		// Allow public pages
		if (isPublic) {
			chain.doFilter(request, response);
			return;
		}

		// Not logged in
		if (!isLoggedIn) {
			res.sendRedirect(contextPath + LOGIN);
			return;
		}

		// Admin users
		if ("admin".equals(role)) {
			chain.doFilter(request, response);
			return;
		}

		// Regular users trying to access admin-only pages
		if (isAdminOnly) {
			res.sendRedirect(contextPath + HOME);
			return;
		}

		// All other logged-in users access
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Cleanup logic if needed
	}
}