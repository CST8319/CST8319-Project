package factories;

import beans.Register;

public class RegistrationFactory {
	
	public static Register createNewUser(String email, String username, String password) {
		return new Register(email, username, password);
	}
	
	public static Register createUserFromDatabase(int id, String email, String username, String password, boolean isVerified) {
		return new Register(id, email, username, password, isVerified);
	}

}
