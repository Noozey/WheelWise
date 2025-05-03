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
 * Servlet implementation class coustomersController
 */
@WebServlet("/coustomers")
public class coustomersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserService userService = new UserService();
		List<UserModel> customers = userService.getAllUsers();

		request.setAttribute("customers", customers);
		request.getRequestDispatcher("WEB-INF/pages/admin/coustomers.jsp").forward(request, response);
	}
}
