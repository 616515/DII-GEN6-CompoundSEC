package CML;

public class User {
    private String userId;
    private String name;
    private String role;
    private String contactInfo;

    public User(String userId, String name, String role, String contactInfo) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.contactInfo = contactInfo;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getContactInfo() { return contactInfo; }
    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}