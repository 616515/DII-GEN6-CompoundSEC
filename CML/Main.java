package CML;

import java.util.InputMismatchException;
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
            try {
                int mainChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (mainChoice) {
                    case 1:// Edit User and Card
                        handleEditUserAndCard(system, scanner);
                        break;
                    case 2:// Building Management
                        handleBuildingManagement(system, scanner);
                        break;
                    case 3:// Check Everything
                        handleCheckEverything(system, scanner);
                        break;
                    case 4:// Test
                        handleTest(system, scanner);
                        break;
                    case 5:// Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void handleEditUserAndCard(AccessControlSystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Edit User and Card ---");
            System.out.println("1. User");
            System.out.println("2. Card");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            try {
                int editChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (editChoice) {
                    case 1:// User
                        handleUserManagement(system, scanner);
                        break;
                    case 2:// Card
                        handleCardManagement(system, scanner);
                        break;
                    case 3:// Exit
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void handleUserManagement(AccessControlSystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- User Management ---");
            System.out.println("1. Add User");
            System.out.println("2. Modified User");
            System.out.println("3. Delete User");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            try {
                int userChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (userChoice) {
                    case 1: // Add User
                        System.out.print("Enter user name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter user role (customer/staff/admin): ");
                        String role = scanner.nextLine();
                        if (!role.equals("customer") && !role.equals("staff") && !role.equals("admin")) {
                            System.out.println("Invalid user role. Please enter 'customer', 'staff', or 'admin'.");
                            break;
                        }
                        System.out.print("Enter contact info: ");
                        String contact = scanner.nextLine();
                        String userId = system.addUser(name, role, contact);
                        System.out.println("User added with ID: " + userId);
                        break;
                    case 2:// Modified User
                        System.out.print("Enter user ID: ");
                        String editUserId = scanner.nextLine();
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new role: ");
                        String newRole = scanner.nextLine();
                        if (!newRole.equals("customer") && !newRole.equals("staff") && !newRole.equals("admin")) {
                            System.out.println("Invalid user role. Please enter 'customer', 'staff', or 'admin'.");
                            break;
                        }
                        System.out.print("Enter new contact info: ");
                        String newContactInfo = scanner.nextLine();
                        system.modifyUser(editUserId, newName, newRole, newContactInfo);
                        System.out.println("User modified.");
                        break;
                    case 3:// Delete User
                        System.out.print("Enter user ID: ");
                        String deleteUserId = scanner.nextLine();
                        system.deleteUser(deleteUserId);
                        System.out.println("User deleted.");
                        break;
                    case 4:// Exit
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void handleCardManagement(AccessControlSystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Card Management ---");
            System.out.println("1. Add Card");
            System.out.println("2. Modified Card");
            System.out.println("3. Delete Card");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            try {
                int cardChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (cardChoice) {
                    case 1: // Add Card
                        System.out.print("Enter user ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter validity period (YYYY-MM-DD): ");
                        String validityPeriod = scanner.nextLine();
                        System.out.print("Enter multi-facades ID (e.g., FAC-001): ");
                        String multiFacadesId = scanner.nextLine();
                        System.out.print("Enter accessible floor number for low access level: ");
                        int floorNumber = scanner.nextInt();
                        System.out.print("Enter accessible room number for low access level: ");
                        int roomNumber = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        if (!system.roomExists(floorNumber, roomNumber)) {
                            System.out.println("Room not found.");
                            break;
                        }
                        String accessibleRoom = String.format("%d%02d", floorNumber, roomNumber);
                        String cardId = system.addCard(userId, validityPeriod, multiFacadesId, accessibleRoom);
                        if (cardId != null) {
                            System.out.println("Card added with ID: " + cardId);
                        }
                        break;
                    case 2:// Modified Card
                        System.out.print("Enter card ID: ");
                        String editCardId = scanner.nextLine();
                        System.out.print("Enter new access level (low/medium/high): ");
                        String newAccessLevel = scanner.nextLine();
                        System.out.print("Enter new validity period (YYYY-MM-DD): ");
                        String newValidityPeriod = scanner.nextLine();
                        system.modifyCard(editCardId, newAccessLevel, newValidityPeriod);
                        System.out.println("Card modified.");
                        break;
                    case 3:// Delete Card
                        System.out.print("Enter card ID: ");
                        String deleteCardId = scanner.nextLine();
                        system.revokeCard(deleteCardId);
                        System.out.println("Card deleted.");
                        break;
                    case 4:// Exit
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void handleBuildingManagement(AccessControlSystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Building Management ---");
            System.out.println("1. Add Rooms to Floor");
            System.out.println("2. Add Multiple Floors");
            System.out.println("3. Change Room Status");
            System.out.println("4. Change Multiple Room Status");
            System.out.println("5. Delete Room");
            System.out.println("6. Delete Multiple Rooms");
            System.out.println("7. Exit to Main Menu");
            System.out.print("Choose an option: ");
            try {
                int buildingChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (buildingChoice) {
                    case 1: // Add Rooms to Floor
                        System.out.print("Enter floor number: ");
                        int floorNumber = scanner.nextInt();
                        System.out.print("Enter number of rooms to add: ");
                        int roomsToAdd = scanner.nextInt();
                        system.addRoomsToFloor(floorNumber, roomsToAdd);
                        break;

                    case 2: // Add Multiple Floors
                        System.out.print("Enter start floor: ");
                        int startFloor = scanner.nextInt();
                        System.out.print("Enter end floor: ");
                        int endFloor = scanner.nextInt();
                        System.out.print("Enter number of rooms per floor: ");
                        int roomsPerFloor = scanner.nextInt();
                        system.addMultipleFloors(startFloor, endFloor, roomsPerFloor);
                        break;

                    case 3: // Change Room Status
                        System.out.print("Enter room code (e.g., 401 for floor 4 room 1): ");
                        int roomCode = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new status (low/medium/high): ");
                        String newStatus = scanner.nextLine();
                        system.changeRoomStatus(roomCode, newStatus);
                        break;
                    case 4:// Change Multiple Room Status
                        System.out.print("Enter start room code (e.g., 401): ");
                        int startRoomCode = scanner.nextInt();
                        System.out.print("Enter end room code (e.g., 512): ");
                        int endRoomCode = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new status (low/medium/high): ");
                        String newStatusMultiple = scanner.nextLine();
                        system.changeMultipleRoomStatus(startRoomCode, endRoomCode, newStatusMultiple);
                        break;
                    case 5: // Delete Room
                        System.out.print(
                                "Enter room code (e.g., 401 for floor 4 room 1, or 400 to delete entire floor): ");
                        int deleteRoomCode = scanner.nextInt();
                        system.deleteRoom(deleteRoomCode);
                        break;
                    case 6:// Delete Multiple Rooms
                        System.out.print("Enter start room code (e.g., 401): ");
                        int startDeleteRoomCode = scanner.nextInt();
                        System.out.print("Enter end room code (e.g., 512): ");
                        int endDeleteRoomCode = scanner.nextInt();
                        system.deleteMultipleRooms(startDeleteRoomCode, endDeleteRoomCode);
                        break;
                    case 7: // Exit to Main Menu
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void handleCheckEverything(AccessControlSystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Check Everything ---");
            System.out.println("1. Check Access");
            System.out.println("2. View Card Logs");
            System.out.println("3. Print Building");
            System.out.println("4. Check User Log Changes");
            System.out.println("5. Check All Card Logs");
            System.out.println("6. Screen IDs");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            try {
                int checkChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (checkChoice) {
                    case 1:// Check Access
                        System.out.print("Enter card ID: ");
                        String cardID = scanner.nextLine();
                        System.out.print("Enter floor level (low/medium/high): ");
                        String floorLevel = scanner.nextLine();
                        System.out.print("Enter room number: ");
                        String roomNumber = scanner.nextLine();
                        system.checkAccess(cardID, floorLevel, roomNumber);
                        break;
                    case 2:// View Card Logs
                        System.out.print("Enter card ID: ");
                        String logCardID = scanner.nextLine();
                        System.out.print("Enter date range (e.g., 2023-01-01 to 2023-12-31): ");
                        String dateRange = scanner.nextLine();
                        system.viewCardLogs(logCardID, dateRange);
                        break;
                    case 3:// Print Building
                        system.printBuilding();
                        break;
                    case 4:// Check User Log Changes
                        System.out.print("Enter user ID: ");
                        String userId = scanner.nextLine();
                        system.checkUserLogChanges(userId);
                        break;
                    case 5:// Check All Card Logs
                        System.out.print("Enter date range (e.g., 2023-01-01 to 2023-12-31): ");
                        String allCardLogsDateRange = scanner.nextLine();
                        system.checkAllCardLogs(allCardLogsDateRange);
                        break;
                    case 6:// Screen IDs
                        system.screenId();
                        break;
                    case 7:// Exit
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void handleTest(AccessControlSystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Test ---");
            System.out.println("1. Test Room Access");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            try {
                int testChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (testChoice) {
                    case 1: // Test Room Access
                        System.out.print("Enter user ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter user card: ");
                        String userCard = scanner.nextLine();
                        System.out.print("Enter contact info: ");
                        String contactInfo = scanner.nextLine();
                        System.out.print("Enter room number: ");
                        String roomNumber = scanner.nextLine();
                        if (system.checkRoomAccess(userId, userCard, contactInfo, roomNumber)) {
                            System.out.println("Access granted.");
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;
                    case 2:// Exit
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}