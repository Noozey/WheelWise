package com.WheelWise.controller;

import java.io.IOException;
import java.util.List;

import com.WheelWise.model.ProductModel;
import com.WheelWise.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/product" })
public class productController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;

	@Override
	public void init() throws ServletException {
		// Initialize the product service
		productService = new ProductService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] selectedBrands = request.getParameterValues("brands");
		String[] selectedCategories = request.getParameterValues("categories");
		String sort = request.getParameter("sort");
		String priceMaxStr = request.getParameter("priceMax");

		double priceMax = 1000;
		if (priceMaxStr != null && !priceMaxStr.isEmpty()) {
			priceMax = Double.parseDouble(priceMaxStr);
		}

		// Call the ProductService to get products
		List<ProductModel> products = productService.getProducts(selectedBrands, selectedCategories, sort, priceMax);

		// Set the products attribute and forward to the JSP page
		request.setAttribute("products", products);
		request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
