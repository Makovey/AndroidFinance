package ru.makovey.financeCore.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ru.makovey.financeCore.database.SQLConnection;


public class Test {
    public static void main(String[] args) {
        try(Statement stmt = SQLConnection.getConncetion().createStatement(); ResultSet set = stmt.executeQuery("SELECT * FROM storage")) {
            System.out.println(set.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
