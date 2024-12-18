package services;

import beans.Register;

/**
 * Interface that defines the services for user management, including
 * user registration, validation, verification, and password management.
 *
 * @param <Register> the type representing user registration data.
 */
public interface UserService<Register> {

	/**
	 * Registers a new user with the provided registration details.
	 *
	 * @param register the registration details of the user
	 */
	void newUser(Register register);

	/**
	 * Checks if the given username already exists.
	 *
	 * @param username the username to check
	 * @return true if the username exists, false otherwise
	 */
	boolean userCheck(String username);

	/**
	 * Checks if the given email is already associated with an existing user.
	 *
	 * @param email the email to check
	 * @return true if the email exists, false otherwise
	 */
	boolean emailCheck(String email);

	/**
	 * Validates the user by checking the provided username or email and password.
	 *
	 * @param usernameOrEmail the username or email to validate
	 * @param password        the password to validate
	 * @return true if the username/email and password match, false otherwise
	 */
	boolean validateUser(String usernameOrEmail, String password);

	/**
	 * Checks if the user with the given user ID is verified.
	 *
	 * @param userId the ID of the user
	 * @return true if the user is verified, false otherwise
	 */
	boolean isUserVerified(int userId);

	/**
	 * Checks if the user with the given username or email is verified.
	 *
	 * @param usernameOrEmail the username or email to check
	 * @return true if the user is verified, false otherwise
	 */
	boolean isUserVerified(String usernameOrEmail);

	/**
	 * Marks the user with the given email as verified.
	 *
	 * @param email the email of the user to mark as verified
	 */
	void markUserAsVerified(String email);

	/**
	 * Retrieves the user details associated with the provided username or email.
	 *
	 * @param usernameOrEmail the username or email of the user to retrieve
	 * @return the user details, or null if no user is found
	 */
	Register getUser(String usernameOrEmail);

	/**
	 * Updates the password for the user associated with the provided email.
	 *
	 * @param email       the email of the user whose password will be updated
	 * @param newPassword the new password to set
	 */
	void updatePassword(String email, String newPassword);
}
