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

public class QUERY1 {
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

	private ObservableList<Beautician> beauticianData = FXCollections.observableArrayList();

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
	}
	
	private void getDatabaseData() {
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Cosm, Prenume, Nume, CNP, Strada, Numar, Oras, Judet, Sex, Telefon, DataAngajarii, DataNasterii, Salariu"
					+ " FROM Cosmetician WHERE Salariu > (SELECT Salariu FROM Cosmetician WHERE ID_Cosm=" +SelectedBeautician.testInstance().idCosm+ ")";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				beauticianData.add(new Beautician(rs.getString("ID_Cosm"), rs.getString("Prenume"),
						rs.getString("Nume"), rs.getString("CNP"), rs.getString("Strada"), rs.getString("Numar"),
						rs.getString("Oras"), rs.getString("Judet"), rs.getString("Sex"), rs.getString("Telefon"),
						rs.getString("DataAngajarii"), rs.getString("DataNasterii"), rs.getString("Salariu")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}