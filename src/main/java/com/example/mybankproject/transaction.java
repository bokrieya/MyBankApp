package com.example.mybankproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;


public class transaction {
    int id;
    Date date;
    int transfer_from;
    int transfer_to;

    float amount;
    String status;
    String transfer_type;
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTransfer_from() {
        return transfer_from;
    }

    public void setTransfer_from(int transfer_from) {
        this.transfer_from = transfer_from;
    }

    public int getTransfer_to() {
        return transfer_to;
    }

    public void setTransfer_to(int transfer_to) {
        this.transfer_to = transfer_to;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public transaction(int id,Date date, int transfer_from, int transfer_to, float amount, String status, String transfer_type, String description) {
        this.id=id;
        this.date = date;
        this.transfer_from = transfer_from;
        this.transfer_to = transfer_to;
        this.amount = amount;
        this.status = status;
        this.transfer_type = transfer_type;
        this.description = description;
    }


    Connection con = null;
    Statement st = null;

    public transaction() {
        con = MaConnection.connect();
        if (con != null) {
            try {
                st = con.createStatement();
            } catch (SQLException e) {
                System.out.println("error");
            }
        }
    }

    public int inserer(Date date,int transfer_from, int transfer_to, float amount, String description) {
        if (con != null) {
            String req = "insert into transaction (date, transfer_from, transfer_to,amount,description) values (?,?,?,?,?)";
            try {
                PreparedStatement ps = con.prepareStatement(req);

                ps.setDate(1, date);
                ps.setInt(2, transfer_from);
                ps.setInt(3, transfer_to);
                ps.setFloat(4, amount);
                ps.setString(5, description);


                return ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("error insert " + e.getMessage());
            }
        }
        return 0;
    }

    public ObservableList<transaction> getTrans(ResultSet rs) {
        ObservableList<transaction> users = FXCollections.observableArrayList();
        if (rs != null) {
            try {
                while (rs.next()) {
                    int id= rs.getInt(1);
                    Date date= rs.getDate(2);
                    int transf= rs.getInt(3);
                    int transt= rs.getInt(4);
                    float amount= rs.getFloat(5);
                    String status= rs.getString(6);
                    String trantype= rs.getString(7);
                    String description= rs.getString(8);
                    transaction transaction = new transaction(id,date,transf,transt,amount,status,trantype,description);
                    users.add(transaction);
                }
                return users;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("rs null");
            return null;
        }
    }



    public ResultSet select(String req) {
        try {
            return st.executeQuery(req);
        } catch (SQLException e) {
            return null;
        }

    }

    public String verifierValue(ResultSet rs) {
        if (rs != null) {
            String name = null;
            try {
                while (rs.next()) {
                    name = rs.getString(1);

                }
                return name;

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

