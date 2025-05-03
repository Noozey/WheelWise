package com.WheelWise.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.WheelWise.config.DbConfig;

public class AdminDashBoardInformation {

	public int getTotalOrders() {
		return getCount("SELECT COUNT(*) FROM `order`");
	}

	public int getTotalCustomers() {
		return getCount("SELECT COUNT(*) FROM User WHERE role = 'user'");
	}

	public int getTotalProducts() {
		return getCount("SELECT COUNT(*) FROM product");
	}

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
