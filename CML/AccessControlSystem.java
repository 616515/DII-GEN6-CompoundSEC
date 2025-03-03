package CML;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Singleton Pattern
public class AccessControlSystem {
    private static AccessControlSystem instance;
    private Map<String, Card> cards = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<AccessLog> accessLogs = new ArrayList<>();
    private List<CardManagementLog> cardManagementLogs = new ArrayList<>();
    private Map<Integer, Floor> floors = new HashMap<>();
    private Map<String, Room> rooms = new HashMap<>(); // เพิ่มการเก็บข้อมูลห้อง
    private int nextUserId = 1;
    private int nextCardId = 1;

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
    public String addCard(String userId, String validityPeriod, String multiFacadesId, String accessibleRoom) {
        if (!users.containsKey(userId)) {
            System.out.println("User not found!");
            return null;
        }

        if (!multiFacadesId.matches("FAC-\\d{3}")) {
            throw new IllegalArgumentException("Invalid multi-facades ID format. Expected format: FAC-001");
        }

        User user = users.get(userId);
        String accessLevel = "low"; // Default for customer
        if (user.getRole().equals("staff")) {
            accessLevel = "medium";
        } else if (user.getRole().equals("admin")) {
            accessLevel = "high";
        } else if (!user.getRole().equals("customer")) {
            throw new IllegalArgumentException("Invalid user role");
        }

        String cardId = String.format("%03d", nextCardId++); // สร้าง cardId

        cards.put(cardId, new Card(cardId, userId, accessLevel, validityPeriod, multiFacadesId, accessibleRoom));

        // บันทึกล็อกเมื่อเพิ่มการ์ด
        cardManagementLogs.add(new CardManagementLog(cardId, "ADD", System.currentTimeMillis()));

        System.out.println("Card added: " + cardId);
        return cardId;
    }

    // modifyCard
    public void modifyCard(String cardId, String accessLevel, String validityPeriod) {
        if (cards.containsKey(cardId)) {
            Card card = cards.get(cardId);
            card.setAccessLevel(accessLevel);
            card.setValidityPeriod(validityPeriod);

            // บันทึกล็อกเมื่อแก้ไขการ์ด
            cardManagementLogs.add(new CardManagementLog(cardId, "MODIFY", System.currentTimeMillis()));

            System.out.println("Card modified: " + cardId);
        } else {
            System.out.println("Card not found");
        }
    }

    public void revokeCard(String cardId) {
        if (cards.containsKey(cardId)) {
            cards.remove(cardId);

            // บันทึกล็อกเมื่อยกเลิกการ์ด
            cardManagementLogs.add(new CardManagementLog(cardId, "REVOKE", System.currentTimeMillis()));

            System.out.println("Card revoked: " + cardId);
        } else {
            System.out.println("Card not found");
        }
    }

    // Access Control
    public void checkAccess(String cardId, String floorLevel, String roomNumber) {
        if (!floorLevel.matches("low|medium|high")) {
            System.out.println("Invalid floor level");
            return;
        }

        if (cards.containsKey(cardId)) {
            Card card = cards.get(cardId);
            String accessLevel = card.getAccessLevel();

            // ตรวจสอบระดับการเข้าถึงของห้อง
            String roomStatus = getRoomStatus(floorLevel, roomNumber);
            if (roomStatus == null) {
                System.out.println("Room not found");
                return;
            }

            // ตรวจสอบเงื่อนไขการเข้าถึง
            if (accessLevel.equals("high") ||
                    (accessLevel.equals("medium") && (roomStatus.equals("low") || roomStatus.equals("medium"))) ||
                    (accessLevel.equals("low") && roomStatus.equals("low"))) {
                System.out.println("Access granted.");
                logAccess(cardId, floorLevel, roomNumber, System.currentTimeMillis());
            } else {
                System.out.println("Access denied: Insufficient access level");
            }
        } else {
            System.out.println("Access denied: Card not found");
        }
    }

    private String getRoomStatus(String floorLevel, String roomNumber) {
        int floorNum = Integer.parseInt(floorLevel.replaceAll("[^0-9]", ""));
        int roomNum = Integer.parseInt(roomNumber);

        if (floors.containsKey(floorNum)) {
            Floor floor = floors.get(floorNum);
            for (Room room : floor.getRooms()) {
                if (room.getRoomNumber() == roomNum) {
                    return room.getStatus();
                }
            }
        }
        return null; // ห้องไม่พบ
    }

