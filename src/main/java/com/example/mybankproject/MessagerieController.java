package com.example.mybankproject;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;


public class MessagerieController implements Initializable {
    @FXML
    private TextField tf_message;
    @FXML
    private Button button_send;
    @FXML
    private ScrollPane sp_main;
    @FXML
    private VBox vbox_messages;
    @FXML
    void logout_btn(MouseEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private Client client;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("127.0.0.1", 9000));
            System.out.println("connected to server");
        } catch (IOException e) {
            System.out.println("erreur de creer client");
            e.printStackTrace();
        }
        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
                                                       @Override
                                                       public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                                                           sp_main.setVvalue((Double) t1 );}
                                                   }
        );
        client.receiveMessageFromServer(vbox_messages);
        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent actionEvent) {
                System.out.println(tf_message.getText());
                String messageToSend=tf_message.getText();
                if (!messageToSend.isEmpty()){
                    HBox hbox=new HBox();
                    hbox.setAlignment(Pos.CENTER_RIGHT);
                    hbox.setPadding(new Insets(5,5,5,10));
                    Text text=new Text(messageToSend);
                    TextFlow textFlow =new TextFlow(text);
                    textFlow.setStyle("-fx-color: rgb(239,255,255);"+"-fx-background-color: rgba(98,34,60,0.66);"+"-fx-background-radius:20px;");
                    textFlow.setPadding(new Insets(5,10,5,10));
                    text.setFill(Color.WHITE);
                    hbox.getChildren().add(textFlow);
                    vbox_messages.getChildren().add(hbox);
                    client.sendMessageToServer(messageToSend);
                    tf_message.clear();
                }
            }

        });

    }
    public static void button_send(String messageFromServer,VBox vbox){
        HBox hbox=new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5,5,5,10));
        Text text=new Text(messageFromServer);
        TextFlow textFlow =new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgba(98,34,60,0.29);"+"-fx-background-radius:20px;");
        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.WHITE);
        hbox.getChildren().add(textFlow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hbox);
            }
        });
    }


}
