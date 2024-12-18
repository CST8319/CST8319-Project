package factories;

import beans.Register;

/**
 * Factory class for creating instances of the {@link Register} bean.
 * Provides methods to create user objects with different sets of parameters,
 * tailored for new user registration or data retrieved from the database.
 */
public class RegistrationFactory {

	/**
	 * Creates a new {@link Register} object for a user being registered.
	 *
	 * @param email    the email of the user
	 * @param username the username of the user
	 * @param password the password of the user
	 * @return a {@link Register} object initialized with the provided parameters
	 */
	public static Register createNewUser(String email, String username, String password) {
		return new Register(email, username, password);
	}

	/**
	 * Creates a {@link Register} object for a user retrieved from the database.
	 *
	 * @param id         the ID of the user
	 * @param email      the email of the user
	 * @param username   the username of the user
	 * @param password   the password of the user
	 * @param isVerified whether the user's email is verified
	 * @return a {@link Register} object initialized with the provided parameters
	 */
	public static Register createUserFromDatabase(int id, String email, String username, String password,
			boolean isVerified) {
		return new Register(id, email, username, password, isVerified);
	}
}
