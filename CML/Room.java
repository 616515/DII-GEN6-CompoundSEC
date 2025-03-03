package CML;

public class Room {
    private int roomNumber;
    private String status;

    public Room(int roomNumber, String status) {
        this.roomNumber = roomNumber;
        this.status = status;
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
        return "Room " + roomNumber + " - Status: " + status;
    }
}