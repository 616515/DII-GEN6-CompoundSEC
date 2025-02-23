package CML;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private List<Floor> floors;

    public Building() {
        this.floors = new ArrayList<>();
        // Initialize with low, medium, high floors
        floors.add(new Floor("low", 0));
        floors.add(new Floor("medium", 0));
        floors.add(new Floor("high", 0));
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

    public void printBuilding() {
        for (Floor floor : floors) {
            System.out.println(floor);
        }
    }

    public List<Floor> getFloors() {
        return floors;
    }
}