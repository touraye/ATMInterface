package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Alert {
    public static void display(String title, String Msg){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(650);
        window.setMinHeight(250);

        Label text = new Label();
        text.setText(Msg);
        javafx.scene.control.Button closeBtn = new javafx.scene.control.Button("Close");
        closeBtn.setOnAction(event -> window.close());

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(text, closeBtn);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
