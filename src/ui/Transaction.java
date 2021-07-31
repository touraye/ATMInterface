package ui;

import database.UserDB;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {

    public VBox transactionLayout(){

        VBox transactions = new VBox(30);
        transactions.setAlignment(Pos.CENTER);

        Label displayMsg = new Label("Please Verify Your Account To View Your Transactions History");
        displayMsg.setId("displayMsg");

        HBox transactionsAccountLayout = new HBox(20);
        transactionsAccountLayout.setAlignment(Pos.CENTER);
        transactionsAccountLayout.setId("inputHBox");

        Label transAccountLbl = new Label("Enter Account Number");
        transAccountLbl.setId("label");
        TextField transAccountFld = new TextField();
        transAccountFld.setId("field");

        Label msg = new Label();
        msg.setId("alertMsg");
        Button transBtn = new Button("SUBMIT");
        transBtn.setId("actionBtn");
        transBtn.setOnAction(event -> {

            //Get account number
            String acc = transAccountFld.getText();
            if (acc.length() != 0){
                msg.setText("Account Number Provided");
                UserDB verifyAccount = new UserDB();
                boolean validate = verifyAccount.verifyAccount(acc);
                if (validate){
                    msg.setText("Account Validated");
                    try {
                        UserDB trans = new UserDB();
                        ResultSet query = trans.transactionList(acc);
                        while (query.next()){
                            String list = query.getString(1)+ " " +query.getString(2)+ " " +query.getString(3)+ " " +query.getString(4);
                            msg.setText(list);
                            System.out.println(list);
                            double convertList = Double.parseDouble(list);
//                            Alert.display("Transaction",  convertList);
                        }
                    } catch (SQLException ex){
                        System.out.println(ex.getMessage());
                    }

                } else {
                    msg.setText("Account Not Validated. Provide A Correct Account Number");
                }
            } else {
                msg.setText("Please Provide An Account Number");
            }
        });

        //Append Child
        transactionsAccountLayout.getChildren().addAll( transAccountLbl, transAccountFld);
        transactions.getChildren().addAll(displayMsg, transactionsAccountLayout, transBtn, msg);


        //Todo Return VBox
        return transactions;
    }


    //Recipient Transaction History
    public VBox transactionTransLayoutR(){

        VBox transactions = new VBox(30);
        transactions.setAlignment(Pos.CENTER);

        Label displayMsg = new Label("Please VerifyYourAccount To View Your Received Transfers Transactions History");
        displayMsg.setId("displayMsg");

        HBox transactionsAccountLayout = new HBox(20);
        transactionsAccountLayout.setAlignment(Pos.CENTER);
        transactionsAccountLayout.setId("inputHBox");

        Label transAccountLbl = new Label("Enter Account Number");
        transAccountLbl.setId("label");
        TextField transAccountFld = new TextField();
        transAccountFld.setId("field");

        Label msg = new Label();
        msg.setId("alertMsg");

        Button transBtn = new Button("SUBMIT");
        transBtn.setId("actionBtn");
        transBtn.setOnAction(event -> {

            //Get account number
            String acc = transAccountFld.getText();
            if (acc.length() != 0){

                UserDB verifyAccount = new UserDB();
                boolean validate = verifyAccount.verifyAccount(acc);
                if (validate){
                    msg.setText("Account Validated");

                    try {
                        UserDB handleRecipient = new UserDB();
                        ResultSet query = handleRecipient.transferRecipient(acc);
                        System.out.println("Date \t\t\t Recipient  Sender  Amount");
                        while (query.next()){
                            System.out.println(""+query.getString("transfer_date")+ " " +query.getString("sender")+ " " +query.getString("recipient")+ " " +query.getString("amount"));
                            String message = "Date: "+query.getString(1)+" Sender: "+query.getString(2)+" Recipient "+query.getString(3)+" Amount D"+query.getString(4);
                            msg.setText(message);
                            double convertMessage = Double.parseDouble(message);
//                            Alert.display("Transaction Successful", convertMessage);
                        }

                    } catch (SQLException ex){
                        System.out.println(ex.getMessage());
                    }
                } else {
                    msg.setText("Account Not Validated. Provide A Correct Account Number");
                }
            } else {
                msg.setText("Please Provide An Account Number");
            }
        });

        //Append Child
        transactionsAccountLayout.getChildren().addAll( transAccountLbl, transAccountFld);
        transactions.getChildren().addAll(displayMsg, transactionsAccountLayout, transBtn, msg);


        //Todo Return VBox
        return transactions;
    }


    //Sender Transfer Transaction History
    public VBox transactionTransLayoutS(){

        VBox transactions = new VBox(30);
        transactions.setAlignment(Pos.CENTER);

        Label displayMsg = new Label("Please VerifyYourAccount To View Your Transfers Transactions History");
        displayMsg.setId("displayMsg");

        HBox transactionsAccountLayout = new HBox(20);
        transactionsAccountLayout.setAlignment(Pos.CENTER);
        transactionsAccountLayout.setId("inputHBox");

        Label transAccountLbl = new Label("Enter Account Number");
        transAccountLbl.setId("label");
        TextField transAccountFld = new TextField();
        transAccountFld.setId("field");

        Label msg = new Label();
        msg.setId("alertMsg");

        //Create a Dialog
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Sender Transactions History!");


        Button transBtn = new Button("SUBMIT");
        transBtn.setId("actionBtn");
        transBtn.setOnAction(event -> {
            msg.setText("Transfers Trans Btn");

            //Get account number
            String acc = transAccountFld.getText();
            if (acc.length() != 0){

                UserDB verifyAcc = new UserDB();
                boolean validate = verifyAcc.verifyAccount(acc);
                if (validate){
                    UserDB senderHandler = new UserDB();
                    try {
                        ResultSet get = senderHandler.transferSender(acc);
                        System.out.println("Date \t Sender  Recipient  Amount");
                        String result = null;
                        while (get.next()){
                             result = "Date: "+get.getString("transfer_date")+ " Sender: " +get.getString("sender")+ " Recipient " +get.getString("recipient")+ " Amount D" +get.getString("amount");
                            
                            System.out.println(""+get.getString("transfer_date")+ " " +get.getString("sender")+ " " +get.getString("recipient")+ " " +get.getString("amount"));
                            msg.setText(result);
                            dialog.setContentText(result);
//                            dialog.getDialogPane().getButtonTypes().add(transBtn);
                        }
                        double convertResult = Double.parseDouble(result);
//                        Alert.display("Transaction Successful", convertResult);
                    } catch (SQLException ex){
                        System.out.println(ex.getMessage());
                    }
                    //Todo
                } else {
                    msg.setText("Account Not Validated. Provide A Correct Account Number");
                }
            } else {
                msg.setText("Please Provide An Account Number");
            }
        });

        //Append Child
        transactionsAccountLayout.getChildren().addAll( transAccountLbl, transAccountFld);
        transactions.getChildren().addAll(displayMsg, transactionsAccountLayout, transBtn, msg);


        //Todo Return VBox
        return transactions;
    }

    //Enquiry
    public VBox enquiryBalance(){
        VBox transactions = new VBox(30);
        transactions.setAlignment(Pos.CENTER);

        Label displayMsg = new Label("Please Verify That You're An Account Holder To Enquiry Your Account.");
        displayMsg.setId("displayMsg");

        HBox enquiryLayout = new HBox(20);
        enquiryLayout.setAlignment(Pos.CENTER);
        enquiryLayout.setId("inputHBox");

        Label enquiryLbl = new Label("Enter Account Number");
        enquiryLbl.setId("label");
        TextField enquiryFld = new TextField();
        enquiryFld.setId("field");

        Label msg = new Label();
        msg.setId("alertMsg");

        Button enquiryBtn = new Button("SUBMIT");
        enquiryBtn.setId("actionBtn");
        enquiryBtn.setOnAction(e-> {
            String accNum = enquiryFld.getText();
            if (accNum.length() != 0){
                UserDB verifyAcc = new UserDB();
                boolean validate = verifyAcc.verifyAccount(accNum);
                if (validate){
                    try {
                        UserDB enquiry = new UserDB();
                        ResultSet query = enquiry.getCurBalance(accNum);
                        while (query.next()){
                            double detail = query.getDouble("current_balance");
//                            msg.setText("Your Current Balance is D" +(detail));
                            double amount = detail;
                            //String title,String info, double updatedBal, String New, double Msg
//                            display("Balance Enquiry", "Your current balance is D", detail);
                            AlertTransactions.display("Balance Enquiry", "Your current balance is D", detail);
                        }
                    } catch (SQLException ex){
                        System.out.println(ex.getMessage());
                    }
                } else {
//                    msg.setText("Incorrect Account Number! Try Provide A Correct Account Number.");
                    AlertError.display("Error", "Incorrect Account Number! Try Provide A Correct Account Number.");
                }

            } else {
//                msg.setText("Please Provide An Account...");
                AlertError.display("Error", "Please Provide An Account");
            }

        });


        //Append Child
        enquiryLayout.getChildren().addAll(enquiryLbl, enquiryFld);
        transactions.getChildren().addAll(displayMsg, enquiryLayout, enquiryBtn, msg);

        return transactions;

    }

    public static void display(String title,String info, double updatedBal){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(650);
        window.setMinHeight(250);


        Label infoMsg = new Label();
        infoMsg.setText(info);
        infoMsg.setId("alertMsg");

        Label accountUpdate = new Label();
        accountUpdate.setText(String.valueOf(updatedBal));
        accountUpdate.setId("alertMsg");

        javafx.scene.control.Button closeBtn = new javafx.scene.control.Button("Close");
        closeBtn.setOnAction(event -> window.close());

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);

        HBox showMsg = new HBox(0);
        showMsg.setAlignment(Pos.CENTER);
        showMsg.getChildren().addAll(infoMsg, accountUpdate);

        layout.getChildren().addAll(showMsg, closeBtn);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

}
