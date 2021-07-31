package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertTrans {
    public static void display(String title,String SenderInfo,double sentBal, String sendMsg, double SenderBal, String recipientInfo,
                               double updateBal, String accText){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(650);
        window.setMinHeight(250);

        Label infoMsg = new Label();
        infoMsg.setText(SenderInfo);
        Label text = new Label();
        text.setText(String.valueOf(SenderBal));

        Label newSent = new Label();
        newSent.setText(String.valueOf(sentBal));

        Label senderInfo = new Label();
        senderInfo.setText(sendMsg);

        Label recipientMsg = new Label();
        recipientMsg.setText(recipientInfo);

        Label recipientUpdate = new Label();
        recipientUpdate.setText(String.valueOf(updateBal));

        Label textMsg = new Label();
        textMsg.setText(accText);

        javafx.scene.control.Button closeBtn = new javafx.scene.control.Button("Close");
        closeBtn.setOnAction(event -> window.close());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        HBox senderMsg = new HBox(0);
        senderMsg.setAlignment(Pos.CENTER);
        senderMsg.getChildren().addAll(infoMsg,newSent,senderInfo, text);

        HBox recipientMs = new HBox(0);
        recipientMs.setAlignment(Pos.CENTER);
        recipientMs.getChildren().addAll(recipientMsg, recipientUpdate, textMsg);

        layout.getChildren().addAll(senderMsg, recipientMs, closeBtn);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
