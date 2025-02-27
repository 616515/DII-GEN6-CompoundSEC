package CML;

public class User {
    private String id;
    private String name;
    private String role;
    private String contactInfo;
    private String userCard;

    // Constructor
    public User(String id, String name, String role, String contactInfo, String userCard) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.contactInfo = contactInfo;
        this.userCard = userCard;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    // Add this method
    public String getContact() {
        return contactInfo;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", contact='" + contactInfo + '\'' +
                ", userCard='" + userCard + '\'' +
                '}';
    }
}