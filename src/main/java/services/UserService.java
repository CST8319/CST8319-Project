package services;


import beans.Register;

public interface UserService<Register>{
	
	void newUser(Register register);
	
	boolean userCheck(String username);
	
	boolean emailCheck(String email);
	
	boolean validateUser(String usernameOrEmail, String password);
	
	boolean isUserVerified(int userId);

	boolean isUserVerified(String usernameOrEmail);

	void markUserAsVerified(String email);

	Register getUser(String usernameOrEmail);

	void updatePassword(String email, String newPassword);
	
}