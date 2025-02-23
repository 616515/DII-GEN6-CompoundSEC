package CML;

public class AccessLog {
    private String cardId;
    private String floorLevel;
    private String roomNumber;
    private long timestamp;

    public AccessLog(String cardId, String floorLevel, String roomNumber, long timestamp) {
        this.cardId = cardId;
        this.floorLevel = floorLevel;
        this.roomNumber = roomNumber;
        this.timestamp = timestamp;
    }

    // Getters
    public String getCardId() { return cardId; }
    public String getFloorLevel() { return floorLevel; }
    public String getRoomNumber() { return roomNumber; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "AccessLog{" +
                "cardId='" + cardId + '\'' +
                ", floorLevel='" + floorLevel + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}