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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class BeauticiansControllerADMIN {
	@FXML
	private TableView<Beautician> tableView;
	@FXML
	private TableColumn<Beautician, String> firstNameColumn;
	@FXML
	private TableColumn<Beautician, String> lastNameColumn;
	@FXML
	private TableColumn<Beautician, String> cnpColumn;
	@FXML
	private TableColumn<Beautician, String> streetColumn;
	@FXML
	private TableColumn<Beautician, String> numberColumn;
	@FXML
	private TableColumn<Beautician, String> cityColumn;
	@FXML
	private TableColumn<Beautician, String> countyColumn;
	@FXML
	private TableColumn<Beautician, String> sexColumn;
	@FXML
	private TableColumn<Beautician, String> phoneColumn;
	@FXML
	private TableColumn<Beautician, String> employmentDateColumn;
	@FXML
	private TableColumn<Beautician, String> birthDateColumn;
	@FXML
	private TableColumn<Beautician, String> salaryColumn;

	@FXML
	private TextField fnameTextField;
	@FXML
	private TextField lnameTextField;
	@FXML
	private TextField cnpTextField;
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
	private TextField employmentDateTextField;
	@FXML
	private TextField birthDateTextField;
	@FXML
	private Label findOutLabel;
	@FXML
	private Label findOutLabel2;
	@FXML
	private Label appointmentLabel;
	@FXML
	private Label clientsLabel;

	@FXML
	private Label labelNewBeautician;

	private ObservableList<Beautician> beauticianData = FXCollections.observableArrayList();

	// initializeaza tabelul cu valorile citite si face editabile campurile date
	@FXML
	private void initialize() { // se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER
		getDatabaseData();
		tableView.setItems(beauticianData);
		// Initialize the person table with the three columns.

		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().fnameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lnameProperty());
		cnpColumn.setCellValueFactory(cellData -> cellData.getValue().cnpProperty());
		streetColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
		numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
		cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
		countyColumn.setCellValueFactory(cellData -> cellData.getValue().countyProperty());
		sexColumn.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		employmentDateColumn.setCellValueFactory(cellData -> cellData.getValue().employmentDateProperty());
		birthDateColumn.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());
		salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty());

		tableView.setEditable(true);
		firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cnpColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		streetColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		countyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		sexColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		employmentDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		birthDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		
		firstNameColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.firstName.setValue(event.getNewValue());
			updateDatabase(b);
		});

		lastNameColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.lastName.setValue(event.getNewValue());
			updateDatabase(b);
		});
		
		cnpColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.CNP.setValue(event.getNewValue());
			updateDatabase(b);
		});

		streetColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.street.setValue(event.getNewValue());
			updateDatabase(b);
		});
		
		numberColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.number.setValue(event.getNewValue());
			updateDatabase(b);
		});

		cityColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.city.setValue(event.getNewValue());
			updateDatabase(b);
		});
		
		countyColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.county.setValue(event.getNewValue());
			updateDatabase(b);
		});

		sexColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.sex.setValue(event.getNewValue());
			updateDatabase(b);
		});
		
		phoneColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.phoneNr.setValue(event.getNewValue());
			updateDatabase(b);
		});

		employmentDateColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.employmentDate.setValue(event.getNewValue());
			updateDatabase(b);
		});
		
		birthDateColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.birthDate.setValue(event.getNewValue());
			updateDatabase(b);
		});

		salaryColumn.setOnEditCommit(event -> {
			Beautician b = event.getRowValue();
			b.salary.setValue(event.getNewValue());
			updateDatabase(b);
		});
	}

	// functia de modificare a unei celule selectate
	private void updateDatabase(Beautician beautician) {
		// update DB
		
		Date employmentDate = null;
		Date birthDate = null;
		
		if (beautician.getEmploymentDate() != null ) {
			employmentDate = Date.valueOf(beautician.getEmploymentDate());
    	}
    	
    	if (beautician.getBirthDate() != null) {
    		birthDate = Date.valueOf(beautician.getBirthDate());
    	}
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL1 = "UPDATE [Cosmetician] SET Nume='" + beautician.getlName() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL2 = "UPDATE [Cosmetician] SET Prenume='" + beautician.getfName() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL3 = "UPDATE [Cosmetician] SET CNP='" + beautician.getCNP() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL4 = "UPDATE [Cosmetician] SET Strada='" + beautician.getStreet() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL5 = "UPDATE [Cosmetician] SET Numar='" + beautician.getNumber() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL6 = "UPDATE [Cosmetician] SET Oras='" + beautician.getCity() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL7 = "UPDATE [Cosmetician] SET Judet='" + beautician.getCounty() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL8 = "UPDATE [Cosmetician] SET Sex='" + beautician.getSex() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL9 = "UPDATE [Cosmetician] SET Telefon='" + beautician.getPhone() + "' WHERE ID_Cosm=" +beautician.getId();
			String SQL12 = "UPDATE [Cosmetician] SET Salariu='" + Float.parseFloat(beautician.getSalary()) + "' WHERE ID_Cosm=" +beautician.getId();
		
			
			if (beautician.getEmploymentDate() != null ) {
				employmentDate = Date.valueOf(beautician.getEmploymentDate());
				String SQL10 = "UPDATE [Cosmetician] SET DataAngajarii='" + employmentDate + "' WHERE ID_Cosm=" +beautician.getId();
				stmt.executeUpdate(SQL10);
	    	}
	    	
			if (beautician.getBirthDate() != null) {
	    		birthDate = Date.valueOf(beautician.getBirthDate());
	    		String SQL11 = "UPDATE [Cosmetician] SET DataNasterii='" +birthDate + "' WHERE ID_Cosm=" +beautician.getId();
	    		stmt.executeUpdate(SQL11);
	    	}
			
			
			stmt.executeUpdate(SQL1);
			stmt.executeUpdate(SQL2);
			stmt.executeUpdate(SQL3);
			stmt.executeUpdate(SQL4);
			stmt.executeUpdate(SQL5);
			stmt.executeUpdate(SQL6);
			stmt.executeUpdate(SQL7);
			stmt.executeUpdate(SQL8);
			stmt.executeUpdate(SQL9);
			stmt.executeUpdate(SQL12);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// popularea TableView-ului din baza de date
	private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Cosm, Prenume, Nume, CNP, Strada, Numar, Oras, Judet, Sex, Telefon, DataAngajarii, DataNasterii, Salariu"
					+ " FROM Cosmetician";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				// idBeautician.add(rs.getInt("ID_Cosm"));
				beauticianData.add(new Beautician(rs.getString("ID_Cosm"), rs.getString("Prenume"),
						rs.getString("Nume"), rs.getString("CNP"), rs.getString("Strada"), rs.getString("Numar"),
						rs.getString("Oras"), rs.getString("Judet"), rs.getString("Sex"), rs.getString("Telefon"),
						rs.getString("DataAngajarii"), rs.getString("DataNasterii"), rs.getString("Salariu")));
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
			String SQL = "SELECT ID_Cosm, Prenume, Nume, CNP, Strada, Numar, Oras, Judet, Sex, Telefon, DataAngajarii, DataNasterii, Salariu"
					+ " FROM Cosmetician";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				// idBeautician.add(rs.getInt("ID_Cosm"));
				beauticianData.add(new Beautician(rs.getString("ID_Cosm"), rs.getString("Prenume"),
						rs.getString("Nume"), rs.getString("CNP"), rs.getString("Strada"), rs.getString("Numar"),
						rs.getString("Oras"), rs.getString("Judet"), rs.getString("Sex"), rs.getString("Telefon"),
						rs.getString("DataAngajarii"), rs.getString("DataNasterii"), rs.getString("Salariu")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// buton de stergere a randului selectat
	@FXML
	private void deleteButtonPressed() {
		Beautician selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {

			String SQL = "DELETE FROM Cosmetician WHERE ID_Cosm=" + selectedItem.getId();
			stmt.executeUpdate(SQL);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// buton adaugare un nou cosmetician
	@FXML
	private void addButtonPressed() {
		String fname = fnameTextField.getText();
		String lname = lnameTextField.getText();
		String cnp = cnpTextField.getText();
		String street = streetTextField.getText();
		String number = numberTextField.getText();
		String city = cityTextField.getText();
		String county = countyTextField.getText();
		String phone = phoneTextField.getText();
		String employmentDate = employmentDateTextField.getText();
		String birthDate = birthDateTextField.getText();

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "INSERT INTO [Cosmetician](Nume, Prenume, CNP, Strada, Numar, Oras, Judet, Telefon, DataAngajarii, DataNasterii)"
					+ " VALUES('" + lname + "','" + fname + "','" + cnp + "','" + street + "','" + number + "','" + city
					+ "','" + county + "','" + phone + "','" + employmentDate + "','" + birthDate + "')";
			stmt.executeUpdate(SQL);
			labelNewBeautician.setVisible(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void hidelabelNewBeautician() {
		labelNewBeautician.setVisible(false);
	}

	@FXML
	private void findOutButtonPressed() {
		Beautician selectedItem = tableView.getSelectionModel().getSelectedItem();
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT COUNT(*) AS NrProgramari FROM Client C INNER JOIN Programare P ON C.ID_Client = P.ID_Client INNER JOIN Cosmetician Co ON Co.ID_Cosm = P.ID_Cosm"
					+ " WHERE Co.ID_Cosm=" +selectedItem.getId();
			ResultSet rs = stmt.executeQuery(SQL);

			if(rs.next()) {
				findOutLabel.setText(rs.getString("NrProgramari"));
			}
			findOutLabel.setVisible(true);
			appointmentLabel.setVisible(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void hideLabel() {
		findOutLabel.setVisible(false);
		findOutLabel2.setVisible(false);
		appointmentLabel.setVisible(false);
		clientsLabel.setVisible(false);
	}
	
	@FXML
	private void findOutSalary() {
		Beautician selectedItem = tableView.getSelectionModel().getSelectedItem();
		SelectedBeautician.testInstance().idCosm = Integer.parseInt(selectedItem.getId());
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("QUERY1.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 1285, 400);
			Stage stage = new Stage();

			stage.setTitle("QUERY1");
			stage.setScene(scene);
			stage.show();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void findOutClients() {
		Beautician selectedItem = tableView.getSelectionModel().getSelectedItem();
		SelectedBeautician.testInstance().idCosm = Integer.parseInt(selectedItem.getId());
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";
		
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT COUNT(*) AS PretTotal FROM Client WHERE DataInregistrarii > (SELECT DataAngajarii FROM Cosmetician WHERE ID_Cosm=" +selectedItem.getId()+ ")";
			ResultSet rs = stmt.executeQuery(SQL);

			if(rs.next()) {
				findOutLabel2.setText(rs.getString("PretTotal"));
			}
			findOutLabel2.setVisible(true);
			clientsLabel.setVisible(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}