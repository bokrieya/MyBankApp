package com.example.mybankproject;

import java.sql.*;

public class MaConnection {

    public MaConnection() {
    }

    public static Connection connect() {
        Connection conn = null;
        String URL = "jdbc:mysql://localhost/mybankproject";
        String user = "root";
        String mp = "";
        String nomDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(nomDriver);
            conn = DriverManager.getConnection(URL, user, mp);
            System.out.println("Connection success .......");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Connection failed ......");
        }
        return conn;
    }

}