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
