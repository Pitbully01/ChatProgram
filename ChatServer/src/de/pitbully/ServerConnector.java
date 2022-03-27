package de.pitbully;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServerConnector {
    public ServerConnector() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_information",
                                                            "root", "1234");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from user_information");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("user_id") + " " +
                                   resultSet.getString("username") + " " +
                                   resultSet.getString("passwd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
