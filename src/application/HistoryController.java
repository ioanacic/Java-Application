package application;

import java.sql.Connection;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HistoryController {
	@FXML
    private TableView<History> tableView;
    @FXML
    private TableColumn<History, String> serviceColumn;
    @FXML
    private TableColumn<History, String> beauticianColumn;
    @FXML
    private TableColumn<History, String> dateColumn;
    @FXML
    private TableColumn<History, String> timeColumn;
    
    @FXML
	private Label findOutLabel;
	@FXML
	private Label ronLabel;
	@FXML
	private Button findOut;

    private ObservableList<History> historyData = FXCollections.observableArrayList();

    String beautician, service;
	Integer idServ, idBeaut;

    HashMap<Integer, String> hmBeautician = new HashMap<Integer, String>();
    HashMap<Integer, String> hmService = new HashMap<Integer, String>();

    
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
    private void initialize() {		// se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER 
    	getDatabaseData();
    	tableView.setItems(historyData);
        // Initialize the person table with the three columns.
    	
    	readBeauticianList(); 
    	readServiceList();
    	
    	serviceColumn.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());
    	beauticianColumn.setCellValueFactory(cellData -> cellData.getValue().beauticianProperty());
    	dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    	timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
    }
    
    private void getDatabaseData() {
    	
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		Integer userID;
		if (Singleton.getInstance().idClient != 0) {
			userID = Singleton.getInstance().idClient;
		}
		else {
			userID = SelectedUser.testInstance().idClient;
		}
		
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Serv, ID_Cosm, Data_Programare, Ora_Programare FROM Programare WHERE ID_Client=" +userID;
			ResultSet rs = stmt.executeQuery(SQL);

//			methodBeaut();
//	    	methodServ();
			
			readBeauticianList(); 
	    	readServiceList();
			
			while (rs.next()) {
				idServ = rs.getInt("ID_Serv");
				service = hmService.get(idServ);
				idBeaut = rs.getInt("ID_Cosm");
				beautician = hmBeautician.get(idBeaut);
				historyData.add(new History(service, beautician, rs.getString("Data_Programare"), rs.getString("Ora_Programare")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    
    @FXML
	private void findOutButtonPressed() {
    	Integer userID;
		if (Singleton.getInstance().idClient != 0) {
			userID = Singleton.getInstance().idClient;
		}
		else {
			userID = SelectedUser.testInstance().idClient;
		}
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT SUM(Pret) PretTotal FROM Programare P INNER JOIN Serviciu S ON P.ID_Serv = S.ID_Serv INNER JOIN Client C ON C.ID_Client = P.ID_Client "
					+ "WHERE C.ID_Client=" +userID;
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
}