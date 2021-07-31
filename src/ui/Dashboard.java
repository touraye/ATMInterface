package ui;
import javafx.scene.control.*;
import main.Login;// get thw ArrayList from the Login Class
import database.UserDB;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class Dashboard {
    Login user = new Login();
//    String currentUser = user.
    public Scene dashboard(){
        BorderPane appLayout = new BorderPane();

        HBox appMenu = new HBox(50);
        appMenu.setId("app");

        Button deposit = new Button("Deposit");
        deposit.setId("menuBtn");
        deposit.setOnAction(e->{
            //Todo Event Handler

        Deposit saveMoney = new Deposit();
        appLayout.setCenter(saveMoney.depositLayout());

        });
        Button transfer = new Button("Transfer");
        transfer.setId("menuBtn");
        transfer.setOnAction(event -> {
            //Todo Event Handler
            Transfer transfer1 = new Transfer();
            appLayout.setCenter(transfer1.transferLayout());
        });

        //Instantiate
        Transaction handler = new Transaction();

        SplitMenuButton transactionMenu = new SplitMenuButton();
        transactionMenu.setText("Transactions");
        transactionMenu.setId("menuSplBtn");

        MenuItem transfersS = new MenuItem("Sender Trans..");
        transfersS.setId("menuSplBtn");
        transfersS.setOnAction(event -> {
            appLayout.setCenter(handler.transactionTransLayoutS());
        });

        MenuItem transfersR = new MenuItem("Recipient Transs");
        transfersR.setId("menuSplBtn");
        transfersR.setOnAction(event -> {
            appLayout.setCenter(handler.transactionTransLayoutR());
        });

        MenuItem transaction = new MenuItem("Transactions");
        transaction.setId("menuSplBtn");
        transaction.setOnAction(event -> {
            appLayout.setCenter(handler.transactionLayout());
        });

        Button balEnquiry = new Button("Balance Enquiry");
        balEnquiry.setId("menuBtn");
        balEnquiry.setOnAction(event -> {
            appLayout.setCenter(handler.enquiryBalance());
        });

//        transactionMenu.getItems().addAll(transfersS, transfersR, transaction, balEnquiry);

        Button withdrawal = new Button("Withdrawal");
        withdrawal.setId("menuBtn");
        withdrawal.setOnAction(event -> {
            Withdrawal withdraw = new Withdrawal();
            appLayout.setCenter(withdraw.withdrawalLayout());
        });
        Button transactionHistory = new Button("Transaction History");
        transactionHistory.setId("menuBtn");
        transactionHistory.setOnAction(event -> {
            //Todo Event Handler
            appLayout.setCenter(this.verifyAccount());
        });
        Button quitApp = new Button("Quite");
        quitApp.setId("quiteApp");
        quitApp.setOnAction(event -> {
            //Todo Handle Click Event
            quiteApp();
        });

        VBox appMain = new VBox(30);
        appMain.setAlignment(Pos.CENTER);
        appMain.setId("appMain");

        HBox firstChild = new HBox(20);
        firstChild.setAlignment(Pos.CENTER);
        firstChild.setId("input-HBox");


        Label welMsg = new Label("Welcome To Your First Bank ATM Service");
        welMsg.setId("welMsg");

        firstChild.getChildren().add(welMsg);

        HBox secondChild = new HBox(20);
        secondChild.setAlignment(Pos.CENTER);
        secondChild.setId("input-HBox");
        secondChild.getChildren().addAll(deposit, transfer);

        //Append Child
        appMenu.getChildren().addAll(deposit, withdrawal,transfer, balEnquiry, quitApp);
        appMain.getChildren().add(firstChild);
        appLayout.setTop(appMenu);
        appLayout.setCenter(appMain);
        Scene appScene = new Scene(appLayout, 1000,600);
        appScene.getStylesheets().add("styles.css");
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
//            transaction.transferList();

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
        infoMsg.setId("showMsg");
        HBox verifyAccount = new HBox(20);
        verifyAccount.setAlignment(Pos.CENTER);
        verifyAccount.setId("vAcc");

        Label msg = new Label();
        msg.setId("alertMsg");
        Label acc_num = new Label("Enter Account Number");
        acc_num.setId("label");
        TextField accNumFld = new TextField();
        accNumFld.setId("field");

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
        accNumBtn.setId("actionBtn");


        //Append Child
        verifyAccount.getChildren().addAll(acc_num, accNumFld);
        verifyAccountLayout.getChildren().addAll(infoMsg, verifyAccount, accNumBtn, msg);

        return verifyAccountLayout;
    }

    //Verify Account Number
    public boolean accessAcc(String acc_num){
        UserDB acc = new UserDB();
        return acc.verifyAccount(acc_num);
    }

    //Handle Quite
    public void quiteApp(){
        System.exit(0);
    }
}
