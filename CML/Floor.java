package CML;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private String floorNumber; // low, medium, high
    private List<Room> rooms;

    public Floor(String floorNumber, int numberOfRooms) {
        this.floorNumber = floorNumber;
        this.rooms = new ArrayList<>();
        for (int i = 1; i <= numberOfRooms; i++) {
            rooms.add(new Room(i, i, floorNumber));
        }
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRooms(int numberOfRooms) {
        for (int i = 1; i <= numberOfRooms; i++) {
            rooms.add(new Room(rooms.size() + 1, i, floorNumber));
        }
    }

    public void deleteRoom(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }

    @Override
    public String toString() {
        return "Floor{" +
                "floorNumber='" + floorNumber + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}