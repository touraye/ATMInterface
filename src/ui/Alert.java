package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Alert {
    public static void display(String title,String info, double updatedBal, String New, double Msg){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(700);
        window.setMinHeight(250);

        Label infoMsg = new Label();
        infoMsg.setText(info);

        Label text = new Label();
        text.setText(String.valueOf(Msg));

        Label accountUpdate = new Label();
        accountUpdate.setText(String.valueOf(updatedBal));

        Label newDetails = new Label();
        newDetails.setText(New);

        javafx.scene.control.Button closeBtn = new javafx.scene.control.Button("Close");
        closeBtn.setOnAction(event -> window.close());

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);

        HBox showMsg = new HBox(0);
        showMsg.setAlignment(Pos.CENTER);
        showMsg.getChildren().addAll(infoMsg, accountUpdate, newDetails, text);

        layout.getChildren().addAll(showMsg, closeBtn);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
