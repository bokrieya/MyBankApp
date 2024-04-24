package com.example.mybankproject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField address_fld;

    @FXML
    private Label adress_lbl;

    @FXML
    private Label error_lbl;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField password_fld;

    @FXML
    private Label password_lbl;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String us =address_fld.getText();
                String ps =password_fld.getText();

                if (DataBasefonctions.checkLogin(us,ps)){
                    DataBasefonctions.SwitchScene(event,"Clientmenu.fxml",us,"");


                }else {
                    error_lbl.setText("User or password incorrect");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Essayez autrement!");
                    alert.show();
                }


            }

        });




    }

}