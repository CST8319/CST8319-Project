package beans;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmailVerification {
	private String verificationCode;
	private boolean verificationCodeUsed;
	private LocalDateTime codeExpirationDate;
	
	public EmailVerification() {
		this.verificationCode = UUID.randomUUID().toString();
		this.verificationCodeUsed = false;
		this.codeExpirationDate = LocalDateTime.now().plusHours(24);
	}

	public String getVerificationCode() {
		return verificationCode;
	}
	
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	public boolean isVerficationCodeUsed() {
		return verificationCodeUsed;
	}
 
	public void setVerificationCodeUsed(boolean verificationCodeUsed) {
		this.verificationCodeUsed = verificationCodeUsed;
	}
	
	public boolean isTokenValid(String inputToken) {
		return this.verificationCode.equals(inputToken) && !this.verificationCodeUsed;
	}
	
	public void CodeUsed() {
		this.verificationCodeUsed = true;
	}
	
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(codeExpirationDate);
	}
	
}