package com.WheelWise.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.ProductModel;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/product" })
public class productController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String[] selectedBrands = request.getParameterValues("brands");
		String[] selectedCategories = request.getParameterValues("categories");
		String sort = request.getParameter("sort");
		String priceMaxStr = request.getParameter("priceMax");

		double priceMax = 1000;
		if (priceMaxStr != null && !priceMaxStr.isEmpty()) {
			priceMax = Double.parseDouble(priceMaxStr);
		}

		StringBuilder query = new StringBuilder("SELECT * FROM product WHERE price <= ?");
		List<Object> params = new ArrayList<>();
		params.add(priceMax);

		if (selectedBrands != null && selectedBrands.length > 0) {
			query.append(" AND brand IN (");
			for (int i = 0; i < selectedBrands.length; i++) {
				query.append("?");
				if (i < selectedBrands.length - 1)
					query.append(",");
				params.add(selectedBrands[i]);
			}
			query.append(")");
		}

		if (selectedCategories != null && selectedCategories.length > 0) {
			query.append(" AND category IN (");
			for (int i = 0; i < selectedCategories.length; i++) {
				query.append("?");
				if (i < selectedCategories.length - 1)
					query.append(",");
				params.add(selectedCategories[i]);
			}
			query.append(")");
		}

		if ("price-high".equals(sort))
			query.append(" ORDER BY price DESC");
		else if ("price-low".equals(sort))
			query.append(" ORDER BY price ASC");
		else if ("name-asc".equals(sort))
			query.append(" ORDER BY product_name ASC");
		else if ("name-desc".equals(sort))
			query.append(" ORDER BY product_name DESC");

		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(query.toString())) {

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = stmt.executeQuery();
			List<ProductModel> products = new ArrayList<>();
			while (rs.next()) {
				ProductModel p = new ProductModel();
				p.setId(rs.getInt("product_id"));
				p.setName(rs.getString("product_name"));
				p.setBrand(rs.getString("brand"));
				p.setCategory(rs.getString("category"));
				p.setPrice(rs.getDouble("price"));
				p.setImageUrl(rs.getString("product_image"));
				products.add(p);
			}

			request.setAttribute("products", products);
			request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
