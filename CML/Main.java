package CML;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccessControlSystem system = AccessControlSystem.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Access Control and Building Management System ---");
            System.out.println("1. Edit User and Card");
            System.out.println("2. Edit Room");
            System.out.println("3. Check Everything");
            System.out.println("4. Test");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (mainChoice) {
                case 1:
                    handleEditUserAndCard(system, scanner);
                    break;
                case 2:
                    handleBuildingManagement(system, scanner);
                    break;
                case 3:
                    handleCheckEverything(system, scanner);
                    break;
                case 4:
                    handleTest(system, scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void handleEditUserAndCard(AccessControlSystem system, Scanner scanner) {
        System.out.println("\n--- Edit User and Card ---");
        System.out.println("1. User");
        System.out.println("2. Card");
        System.out.print("Choose an option: ");
        int editChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (editChoice) {
            case 1:
                handleUserManagement(system, scanner);
                break;
            case 2:
                handleCardManagement(system, scanner);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void handleUserManagement(AccessControlSystem system, Scanner scanner) {
        System.out.println("\n--- User Management ---");
        System.out.println("1. Add User");
        System.out.println("2. Edit User");
        System.out.println("3. Delete User");
        System.out.print("Choose an option: ");
        int userChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (userChoice) {
            case 1:
                System.out.print("Enter user name: ");
                String name = scanner.nextLine();
                String userId = system.addUser(name, "user", "contact@example.com");
                break;
            case 2:
                System.out.print("Enter user ID: ");
                String editUserId = scanner.nextLine();
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new role: ");
                String newRole = scanner.nextLine();
                System.out.print("Enter new contact info: ");
                String newContactInfo = scanner.nextLine();
                system.modifyUser(editUserId, newName, newRole, newContactInfo);
                System.out.println("User modified.");
                break;
            case 3:
                System.out.print("Enter user ID: ");
                String deleteUserId = scanner.nextLine();
                system.deleteUser(deleteUserId);
                System.out.println("User deleted.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void handleCardManagement(AccessControlSystem system, Scanner scanner) {
        System.out.println("\n--- Card Management ---");
        System.out.println("1. Add Card");
        System.out.println("2. Edit Card");
        System.out.println("3. Delete Card");
        System.out.print("Choose an option: ");
        int cardChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (cardChoice) {
            case 1:
                System.out.print("Enter user ID: ");
                String userID = scanner.nextLine();
                System.out.print("Enter access level (low/medium/high): ");
                String accessLevel = scanner.nextLine();
                System.out.print("Enter validity period (YYYY-MM-DD): ");
                String validityPeriod = scanner.nextLine();
                String cardId = system.addCard(userID, accessLevel, validityPeriod);
                break;
            case 2:
                System.out.print("Enter card ID: ");
                String editCardId = scanner.nextLine();
                System.out.print("Enter new access level (low/medium/high): ");
                String newAccessLevel = scanner.nextLine();
                System.out.print("Enter new validity period (YYYY-MM-DD): ");
                String newValidityPeriod = scanner.nextLine();
                system.modifyCard(editCardId, newAccessLevel, newValidityPeriod);
                System.out.println("Card modified.");
                break;
            case 3:
                System.out.print("Enter card ID: ");
                String deleteCardId = scanner.nextLine();
                system.revokeCard(deleteCardId);
                System.out.println("Card deleted.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void handleBuildingManagement(AccessControlSystem system, Scanner scanner) {
        System.out.println("\n--- Building Management ---");
        System.out.println("1. Add Rooms to Floor");
        System.out.println("2. Delete Room");
        System.out.print("Choose an option: ");
        int buildingChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (buildingChoice) {
            case 1:
                System.out.print("Enter floor level (low/medium/high): ");
                String floorLevel = scanner.nextLine();
                System.out.print("Enter number of rooms to add: ");
                int roomsToAdd = scanner.nextInt();
                system.addRoomsToFloor(floorLevel, roomsToAdd);
                break;
            case 2:
                System.out.print("Enter floor level (low/medium/high): ");
                String floorNumToDeleteRoom = scanner.nextLine();
                System.out.print("Enter room number to delete: ");
                int roomToDelete = scanner.nextInt();
                system.deleteRoom(floorNumToDeleteRoom, roomToDelete);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void handleCheckEverything(AccessControlSystem system, Scanner scanner) {
        System.out.println("\n--- Check Everything ---");
        System.out.println("1. Check Access");
        System.out.println("2. View Card Logs");
        System.out.println("3. Print Building");
        System.out.print("Choose an option: ");
        int checkChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (checkChoice) {
            case 1:
                System.out.print("Enter card ID: ");
                String cardID = scanner.nextLine();
                System.out.print("Enter floor level (low/medium/high): ");
                String floorLevel = scanner.nextLine();
                System.out.print("Enter room number: ");
                String roomNumber = scanner.nextLine();
                system.checkAccess(cardID, floorLevel, roomNumber);
                break;
            case 2:
                System.out.print("Enter card ID: ");
                String logCardID = scanner.nextLine();
                System.out.print("Enter date range (e.g., 2023-01-01 to 2023-12-31): ");
                String dateRange = scanner.nextLine();
                system.viewCardLogs(logCardID, dateRange);
                break;
            case 3:
                system.printBuilding();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void handleTest(AccessControlSystem system, Scanner scanner) {
        System.out.println("\n--- Test ---");
        System.out.println("1. Test Access");
        System.out.print("Choose an option: ");
        int testChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (testChoice) {
            case 1:
                System.out.print("Enter card ID: ");
                String testCardID = scanner.nextLine();
                System.out.print("Enter floor level (low/medium/high): ");
                String testFloorLevel = scanner.nextLine();
                System.out.print("Enter room number: ");
                String testRoomNumber = scanner.nextLine();
                system.checkAccess(testCardID, testFloorLevel, testRoomNumber);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}