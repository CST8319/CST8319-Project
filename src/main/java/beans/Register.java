package beans;

public class Register {
    private int id; // auto-incremented by database
    private String email;
    private String username;
    private String password;
    private boolean isVerified; // needs to be true to be able to login. 

    // Constructor for creating new user
    public Register(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isVerified = false;
    }
    
    // Constructor for pulling user from database
    public Register(int id, String email, String username, String password, boolean isVerified) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isVerified = isVerified;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }


}
