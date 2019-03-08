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
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientsControllerADMIN {
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
	private Label findOutLabel;
	@FXML
	private Label ronLabel;
	
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

	@FXML
	private TextField fnameTextField;
	@FXML
	private TextField lnameTextField;
	@FXML
	private TextField streetTextField;
	@FXML
	private TextField numberTextField;
	@FXML
	private TextField cityTextField;
	@FXML
	private TextField countyTextField;
	@FXML
	private TextField phoneTextField;
	@FXML
	private TextField birthDateTextField;
	@FXML
	private TextField registerDateTextField;
	
	@FXML
	private Label labelNewClient;

	private ObservableList<Clients> clientsData = FXCollections.observableArrayList();

	@FXML
	private void initialize() { // se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER
		readCategoryList();
		readBeauticianList();
		readServiceList();

		hourChoiceBox.setItems(hourList);
		beauticianChoiceBox.setItems(beauticianList);
		categoryChoiceBox.setItems(categoryList);
		
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

		tableView.setEditable(true);
		lnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		fnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		streetColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		countyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		sexColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		registerDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		birthDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		fnameColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.fname.setValue(event.getNewValue());
			updateDatabase(c);
		});

		lnameColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.lname.setValue(event.getNewValue());
			updateDatabase(c);
		});

		streetColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.street.setValue(event.getNewValue());
			updateDatabase(c);
		});

		numberColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.number.setValue(event.getNewValue());
			updateDatabase(c);
		});

		cityColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.city.setValue(event.getNewValue());
			updateDatabase(c);
		});

		countyColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.county.setValue(event.getNewValue());
			updateDatabase(c);
		});

		sexColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.sex.setValue(event.getNewValue());
			updateDatabase(c);
		});

		phoneColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.phone.setValue(event.getNewValue());
			updateDatabase(c);
		});

		registerDateColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.registerDate.setValue(event.getNewValue());
			updateDatabase(c);
		});

		birthDateColumn.setOnEditCommit(event -> {
			Clients c = event.getRowValue();
			c.birthDate.setValue(event.getNewValue());
			updateDatabase(c);
		});
	}

	private void updateDatabase(Clients client) {
		// update DB
		Date registerDate = null;
		Date birthDate = null;

		if (client.getRegisterDate() != null) {
			registerDate = Date.valueOf(client.getRegisterDate());
		}

		if (client.getBirthDate() != null) {
			birthDate = Date.valueOf(client.getBirthDate());
		}

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL1 = "UPDATE [Client] SET Nume='" + client.getLName() + "' WHERE ID_Client=" + client.getId();
			String SQL2 = "UPDATE [Client] SET Prenume='" + client.getFName() + "' WHERE ID_Client=" + client.getId();
			String SQL4 = "UPDATE [Client] SET Strada='" + client.getStreet() + "' WHERE ID_Client=" + client.getId();
			String SQL5 = "UPDATE [Client] SET Numar='" + client.getNumber() + "' WHERE ID_Client=" + client.getId();
			String SQL6 = "UPDATE [Client] SET Oras='" + client.getCity() + "' WHERE ID_Client=" + client.getId();
			String SQL7 = "UPDATE [Client] SET Judet='" + client.getCounty() + "' WHERE ID_Client=" + client.getId();
			String SQL8 = "UPDATE [Client] SET Sex='" + client.getSex() + "' WHERE ID_Client=" + client.getId();
			String SQL9 = "UPDATE [Client] SET Telefon='" + client.getPhone() + "' WHERE ID_Client=" + client.getId();

			if (client.getRegisterDate() != null) {
				registerDate = Date.valueOf(client.getRegisterDate());
				String SQL10 = "UPDATE [Client] SET DataInregistrarii='" + registerDate + "' WHERE ID_Client="
						+ client.getId();
				stmt.executeUpdate(SQL10);
			}

			if (client.getBirthDate() != null) {
				birthDate = Date.valueOf(client.getBirthDate());
				String SQL11 = "UPDATE [Client] SET DataNasterii='" + birthDate + "' WHERE ID_Client=" + client.getId();
				stmt.executeUpdate(SQL11);
			}

			stmt.executeUpdate(SQL1);
			stmt.executeUpdate(SQL2);
			stmt.executeUpdate(SQL4);
			stmt.executeUpdate(SQL5);
			stmt.executeUpdate(SQL6);
			stmt.executeUpdate(SQL7);
			stmt.executeUpdate(SQL8);
			stmt.executeUpdate(SQL9);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Client, Nume, Prenume, Strada, Numar, Oras, Judet, Sex, Telefon, DataInregistrarii, DataNasterii"
					+ " FROM Client";
			ResultSet rs = stmt.executeQuery(SQL);

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

	@FXML
	private void addButtonPressed() {
		String fname = fnameTextField.getText();
		String lname = lnameTextField.getText();
		String street = streetTextField.getText();
		String number = numberTextField.getText();
		String city = cityTextField.getText();
		String county = countyTextField.getText();
		String phone = phoneTextField.getText();
		String registerDate = registerDateTextField.getText();
		String birthDate = birthDateTextField.getText();
		String username = lname + fname;
		String password = "parola";
		int idUser = 0;

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL1 = "INSERT INTO [User](Username, Password, Nume, Prenume)" + "VALUES('" + username + "','"
					+ password + "','" + lname + "','" + fname + "')";
			stmt.executeUpdate(SQL1);

			String SQL2 = "SELECT ID_User FROM [User] WHERE Username='" + lname + fname + "'";
			ResultSet rs = stmt.executeQuery(SQL2);
			if (rs.next()) {
				idUser = rs.getInt("ID_user");
			}

			String SQL = "INSERT INTO [Client](ID_User, Nume, Prenume, Strada, Numar, Oras, Judet, Telefon, DataInregistrarii, DataNasterii)"
					+ " VALUES('" + idUser + "','" + lname + "','" + fname + "','" + street + "','" + number + "','"
					+ city + "','" + county + "','" + phone + "','" + registerDate + "','" + birthDate + "')";
			stmt.executeUpdate(SQL);
			labelNewClient.setVisible(true);
			System.out.println(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void refreshButtonPressed() {

		tableView.getItems().clear();

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Client, Nume, Prenume, Strada, Numar, Oras, Judet, Sex, Telefon, DataInregistrarii, DataNasterii"
					+ " FROM Client";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				clientsData.addAll(new Clients(rs.getString("ID_Client"), rs.getString("Nume"), rs.getString("Prenume"),
						rs.getString("Strada"), rs.getString("Numar"), rs.getString("Oras"), rs.getString("Judet"),
						rs.getString("Sex"), rs.getString("Telefon"), rs.getString("DataInregistrarii"),
						rs.getString("DataNasterii")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void deleteButtonPressed() {
		Clients selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String id_user = null;
			
			String SQL1 = "SELECT ID_user FROM [Client] WHERE ID_Client=" + selectedItem.getId();
			ResultSet rs = stmt.executeQuery(SQL1);
			if(rs.next()) {
				id_user = rs.getString("ID_user");
			}
		
			String SQL = "DELETE FROM Client WHERE ID_Client=" + selectedItem.getId();
			stmt.executeUpdate(SQL);

			String SQL2 = "DELETE FROM [User] WHERE ID_user='" +id_user+ "'";
			stmt.executeUpdate(SQL2);
			
//			String SQL3 = "DELETE FROM [Programare] WHERE ID_Client=" + selectedItem.getId();
//			stmt.executeUpdate(SQL3);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void historyButtonPressed() {
		
		Clients selectedItem = tableView.getSelectionModel().getSelectedItem();
		SelectedUser.testInstance().idClient = Integer.parseInt(selectedItem.getId());
	
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("History.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 510, 480);
			Stage stage = new Stage();

			stage.setTitle("History");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelCannotMadeApp.getScene().getWindow();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void hidelabelNewClient() {
		labelNewClient.setVisible(false);
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
	private void choiceboxPressed() {
		// System.out.print(categoryChoiceBox.getSelectionModel().getSelectedItem()); //imi returneaza optiunea selectata
		int categoryID = (int) categoryHm.get(categoryChoiceBox.getSelectionModel().getSelectedItem().toString());
		serviceChoiceBox.setItems((ObservableList) categoryServices.get(categoryID));
	}
	
	@FXML
	private void saveButtonPressed() {
		Clients selectedItem = tableView.getSelectionModel().getSelectedItem();
		String hour = hourChoiceBox.getValue().toString();
		String date = datePicker.getValue().toString();

		int idServ = (int) serviceHm.get(serviceChoiceBox.getSelectionModel().getSelectedItem().toString());
		int idBeaut = (int) beauticianHm.get(beauticianChoiceBox.getSelectionModel().getSelectedItem().toString());

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Cosm, Data_Programare, Ora_Programare FROM [Programare] " + "WHERE ID_Cosm="
					+ idBeaut + " AND Data_Programare='" + date + "' AND Ora_Programare='" + hour + "'";
			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				labelCannotMadeApp.setVisible(true);
			} else {
				labelCannotMadeApp.setVisible(false);
				try (Connection con1 = DriverManager.getConnection(connectionUrl);
						Statement stmt1 = con1.createStatement();) {
					String SQL1 = "INSERT INTO [Programare](ID_Client, ID_Serv, ID_Cosm, Data_Programare, Ora_Programare, Observatii)"
							+ " VALUES('" + selectedItem.getId() + "','" + idServ + "','" + idBeaut + "','"
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
	}

	@FXML
	public void hideAppoinmentMade() {
		labelAppointmentMade.setVisible(false);
	}

	@FXML
	public void hidelabelCannotMadeApp() {
		labelCannotMadeApp.setVisible(false);
	}
	
	
	@FXML
	private void findOutButtonPressed() {
		Clients selectedItem = tableView.getSelectionModel().getSelectedItem();
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT SUM(Pret) PretTotal FROM Programare P INNER JOIN Serviciu S ON P.ID_Serv = S.ID_Serv INNER JOIN Client C ON C.ID_Client = P.ID_Client "
					+ "WHERE C.ID_Client=" +selectedItem.getId();
			ResultSet rs = stmt.executeQuery(SQL);

			if(rs.next()) {
				findOutLabel.setText(rs.getString("PretTotal"));
			}
			findOutLabel.setVisible(true);
			ronLabel.setVisible(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void hideLabel() {
		findOutLabel.setVisible(false);
		ronLabel.setVisible(false);
	}
	
	
	@FXML
	private void findOutStreet() {
		Clients selectedItem = tableView.getSelectionModel().getSelectedItem();
		SelectedUser.testInstance().street = selectedItem.getStreet();
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("QUERY2.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 1180, 400);
			Stage stage = new Stage();

			stage.setTitle("QUERY2");
			stage.setScene(scene);
			stage.show();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}