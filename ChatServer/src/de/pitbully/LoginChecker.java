package de.pitbully;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginChecker {
    public boolean checkLogon(String login, String password){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_information",
                    "root", "1234");
            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select user_id, username, passwd from user_information");

//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("user_id") + " " +
//                        resultSet.getString("username") + " " +
//                        resultSet.getString("passwd"));
//            }
            System.out.println("LOG: login = " + login + ", " + password);
            int userId = checkUserName(connection, login);
            System.out.println("LOG: userid = " + userId);
            return checkPassword(connection, userId, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private int checkUserName(Connection connection,String login){
        try {
            Statement statement = connection.createStatement();
            ResultSet loginSet = statement.executeQuery("select user_id, username from user_information");
            while (loginSet.next()) {
                if (loginSet.getString("username").equals(login))
                    return loginSet.getInt("user_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    private Boolean checkPassword(Connection connection,int user_id, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet passwdSet = statement.executeQuery(("select user_id, passwd from user_information where user_id = " + user_id));
            System.out.println("LOG: login = " + user_id + ", " + password);
            while(passwdSet.next()) {
                if (passwdSet.getString("passwd").equals(password))
                    System.out.println("LOG: " + passwdSet.getString("passwd"));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            }
        return false;
    }
}
