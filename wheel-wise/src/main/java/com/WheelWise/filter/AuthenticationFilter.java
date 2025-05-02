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
				|| path.equals(PRODUCT) || path.equals(ABOUTUS) || path.equals(CONTACT);

		// Admin user trying to access login or register – redirect to dashboard
		if (isLoggedIn && ("admin".equals(role) || "user".equals(role))
				&& (path.equals(LOGIN) || path.equals(REGISTER))) {
			res.sendRedirect(contextPath + (role.equals("admin") ? ADMIN_DASHBOARD : HOME));
			return;
		}

		// Allow public pages
		if (isPublic) {
			chain.doFilter(request, response);
			return;
		}

		// Not logged in, trying to access protected page – redirect to login
		if (!isLoggedIn) {
			res.sendRedirect(contextPath + LOGIN);
			return;
		}

		// Admin access rules
		if ("admin".equals(role)) {
			if (path.equals(ADMIN_DASHBOARD) || path.startsWith("/admin") || path.matches(".*\\.(css|js)$")
					|| path.equals(ADMINPRODUCT) || path.equals(order)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(contextPath + ADMIN_DASHBOARD);
			}
			return;
		}

		// User trying to access admin dashboard
		if (path.equals(ADMIN_DASHBOARD)) {
			res.sendRedirect(contextPath + HOME);
			return;
		}

		// All other cases – allow
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Cleanup logic if needed
	}
}
