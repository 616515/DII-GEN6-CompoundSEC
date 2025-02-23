package GUI;

public class Card {
    private String cardId;
    private String userId;
    private String accessLevel;
    private String validityPeriod;

    public Card(String cardId, String userId, String accessLevel, String validityPeriod) {
        this.cardId = cardId;
        this.userId = userId;
        this.accessLevel = accessLevel;
        this.validityPeriod = validityPeriod;
    }

    // Getters and Setters
    public String getCardId() { return cardId; }
    public String getUserId() { return userId; }
    public String getAccessLevel() { return accessLevel; }
    public String getValidityPeriod() { return validityPeriod; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
    public void setValidityPeriod(String validityPeriod) { this.validityPeriod = validityPeriod; }

    @Override
    public String toString() {
        return "Card{" +
                "cardId='" + cardId + '\'' +
                ", userId='" + userId + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", validityPeriod='" + validityPeriod + '\'' +
                '}';
    }
}