package CML;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private String floorNumber;
    private List<Room> rooms;

    public Floor(String floorNumber, int initialRooms) {
        this.floorNumber = floorNumber;
        this.rooms = new ArrayList<>();
        addRooms(initialRooms);
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRooms(int numberOfRooms) {
        for (int i = 1; i <= numberOfRooms; i++) {
            rooms.add(new Room(i, "low")); // Default status is "low"
        }
    }

    public void deleteRoom(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }
}