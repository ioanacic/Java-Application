package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class BeauticiansController {
	@FXML
    private TableView<Beautician> tableView;
    @FXML
    private TableColumn<Beautician, String> firstNameColumn;
    @FXML
    private TableColumn<Beautician, String> lastNameColumn;
    @FXML
    private TableColumn<Beautician, String> phoneColumn;

    private ObservableList<Beautician> beauticianData = FXCollections.observableArrayList();
    
    // initializeaza tabelul cu valorile citite si face editabile campurile date
    @FXML
    private void initialize() {		// se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER 
    	getDatabaseData();
    	tableView.setItems(beauticianData);
        // Initialize the person table with the three columns.
    	
    	firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().fnameProperty());
    	lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lnameProperty());
    	phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
    }
    
    // popularea TableView-ului din baza de date
    private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Cosm, Prenume, Nume, Telefon FROM Cosmetician";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				beauticianData.add(new Beautician(rs.getString("ID_Cosm"),rs.getString("Prenume"), rs.getString("Nume"), rs.getString("Telefon")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}