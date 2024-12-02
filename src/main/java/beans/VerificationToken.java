package beans;

public class VerificationToken {
    private int id; // auto-incremented by database
    private String email;
    private String token;
    
    // Constructor for creating new token
    public VerificationToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