    // logAccess
    public void logAccess(String cardId, String floorLevel, String roomNumber, long timestamp) {
        accessLogs.add(new AccessLog(cardId, floorLevel, roomNumber, timestamp));
        System.out.println("Access logged");
    }

    // User Management
    public String addUser(String name, String role, String contact) {
        String userId = String.format("%03d", nextUserId++);
        users.put(userId, new User(userId, name, role, contact, "defaultPassword"));
        System.out.println("User added: " + name);
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
    public boolean checkRoomAccess(String userCard, int floorNumber, int roomNumber) {
        Card card = cards.get(userCard);
        if (card == null) {
            return false;
        }

        User user = users.get(card.getUserId());
        if (user == null) {
            return false;
        }

        return checkCardAccessToRoom(card, floorNumber, roomNumber);
    }

    private boolean checkCardAccessToRoom(Card card, int floorNumber, int roomNumber) {
        Floor floor = floors.get(floorNumber);
        if (floor == null) {
            System.out.println("Floor not found.");
            return false;
        }

        Room room = floor.getRoom(roomNumber);
        if (room == null) {
            System.out.println("Room not found.");
            return false;
        }

        String accessLevel = card.getAccessLevel();
        String roomStatus = room.getStatus();

        switch (accessLevel) {
            case "high":
                return true;
            case "medium":
                return roomStatus.equals("low") || roomStatus.equals("medium");
            case "low":
                return roomStatus.equals("low");
            default:
                return false;
        }
    }

    // Audit and Reporting
    public void viewUserLogs(String userId, String dateRange) {
        if (!dateRange.matches("\\d{4}-\\d{2}-\\d{2} to \\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date range format (YYYY-MM-DD to YYYY-MM-DD)");
            return;
        }

        boolean hasLogs = false;
        System.out.println("Viewing user logs for user ID: " + userId + " within date range: " + dateRange);
        int pageSize = 10; // แสดง 10 รายการต่อหน้า
        int page = 0; // หน้าเริ่มต้น
        for (int i = page * pageSize; i < Math.min((page + 1) * pageSize, accessLogs.size()); i++) {
            AccessLog log = accessLogs.get(i);
            if (cards.containsKey(log.getCardId()) && cards.get(log.getCardId()).getUserId().equals(userId)) {
                System.out.println(log);
                hasLogs = true;
            }
        }
        if (!hasLogs) {
            System.out.println("NOt have logs");
        }
    }

    // viewCardManagementLogs
    public void viewCardLogs(String cardId, String dateRange) {
        if (!dateRange.matches("\\d{4}-\\d{2}-\\d{2} to \\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date range format (YYYY-MM-DD to YYYY-MM-DD)");
            return;
        }

        // แยกวันที่เริ่มต้นและสิ้นสุด
        String[] dates = dateRange.split(" to ");
        long startDate = parseDateToMillis(dates[0]);
        long endDate = parseDateToMillis(dates[1]);

        boolean hasLogs = false;
        System.out.println("Viewing card logs for card ID: " + cardId + " within date range: " + dateRange);
        for (CardManagementLog log : cardManagementLogs) {
            if (log.getCardId().equals(cardId) && log.getTimestamp() >= startDate && log.getTimestamp() <= endDate) {
                System.out.println(log);
                hasLogs = true;
            }
        }
        if (!hasLogs) {
            System.out.println("No logs found");
        }
    }

    // เมธอดแปลงวันที่เป็น milliseconds
    private long parseDateToMillis(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = sdf.parse(date);
            return parsedDate.getTime();
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            return 0;
        }
    }

    // Check log User
    public void checkUserLogChanges(String userId) {
        boolean hasChanges = false;
        System.out.println("Checking log changes for user ID: " + userId);
        for (CardManagementLog log : cardManagementLogs) {
            Card card = cards.get(log.getCardId());
            if (card != null && card.getUserId().equals(userId)) {
                System.out.println(log);
                hasChanges = true;
            }
        }
        if (!hasChanges) {
            System.out.println("No changes found for user ID: " + userId);
        }
    }

