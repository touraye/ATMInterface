package main;

import database.UserDB;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class Login extends Application {
    private Statement sts;
    private Connection cn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ATM INTERFACE");
        primaryStage.show();
    }

    public static void main(String args[]){
        Scanner input = new Scanner(System.in);

        String uID;
        String uPin;

        int counter = 0;
        do{
            System.out.println("Enter your user ID");
            uID = input.next();
            System.out.println("Enter your Pin");
            uPin = input.next();
            UserDB validate = new UserDB();//instantiate the UserDB class
          boolean auth = validate.Login(uID, uPin);//making a method call and passing the params(uId, uPin)
            if(auth){
                launch(args);// launch the APP.
                break;
            }
            System.out.println("Incorrect user name or pin. Please try again!");
            counter++;
            System.out.println(counter+ " out of 3 trail remain after that your card will Block!");
        } while (counter < 3);

        System.out.println("Card is Block!");//Block the ATM Card
        System.exit(0);//Exit the APP.

    }
}
