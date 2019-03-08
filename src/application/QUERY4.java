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

public class QUERY4 {
	
	ObservableList<String> categoryList = FXCollections.observableArrayList();
	HashMap categoryServices = new HashMap<Integer, ObservableList<String>>();
	HashMap categoryHm = new HashMap();			// denumire, id
	
//	String category;
//	Integer idCateg;
//    HashMap<Integer, String> hmCategory = new HashMap<Integer, String>();			// id, denumire
	
	@FXML
    private TableView<Services> tableView;
    @FXML
    private TableColumn<Services, String> nameColumn;
    @FXML
    private TableColumn<Services, String> categColumn;
    @FXML
    private TableColumn<Services, String> timeColumn;
    @FXML
    private TableColumn<Services, String> priceColumn;
    

    private ObservableList<Services> servicesData = FXCollections.observableArrayList();
    private ObservableList<Category> categoryData = FXCollections.observableArrayList();
    
    
    @FXML
    private void initialize() {		// se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER 
    	readCategoryList();
    	
    	getDatabaseData();
    	tableView.setItems(servicesData);
        // Initialize the person table with the three columns.
    	
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	categColumn.setCellValueFactory(cellData -> cellData.getValue().idCategProperty());
    	timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
    	priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
    	
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
	//			hmCategory.put(id, denumire);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    
    private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Serv, ID_Categ, Denumire, Durata, Pret FROM Serviciu "
					+ "WHERE Durata > (SELECT AVG(Durata) FROM Serviciu)";
			ResultSet rs = stmt.executeQuery(SQL);

		//	readCategoryList();
			
			while (rs.next()) {
				servicesData.add(new Services(rs.getString("ID_Serv"), rs.getString("ID_Categ"), rs.getString("Denumire"), 
						rs.getString("Durata"), rs.getString("Pret")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
}