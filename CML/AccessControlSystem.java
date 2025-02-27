package CML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Singleton Pattern
public class AccessControlSystem {
    private static AccessControlSystem instance;
    private Map<String, Card> cards = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<AccessLog> accessLogs = new ArrayList<>();
    private List<CardManagementLog> cardManagementLogs = new ArrayList<>();
    private Map<Integer, List<Room>> floors; // Key: floor number, Value: List of rooms
    private int nextUserId = 1;

    private AccessControlSystem() {
        floors = new HashMap<>();
    }

    public static AccessControlSystem getInstance() {
        if (instance == null) {
            instance = new AccessControlSystem();
        }
        return instance;
    }

    // Card Management
    public String addCard(String userId, String validityPeriod) {
        if (!users.containsKey(userId)) {
            System.out.println("User not found!");
            return null;
        }

        User user = users.get(userId);
        String accessLevel = "low"; // Default for customer
        if (user.getRole().equals("staff")) {
            accessLevel = "medium";
        } else if (user.getRole().equals("admin")) {
            accessLevel = "high";
        }

        String cardId = UUID.randomUUID().toString();
        cards.put(cardId, new Card(cardId, userId, accessLevel, validityPeriod));
        System.out.println("Card added: " + cardId);
        return cardId;
    }

    // modifyCard
    public void modifyCard(String cardId, String accessLevel, String validityPeriod) {
        if (cards.containsKey(cardId)) {
            Card card = cards.get(cardId);
            card.setAccessLevel(accessLevel);
            card.setValidityPeriod(validityPeriod);
            cardManagementLogs.add(new CardManagementLog(cardId, "MODIFY", System.currentTimeMillis()));
            System.out.println("Card modified: " + cardId);
        } else {
            System.out.println("Card not found");
        }
    }

    public void revokeCard(String cardId) {
        if (cards.containsKey(cardId)) {
            cards.remove(cardId);
            cardManagementLogs.add(new CardManagementLog(cardId, "REVOKE", System.currentTimeMillis()));
            System.out.println("Card revoked: " + cardId);
        } else {
            System.out.println("Card not found");
        }
    }

    // Access Control
    public void checkAccess(String cardId, String floorLevel, String roomNumber) {
        if (cards.containsKey(cardId)) {
            Card card = cards.get(cardId);
            AccessStrategy strategy = new StandardAccessStrategy(); // Default strategy
            if (card.getAccessLevel().equals("high")) {
                strategy = new HighSecurityAccessStrategy(); // Change strategy
            }
            if (strategy.checkAccess(card, floorLevel)) {
                accessLogs.add(new AccessLog(cardId, floorLevel, roomNumber, System.currentTimeMillis()));
                System.out.println("Access granted");
            } else {
                System.out.println("Access denied: Invalid floor level");
            }
        } else {
            System.out.println("Access denied: Card not found");
        }
    }

    public void logAccess(String cardId, String floorLevel, String roomNumber, long timestamp) {
        accessLogs.add(new AccessLog(cardId, floorLevel, roomNumber, timestamp));
        System.out.println("Access logged");
    }

    // User Management
    public String addUser(String name, String role, String contact) {
        String userId = String.format("%03d", nextUserId++); // Generate 3-digit user ID
        String userCard = UUID.randomUUID().toString(); // Generate user card
        users.put(userId, new User(userId, name, role, contact, userCard));
        System.out.println("User added: " + userId);
        System.out.println("User card: " + userCard); // Show user card to user
        return userId;
    }

    // modifyUser
    public void modifyUser(String userId, String name, String role, String contactInfo) {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            user.setName(name);
            user.setRole(role);
            user.setContactInfo(contactInfo);
            System.out.println("User modified: " + userId);
        } else {
            System.out.println("User not found");
        }
    }

    // deleteUser
    public void deleteUser(String userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            System.out.println("User deleted: " + userId);
        } else {
            System.out.println("User not found");
        }
    }

    // checkRoomAccess
    public boolean checkRoomAccess(String userId, String userCard, String contactInfo) {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            if (user.getUserCard().equals(userCard) && user.getContact().equals(contactInfo)) {
                return true; // Access granted
            }
        }
        return false; // Access denied
    }

    // Audit and Reporting
    public void viewCardLogs(String cardId, String dateRange) {
        System.out.println("Viewing card logs for card ID: " + cardId + " within date range: " + dateRange);
        for (AccessLog log : accessLogs) {
            if (log.getCardId().equals(cardId)) {
                System.out.println(log);
            }
        }
    }

    public void viewCardManagementLogs(String dateRange) {
        System.out.println("Viewing card management logs within date range: " + dateRange);
        for (CardManagementLog log : cardManagementLogs) {
            System.out.println(log);
        }
    }

    // Building Management
    public void addRoomsToFloor(int floorNumber, int numberOfRooms) {
        List<Room> rooms = floors.getOrDefault(floorNumber, new ArrayList<>());
        for (int i = 1; i <= numberOfRooms; i++) {
            rooms.add(new Room(floorNumber, i, "low")); // Default status is low
        }
        floors.put(floorNumber, rooms);
        System.out.println("Added " + numberOfRooms + " rooms to floor " + floorNumber + ".");
    }

    // changeRoomStatus
    public void changeRoomStatus(int roomCode, String newStatus) {
        int floorNumber = roomCode / 100; // Extract floor number
        int roomNumber = roomCode % 100; // Extract room number

        if (floors.containsKey(floorNumber)) {
            List<Room> rooms = floors.get(floorNumber);
            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNumber) {
                    room.setStatus(newStatus);
                    System.out.println("Changed status of room " + roomCode + " to " + newStatus + ".");
                    return;
                }
            }
            System.out.println("Room " + roomCode + " not found.");
        } else {
            System.out.println("Floor " + floorNumber + " not found.");
        }
    }

    // deleteRoom
    public void deleteRoom(int roomCode) {
        int floorNumber = roomCode / 100; // Extract floor number
        int roomNumber = roomCode % 100; // Extract room number

        if (floors.containsKey(floorNumber)) {
            List<Room> rooms = floors.get(floorNumber);
            if (roomNumber == 0) { // Delete entire floor
                floors.remove(floorNumber);
                System.out.println("Deleted floor " + floorNumber + ".");
            } else { // Delete specific room
                rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
                System.out.println("Deleted room " + roomCode + ".");
            }
        } else {
            System.out.println("Floor " + floorNumber + " not found.");
        }
    }

    // printBuilding
    public void printBuilding() {
        for (Map.Entry<Integer, List<Room>> entry : floors.entrySet()) {
            System.out.println("Floor: " + entry.getKey());
            for (Room room : entry.getValue()) {
                System.out.println("  " + room);
            }
        }
    }

    // addMultipleFloors
    public void addMultipleFloors(int startFloor, int endFloor, int roomsPerFloor) {
        for (int floorNumber = startFloor; floorNumber <= endFloor; floorNumber++) {
            List<Room> rooms = new ArrayList<>();
            for (int i = 1; i <= roomsPerFloor; i++) {
                rooms.add(new Room(floorNumber, i, "low")); // Default status is low
            }
            floors.put(floorNumber, rooms);
        }
        System.out
                .println("Added floors " + startFloor + " to " + endFloor + " with " + roomsPerFloor + " rooms each.");
    }
}
