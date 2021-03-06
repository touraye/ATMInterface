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

public class Withdrawal {
    final double lastBal = 300;
    public VBox withdrawalLayout(){

        VBox withdrawal = new VBox(30);
        withdrawal.setAlignment(Pos.CENTER);

        Label displayMsg = new Label("Verify Your Account And Enter The Your Transaction Amount To Proceed With Your Withdrawal.");
        displayMsg.setId("displayMsg");

        HBox verifyAccount = new HBox(20);
        verifyAccount.setAlignment(Pos.CENTER);
        verifyAccount.setId("inputHBox");


        Label acc_num = new Label("Enter Account Number");
        acc_num.setId("label");
        TextField accNumFld = new TextField();
        accNumFld.setId("field");

        HBox withdrawLayout = new HBox(20);
        withdrawLayout.setAlignment(Pos.CENTER);
        withdrawLayout.setId("inputHBox");
        Label withdrawalLbl = new Label("Enter Amount               ");
        withdrawalLbl.setId("label");
        TextField withdrawalFld = new TextField();
        withdrawalFld.setId("field");
        Label msg = new Label();
        Button amountBTn = new Button("SUBMIT");
        amountBTn.setId("actionBtn");
        amountBTn.setOnAction(event -> {
            //Todo Event Handler
            UserDB saveMoney = new UserDB();//Trying Saving Money
            String currAcc = accNumFld.getText();
            String currMoney = withdrawalFld.getText();
            if (currMoney.length() != 0 && currAcc.length() != 0){
                //Convert String to Double
                try {
                    UserDB verifyAcc = new UserDB();
                    boolean checkAcc = verifyAcc.verifyAccount(currAcc);
                    verifyAcc.verifyAccount(currAcc); //get the verify account method
                    double conv = Double.parseDouble(currMoney); //Convert the amount String from the TextField to Double

                    if (checkAcc && conv >= 200 && conv <= 2000){
                        //Todo implement Withdrawal
                        ResultSet currentBal = saveMoney.getCurBalance(currAcc);//Get the current balance from the account

                        double get = 0;

                            while (currentBal.next()){
                                get = currentBal.getDouble("current_balance");
//                                System.out.println("The Current Balance For This Account is D" +get);
                            }
                            boolean flag = minimumWithdrawal(get, conv);
                            if (flag){
                                //Add the amount passed in to the query balance GET
                                double updateBal = get - conv;
                                saveMoney.deposit(updateBal, currAcc);
//                                System.out.println("The current account number is " + currAcc);
//                                msg.setText("Account Updated. Your New Balance Is D" +updateBal);
//                                Alert.display("Withdrawal", "Account Updated. Your New Balance Is D", updateBal);
                                Alert.display("Withdrawal", "D", conv, " been successfully withdrawn from your account, your current balance is D",updateBal);
                                    //D300 has been successfully withdrawn from your account, your current balance is D12300

                                //Write to transactions
                                UserDB transactions = new UserDB();
                                String type = "withdrawal";
                                transactions.transactions(type, conv, currAcc);

                            } else {
//                                msg.setText("Your Left Over Cannot Be Less Than " +lastBal+ " Your Current Balance is D" +get);
                                AlertMin.display("Error", "our Left Over Cannot Be Less Than D", lastBal, "Your Current Balance is D", get);
                            }

//                        }

                    } else {
//                        msg.setText("Please Check Your Account Number And The Min Withdrawal Is D200.00, Max Is D2,000.00");
                        AlertError.display("Error", "Please Check Your Account Number And The Min Withdrawal Is D200.00, Max Is D2,000.00");
                    }
                } catch (NumberFormatException | SQLException ex){
                    System.out.println(ex.getMessage());
//                    msg.setText("Wrong Input. Please Try Inputting A Correct Account And None Zero Double Value...");
                    AlertError.display("Wrong Input", "Please Try Inputting A Correct Account And None Zero Double Value");
                }
            } else {
//                msg.setText("All Fields Required");
                AlertError.display("Error", "All Fields Required");
            }
        });


        //Append Child
        verifyAccount.getChildren().addAll(acc_num, accNumFld);
        withdrawLayout.getChildren().addAll(withdrawalLbl, withdrawalFld);
        withdrawal.getChildren().addAll(displayMsg ,verifyAccount, withdrawLayout, amountBTn, msg);



        //Todo
        return  withdrawal;
    }

    //Helper Method for withdrawal           700    -         200
    public boolean minimumWithdrawal(double curBal, double withdrawalAmount){
        //get your current balance and minus the new withdrawal : check whether the balance is more than or equals to 300
                    //500
            double newBal = curBal - withdrawalAmount;
            if (newBal >= lastBal){
//                System.out.println("Your left over money is D" +newBal);
                return true;
            }
            return false;
    }

}
