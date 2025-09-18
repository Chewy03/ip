package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

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
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);
        jT = new JimmyTimmy("data/jimmyTimmy.txt");
        jT.init();

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.setTitle("JimmyTimmy");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);
        stage.show();

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());

        dialogContainer.getChildren().add(
                DialogBox.getJTDialog("Hello! I am JimmyTimmy!\nHow may I help you today?", jTImage)
        );

        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());

        stage.setOnCloseRequest(event -> {
            String byeMessage = jT.getResponse("bye");
            dialogContainer.getChildren().add(
                    DialogBox.getJTDialog(byeMessage, jTImage)
            );
            javafx.application.Platform.exit();
        });

    }

    /**
     * Handles user input by creating dialog boxes for the user and JimmyTimmy's responses.
     */
    private void handleUserInput() {
        String userText = userInput.getText();
        String jTText = jT.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getJTDialog(jTText, jTImage)
        );
        userInput.clear();
        if (userText.equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }
}
