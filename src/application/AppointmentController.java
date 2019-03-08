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

public class AppointmentController {
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

	// creaza HashMap-ul cu clienti
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

	// creaza HashMap-ul cu cosmeticieni
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

	// creaza HashMap-ul cu servicii
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

	// initializeaza tabelul cu valorile citite si face editabile campurile date
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
		
		tableView.setEditable(true);
		dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		hourColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		dateColumn.setOnEditCommit(event -> {
			Appointment b = event.getRowValue();
			b.date.setValue(event.getNewValue());
			updateDatabase(b);
		});
		
		hourColumn.setOnEditCommit(event -> {
			Appointment b = event.getRowValue();
			b.hour.setValue(event.getNewValue());
			updateDatabase(b);
		});
	}
	
	// functia de modificare a unei celule selectate
	private void updateDatabase(Appointment appointment) {
		// update DB
		
		Date date = null;
		
		if (appointment.getDate() != null ) {
			date = Date.valueOf(appointment.getDate());
    	}
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL1 = "UPDATE [Programare] SET Ora_Programare='" + appointment.getHour() + "' WHERE ID_Programare=" +appointment.getId();
			
			if (appointment.getDate() != null ) {
				date = Date.valueOf(appointment.getDate());
				String SQL2 = "UPDATE [Programare] SET Data_Programare='" + date + "' WHERE ID_Programare=" +appointment.getId();
				stmt.executeUpdate(SQL2);
	    	}
			
			stmt.executeUpdate(SQL1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// popularea TableView-ului din baza de date
	private void getDatabaseData() {

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Programare, ID_Client, ID_Serv, ID_Cosm, Data_Programare, Ora_Programare FROM Programare";
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

	// buton de reincarcare a tabelului din baza de date
	@FXML
	private void refreshButtonPressed() {

		tableView.getItems().clear();

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Programare, ID_Client, ID_Serv, ID_Cosm, Data_Programare, Ora_Programare FROM Programare";
			ResultSet rs = stmt.executeQuery(SQL);

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
	
	// buton de stergere a randului selectat
	@FXML
	private void deleteButtonPressed() {
		Appointment selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {

			String SQL = "DELETE FROM Programare WHERE ID_Programare=" + selectedItem.getId();
			stmt.executeUpdate(SQL);

			System.out.println(SQL);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// buton de accesare a programarilor care au data mai mare decat data curenta
	// se deschide un ecran nou
	@FXML
	private void seeFutureAppointments() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("QUERY5.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 720, 450);
			Stage stage = new Stage();

			stage.setTitle("QUERY5");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) tableView.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}