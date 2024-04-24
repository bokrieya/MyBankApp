
        package com.example.mybankproject;
        import java.sql.Date;
        import java.time.LocalDate;

        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.input.MouseDragEvent;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.VBox;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

        import java.util.ResourceBundle;


        public class ClientmenuController {

            @FXML
            private VBox container;
            @FXML
            private Label amount;


            @FXML
            private Label expenses;

            @FXML
            private Label income;

            @FXML
            private Label num;

            @FXML
            private Label savamount;

            @FXML
            private Label savnum;

            @FXML
            private Label savtype;

            @FXML
            private Label type;
            @FXML
            private TextField Payee_Address_fld;
            @FXML
            private TextField amount_fld;
            @FXML
            private TextField description_fld;

            public void setInformations(String address){
                Client client = new Client();
                Client user = client.getClient(client.select("select * from client where address='"+address+"'"));
                WelcomeLabel.setText(user.getName());
                Account account = new Account();
                Account acc1 = account.getAccount(account.select("select * from account where id_client='"+user.getId()+"' and type='Checking account'"));
                Account acc2 = account.getAccount(account.select("select * from account where id_client='"+user.getId()+"' and type='Savings account'"));
                amount.setText(acc1.getAmount()+"");
                num.setText(acc1.getAccount_num()+"");
                type.setText(acc1.getType()+"");
                savamount.setText(acc2.getAmount()+"");
                savtype.setText(acc2.getType()+"");
                savnum.setText(acc2.getAccount_num()+"");
                income.setText(acc1.getIncome()+acc2.getIncome()+"");
                expenses.setText(acc1.getExpenses()+acc2.getExpenses()+"");
            }




                @FXML
            void accounts_btn(ActionEvent event) throws IOException, SQLException {
                    container.getChildren().clear();
                    container.getChildren().add(createTableA());
            }
            @FXML
            private Label WelcomeLabel;

            @FXML
            void dashboard_btn(ActionEvent event) throws IOException {
                Client c=new Client();

                String us=c.getaddress(c.select("select address from client where name='"+WelcomeLabel.getText()+"'"));
                DataBasefonctions.SwitchScene(event,"Clientmenu.fxml",us,"");
            }

            @FXML
            void logout_btn(MouseEvent event) throws IOException{

                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

            @FXML
            void msg_btn(MouseEvent event) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("Messagerie.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            public TableView<transaction> createTable() {
                int username= Integer.parseInt(num.getText());
                TableView<transaction> tableView = new TableView<transaction>();
                tableView.setPrefWidth(750);
                tableView.setPrefHeight(650);
                tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                TableColumn<transaction, Date> dateColumn = new TableColumn<>("Date");
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                TableColumn<transaction, Integer> transfered_fromColumn = new TableColumn<>("Transfered from");
                transfered_fromColumn.setCellValueFactory(new PropertyValueFactory<>("transfer_from"));

                TableColumn<transaction, Integer> transfered_toColumn = new TableColumn<>("Transfered to");
                transfered_toColumn.setCellValueFactory(new PropertyValueFactory<>("transfer_to"));

                TableColumn<transaction, Float> amountColumn = new TableColumn<>("Amount");
                amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

                TableColumn<transaction, String> statusColumn = new TableColumn<>("Status");
                statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

                TableColumn<transaction, String> transfer_typeColumn = new TableColumn<>("Transfer type");
                transfer_typeColumn.setCellValueFactory(new PropertyValueFactory<>("transfer_type"));

                TableColumn<transaction, String> descriptiondColumn = new TableColumn<>("Description");
                descriptiondColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

                tableView.getColumns().add(dateColumn);
                tableView.getColumns().add(transfered_fromColumn);
                tableView.getColumns().add(transfered_toColumn);
                tableView.getColumns().add(amountColumn);
                tableView.getColumns().add(statusColumn);
                tableView.getColumns().add(transfer_typeColumn);
                tableView.getColumns().add(descriptiondColumn);

                //Account account = new Account();

                //Account account1 = account.getAccount(account.select("select * from account where id_client=184500"));

                transaction user = new transaction();
                //ResultSet resultSet = null;

                tableView.setItems(user.getTrans(user.select("select * from transaction where transfer_from='"+username+"'")));
                tableView.setFixedCellSize(30);
                return tableView;
            }

            public TableView<Account> createTableA() throws SQLException {
                TableView<Account> tableView = new TableView<Account>();
                tableView.setPrefWidth(750);
                tableView.setPrefHeight(650);
                tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                //TableColumn<Account, String> nom_clientCol = new TableColumn<>("");
               // nom_clientCol.setCellValueFactory(new PropertyValueFactory<>(""));

                TableColumn<Account, Integer> NuméroAccountCol = new TableColumn<>("account_num");
                NuméroAccountCol.setCellValueFactory(new PropertyValueFactory<>("account_num"));
                TableColumn<Account, String> Account_TypeCol = new TableColumn<>("type");
                Account_TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                TableColumn<Account, Float> AmountCol = new TableColumn<>("amount");
                AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
                TableColumn<Account, Float> incomeCol = new TableColumn<>("income");
                incomeCol.setCellValueFactory(new PropertyValueFactory<>("income"));
                TableColumn<Account, Float> expensesCol = new TableColumn<>("expenses");
                expensesCol.setCellValueFactory(new PropertyValueFactory<>("expenses"));


                TableColumn<Account, Integer>  Account_CodeCol= new TableColumn<>("code");
                Account_CodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));


                TableColumn<Account, String> Account_CurrencyCol = new TableColumn<>("currency");
                Account_CurrencyCol.setCellValueFactory(new PropertyValueFactory<>("currency"));


                TableColumn<Account, Integer>  Initial_BalanceCol= new TableColumn<>("initial_balance");
                Initial_BalanceCol.setCellValueFactory(new PropertyValueFactory<>("initial_balance"));


                tableView.getColumns().add(NuméroAccountCol);
                tableView.getColumns().add(Account_TypeCol);
                tableView.getColumns().add(AmountCol);
                tableView.getColumns().add(incomeCol);
                tableView.getColumns().add(expensesCol);
                tableView.getColumns().add(Account_CodeCol);
                tableView.getColumns().add(Account_CurrencyCol);
                tableView.getColumns().add(Initial_BalanceCol);
                Client c =new Client();
                int id=c.getId(c.select("select id from client where name='"+WelcomeLabel.getText()+"'"));
                Account acc = new Account();
                tableView.setItems(acc.getAccounts(acc.select("select * from account where id_client='"+id+"'")));
                tableView.setFixedCellSize(30);
                return tableView;
            }

            @FXML
            void transaction_btn(ActionEvent event) throws IOException{
                container.getChildren().clear();

                container.getChildren().add(createTable());



            }

            @FXML
            void sendMoney(ActionEvent event) {
                System.out.println("bhebhdzbjhdzej");
                
            int transfer_from= Integer.parseInt(num.getText());
            int transfer_to= Integer.parseInt(Payee_Address_fld.getText());
            float amount= Float.parseFloat(amount_fld.getText());
            String description= description_fld.getText();
            transaction transaction=new transaction();
            transaction.inserer(Date.valueOf(LocalDate.now()),transfer_from,transfer_to,amount,description);

            }


        }

