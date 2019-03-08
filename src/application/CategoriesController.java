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

public class CategoriesController {
	
    @FXML
    private TableView<Category> tableView;
    @FXML
    private TableColumn<Category, String> nameColumn;
    @FXML
    private TableColumn<Category, String> descriptionColumn;

    private ObservableList<Category> categoryData = FXCollections.observableArrayList();
	
 // initializeaza tabelul cu valorile citite si face editabile campurile date
    @FXML
    private void initialize() {		// se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER 
    	getDatabaseData();
    	tableView.setItems(categoryData);
    	
        // Initialize the person table with the two columns.
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    // popularea TableView-ului din baza de date
	private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT Denumire, Descriere FROM Categorie";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				categoryData.add(new Category(rs.getString("Denumire"), rs.getString("Descriere")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}