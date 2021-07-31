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

public class Deposit {

    public VBox depositLayout(){

        VBox deposit = new VBox(30);
        deposit.setAlignment(Pos.CENTER);

        Label displayMsg = new Label("Verify Your Account And Enter The Your Transaction Amount Proceed With Your Deposit.");
        displayMsg.setId("displayMsg");

        HBox verifyAccount = new HBox(20);
        verifyAccount.setAlignment(Pos.CENTER);
        verifyAccount.setId("inputHBox");


        Label acc_num = new Label("Enter Account Number");
        acc_num.setId("label");
        TextField accNumFld = new TextField();
        accNumFld.setId("field");

        HBox depositLayout = new HBox(20);
        depositLayout.setAlignment(Pos.CENTER);
        depositLayout.setId("inputHBox");

        Label depositLbl = new Label("Enter Amount               ");
        depositLbl.setId("label");
        TextField depositFld = new TextField();
        depositFld.setId("field");

        Label msg = new Label();
        msg.setId("alertMsg");

        Button amountBTn = new Button("SUBMIT");
        amountBTn.setId("actionBtn");
        amountBTn.setOnAction(event -> {
            //Todo Event Handler
            UserDB saveMoney = new UserDB();//Trying Saving Money
            String currAcc = accNumFld.getText();
            String currMoney = depositFld.getText();
            if (currMoney.length() != 0 && currAcc.length() != 0){
                //Convert String to Double
           try {
               UserDB verifyAcc = new UserDB();
                boolean checkAcc = verifyAcc.verifyAccount(currAcc);
               verifyAcc.verifyAccount(currAcc); //get the verify account method
               double conv = Double.parseDouble(currMoney); //Convert the amount String from the TextField to Double
               if (checkAcc && conv >= 200){
                   //Todo implement Savings
                    ResultSet currentBal = saveMoney.getCurBalance(currAcc);
                   double get = 0;
                    while (currentBal.next()){
                        get = currentBal.getDouble("current_balance");
//                        System.out.println("The Current Balance For This Account is D" +get);
                    }
                    //Add the amount passed in to the query balance GET
                   double updateBal = get + conv;
                    saveMoney.deposit(updateBal, currAcc);
//                   System.out.println("The current account number is " + currAcc);
//                   msg.setText("Account Updated. Your New Balance Is D" +updateBal);
                   Alert.display("Deposit", "You have successfully updated with D", conv, " to your account and your current balance is D",updateBal);


                //Write into transactions
                   UserDB transactions = new UserDB();
                   String type = "deposit";
                   transactions.transactions(type, conv,currAcc);
               } else {
//                   msg.setText("Please Check Your Account Number And Deposit Cannot Be Less than D200");
                   AlertError.display("Error","Please Check Your Account Number And Deposit Cannot Be Less than D200");
               }
           } catch (NumberFormatException | SQLException ex){
               System.out.println(ex.getMessage());
//               msg.setText("Wrong Input. Please Try Inputting A Correct Account And None Zero Double Value...");
               AlertError.display("Wrong Input", "Please Try Inputting A Correct Account And None Zero Double Value");
           }
            } else {
//                msg.setText("All Fields Required");
                AlertError.display("Error", "All Fields Required");
            }
        });


        //Append Child
        verifyAccount.getChildren().addAll(acc_num, accNumFld);
        depositLayout.getChildren().addAll(depositLbl, depositFld);
        deposit.getChildren().addAll(displayMsg ,verifyAccount, depositLayout, amountBTn, msg);

        //Todo
        return  deposit;
    }


}
