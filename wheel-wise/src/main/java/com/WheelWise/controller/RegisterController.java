package com.WheelWise.controller;

import java.io.IOException;
import java.time.LocalDate;

import com.WheelWise.model.UserModel;
import com.WheelWise.service.RegisterService;
import com.WheelWise.util.ImageUtil;
import com.WheelWise.util.PasswordUtil;
import com.WheelWise.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * RegisterController handles user registration requests. It manages form
 * validation, user data extraction, image upload, and interaction with
 * RegisterService to add new users.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB before writing to disk
		maxFileSize = 1024 * 1024 * 10, // Max image size 10MB
		maxRequestSize = 1024 * 1024 * 50 // Max request size 50MB
)
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Utilities and services for image handling and user registration
	private final ImageUtil imageUtil = new ImageUtil();
	private final RegisterService registerService = new RegisterService();

	/**
	 * Handles GET requests to show the registration form.
	 *
	 * @param req  HttpServletRequest object
	 * @param resp HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	/**
	 * Handles POST requests to process registration form submissions. Performs
	 * validation, user creation, image upload, and appropriate redirection.
	 *
	 * @param req  HttpServletRequest object
	 * @param resp HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Validate registration form inputs
			String validationMessage = validateRegistrationForm(req);
			if (validationMessage != null) {
				// Validation failed, show error
				handleError(req, resp, validationMessage);
				return;
			}

			// Extract user data into UserModel object
			UserModel userModel = extractUserModel(req);

			// Add new user through service layer
			Boolean isAdded = registerService.addUser(userModel);

			if (isAdded == null) {
				// Service is down or maintenance
				handleError(req, resp, "Our server is under maintenance. Please try again later!");
			} else if (isAdded) {
				// User added successfully, try to upload profile image
				if (uploadImage(req)) {
					// Success: show success message and redirect to login page
					handleSuccess(req, resp, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
				} else {
					// Image upload failed
					handleError(req, resp, "Could not upload the image. Please try again later!");
				}
			} else {
				// User addition failed for other reasons
				handleError(req, resp, "Could not register your account. Please try again later!");
			}
		} catch (Exception e) {
			// Unexpected errors
			handleError(req, resp, "An unexpected error occurred. Please try again later!");
			e.printStackTrace();
		}
	}

	/**
	 * Validates all registration form fields including image file.
	 *
	 * @param req HttpServletRequest object containing form parameters
	 * @return error message string if validation fails, null if validation passes
	 */
	private String validateRegistrationForm(HttpServletRequest req) {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String username = req.getParameter("username");
		String dobStr = req.getParameter("dob");
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");
		String number = req.getParameter("phoneNumber");
		String password = req.getParameter("password");
		String retypePassword = req.getParameter("retypePassword");

		// Basic null or empty checks for mandatory fields
		if (ValidationUtil.isNullOrEmpty(firstName))
			return "First name is required.";
		if (ValidationUtil.isNullOrEmpty(lastName))
			return "Last name is required.";
		if (ValidationUtil.isNullOrEmpty(username))
			return "Username is required.";
		if (ValidationUtil.isNullOrEmpty(dobStr))
			return "Date of birth is required.";
		if (ValidationUtil.isNullOrEmpty(gender))
			return "Gender is required.";
		if (ValidationUtil.isNullOrEmpty(email))
			return "Email is required.";
		if (ValidationUtil.isNullOrEmpty(number))
			return "Phone number is required.";
		if (ValidationUtil.isNullOrEmpty(password))
			return "Password is required.";
		if (ValidationUtil.isNullOrEmpty(retypePassword))
			return "Please retype the password.";

		// Validate date format and parse
		LocalDate dob;
		try {
			dob = LocalDate.parse(dobStr);
		} catch (Exception e) {
			return "Invalid date format. Please use YYYY-MM-DD.";
		}

		// Validate specific field formats and constraints
		if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
			return "Username must start with a letter and contain only letters and numbers.";
		if (!ValidationUtil.isValidGender(gender))
			return "Gender must be 'male' or 'female'.";
		if (!ValidationUtil.isValidEmail(email))
			return "Invalid email format.";
		if (!ValidationUtil.isValidPhoneNumber(number))
			return "Phone number must be 10 digits and start with 98.";
		if (!ValidationUtil.isValidPassword(password))
			return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
		if (!ValidationUtil.doPasswordsMatch(password, retypePassword))
			return "Passwords do not match.";
		if (!ValidationUtil.isAgeAtLeast16(dob))
			return "You must be at least 16 years old to register.";

		// Validate uploaded image extension
		try {
			Part image = req.getPart("image");
			if (!ValidationUtil.isValidImageExtension(image))
				return "Invalid image format. Only jpg, jpeg, png, and gif are allowed.";
		} catch (IOException | ServletException e) {
			return "Error handling image file. Please ensure the file is valid.";
		}

		return null; // No validation errors
	}

	/**
	 * Extracts form data and constructs a UserModel object. Encrypts the password
	 * before setting it.
	 *
	 * @param req HttpServletRequest containing form parameters and uploaded file
	 * @return UserModel with filled-in user data
	 * @throws Exception if image extraction or parsing fails
	 */
	private UserModel extractUserModel(HttpServletRequest req) throws Exception {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String username = req.getParameter("username");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");
		String number = req.getParameter("phoneNumber");
		String password = req.getParameter("password");

		// Encrypt password using username as salt/key
		password = PasswordUtil.encrypt(username, password);

		// Extract image file name
		Part image = req.getPart("image");
		String imagePath = imageUtil.getImageNameFromPart(image);

		// Create and return user model
		return new UserModel(firstName, lastName, username, dob, gender, email, number, password, imagePath);
	}

	/**
	 * Handles uploading the image file to the server.
	 *
	 * @param req HttpServletRequest containing uploaded image
	 * @return true if upload succeeds, false otherwise
	 * @throws IOException      if an I/O error occurs during upload
	 * @throws ServletException if servlet error occurs during upload
	 */
	private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("image");
		// Uploads image to the specified directory under "users" folder
		return imageUtil.uploadImage(image, "/Users/nooze/eclipse-workspace/wheel-wise/src/main/webapp", "users");
	}

	/**
	 * Forwards to a success page with a success message.
	 *
	 * @param req          HttpServletRequest object
	 * @param resp         HttpServletResponse object
	 * @param message      Success message to display
	 * @param redirectPage JSP page to forward to after success
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		req.getRequestDispatcher(redirectPage).forward(req, resp);
	}

	/**
	 * Handles errors by setting an error message and retaining form input values
	 * before forwarding back to the registration page.
	 *
	 * @param req     HttpServletRequest object
	 * @param resp    HttpServletResponse object
	 * @param message Error message to display
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		// Retain submitted form data so the user doesn't have to retype everything
		req.setAttribute("firstName", req.getParameter("firstName"));
		req.setAttribute("lastName", req.getParameter("lastName"));
		req.setAttribute("username", req.getParameter("username"));
		req.setAttribute("dob", req.getParameter("dob"));
		req.setAttribute("gender", req.getParameter("gender"));
		req.setAttribute("email", req.getParameter("email"));
		req.setAttribute("phoneNumber", req.getParameter("phoneNumber"));
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
}
