package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private String instructions = """
        --- Commands ---

        1. list
           View all items currently in your cart.

        2. todo <item>
           Add a grocery item to your cart.

        3. deadline <item> /by <yyyy-MM-dd HH:mm>
           Add a grocery item with an expiry date.

        4. event <item> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>
           Add a promotional period or sale.

        5. mark <item number>
           Mark an item as purchased.

        6. unmark <item number>
           Return a purchased item back to the cart.

        7. delete <item number>
           Remove an item from the cart.

        8. undo / redo
           Undo or redo the last action.

        9. bye
           Exit JimmyTimmy and save your cart.

        --- Tips ---
        - Use correct date/time format: yyyy-MM-dd HH:mm
        - Use item numbers from 'list' when marking, unmarking, or deleting
        """;

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image jTImage = new Image(this.getClass().getResourceAsStream("/images/JimmyTimmy.png"));
    private JimmyTimmy jT;

    @Override
    public void start(Stage stage) {
        Label instructionsLabel = new Label(instructions);
        instructionsLabel.setWrapText(true);
        instructionsLabel.getStyleClass().add("instructions-label");

        // Wrap in ScrollPane
        ScrollPane instructionsScroll = new ScrollPane(instructionsLabel);
        instructionsScroll.setFitToWidth(true);
        instructionsScroll.setMaxHeight(150);
        instructionsScroll.setStyle(
                "-fx-background-color: #fffde7; " +  // pastel yellow
                        "-fx-border-color: transparent;"
        );

        // TitledPane
        TitledPane instructionsPane = new TitledPane("📘 Instructions", instructionsScroll);
        instructionsPane.setExpanded(false);
        instructionsPane.getStyleClass().add("instructions-pane");


        // --- Chat area ---
        dialogContainer = new VBox(10);
        scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // --- VBox containing instructions + chat ---
        VBox chatVBox = new VBox(instructionsPane, scrollPane);
        chatVBox.setSpacing(0);

        // --- Input bar ---
        userInput = new TextField();
        userInput.setPromptText("Type your message...");
        sendButton = new Button("➤");
        sendButton.getStyleClass().add("send-button");

        // --- AnchorPane main layout ---
        AnchorPane mainLayout = new AnchorPane(chatVBox, userInput, sendButton);

        // --- Anchors ---
        AnchorPane.setTopAnchor(chatVBox, 0.0);
        AnchorPane.setLeftAnchor(chatVBox, 0.0);
        AnchorPane.setRightAnchor(chatVBox, 0.0);
        AnchorPane.setBottomAnchor(chatVBox, 60.0);

        AnchorPane.setLeftAnchor(userInput, 5.0);
        AnchorPane.setBottomAnchor(userInput, 5.0);
        AnchorPane.setRightAnchor(sendButton, 5.0);
        AnchorPane.setBottomAnchor(sendButton, 5.0);

        // --- Scene and width binding ---
        scene = new Scene(mainLayout, 400, 650);
        userInput.prefWidthProperty().bind(scene.widthProperty().subtract(sendButton.widthProperty()).subtract(15));

        // --- Stylesheet ---
        scene.getStylesheets().add(getClass().getResource("/style/chat.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("JimmyTimmy");
        stage.setResizable(true);
        stage.show();

        // --- Scroll auto ---
        dialogContainer.heightProperty().addListener((obs) -> scrollPane.setVvalue(1.0));

        // --- Event handlers ---
        sendButton.setOnMouseClicked(e -> handleUserInput());
        userInput.setOnAction(e -> handleUserInput());

        // --- Initialize JimmyTimmy ---
        jT = new JimmyTimmy("data/jimmyTimmy.txt");
        jT.init();

        // --- Welcome message ---
        dialogContainer.getChildren().add(
                DialogBox.getJTDialog("🛒 Welcome to JimmyTimmy, your ToDo Shopping Cart! 🛒\n" + "\n" +
                        "Hello! I’m JimmyTimmy, your trusty shopping-cart buddy. I’ll help you\n" +
                        "keep track of your grocery items, expiry dates, and promotional deals,\n" +
                        "so you never forget anything in your cart.", jTImage, 60)
        );

        stage.setOnCloseRequest(event -> {
            String byeMessage = jT.getResponse("bye");
            dialogContainer.getChildren().add(DialogBox.getJTDialog(byeMessage, jTImage, 60));
            javafx.application.Platform.exit();
        });
    }

    private void handleUserInput() {
        String userText = userInput.getText();
        String jTText = jT.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage, 60),
                DialogBox.getJTDialog(jTText, jTImage, 60)
        );
        userInput.clear();
        if (userText.equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
