package CML;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private String floorNumber;
    private List<Room> rooms;

    public Floor(String floorNumber, int numberOfRooms) {
        this.floorNumber = floorNumber;
        this.rooms = new ArrayList<>(numberOfRooms);
        for (int i = 1; i <= numberOfRooms; i++) {
            rooms.add(new Room(i, "low")); // Default status is "low"
        }
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int roomNumber) {
        if (roomNumber < 1 || roomNumber > rooms.size()) {
            System.out.println("Room number " + roomNumber + " is out of bounds.");
            return null;
        }
        return rooms.get(roomNumber - 1);
    }

    public void deleteRoom(int roomNumber) {
        if (roomNumber < 1 || roomNumber > rooms.size()) {
            System.out.println("Room number " + roomNumber + " is out of bounds.");
            return;
        }
        rooms.remove(roomNumber - 1);
    }
}