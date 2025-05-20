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

/**
 * productController handles requests related to product listing and filtering.
 * It interacts with ProductService to fetch products based on filters and
 * sorting, then forwards the results to a JSP page for display.
 */
@WebServlet(urlPatterns = { "/product" })
public class productController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;

	/**
	 * Initializes the servlet and instantiates ProductService.
	 */
	@Override
	public void init() throws ServletException {
		// Initialize the product service
		productService = new ProductService();
	}

	/**
	 * Handles GET requests to fetch and display products based on filter criteria.
	 * 
	 * @param request  HttpServletRequest object containing client request data
	 * @param response HttpServletResponse object for sending responses to client
	 * @throws ServletException if servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve filter parameters from the request
		String[] selectedBrands = request.getParameterValues("brands");
		String[] selectedCategories = request.getParameterValues("categories");
		String sort = request.getParameter("sort");
		String priceMaxStr = request.getParameter("priceMax");

		// Set default max price if none provided
		double priceMax = 1000;
		if (priceMaxStr != null && !priceMaxStr.isEmpty()) {
			priceMax = Double.parseDouble(priceMaxStr);
		}

		// Use ProductService to retrieve filtered and sorted product list
		List<ProductModel> products = productService.getProducts(selectedBrands, selectedCategories, sort, priceMax);

		// Add the product list as a request attribute
		request.setAttribute("products", products);

		// Forward the request and response to the product JSP page for rendering
		request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests by delegating to doGet.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
