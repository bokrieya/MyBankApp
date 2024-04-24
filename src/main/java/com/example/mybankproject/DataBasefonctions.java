package com.example.mybankproject;
import com.example.mybankproject.ClientmenuController;
import com.example.mybankproject.MaConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DataBasefonctions {
    Connection con = null;
    Statement st = null;
    @FXML
    public static Label SignUpMessage;
    @FXML
    public static Label loginBtn;


    DataBasefonctions() {
        con = MaConnection.connect();
        if (con != null) {
            try {
                st = con.createStatement();
            } catch (SQLException e) {
                System.out.println("err connection " + e.getMessage());
            }
        }

    }

    public static void SwitchScene(ActionEvent event, String FxmlFile, String username, String title) {
        Parent root = null;
        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DataBasefonctions.class.getResource(FxmlFile));

                root = loader.load();
                ClientmenuController loggedInController = loader.getController();
                loggedInController.setInformations(username);

            } catch (Exception e) {
                System.out.println("Switch to Scene exception " + e.getMessage());
            }
        } else {
            try {
                root = FXMLLoader.load(DataBasefonctions.class.getResource(FxmlFile));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();


    }



    public static boolean checkLogin(String address, String password) {
        boolean loginSuccessful = false;

        try {
            MaConnection con = new MaConnection();
            Connection ConnectDB = con.connect();

            String query = "SELECT * FROM client WHERE address = ? AND password = ?";
            PreparedStatement preparedStatement = ConnectDB.prepareStatement(query);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                loginSuccessful = true;
            }

            resultSet.close();
            preparedStatement.close();
            ConnectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginSuccessful;
    }

    public static boolean checkUserExists(String address) {
        boolean userExists = false;

        try {
            // Connect to the database
            MaConnection con = new MaConnection();
            Connection ConnectDB = con.connect();

            // Prepare the SQL query
            String query = "SELECT * FROM client WHERE address = ?";
            PreparedStatement preparedStatement = ConnectDB.prepareStatement(query);
            preparedStatement.setString(1, address);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set has any rows
            if (resultSet.next()) {
                userExists = true;
            }

            // Close the database resources
            resultSet.close();
            preparedStatement.close();
            ConnectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userExists;
    }




}