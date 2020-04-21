package ru.makovey.financeCore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConnection {
    private static Connection conncetion;

    public static Connection getConncetion() {
        try {
            if (conncetion == null) conncetion = DriverManager.getConnection("jdbc:sqlite:db\\FinanceDB.db");
            return conncetion;
        } catch (SQLException e){
            Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
