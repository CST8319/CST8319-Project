package services;

/**
 * Service interface for managing verification and reset tokens.
 * Provides methods to save, retrieve, validate, and delete tokens.
 */
public interface TokenService {

	/**
	 * Saves a verification code associated with a specific email address.
	 *
	 * @param email            the email address to associate with the verification
	 *                         code
	 * @param verificationCode the verification code to save
	 */
	void saveVerificationCode(String email, String verificationCode);

	/**
	 * Saves a reset code associated with a specific email address.
	 *
	 * @param email     the email address to associate with the reset code
	 * @param resetCode the reset code to save
	 */
	void saveResetCode(String email, String resetCode);

	/**
	 * Retrieves the verification code associated with a specific email address.
	 *
	 * @param email the email address to look up the verification code
	 * @return the verification code associated with the email, or null if not found
	 */
	String getVerificationCode(String email);

	/**
	 * Retrieves the reset code associated with a specific email address.
	 *
	 * @param email the email address to look up the reset code
	 * @return the reset code associated with the email, or null if not found
	 */
	String getResetCode(String email);

	/**
	 * Validates a verification code for a specific email address.
	 *
	 * @param email            the email address associated with the verification
	 *                         code
	 * @param verificationCode the verification code to validate
	 * @return true if the verification code is valid, false otherwise
	 */
	boolean validateVerificationCode(String email, String verificationCode);

	/**
	 * Validates a reset code for a specific email address.
	 *
	 * @param email     the email address associated with the reset code
	 * @param resetCode the reset code to validate
	 * @return true if the reset code is valid, false otherwise
	 */
	boolean validateResetCode(String email, String resetCode);

	/**
	 * Deletes a token (verification or reset) associated with a specific email
	 * address.
	 *
	 * @param email the email address associated with the token
	 * @param token the token to delete
	 * @param type  the type of token (e.g., "verification" or "reset")
	 */
	void deleteVerificationToken(String email, String token, String type);
}
