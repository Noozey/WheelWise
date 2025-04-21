package com.WheelWise.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.WheelWise.config.DbConfig;
import com.WheelWise.model.UserModel;

/**
 * RegisterService handles the registration of new students. It manages database
 * interactions for student registration.
 */
public class RegisterService {

	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public RegisterService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Registers a new student in the database.
	 *
	 * @param UserModel the student details to be registered
	 * @return Boolean indicating the success of the operation
	 */
	public Boolean addUser(UserModel UserModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String insertQuery = "INSERT INTO User (first_name, last_name, username, dob, gender, email, number, password, image_path,role) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

		try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

			// Insert student details
			insertStmt.setString(1, UserModel.getFirstName());
			insertStmt.setString(2, UserModel.getLastName());
			insertStmt.setString(3, UserModel.getUserName());
			insertStmt.setDate(4, Date.valueOf(UserModel.getDob()));
			insertStmt.setString(5, UserModel.getGender());
			insertStmt.setString(6, UserModel.getEmail());
			insertStmt.setString(7, UserModel.getNumber());
			insertStmt.setString(8, UserModel.getPassword());
			insertStmt.setString(9, UserModel.getImagePath());
			insertStmt.setString(10, "user");

			return insertStmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error during student registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static boolean updateUserProfile(int userId, String firstName, String lastName, String username, String dob,
			String gender, String email, String phoneNumber, String password) throws ClassNotFoundException {

		String sql = "UPDATE User SET first_name = ?, last_name = ?, username = ?, dob = ?, gender = ?, email = ?, number = ?, password = ? WHERE user_id = ?";

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, username);
			stmt.setString(4, dob);
			stmt.setString(5, gender);
			stmt.setString(6, email);
			stmt.setString(7, phoneNumber);
			stmt.setString(8, password);
			stmt.setInt(9, userId);

			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