    // Building Management
    public void addRoomsToFloor(int floorNumber, int numberOfRooms) {
        Floor floor = floors.get(floorNumber);
        if (floor == null) {
            floor = new Floor(String.valueOf(floorNumber), numberOfRooms);
            floors.put(floorNumber, floor);
        }

        for (int i = 1; i <= numberOfRooms; i++) {
            Room room = new Room(i, "low"); // Default status is "low"
            floor.getRooms().add(room);
        }
        System.out.println("Added " + numberOfRooms + " rooms to floor " + floorNumber);
    }

    // changeRoomStatus
    public void changeRoomStatus(int roomCode, String newStatus) {
        int floorNumber = roomCode / 100; // Extract floor number
        int roomNumber = roomCode % 100; // Extract room number

        if (floors.containsKey(floorNumber)) {
            Floor floor = floors.get(floorNumber);
            for (Room room : floor.getRooms()) {
                if (room.getRoomNumber() == roomNumber) {
                    room.setStatus(newStatus);
                    rooms.put(String.valueOf(roomNumber), room); // เพิ่มการเก็บข้อมูลห้อง
                    System.out.println("Changed status of room " + roomCode + " to " + newStatus + ".");
                    return;
                }
            }
            System.out.println("Room " + roomCode + " not found.");
        } else {
            System.out.println("Floor " + floorNumber + " not found.");
        }
    }

    // Change multiple room status
    public void changeMultipleRoomStatus(int startRoomCode, int endRoomCode, String newStatus) {
        final int startFloor = startRoomCode / 100;
        final int startRoom = startRoomCode % 100;
        final int endFloor = endRoomCode / 100;
        final int endRoom = endRoomCode % 100;

        for (int floor = startFloor; floor <= endFloor; floor++) {
            if (floors.containsKey(floor)) {
                Floor floorObj = floors.get(floor);
                for (Room room : floorObj.getRooms()) {
                    if ((floor == startFloor && room.getRoomNumber() >= startRoom) ||
                            (floor == endFloor && room.getRoomNumber() <= endRoom) ||
                            (floor > startFloor && floor < endFloor)) {
                        room.setStatus(newStatus);
                        rooms.put(String.valueOf(room.getRoomNumber()), room); // เพิ่มการเก็บข้อมูลห้อง
                        System.out.println("Changed status of room " + (floor * 100 + room.getRoomNumber()) + " to "
                                + newStatus + ".");
                    }
                }
            }
        }
    }

    // deleteRoom
    public void deleteRoom(int roomCode) {
        int floorNumber = roomCode / 100; // Extract floor number
        int roomNumber = roomCode % 100; // Extract room number

        if (floors.containsKey(floorNumber)) {
            Floor floor = floors.get(floorNumber);
            if (roomNumber == 0) { // Delete entire floor
                floors.remove(floorNumber);
                System.out.println("Deleted floor " + floorNumber + ".");
            } else { // Delete specific room
                floor.deleteRoom(roomNumber);
                rooms.remove(String.valueOf(roomNumber)); // ลบข้อมูลห้อง
                System.out.println("Deleted room " + roomCode + ".");
            }
        } else {
            System.out.println("Floor " + floorNumber + " not found.");
        }
    }

    // Delete multiple rooms
    public void deleteMultipleRooms(int startRoomCode, int endRoomCode) {
        int startFloor = startRoomCode / 100;
        int startRoom = startRoomCode % 100;
        int endFloor = endRoomCode / 100;
        int endRoom = endRoomCode % 100;

        for (int floor = startFloor; floor <= endFloor; floor++) {
            if (floors.containsKey(floor)) {
                Floor floorObj = floors.get(floor);
                List<Room> roomsToRemove = new ArrayList<>();
                for (Room room : floorObj.getRooms()) {
                    int roomNumber = room.getRoomNumber();
                    if ((floor == startFloor && roomNumber >= startRoom) ||
                            (floor == endFloor && roomNumber <= endRoom) ||
                            (floor > startFloor && floor < endFloor)) {
                        roomsToRemove.add(room);
                        rooms.remove(String.valueOf(roomNumber)); // ลบข้อมูลห้อง
                    }
                }
                floorObj.getRooms().removeAll(roomsToRemove);
                System.out.println(
                        "Deleted rooms from " + startRoomCode + " to " + endRoomCode + " on floor " + floor + ".");
            } else {
                System.out.println("Floor " + floor + " not found.");
            }
        }
    }

