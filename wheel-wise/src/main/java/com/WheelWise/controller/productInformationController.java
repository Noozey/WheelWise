package com.WheelWise.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.ProductModel;
import com.WheelWise.model.ReviewModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * productInformationController handles requests to view product details and
 * reviews, as well as to submit new reviews for a specific product.
 */
@WebServlet("/productInformation")
public class productInformationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for productInformationController.
	 */
	public productInformationController() {
		super();
	}

	/**
	 * Handles GET requests to fetch product details and associated reviews.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productIdStr = request.getParameter("id");

		// Redirect to product list page if product ID is missing
		if (productIdStr == null || productIdStr.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/product");
			return;
		}

		try {
			int productId = Integer.parseInt(productIdStr);

			try (Connection conn = DbConfig.getDbConnection();
					PreparedStatement stmt = conn.prepareStatement("SELECT * FROM product WHERE product_id = ?");
					PreparedStatement reviewStmt = conn
							.prepareStatement("SELECT r.comments, r.review_date, u.username, u.image_path "
									+ "FROM review r JOIN User u ON r.user_id = u.user_id "
									+ "WHERE r.product_id = ? ORDER BY r.review_date DESC")) {

				// Fetch product details
				stmt.setInt(1, productId);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					ProductModel product = new ProductModel();
					product.setId(rs.getInt("product_id"));
					product.setName(rs.getString("product_name"));
					product.setBrand(rs.getString("brand"));
					product.setCategory(rs.getString("category"));
					product.setPrice(rs.getDouble("price"));
					product.setImageUrl(rs.getString("product_image"));

					request.setAttribute("product", product);
				}

				// Fetch product reviews
				reviewStmt.setInt(1, productId);
				ResultSet reviewRs = reviewStmt.executeQuery();

				List<ReviewModel> reviews = new ArrayList<>();
				while (reviewRs.next()) {
					ReviewModel review = new ReviewModel();
					review.setUserName(reviewRs.getString("username"));
					review.setContent(reviewRs.getString("comments"));
					review.setCommentDate(reviewRs.getString("review_date"));
					review.setUserImagePath(reviewRs.getString("image_path"));
					reviews.add(review);
				}

				// Set product and reviews as request attributes
				request.setAttribute("comments", reviews);
				request.getRequestDispatcher("WEB-INF/pages/productsInformation.jsp").forward(request, response);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/product");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/product");
		}
	}

	/**
	 * Handles POST requests to submit a new review for a product.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");

		// Redirect to login if user is not logged in
		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String productIdStr = request.getParameter("productId");
		String comment = request.getParameter("reviewContent");

		// Only insert review if productId and comment are valid
		if (productIdStr != null && comment != null && !comment.trim().isEmpty()) {
			try (Connection conn = DbConfig.getDbConnection();
					PreparedStatement stmt = conn
							.prepareStatement("INSERT INTO review (product_id, user_id, comments) VALUES (?, ?, ?)")) {

				stmt.setInt(1, Integer.parseInt(productIdStr));
				stmt.setInt(2, userId);
				stmt.setString(3, comment);
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Redirect back to the product info page to reflect the new comment
		response.sendRedirect(request.getContextPath() + "/productInformation?id=" + productIdStr);
	}
}
