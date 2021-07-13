package ui;
import main.Login;// get thw ArrayList from the Login Class
import database.UserDB;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class Dashboard {
    Login user = new Login();
//    String currentUser = user.
    public Scene dashboard(){
        BorderPane appLayout = new BorderPane();

        HBox appMenu = new HBox(30);
        appMenu.setId("app");

        Button deposit = new Button("Deposit");
        deposit.setId("depositBtn");
        deposit.setOnAction(e->{
            //Todo Event Handler

        Deposit saveMoney = new Deposit();
        appLayout.setCenter(saveMoney.depositLayout());

//            appLayout.setCenter(this.verifyAccount());
        });
        Button transfer = new Button("Transfer");
        transfer.setId("transferBtn");
        transfer.setOnAction(event -> {
            //Todo Event Handler
            appLayout.setCenter(this.verifyAccount());
        });
        Button transaction = new Button("Transactions");
        transaction.setId("transactionBtn");
        transaction.setOnAction(event -> {
            Transaction trans = new Transaction();
            appLayout.setCenter(this.transactionHistoryForm());
        });
        Button withdrawal = new Button("Withdrawal");
        withdrawal.setId("withdrawalBtn");
        withdrawal.setOnAction(event -> {
            appLayout.setCenter(this.verifyAccount());
        });
        Button transactionHistory = new Button("Transaction History");
        transactionHistory.setId("transBtn");
        transactionHistory.setOnAction(event -> {
            //Todo Event Handler
            appLayout.setCenter(this.verifyAccount());
        });
        Button quitApp = new Button("Quit");
        quitApp.setId("quitApp");
        quitApp.setOnAction(event -> {
            //Todo Handle Click Event
        });

        VBox appMain = new VBox(30);
        appMain.setAlignment(Pos.CENTER);
        appMain.setId("appMain");

        HBox firstChild = new HBox(20);
        firstChild.setAlignment(Pos.CENTER);


        Label welMsg = new Label("Welcome To Your First Bank ATM Service");
        welMsg.setId("welMsg");

        firstChild.getChildren().add(welMsg);

        HBox secondChild = new HBox(20);
        secondChild.setAlignment(Pos.CENTER);

        secondChild.getChildren().addAll(deposit, transfer);

        //Append Child
        appMenu.getChildren().addAll(deposit, withdrawal, transfer, transactionHistory, quitApp);
        appMain.getChildren().add(firstChild);
        appLayout.setTop(appMenu);
        appLayout.setCenter(appMain);
        Scene appScene = new Scene(appLayout, 700,600);

        return appScene;
    }

    //Transaction Form
    public VBox transactionHistoryForm(){
        VBox transactionHistoryFormLayout = new VBox(30);
        transactionHistoryFormLayout.setAlignment(Pos.CENTER);

        HBox transactionHistoryFormBtnLayout = new HBox(30);
        transactionHistoryFormBtnLayout.setAlignment(Pos.CENTER);
        Button transferHistoryBtn = new Button("Transfer History");
        transferHistoryBtn.setId("transferHistoryBtn");
        transferHistoryBtn.setOnAction(event -> {
            Transaction trans = new Transaction();

        });
        Button transactionTransferHistory = new Button("Transaction Transfer History");
        transactionTransferHistory.setId("transactionTransferHistory");
        transactionTransferHistory.setOnAction(event -> {
            //Todo transaction History Handler
            Transaction transaction = new Transaction();
            transaction.transferList();

        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancelBtn");
        cancelBtn.setOnAction(e->{
            Dashboard goBack = new Dashboard();

            System.out.println("Home Btn");
            //Todo Implement Home
        });

        HBox info = new HBox(10);
        HBox backMsg = new HBox(10);

        info.setId("info");
        Label transactionInfo = new Label("Please Select The Transaction Type You wish To Review");
        Label homeMsg = new Label("Cancel To Go Home");
        //Append Child
        info.getChildren().add(transactionInfo);
        backMsg.getChildren().add(homeMsg);
        transactionHistoryFormBtnLayout.getChildren().addAll(transferHistoryBtn, transactionTransferHistory, cancelBtn);
        transactionHistoryFormLayout.getChildren().addAll(info, backMsg, transactionHistoryFormBtnLayout);
        return transactionHistoryFormLayout;
    }


    public  VBox verifyAccount(){

        VBox verifyAccountLayout = new VBox(30);
        verifyAccountLayout.setAlignment(Pos.CENTER);
        verifyAccountLayout.setId("verifyAcc");

        Label infoMsg = new Label("Please Verify That You Have A Account.");

        HBox verifyAccount = new HBox(20);
        verifyAccount.setAlignment(Pos.CENTER);
        verifyAccount.setId("vAcc");

        Label msg = new Label();
        Label acc_num = new Label("Enter Account Number");
        acc_num.setId("acc_num");
        TextField accNumFld = new TextField();
        accNumFld.setId("accNumFld");
        Button accNumBtn = new Button("SUBMIT");
        accNumBtn.setOnAction(event -> {
            String currAcc = accNumFld.getText();
            if (currAcc.length() != 0) {

                boolean flag = this.accessAcc(currAcc);
                if (flag){
                    msg.setText("Correct Account Number");
                    Deposit deposit = new Deposit();
                    deposit.depositLayout();
                    Dashboard app = new Dashboard();



                } else {
                    msg.setText("Invalid Account Number! Please Enter A Correct Account Number...");
                }

            } else {
                msg.setText("Account Number Is Required");
            }
        });
        accNumBtn.setId("accNumBtn");


        //Append Child
        verifyAccount.getChildren().addAll(acc_num,accNumFld);
        verifyAccountLayout.getChildren().addAll(infoMsg ,verifyAccount, accNumBtn, msg);

        return verifyAccountLayout;
    }

    //Verify Account Number
    public boolean accessAcc(String acc_num){
        UserDB acc = new UserDB();
        return acc.verifyAccount(acc_num);
    }
}
