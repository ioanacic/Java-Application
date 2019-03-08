package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class QUERY2 {
	@FXML
	private TableView<Clients> tableView;
	@FXML
	private TableColumn<Clients, String> lnameColumn;
	@FXML
	private TableColumn<Clients, String> fnameColumn;
	@FXML
	private TableColumn<Clients, String> streetColumn;
	@FXML
	private TableColumn<Clients, String> numberColumn;
	@FXML
	private TableColumn<Clients, String> cityColumn;
	@FXML
	private TableColumn<Clients, String> countyColumn;
	@FXML
	private TableColumn<Clients, String> sexColumn;
	@FXML
	private TableColumn<Clients, String> phoneColumn;
	@FXML
	private TableColumn<Clients, String> registerDateColumn;
	@FXML
	private TableColumn<Clients, String> birthDateColumn;
	
	private ObservableList<Clients> clientsData = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() { 
		getDatabaseData();
		tableView.setItems(clientsData);
		// Initialize the person table with the three columns.

		lnameColumn.setCellValueFactory(cellData -> cellData.getValue().lnameProperty());
		fnameColumn.setCellValueFactory(cellData -> cellData.getValue().fnameProperty());
		streetColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
		numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
		cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
		countyColumn.setCellValueFactory(cellData -> cellData.getValue().countyProperty());
		sexColumn.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		registerDateColumn.setCellValueFactory(cellData -> cellData.getValue().registerDateProperty());
		birthDateColumn.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());
	}
	
	private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Client, Nume, Prenume, Strada, Numar, Oras, Judet, Sex, Telefon, DataInregistrarii, DataNasterii "
					+ "FROM Client WHERE Oras NOT IN (SELECT Oras FROM Client WHERE Strada='" +SelectedUser.testInstance().street +"')";
			ResultSet rs = stmt.executeQuery(SQL);
			System.out.println(SQL);
			while (rs.next()) {
				clientsData.add(new Clients(rs.getString("ID_Client"), rs.getString("Nume"), rs.getString("Prenume"),
						rs.getString("Strada"), rs.getString("Numar"), rs.getString("Oras"), rs.getString("Judet"),
						rs.getString("Sex"), rs.getString("Telefon"), rs.getString("DataInregistrarii"),
						rs.getString("DataNasterii")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}