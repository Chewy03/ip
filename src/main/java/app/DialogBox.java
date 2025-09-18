package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    public DialogBox(String s, Image i, double imageSize) {
        text = new Label(s);
        text.setWrapText(true);
        text.getStyleClass().add("dialog-bubble");

        displayPicture = new ImageView(i);
        displayPicture.setFitWidth(imageSize);
        displayPicture.setFitHeight(imageSize);

        this.setAlignment(Pos.TOP_RIGHT);
        this.setSpacing(10);
        this.setPadding(new Insets(5));

        this.getChildren().addAll(text, displayPicture);
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String s, Image i, double imageSize) {
        DialogBox db = new DialogBox(s, i, imageSize);
        db.text.getStyleClass().add("user-bubble");
        return db;
    }

    public static DialogBox getJTDialog(String s, Image i, double imageSize) {
        DialogBox db = new DialogBox(s, i, imageSize);
        db.text.getStyleClass().add("jt-bubble");
        db.flip();
        return db;
    }
}
