package ui;

import database.UserDB;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Transfer {
    final double lastBal = 300;
    public VBox transferLayout(){

        VBox transferLayout = new VBox(30);
        transferLayout.setAlignment(Pos.CENTER);

        Label displayMsg = new Label("Verify Your Account And The Account You Are Sending To, Enter Amount To Proceed With Your Transfer.");
        displayMsg.setId("displayMsg");

        HBox transferSender = new HBox(20);
        transferSender.setAlignment(Pos.CENTER);
        transferSender.setId("inputHBox");

        Label sender_acc_num = new Label("Enter Sender Account Number   ");
        sender_acc_num.setId("label");
        TextField senderAccNumFld = new TextField();
        senderAccNumFld.setId("field");

        HBox transferRecipient = new HBox(20);
        transferRecipient.setAlignment(Pos.CENTER);
        transferRecipient.setId("inputHBox");

        Label recipient_acc_num = new Label("Enter Recipient Account Number");
        recipient_acc_num.setId("label");
        TextField recipientAccNumFld = new TextField();
        recipientAccNumFld.setId("field");

        HBox transferAmount = new HBox(20);
        transferAmount.setAlignment(Pos.CENTER);
        transferAmount.setId("inputHBox");

        Label transfer_amount_num = new Label("Enter Amount                                ");
        transfer_amount_num.setId("label");
        TextField transferAmountFld = new TextField();
        transferAmountFld.setId("field");

        Label msg = new Label();
        Label senderMsg = new Label();
        Label confirm = new Label();

        Button accNumBtn = new Button("SUBMIT");
        accNumBtn.setId("actionBtn");
        accNumBtn.setOnAction(event -> {
            UserDB saveMoney = new UserDB();//Trying Saving Money
            String sender = senderAccNumFld.getText();
            String recipient = recipientAccNumFld.getText();
            String amount = transferAmountFld.getText();
            if(sender.length() != 0 && recipient.length() != 0 && amount.length() !=0){
                //check for account match
                if(sender.equals(recipient)){
//                    confirm.setText("Transfer Unsuccessful");
//                    msg.setText("Transfers Is Only Between Two Different Amounts. Please Try Transferring To A Different Account.");
                    AlertError.display("Error", "Transfers Is Only Between Two Different Amounts. Please Try Transferring To A Different Account.");
                } else {

                try {
                    UserDB verifyAcc = new UserDB();
                    boolean checkSender = verifyAcc.verifyAccount(sender);//check the sender
                    boolean checkRecipient = verifyAcc.verifyAccount(recipient);//check the recipient
                    double conv = Double.parseDouble(amount);//convert amount String to a Double

                    if(checkSender && checkRecipient && conv >= 200 && conv <= 2000){
                        ResultSet currentBal = saveMoney.getCurBalance(sender);
                        double getRecipient = 0;
                        double getSender = 0;
                        while (currentBal.next()){
                            getSender = currentBal.getDouble("current_balance");//get sender's current balance
//                            System.out.println("The Current Balance For This Amount Is D" +getSender);
                        }

                        ResultSet currentBal2 = saveMoney.getCurBalance(recipient);
                        while (currentBal2.next()){
                            getRecipient = currentBal2.getDouble("current_balance");//get recipient's current balance
//                            System.out.println("the current balance for this account is D" +getRecipient);
                        }
                        Withdrawal validate = new Withdrawal();//
                        boolean flag = validate.minimumWithdrawal(getSender, conv);
                        if (flag){
                            //Add the amount passed in to the query balance GET
                            double updateSenderBal = getSender - conv;
                            saveMoney.deposit(updateSenderBal, sender);//rewrite to sender account
//                            confirm.setText("Transfer SuccessFull");
//                            senderMsg.setText("Sender: your is account deducted width. Your New Balance Is D" +updateSenderBal);

                            //Add the amount passed in to the query balance GET
                            double updateRecipientBal = getRecipient + conv;
                            saveMoney.deposit(updateRecipientBal, recipient);
//                            msg.setText("Recipient's Account Updated. New Balance Is D" +updateRecipientBal);
                                //String title,String SenderInfo,double sentBal, String sendMsg, double SenderBal, String recipientInfo, double updateBal, String accText
                            AlertTrans.display("Transfer","Sender: your account was deducted with D",conv," Your new balance is D", updateSenderBal, " D",conv, " has been added to Recipient's account");

                            //Write to Transfer Transactions
                            UserDB transfers = new UserDB();
                            transfers.transferTrans(sender, recipient,conv);

                            //Write a Transaction for the recipient
                            String type = "Transfer";
                            transfers.transactions(type, conv, recipient);

                        } else {
                            confirm.setText("Your Left Over Cannot Be Less Than " +lastBal+ " Your Current Balance is D" +getSender);

                        }

                    } else {
                        msg.setText("Please Check Your Account Number And Transfer Cannot Be Less than D200 Or More Than D2,000");
                    }

                } catch (NumberFormatException | SQLException ex){
                    System.out.println(ex.getMessage());
                    msg.setText("Wrong Input. Please Try Inputting A Correct Account And None Zero Value...");
                }
                }
            } else {
                msg.setText("All Fields Are Required!");
            }
        });

        transferSender.getChildren().addAll(sender_acc_num, senderAccNumFld);
        transferRecipient.getChildren().addAll(recipient_acc_num, recipientAccNumFld);
        transferAmount.getChildren().addAll(transfer_amount_num, transferAmountFld);
        transferLayout.getChildren().addAll(displayMsg, transferSender, transferRecipient, transferAmount, accNumBtn,confirm, senderMsg, msg);

        return transferLayout;
    }
}
