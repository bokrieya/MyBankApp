package com.example.mybankproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Account {
    int account_num ;
    int id_client;
    String type;
    float amount;
    float income;
    float expenses;
    String code;
    String Currency;
    float initial_balance;

    public Account(int account_num, int id_client, String type, float amount, float income, float expenses, String code, String currency, float initial_balance) {
        this.account_num = account_num;
        this.id_client = id_client;
        this.type = type;
        this.amount = amount;
        this.income = income;
        this.expenses = expenses;
        this.code = code;
        this.Currency = currency;
        this.initial_balance = initial_balance;
    }

    public int getAccount_num() {
        return account_num;
    }

    public int getId_client() {
        return id_client;
    }

    public String getType() {
        return type;
    }

    public float getAmount() {
        return amount;
    }

    public float getIncome() {
        return income;
    }

    public float getExpenses() {
        return expenses;
    }

    public String getCode() {
        return code;
    }

    public String getCurrency() {
        return Currency;
    }

    public float getInitial_balance() {
        return initial_balance;
    }

    Connection con = null;
    Statement st = null;


    public Account() {
        con = MaConnection.connect();
        if (con != null) {
            try {
                st = con.createStatement();
            } catch (SQLException e) {
                System.out.println("error");
            }

        }

    }
    public Account getAccount(ResultSet rs) {
        if (rs != null) {
            try {
                Account acc = null;
                while (rs.next()) {
                    int account_num = rs.getInt(1);
                    int id_client = rs.getInt(2);
                    String type = rs.getString(3);
                    float amount = rs.getFloat(4);
                    float income = rs.getFloat(5);
                    float expenses = rs.getFloat(6);
                    String code = rs.getString(7);
                    String currency = rs.getString(8);
                    float initial_balance = rs.getFloat(9);
                    acc = new Account(account_num,id_client,type,amount,income,expenses,code,currency,initial_balance);

                }
                return acc;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else{
            System.out.println("rs null");
            return null;
        }
    }


    public ObservableList<Account> getAccounts(ResultSet rs) throws SQLException {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        if (rs != null) {
            try {
                while (rs.next()) {
                    int account_num = rs.getInt(1);
                    int id_client = rs.getInt(2);
                    String type = rs.getString(3);
                    float amount = rs.getFloat(4);
                    float income = rs.getFloat(5);
                   float expenses = rs.getFloat(6);
                    String code = rs.getString(7);
                    String currency = rs.getString(8);
                    float initial_balance = rs.getFloat(9);
                    Account acc = new Account(account_num,id_client,type,amount,income,expenses,code,currency,initial_balance);
                    accounts.add(acc);
                }return accounts;
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    } else{
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
}