    // printBuilding
    public void printBuilding() {
        for (Map.Entry<Integer, Floor> entry : floors.entrySet()) {
            System.out.println("Floor: " + entry.getKey());
            for (Room room : entry.getValue().getRooms()) {
                System.out.print("  " + room);
                for (AccessLog log : accessLogs) {
                    if (log.getRoomNumber().equals(String.valueOf(room.getRoomNumber())) &&
                            log.getFloorLevel().equals(String.valueOf(entry.getKey()))) {
                        System.out.println(" (Card ID: " + log.getCardId() + ")");
                    }
                }
            }
        }
    }

    // addMultipleFloors
    public void addMultipleFloors(int startFloor, int endFloor, int roomsPerFloor) {
        for (int i = startFloor; i <= endFloor; i++) {
            addRoomsToFloor(i, roomsPerFloor);
        }
        System.out
                .println("Added floors " + startFloor + " to " + endFloor + " with " + roomsPerFloor + " rooms each.");
    }

    // showCurrentUsage
    public void showCurrentUsage() {
        System.out.println("Current Usage:");
        for (AccessLog log : accessLogs) {
            Card card = cards.get(log.getCardId());
            if (card != null) {
                User user = users.get(card.getUserId());
                if (user != null) {
                    System.out.println("Card ID: " + log.getCardId() +
                            ", User ID: " + user.getId() +
                            ", User Name: " + user.getName() +
                            ", Room: " + log.getRoomNumber());
                }
            }
        }
    }

    // Check all card logs with timestamps
    public void checkAllCardLogs(String dateRange) {
        if (accessLogs.isEmpty()) {
            System.out.println("No logs found.");
            return;
        }
        System.out.println("Viewing all card logs within date range: " + dateRange);
        for (AccessLog log : accessLogs) {
            System.out.println("Card ID: " + log.getCardId() +
                    ", Floor: " + log.getFloorLevel() +
                    ", Room: " + log.getRoomNumber() +
                    ", Time: " + new Date(log.getTimestamp()));
        }
    }

    // Screen ID
    public void screenId() {
        System.out.println("Screening IDs:");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            String userId = entry.getKey();
            User user = entry.getValue();
            for (Card card : cards.values()) {
                if (card.getUserId().equals(userId)) {
                    System.out.println("User ID: " + userId + " Card ID: " + card.getCardId() + " Name: "
                            + user.getName() + " Role: " + user.getRole());
                }
            }
        }
    }

    // Add specific room to floor
    public void addSpecificRoomToFloor(int floorNumber, int roomNumber) {
        Floor floor = floors.getOrDefault(floorNumber, new Floor(String.valueOf(floorNumber), 0));
        Room room = new Room(roomNumber, "low"); // Default status is "low"
        floor.getRooms().add(room);
        floors.put(floorNumber, floor);
        String roomKey = String.format("%d%02d", floorNumber, roomNumber);
        rooms.put(roomKey, room); // Add room to the rooms map with the correct key format
        System.out.println("Added room " + roomNumber + " to floor " + floorNumber + ".");
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }

    // View access logs
    public void viewCardAccessLogs(String cardId, String dateRange) {
        if (!dateRange.matches("\\d{4}-\\d{2}-\\d{2} to \\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date range format (YYYY-MM-DD to YYYY-MM-DD)");
            return;
        }

        // แยกวันที่เริ่มต้นและสิ้นสุด
        String[] dates = dateRange.split(" to ");
        long startDate = parseDateToMillis(dates[0]);
        long endDate = parseDateToMillis(dates[1]);

        boolean hasLogs = false;
        System.out.println("Viewing card access logs for card ID: " + cardId + " within date range: " + dateRange);
        for (AccessLog log : accessLogs) {
            if (log.getCardId().equals(cardId) && log.getTimestamp() >= startDate && log.getTimestamp() <= endDate) {
                System.out.println(log);
                hasLogs = true;
            }
        }
        if (!hasLogs) {
            System.out.println("No logs found");
        }
    }

    // Check room exists
    public boolean roomExists(int floorNumber, int roomNumber) {
        String roomKey = String.format("%d%02d", floorNumber, roomNumber);
        return rooms.containsKey(roomKey);
    }
}