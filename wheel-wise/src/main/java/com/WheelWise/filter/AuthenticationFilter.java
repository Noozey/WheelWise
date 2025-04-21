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
	private static final String ABOUTUS = "/about";
	private static final String CONTACT = "/contact";

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
		boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
		Cookie roleCookie = CookieUtil.getCookie(req, "role");
		String role = roleCookie != null ? roleCookie.getValue() : null;

		// Always allow static resources
		if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")
				|| uri.endsWith(".jpeg") || uri.endsWith(".woff") || uri.endsWith(".woff2") || uri.endsWith(".ttf")
				|| uri.endsWith(".svg")) {
			chain.doFilter(request, response);
			return;
		}

		// Allow access to public pages
		if (uri.endsWith(PRODUCT) || uri.endsWith(ABOUTUS) || uri.endsWith(CONTACT) || uri.endsWith(HOME)
				|| uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.equals(req.getContextPath() + ROOT)) {

			// If the user is an admin, prevent access to login or register
			if ("admin".equals(role) || "user".equals(role) && (uri.endsWith(LOGIN) || uri.endsWith(REGISTER))) {
				res.sendRedirect(req.getContextPath() + ADMIN_DASHBOARD);
				return;
			}

			chain.doFilter(request, response);
			return;
		}

		// If not logged in, redirect to login
		if (!isLoggedIn) {
			res.sendRedirect(req.getContextPath() + LOGIN);
			return;
		}

		// If logged in as admin
		if ("admin".equals(role)) {
			if (uri.endsWith(ADMIN_DASHBOARD) || uri.endsWith(".css")) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + ADMIN_DASHBOARD);
			}
			return;
		}

		// Prevent regular users from accessing admin dashboard
		if (uri.endsWith(ADMIN_DASHBOARD)) {
			res.sendRedirect(req.getContextPath() + HOME);
			return;
		}

		// Prevent logged-in users from accessing login or register
		if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
			res.sendRedirect(req.getContextPath() + HOME);
			return;
		}

		// Allow access to all other pages for logged-in regular users
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Cleanup logic if needed
	}
}
