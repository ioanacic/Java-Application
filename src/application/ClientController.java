package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import com.sun.javafx.css.converters.StringConverter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientController {

	ObservableList<String> hourList = FXCollections.observableArrayList("10:00", "10:15", "10:30", "10:45", "11:00",
			"11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00",
			"14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00",
			"17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45");

	ObservableList<String> beauticianList = FXCollections.observableArrayList();
	ObservableList<String> categoryList = FXCollections.observableArrayList();

	HashMap categoryServices = new HashMap<Integer, ObservableList<String>>();

	HashMap categoryHm = new HashMap();
	HashMap serviceHm = new HashMap();
	HashMap beauticianHm = new HashMap();

	@FXML
	private Label labelLogout;
	@FXML
	private Label labelCategory;
	@FXML
	private Label labelBeautician;
	@FXML
	private Label labelService;
	@FXML
	private Label labelDate;
	@FXML
	private Label labelHour;
	@FXML
	private Label labelAppointmentMade;
	@FXML
	private Label labelCannotMadeApp;

	@FXML
	private ChoiceBox hourChoiceBox;
	@FXML
	private ChoiceBox beauticianChoiceBox;
	@FXML
	private ComboBox categoryChoiceBox;
	@FXML
	private ComboBox serviceChoiceBox;
	@FXML
	private DatePicker datePicker;

	@FXML
	private Button saveButton;

	@FXML
	private void initialize() {
		readCategoryList();
		readBeauticianList();
		readServiceList();

		hourChoiceBox.setItems(hourList);
		beauticianChoiceBox.setItems(beauticianList);
		categoryChoiceBox.setItems(categoryList);

		hourChoiceBox.setVisible(false);
		beauticianChoiceBox.setVisible(false);
		categoryChoiceBox.setVisible(false);
		serviceChoiceBox.setVisible(false);
		datePicker.setVisible(false);
	}

	private void readCategoryList() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Categ, Denumire FROM [Categorie]";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				int id = rs.getInt("ID_Categ");
				String denumire = rs.getString("Denumire");
				categoryList.add(denumire);
				categoryHm.put(denumire, id);
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
				beauticianList.add(nume);
				beauticianHm.put(nume, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void readServiceList() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Serv, ID_Categ, Denumire FROM [Serviciu]";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				int idServiciu = rs.getInt("ID_Serv");
				int idCategorie = rs.getInt("ID_Categ");
				String denumire = rs.getString("Denumire");

				ObservableList serviceList = (ObservableList) categoryServices.get(idCategorie);
				if (serviceList == null) { // la primul apel initializez lista
					serviceList = FXCollections.observableArrayList();
				}

				serviceList.add(denumire);
				categoryServices.put(idCategorie, serviceList);
				serviceHm.put(denumire, idServiciu);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void logoutButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("LoginInterface.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 400, 250);
			Stage stage = new Stage();

			stage.setTitle("Login");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
			stage.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void categoriesButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("Categories.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 450, 600);
			Stage stage = new Stage();

			stage.setTitle("Categories");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void beauticiansButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("Beauticians.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 450, 600);
			Stage stage = new Stage();

			stage.setTitle("Beauticians");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void servicesButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("Services.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 450, 600);
			Stage stage = new Stage();

			stage.setTitle("Services");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void profileButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("Profile.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 400, 550);
			Stage stage = new Stage();

			stage.setTitle("Profile");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void historyButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("History.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 510, 480);
			Stage stage = new Stage();

			stage.setTitle("History");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void choiceboxPressed() {
		// System.out.print(categoryChoiceBox.getSelectionModel().getSelectedItem()); //imi returneaza optiunea selectata
		int categoryID = (int) categoryHm.get(categoryChoiceBox.getSelectionModel().getSelectedItem().toString());
		serviceChoiceBox.setItems((ObservableList) categoryServices.get(categoryID));
	}

	@FXML
	private void newappButtonPressed() {
		showNewAppointment();
	}

	@FXML
	private void backButtonPressed() {
		hideNewAppointment();
	}

	@FXML
	private void saveButtonPressed() {

		String hour = hourChoiceBox.getValue().toString();
		String date = datePicker.getValue().toString();

		int idServ = (int) serviceHm.get(serviceChoiceBox.getSelectionModel().getSelectedItem().toString());
		int idBeaut = (int) beauticianHm.get(beauticianChoiceBox.getSelectionModel().getSelectedItem().toString());

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Cosm, Data_Programare, Ora_Programare FROM [Programare] " + "WHERE ID_Cosm="
					+ idBeaut + " AND Data_Programare='" + date + "' AND Ora_Programare='" + hour + "'";
			System.out.println(SQL);
			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				labelCannotMadeApp.setVisible(true);
			} else {
				labelCannotMadeApp.setVisible(false);
				try (Connection con1 = DriverManager.getConnection(connectionUrl);
						Statement stmt1 = con1.createStatement();) {
					String SQL1 = "INSERT INTO [Programare](ID_Client, ID_Serv, ID_Cosm, Data_Programare, Ora_Programare, Observatii)"
							+ " VALUES('" + Singleton.getInstance().idClient + "','" + idServ + "','" + idBeaut + "','"
							+ date + "','" + hour + "','" + "" + "')";
					stmt.executeUpdate(SQL1);

					labelAppointmentMade.setVisible(true);

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

//		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
//			String SQL = "INSERT INTO [Programare](ID_Client, ID_Serv, ID_Cosm, Data_Programare, Ora_Programare, Observatii)" 
//						+ " VALUES('" + Singleton.getInstance().idClient + "','" + idServ +"','" + idBeaut + "','"
//						+ date + "','" + hour + "','" + "" + "')";
//			stmt.executeUpdate(SQL);
//			
//			labelAppointmentMade.setVisible(true);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	@FXML
	public void showNewAppointment() {
		labelCategory.setVisible(true);
		labelBeautician.setVisible(true);
		labelService.setVisible(true);
		labelDate.setVisible(true);
		labelHour.setVisible(true);
		hourChoiceBox.setVisible(true);
		beauticianChoiceBox.setVisible(true);
		categoryChoiceBox.setVisible(true);
		serviceChoiceBox.setVisible(true);
		datePicker.setVisible(true);
		saveButton.setVisible(true);
	}

	@FXML
	public void hideNewAppointment() {
		labelCategory.setVisible(false);
		labelBeautician.setVisible(false);
		labelService.setVisible(false);
		labelDate.setVisible(false);
		labelHour.setVisible(false);
		hourChoiceBox.setVisible(false);
		beauticianChoiceBox.setVisible(false);
		categoryChoiceBox.setVisible(false);
		serviceChoiceBox.setVisible(false);
		datePicker.setVisible(false);
		saveButton.setVisible(false);
	}

	@FXML
	public void hideAppoinmentMade() {
		labelAppointmentMade.setVisible(false);
	}

	@FXML
	public void hidelabelCannotMadeApp() {
		labelCannotMadeApp.setVisible(false);
	}

}