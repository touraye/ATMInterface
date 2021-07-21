package database;

import java.sql.*;
import java.util.Scanner;

public class UserDB {
    private Statement sts;
    private Connection cn;

    public UserDB(){
        try {
            cn = DriverManager.getConnection("jdbc:mysql://127.0.01/ATM", "root", "touraye7");
            this.sts = cn.createStatement();
            System.out.println("DB connected");
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    //Handle User Login
    public boolean Login(String uuid, String pin){
        try{
            //select count(*) from users where user_id = '123' and user_pin = 'code12';
            String query = "SELECT COUNT(*) FROM users WHERE user_id = '"+uuid+"' AND user_pin = '"+pin+"'";
            ResultSet getUSer = this.sts.executeQuery(query);
            int flag = -1;
            while(getUSer.next()){
                flag = getUSer.getInt(1);
            }
            if(flag == 0){
                return false;
            }
            return true;
        } catch (SQLException ex){
            return false;
        }
    }

    //Query
    public ResultSet selectQuery(String argQuery){
        ResultSet query = null;
        try {
            query = this.sts.executeQuery(argQuery);
        } catch (SQLException ex) {

        }
        return query;
    }

    //Handle Transaction List
    public ResultSet transactionList(String account){
        try{
            String query = "SELECT * FROM transactions WHERE account = '"+account+"'";
            ResultSet result = this.sts.executeQuery(query);
            return result;
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //Handle Transfer for Sender
    public ResultSet transferSender(String account){
        try {
            String query = "SELECT * FROM transfer_trans WHERE sender = '"+account+"'";
            ResultSet fetch = this.sts.executeQuery(query);
            return  fetch;
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //Handle Transfer for Recipient
    public ResultSet transferRecipient(String account){
        try {
            String query = "SELECT * FROM transfer_trans WHERE recipient = '"+account+"'";
            ResultSet fetch = this.sts.executeQuery(query);
            return  fetch;
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //Deposit Into Account
    public void deposit(double amount, String account){
        //update account set current_balance = 5000 where acc_num = 'acc001';
        try{
            String query = "UPDATE account SET current_balance = '"+amount+"' WHERE acc_num = '"+account+"'";
            this.sts.execute(query);
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    //Verify Account
    public boolean verifyAccount(String account_num){
        try {
            String query = "SELECT COUNT(*) FROM account WHERE acc_num = '"+account_num+"'";
            ResultSet result = this.sts.executeQuery(query);
            int flag = -1;
            while (result.next()){
                flag = result.getInt(1);
            }
            if(flag == 0){
                return false;
            }
            return true;

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }


    //Get the current balance from the current account pass in
    public ResultSet getCurBalance(String acc_num){
        try {
            String fetch = "SELECT current_balance FROM account WHERE acc_num = '"+acc_num+"'";
            ResultSet getCurBal = this.sts.executeQuery(fetch);
            return getCurBal;
        } catch (SQLException ex){
            return  null;
        }
    }


    //Transactions for Deposit, Withdrawal and Transfer
    public void transactions(String type, double amount, String account){
        try {
            String query = "INSERT INTO transactions(trans_type, trans_amount, account) values('"+type+"', '"+amount+"', '"+account+"')";
            this.sts.execute(query);
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    //Transfers Transaction
    public void transferTrans(String sender, String recipient, double amount){
        try {
                String query = "INSERT INTO transfer_trans(sender, recipient, amount) values('"+sender+"', '"+recipient+"', '"+amount+"')";
                this.sts.execute(query);
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    //Enquiry Account
    public ResultSet enquiry(String account){
        //select current_balance  from account where acc_num = 'acc001';
        try {
            String query = "SELECT current_balance FROM account WHERE acc_num = "+account;
            ResultSet select = this.sts.executeQuery(query);
            return  select;
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }


//    public  static void main(String args[]){
//        UserDB db = new UserDB();
////        select count(*) from users where user_id = '133' and user_pin = 'code14';
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter user User ID");
//        String id = input.next();
//        System.out.println("Enter user Pin");
//        String pin = input.next();
//        boolean flag = db.Login(id, pin);
//        System.out.println(flag);
//        System.out.println("uuid " +id+ " and password " +pin);
//    }
}
