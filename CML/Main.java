package CML;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccessControlSystem system = AccessControlSystem.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Access Control System ---");
            System.out.println("1. Add User");
            System.out.println("2. Add Card");
            System.out.println("3. Check Access");
            System.out.println("4. View Card Logs");
            System.out.println("5. Modify User");
            System.out.println("6. Delete User");
            System.out.println("7. View Card Management Logs");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1://Add User
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    String userId = system.addUser(name, "user", "contact@example.com");
                    System.out.println("User added: " + userId);
                    break;

                case 2://Add Card
                    System.out.print("Enter user ID: ");
                    String userID = scanner.nextLine();
                    System.out.print("Enter access level (low/medium/high): ");
                    String accessLevel = scanner.nextLine();
                    System.out.print("Enter validity period (YYYY-MM-DD): ");
                    String validityPeriod = scanner.nextLine();
                    String cardId = system.addCard(userID, accessLevel, validityPeriod);
                    System.out.println("Card added: " + cardId);
                    break;

                case 3://Check Access
                    System.out.print("Enter card ID: ");
                    String cardID = scanner.nextLine();
                    System.out.print("Enter floor level (low/medium/high): ");
                    String floorLevel = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    String roomNumber = scanner.nextLine();
                    system.checkAccess(cardID, floorLevel, roomNumber);
                    break;

                case 4://View Card Logs
                    System.out.print("Enter card ID: ");
                    String logCardID = scanner.nextLine();
                    System.out.print("Enter date range (e.g., 2023-01-01 to 2023-12-31): ");
                    String dateRange = scanner.nextLine();
                    system.viewCardLogs(logCardID, dateRange);
                    break;

                case 5: // Modify User
                    System.out.print("Enter user ID: ");
                    String modifyUserID = scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new role: ");
                    String newRole = scanner.nextLine();
                    System.out.print("Enter new contact info: ");
                    String newContactInfo = scanner.nextLine();
                    system.modifyUser(modifyUserID, newName, newRole, newContactInfo);
                    System.out.println("User modified.");
                    break;
                
                case 6: // Delete User
                    System.out.print("Enter user ID: ");
                    String deleteUserID = scanner.nextLine();
                    system.deleteUser(deleteUserID);
                    System.out.println("User deleted.");
                    break;
                
                case 7: // View Card Management Logs
                    System.out.print("Enter date range (e.g., 2023-01-01 to 2023-12-31): ");
                    String managementDateRange = scanner.nextLine();
                    system.viewCardManagementLogs(managementDateRange);
                    break;

                case 8://Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}