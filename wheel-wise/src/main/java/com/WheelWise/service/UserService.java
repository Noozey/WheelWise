package com.WheelWise.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.UserModel;

public class UserService {
	public List<UserModel> getAllUsers() {
		List<UserModel> users = new ArrayList<>();

		String query = "SELECT * FROM User WHERE role = 'user'"; // Only customers

		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setUserName(rs.getString("username"));
				user.setNumber(rs.getString("number"));
				user.setImagePath(rs.getString("image_path"));
				// Add other fields as needed

				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

}
