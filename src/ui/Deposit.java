package ui;

import database.UserDB;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Deposit {
    public Scene deposit(){

        BorderPane depositLayout = new BorderPane();

        HBox depositMenu = new HBox(30);
        depositMenu.setId("depositMenu");
        depositMenu.setAlignment(Pos.CENTER);

        Button homeBtn = new Button("Home");
        homeBtn.setId("homeBtn");
        homeBtn.setOnAction(event -> {
            Dashboard home = new Dashboard();
            depositLayout.setCenter(home.transactionHistoryForm());
        });
        Button quitAppBtn = new Button("QUIT");
        quitAppBtn.setId("quit");

        depositMenu.getChildren().addAll(homeBtn, quitAppBtn);
        depositLayout.setTop(depositMenu);
        //Append Child
        Scene app = new Scene(depositLayout, 700,500);


        return app;
    }
    public VBox depositLayout(){

        VBox deposit = new VBox(30);
        deposit.setAlignment(Pos.CENTER);

        Label displayMsg = new Label("Verify Your Account And Enter The Your Transaction Amount.");
        displayMsg.setId("displayMsg");

        HBox verifyAccount = new HBox(20);
        verifyAccount.setAlignment(Pos.CENTER);
        verifyAccount.setId("vAcc");


        Label acc_num = new Label("Enter Account Number");
        acc_num.setId("acc_num");
        TextField accNumFld = new TextField();
        accNumFld.setId("accNumFld");
        Button accNumBtn = new Button("SUBMIT");

        HBox depositLayout = new HBox(20);
        depositLayout.setAlignment(Pos.CENTER);
        depositLayout.setId("depositLayout");
        Label depositLbl = new Label("Enter Amount");
        depositLbl.setId("depositLbl");
        TextField depositFld = new TextField();
        depositFld.setId("depositFld");
        Label msg = new Label();
        Button amountBTn = new Button("SUBMIT");
        amountBTn.setId("amountBtn");
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
               if (checkAcc && conv >= 100){
                   //Todo implement Savings
                    ResultSet currentBal = saveMoney.getCurBalance(currAcc);
                   double get = 0;
                    while (currentBal.next()){
                        get = currentBal.getDouble("current_balance");
                        System.out.println("The Current Balance For This Account is D" +get);
                    }
                    //Add the amount passed in to the query balance GET
                   double updateBal = get + conv;
                    saveMoney.deposit(updateBal, currAcc);
                   System.out.println("The current account number is " + currAcc);
                   msg.setText("Account Updated.");
               } else {
                   msg.setText("Please Check Your Account Number And Deposit Cannot Be Less than D100");
               }
           } catch (NumberFormatException | SQLException ex){
               System.out.println(ex.getMessage());
               msg.setText("Wrong Input. Please Try Inputting A Correct Account And None Zero Double Value...");
           }
            } else {
                msg.setText("All Fields Required");
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
