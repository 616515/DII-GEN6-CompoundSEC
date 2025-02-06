package test;
import test.Card;
import test.Room;
import java.util.HashMap;
import java.util.Map;

public class Hotel {
    private Map<Integer, Room> rooms;
    private Map<Integer, Card> cards;

    public Hotel() {
        rooms = new HashMap<>();
        cards = new HashMap<>();
    }

    public void addRoom(int roomNumber) {
        rooms.put(roomNumber, new Room(roomNumber));
    }

    public void addCard(int cardId) {
        cards.put(cardId, new Card(cardId));
    }

    public Card getCard(int cardId) {
        return cards.get(cardId);
    }

    public boolean assignCardToRoom(int cardId, int roomNumber) {
        Room room = rooms.get(roomNumber);
        Card card = cards.get(cardId);

        if (room != null && card != null && !room.isOccupied()) {
            room.setOccupied(true);
            card.assignRoom(roomNumber);
            return true;
        }
        return false;
    }

    public boolean enterRoom(int cardId, int roomNumber, String pinCode) {
        Card card = cards.get(cardId);
        return card != null && card.getAssignedRoom() == roomNumber && card.verifyPinCode(pinCode);
    }
}