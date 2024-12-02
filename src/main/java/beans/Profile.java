package beans;

public class Profile {
    private int userId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private int weight;
    private int height;
    
    // Package constructor so builder can be be used. 
    Profile(ProfileBuilder builder) {
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.gender = builder.gender;
        this.weight = builder.weight;
        this.height = builder.height;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }


    public int getHeight() {
        return height;
    }

}
