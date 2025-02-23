package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private AccessControlSystem system = AccessControlSystem.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Access Control System");

        // Create UI Components
        Label userLabel = new Label("User Name:");
        TextField userField = new TextField();
        Label cardLabel = new Label("Card Access Level:");
        TextField cardField = new TextField();
        Button addUserButton = new Button("Add User");
        Button addCardButton = new Button("Add Card");
        Button checkAccessButton = new Button("Check Access");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        // Event Handlers
        addUserButton.setOnAction(e -> {
            String userName = userField.getText();
            String userId = system.addUser(userName, "user", "contact@example.com");
            resultArea.setText("User added: " + userId);
        });

        addCardButton.setOnAction(e -> {
            String accessLevel = cardField.getText();
            String cardId = system.addCard("user-id", accessLevel, "2023-12-31");
            resultArea.setText("Card added: " + cardId);
        });

        checkAccessButton.setOnAction(e -> {
            String cardId = "card-id"; // Replace with actual card ID logic
            String floorLevel = "high"; // Replace with actual floor level logic
            system.checkAccess(cardId, floorLevel, "101");
            resultArea.setText("Access checked. See logs for details.");
        });

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                userLabel, userField,
                cardLabel, cardField,
                addUserButton, addCardButton, checkAccessButton,
                resultArea
        );

        // Scene
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}