package CML;

public class CardManagementLog {
    private String cardId;
    private String action;
    private long timestamp;

    public CardManagementLog(String cardId, String action, long timestamp) {
        this.cardId = cardId;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getCardId() {
        return cardId;
    }

    @Override
    public String toString() {
        return "CardManagementLog{" +
                "cardId='" + cardId + '\'' +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}