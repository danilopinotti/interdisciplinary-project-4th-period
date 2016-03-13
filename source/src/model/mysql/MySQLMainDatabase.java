package model.mysql;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by danilopinotti on 17/11/15.
 */
public class MySQLMainDatabase {
    private static final String URL = "jdbc:mysql://127.0.0.1/";
    private static final String DATABASE = "pi_2015_2";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    protected Connection conn;
    protected PreparedStatement pstm;
    protected Statement stm;

    protected void openDB() {

        try {
            Class.forName(DRIVER_CLASS).newInstance();
            conn = (Connection) DriverManager.getConnection(URL + DATABASE, USER, PASSWORD);
            stm = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Database open Error:+\n\t" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void closeDB() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database close Error:+\n\t" + e.getMessage());
        }
    }
}
