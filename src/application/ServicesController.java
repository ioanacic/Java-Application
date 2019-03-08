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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ServicesController {
	@FXML
    private TableView<Services> tableView;
    @FXML
    private TableColumn<Services, String> nameColumn;
    @FXML
    private TableColumn<Services, String> timeColumn;
    @FXML
    private TableColumn<Services, String> priceColumn;

    private ObservableList<Services> servicesData = FXCollections.observableArrayList();
    
    
    @FXML
    private void initialize() {		// se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER 
    	getDatabaseData();
    	tableView.setItems(servicesData);
        // Initialize the person table with the three columns.
    	
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
    	priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
    }
    
    private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT Denumire, Durata, Pret FROM Serviciu";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				servicesData.add(new Services(rs.getString("Denumire"), rs.getString("Durata"), rs.getString("Pret")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}