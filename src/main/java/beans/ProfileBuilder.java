package beans;

public class ProfileBuilder {
    protected int userId;
    protected String firstName;
    protected String lastName;
    protected int age;
    protected String gender;
    protected int weight;
    protected int height;

	public ProfileBuilder(int userId) {
		this.userId = userId;
	}
	
	public ProfileBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public ProfileBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public ProfileBuilder setAge(int age) {
		this.age = age;
		return this;
	}
	
	public ProfileBuilder setGender(String gender) {
		this.gender = gender;
		return this;
	}
	
	public ProfileBuilder setWeight (int weight) {
		this.weight = weight;
		return this;
	}
	
	public ProfileBuilder setHeight (int height) {
		this.height = height;
		return this;
	}
	
	public Profile build() {
		return new Profile(this);
	}
	
}
