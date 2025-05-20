package com.WheelWise.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.ProductModel;

/**
 * ProductService handles fetching products from the database with optional
 * filtering and sorting capabilities.
 */
public class ProductService {

	/**
	 * Retrieves a list of products filtered by selected brands, categories, maximum
	 * price, and sorted based on the provided criteria.
	 *
	 * @param selectedBrands     Array of selected brand names to filter by
	 *                           (nullable)
	 * @param selectedCategories Array of selected categories to filter by
	 *                           (nullable)
	 * @param sort               Sorting criteria - e.g. "price-high", "price-low",
	 *                           "name-asc", "name-desc"
	 * @param priceMax           Maximum price filter for products
	 * @return List of ProductModel matching the filters and sorting
	 */
	public List<ProductModel> getProducts(String[] selectedBrands, String[] selectedCategories, String sort,
			double priceMax) {
		// Start building the base query with a price filter
		StringBuilder query = new StringBuilder("SELECT * FROM product WHERE price <= ?");
		List<Object> params = new ArrayList<>();
		params.add(priceMax);

		// Add brand filters if provided
		if (selectedBrands != null && selectedBrands.length > 0) {
			query.append(" AND brand IN (");
			for (int i = 0; i < selectedBrands.length; i++) {
				query.append("?");
				if (i < selectedBrands.length - 1) {
					query.append(",");
				}
				params.add(selectedBrands[i]);
			}
			query.append(")");
		}

		// Add category filters if provided
		if (selectedCategories != null && selectedCategories.length > 0) {
			query.append(" AND category IN (");
			for (int i = 0; i < selectedCategories.length; i++) {
				query.append("?");
				if (i < selectedCategories.length - 1) {
					query.append(",");
				}
				params.add(selectedCategories[i]);
			}
			query.append(")");
		}

		// Add sorting based on the sort parameter
		if ("price-high".equals(sort)) {
			query.append(" ORDER BY price DESC");
		} else if ("price-low".equals(sort)) {
			query.append(" ORDER BY price ASC");
		} else if ("name-asc".equals(sort)) {
			query.append(" ORDER BY product_name ASC");
		} else if ("name-desc".equals(sort)) {
			query.append(" ORDER BY product_name DESC");
		}

		List<ProductModel> products = new ArrayList<>();

		// Execute the constructed query with the collected parameters
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(query.toString())) {

			// Set the prepared statement parameters in order
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}

			// Execute the query and populate the products list
			ResultSet rs = stmt.executeQuery();
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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}
}
