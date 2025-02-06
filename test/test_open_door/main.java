package test.test_open_door;

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.addRoom(101);
        hotel.addRoom(102);
        hotel.addCard(1);
        hotel.addCard(2);

        if (hotel.assignCardToRoom(1, 101)) {
            System.out.println("Card 1 assigned to room 101");
        }

        // Set PIN code for card 1
        hotel.getCard(1).setPinCode("123456");

        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter PIN code
        System.out.print("Enter PIN code for card 1 to enter room 101: ");
        String enteredPin = scanner.nextLine();

        // Attempt to enter room 101 with entered PIN code
        if (hotel.enterRoom(1, 101, enteredPin)) {
            System.out.println("Card 1 entered room 101");
        } else {
            System.out.println("Card 1 failed to enter room 101");
        }

        scanner.close();
    }
}