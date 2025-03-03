package CML;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private List<Floor> floors;

    public Building() {
        this.floors = new ArrayList<>();
        // Initialize with low, medium, high floors
        floors.add(new Floor("1", 3)); // Example initialization
        floors.add(new Floor("2", 3)); // Example initialization
        floors.add(new Floor("3", 3)); // Example initialization
    }

    public void addRoomsToFloor(String floorNumber, int numberOfRooms) {
        for (Floor floor : floors) {
            if (floor.getFloorNumber().equals(floorNumber)) {
                floor.addRooms(numberOfRooms);
                return;
            }
        }
        System.out.println("Floor not found!");
    }

    public void deleteRoom(String floorNumber, int roomNumber) {
        for (Floor floor : floors) {
            if (floor.getFloorNumber().equals(floorNumber)) {
                floor.deleteRoom(roomNumber);
                return;
            }
        }
        System.out.println("Floor not found!");
    }

    // Print all rooms in the building
    public void printBuilding() {
        for (Floor floor : floors) { // ใช้ for-each loop สำหรับ List<Floor>
            System.out.println("Floor: " + floor.getFloorNumber()); // แสดงหมายเลขชั้น
            for (Room room : floor.getRooms()) {
                System.out.println("  " + room); // แสดงห้องแต่ละห้องในชั้น
            }
            System.out.println(); // เพิ่มบรรทัดว่างระหว่างชั้น
        }
    }

    public List<Floor> getFloors() {
        return floors;
    }
}
