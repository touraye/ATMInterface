package main;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends Application {
    private Statement sts;
    private Connection cn;
    //Login
    public Login(){
       try {
           cn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/atm", "root", "touraye7");
           this.sts = cn.createStatement();
           System.out.println("DB Connected");
       } catch (SQLException ex){
           System.out.println(ex.getMessage()); //log error msg
           System.exit(0); //exit on failure
       }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ATM INTERFACE");
        primaryStage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}
