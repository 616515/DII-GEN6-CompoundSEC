package CML;

public class Card {
    private String cardId;
    private String userId;
    private String accessLevel;
    private String validityPeriod;
    private String multiFacadesId;
    private String accessibleRoom; // เพิ่มฟิลด์นี้

    public Card(String cardId, String userId, String accessLevel, String validityPeriod, String multiFacadesId,
            String accessibleRoom) {
        this.cardId = cardId;
        this.userId = userId;
        this.accessLevel = accessLevel;
        this.validityPeriod = validityPeriod;
        this.multiFacadesId = multiFacadesId;
        this.accessibleRoom = accessibleRoom; // กำหนดค่าให้ฟิลด์นี้
    }

    // Getters and Setters
    public String getCardId() {
        return cardId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getMultiFacadesId() {
        return multiFacadesId;
    }

    public void setMultiFacadesId(String multiFacadesId) {
        this.multiFacadesId = multiFacadesId;
    }

    public String getAccessibleRoom() {
        return accessibleRoom;
    }

    public void setAccessibleRoom(String accessibleRoom) {
        this.accessibleRoom = accessibleRoom;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId='" + cardId + '\'' +
                ", userId='" + userId + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", validityPeriod='" + (validityPeriod != null ? validityPeriod : "N/A") + '\'' +
                ", multiFacadesId='" + (multiFacadesId != null ? multiFacadesId : "N/A") + '\'' +
                '}';
    }
}