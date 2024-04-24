package com.example.mybankproject;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Client(Socket socket){
        try{
            this.socket=socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("error building a client");
            closeEverything(socket,bufferedReader,bufferedWriter);
        }}
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendMessageToServer(String messageToServer){
        try{
            bufferedWriter.write(messageToServer);
            System.out.println(bufferedWriter);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            //System.out.println("msg sent is "+messageToServer);


        }catch(IOException e){e.printStackTrace();
            System.out.println("error sending msg to server");
            // closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void receiveMessageFromServer(VBox vbox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromServer = bufferedReader.readLine();
                        System.out.println(messageFromServer );
                        MessagerieController.button_send(messageFromServer, vbox);
                    } catch (IOException e) {
                        System.out.println("Error receiving text: " + e.getMessage());
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start(); // start the thread
    }

    Connection con = null;
    Statement st = null;

    int id ;
    String name ;
    String address ;
    String password ;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Client(int id, String name, String address, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.password = password;
    }

    public Client() {
        con = MaConnection.connect();
        if (con != null) {
            try {
                st = con.createStatement();
            } catch (SQLException e) {
                System.out.println("error");
            }

        }

    }

    public ResultSet select(String req) {
        try {
            return st.executeQuery(req);
        } catch (SQLException e) {
            return null;
        }

    }

    public Client getClient(ResultSet rs) {
        if (rs != null) {
            try {
                Client client = null;
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String address = rs.getString(3);
                    String password = rs.getString(4);
                    client = new Client(id,name,address,password);
                }
                return client;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("rs null");
            return null;
        }
    }

    public int getId(ResultSet rs) {
        if (rs != null) {
            int name = 0;
            try {
                while (rs.next()) {
                    name = rs.getInt(1);

                }
                return name;

            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            System.out.println("rs null");
            return 0;
        }
    }
    public String getaddress(ResultSet rs) {
        if (rs != null) {
            String add = null;
            try {
                while (rs.next()) {
                    add = rs.getString(1);

                }
                return add;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("rs null");
            return null;
        }
    }

}

