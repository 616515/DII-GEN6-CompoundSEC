package CML;

public class Room {
    private int floorNumber;
    private int roomNumber;
    private String status; // low, medium, high

    public Room(int floorNumber, int roomNumber, String status) {
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "floor" + floorNumber + " classroom " + roomNumber + " " + status;
    }
}