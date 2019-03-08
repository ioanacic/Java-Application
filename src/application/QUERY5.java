package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QUERY5 {
	@FXML
	private TableView<Appointment> tableView;
	@FXML
	private TableColumn<Appointment, String> clientColumn;
	@FXML
	private TableColumn<Appointment, String> serviceColumn;
	@FXML
	private TableColumn<Appointment, String> beauticianColumn;
	@FXML
	private TableColumn<Appointment, String> dateColumn;
	@FXML
	private TableColumn<Appointment, String> hourColumn;

	private ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();

	String beautician, service, client;
	Integer idServ, idBeaut, idClient;

	HashMap<Integer, String> hmClient = new HashMap<Integer, String>();
	HashMap<Integer, String> hmBeautician = new HashMap<Integer, String>();
	HashMap<Integer, String> hmService = new HashMap<Integer, String>();

	private void readClientList() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Client, Nume, Prenume FROM [Client]";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				int id = rs.getInt("ID_Client");
				String nume = rs.getString("Nume") + " " + rs.getString("Prenume");
				hmClient.put(id, nume);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void readBeauticianList() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Cosm, Nume, Prenume FROM [Cosmetician]";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				int id = rs.getInt("ID_Cosm");
				String nume = rs.getString("Nume") + " " + rs.getString("Prenume");
				hmBeautician.put(id, nume);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void readServiceList() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Serv, Denumire FROM [Serviciu]";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				int id = rs.getInt("ID_Serv");
				String nume = rs.getString("Denumire");
				hmService.put(id, nume);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() { // se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER
		getDatabaseData();
		tableView.setItems(appointmentData);
		// Initialize the person table with the three columns.

		readClientList();
		readBeauticianList();
		readServiceList();

		clientColumn.setCellValueFactory(cellData -> cellData.getValue().clientProperty());
		serviceColumn.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());
		beauticianColumn.setCellValueFactory(cellData -> cellData.getValue().beauticianProperty());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		hourColumn.setCellValueFactory(cellData -> cellData.getValue().hourProperty());
	}
	
	private void getDatabaseData() {

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT P.ID_Programare, P.ID_Client, P.ID_Serv, P.ID_Cosm, P.Data_Programare, P.Ora_Programare "
					+ "FROM Programare P INNER JOIN Client C ON C.ID_Client = P.ID_Client "
					+ "WHERE P.Data_Programare > GETDATE()";
			ResultSet rs = stmt.executeQuery(SQL);
			readClientList();
			readBeauticianList();
			readServiceList();

			while (rs.next()) {
				idClient = rs.getInt("ID_Client");
				client = hmClient.get(idClient);
				idServ = rs.getInt("ID_Serv");
				service = hmService.get(idServ);
				idBeaut = rs.getInt("ID_Cosm");
				beautician = hmBeautician.get(idBeaut);
				appointmentData.add(new Appointment(rs.getString("ID_Programare"), client, service, beautician,
						rs.getString("Data_Programare"), rs.getString("Ora_Programare")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}