package CML;

import java.util.HashMap;
import java.util.Map;

public class CardManagementLog {
    private String cardId;
    private String action;
    private long timestamp;
    private Map<Integer, Room> rooms = new HashMap<>();

    public CardManagementLog(String cardId, String action, long timestamp) {
        this.cardId = cardId;
        this.action = action;
        this.timestamp = timestamp;
    }

    // Getters
    public String getCardId() {
        return cardId;
    }

    public String getAction() {
        return action;
    }

    public long getTimestamp() {
        return timestamp;
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