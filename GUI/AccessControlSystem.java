package GUI;

import java.util.*;

public class AccessControlSystem {
    private static AccessControlSystem instance;
    private Map<String, Card> cards = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<AccessLog> accessLogs = new ArrayList<>();
    private List<CardManagementLog> cardManagementLogs = new ArrayList<>();

    private AccessControlSystem() {}

    public static AccessControlSystem getInstance() {
        if (instance == null) {
            instance = new AccessControlSystem();
        }
        return instance;
    }
    // Card Management
    public String addCard(String userId, String accessLevel, String validityPeriod) {
        String cardId = UUID.randomUUID().toString();
        cards.put(cardId, new Card(cardId, userId, accessLevel, validityPeriod));
        cardManagementLogs.add(new CardManagementLog(cardId, "ADD", System.currentTimeMillis()));
        System.out.println("Card added: " + cardId);
        return cardId;
    }

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
    public String addUser(String name, String role, String contactInfo) {
        String userId = UUID.randomUUID().toString();
        users.put(userId, new User(userId, name, role, contactInfo));
        System.out.println("User added: " + userId);
        return userId;
    }

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

    public void deleteUser(String userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            System.out.println("User deleted: " + userId);
        } else {
            System.out.println("User not found");
        }
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
}
