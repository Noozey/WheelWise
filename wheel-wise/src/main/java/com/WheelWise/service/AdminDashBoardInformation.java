package com.WheelWise.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.WheelWise.config.DbConfig;

/**
 * AdminDashBoardInformation provides methods to fetch aggregated statistics for
 * the admin dashboard, such as total orders, customers, products, and sales.
 */
public class AdminDashBoardInformation {

	/**
	 * Retrieves the total number of orders in the system.
	 *
	 * @return total order count as an integer
	 */
	public int getTotalOrders() {
		return getCount("SELECT COUNT(*) FROM `order`");
	}

	/**
	 * Retrieves the total number of customers (users with role 'user').
	 *
	 * @return total customer count as an integer
	 */
	public int getTotalCustomers() {
		return getCount("SELECT COUNT(*) FROM User WHERE role = 'user'");
	}

	/**
	 * Retrieves the total number of products available in the system.
	 *
	 * @return total product count as an integer
	 */
	public int getTotalProducts() {
		return getCount("SELECT COUNT(*) FROM product");
	}

	/**
	 * Calculates the total sales amount by summing the product of price and
	 * quantity for all ordered products.
	 *
	 * @return total sales amount as a double
	 */
	public double getTotalSales() {
		double total = 0.0;
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT SUM(p.price * op.quantity) AS total "
						+ "FROM order_product op " + "JOIN product p ON op.product_id = p.product_id");
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				total = rs.getDouble("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * Executes a SQL COUNT query and returns the resulting count. This is a helper
	 * method used by other methods to avoid repetition.
	 *
	 * @param query the SQL query string that returns a COUNT(*)
	 * @return the integer count result of the query
	 */
	private int getCount(String query) {
		int count = 0;
		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
