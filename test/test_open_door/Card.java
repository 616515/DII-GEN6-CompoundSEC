package test.test_open_door;

public class Card {
    private int cardId;
    private int assignedRoom;
    private String pinCode;

    public Card(int cardId) {
        this.cardId = cardId;
        this.assignedRoom = -1; // -1 means no room assigned
        this.pinCode = "";
    }

    public int getCardId() {
        return cardId;
    }

    public int getAssignedRoom() {
        return assignedRoom;
    }

    public void assignRoom(int roomNumber) {
        this.assignedRoom = roomNumber;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean verifyPinCode(String pinCode) {
        return this.pinCode.equals(pinCode);
    }
}