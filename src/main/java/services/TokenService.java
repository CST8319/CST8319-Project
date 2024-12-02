package services;

public interface TokenService {

	void saveVerificationCode(String email, String verificationCode);

	void saveResetCode(String email, String resetCode);

	String getVerificationCode(String email);

	String getResetCode(String email);

	boolean validateVerificationCode(String email, String verificationCode);

	boolean validateResetCode(String email, String resetCode);

	void deleteVerificationToken(String email, String token, String type);
	
}