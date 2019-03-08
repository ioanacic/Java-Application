package application;
import java.sql.Date;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UsersController {
	@FXML
	private TableView<Users> tableView;
	@FXML
	private TableColumn<Users, String> fnameColumn;
	@FXML
	private TableColumn<Users, String> lnameColumn;
	@FXML
	private TableColumn<Users, String> usernameColumn;
	@FXML
	private TableColumn<Users, String> passwordColumn;
	@FXML
	private TableColumn<Users, String> permissionColumn;
	@FXML
	private TableColumn<Users, String> emailColumn;
	
	private ObservableList<Users> userData = FXCollections.observableArrayList();
	
	
	@FXML
	private void initialize() { // se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER
		getDatabaseData();
		tableView.setItems(userData);
		// Initialize the person table with the three columns.

		fnameColumn.setCellValueFactory(cellData -> cellData.getValue().fnameProperty());
		lnameColumn.setCellValueFactory(cellData -> cellData.getValue().lnameProperty());
		usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
		passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
		permissionColumn.setCellValueFactory(cellData -> cellData.getValue().permissionProperty());
		emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		
		
		tableView.setEditable(true);
		permissionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		
		permissionColumn.setOnEditCommit(event -> {
			Users u = event.getRowValue();
			u.permission.setValue(event.getNewValue());
			updateDatabase(u);
		});
		
	}
	
	private void updateDatabase(Users user) {
		// update DB
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL1 = "UPDATE [User] SET Permisiune='" + user.getPermission() + "' WHERE ID_user=" +user.getId();
			
			stmt.executeUpdate(SQL1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	 private void getDatabaseData() {
			String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

			try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
				String SQL = "SELECT ID_user, Username, Password, Permisiune, Nume, Prenume, Email"
						+ " FROM [User]";
				ResultSet rs = stmt.executeQuery(SQL);

				while (rs.next()) {
					userData.add(new Users(rs.getString("ID_user"), rs.getString("Username"), rs.getString("Password"),
							rs.getString("Permisiune"), rs.getString("Nume"), rs.getString("Prenume"), rs.getString("Email")));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}