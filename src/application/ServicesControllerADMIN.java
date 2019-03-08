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

public class ServicesControllerADMIN {

	ObservableList<String> categoryList = FXCollections.observableArrayList();
	HashMap categoryServices = new HashMap<Integer, ObservableList<String>>();
	HashMap categoryHm = new HashMap(); // denumire, id

//	String category;
//	Integer idCateg;
//    HashMap<Integer, String> hmCategory = new HashMap<Integer, String>();			// id, denumire

	@FXML
	private TableView<Services> tableView;
	@FXML
	private TableView<Category> tableView2;
	@FXML
	private TableColumn<Services, String> nameColumn;
	@FXML
	private TableColumn<Services, String> categColumn;
	@FXML
	private TableColumn<Services, String> timeColumn;
	@FXML
	private TableColumn<Services, String> priceColumn;

	@FXML
	private TableColumn<Category, String> nameCategColumn;
	@FXML
	private TableColumn<Category, String> numberCategColumn;

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField timeTextField;
	@FXML
	private TextField priceTextField;
	@FXML
	private ChoiceBox categoryChoiceBox;
	@FXML
	private Label labelNewService;
	@FXML
	private Label findOutLabel;
	@FXML
	private Label appointmentLabel;
	@FXML
	private Label findOutLabel2;
	@FXML
	private Label appointmentLabel2;

	private ObservableList<Services> servicesData = FXCollections.observableArrayList();
	private ObservableList<Category> categoryData = FXCollections.observableArrayList();

	@FXML
	private void initialize() { // se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER
		readCategoryList();
		categoryChoiceBox.setItems(categoryList);

		getDatabaseData();
		tableView.setItems(servicesData);
		tableView2.setItems(categoryData);
		// Initialize the person table with the three columns.

		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		categColumn.setCellValueFactory(cellData -> cellData.getValue().idCategProperty());
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

		nameCategColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		numberCategColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

		tableView.setEditable(true);
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		categColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		timeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		nameColumn.setOnEditCommit(event -> {
			Services b = event.getRowValue();
			b.name.setValue(event.getNewValue());
			updateDatabase(b);
		});

		categColumn.setOnEditCommit(event -> {
			Services b = event.getRowValue();
			b.idCateg.setValue(event.getNewValue());
			updateDatabase(b);
		});

		timeColumn.setOnEditCommit(event -> {
			Services b = event.getRowValue();
			b.time.setValue(event.getNewValue());
			updateDatabase(b);
		});

		priceColumn.setOnEditCommit(event -> {
			Services b = event.getRowValue();
			b.price.setValue(event.getNewValue());
			updateDatabase(b);
		});
	}

	private void updateDatabase(Services service) {
		// update DB

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL1 = "UPDATE [Serviciu] SET Denumire='" + service.getName() + "' WHERE ID_Serv=" + service.getId();
			String SQL2 = "UPDATE [Serviciu] SET Durata='" + service.getTime() + "' WHERE ID_Serv=" + service.getId();
			String SQL3 = "UPDATE [Serviciu] SET Pret='" + service.getPrice() + "' WHERE ID_Serv=" + service.getId();
			String SQL4 = "UPDATE [Serviciu] SET ID_Categ='" + service.getIdCateg() + "' WHERE ID_Serv="
					+ service.getId();

			stmt.executeUpdate(SQL1);
			stmt.executeUpdate(SQL2);
			stmt.executeUpdate(SQL3);
			stmt.executeUpdate(SQL4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				// hmCategory.put(id, denumire);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Serv, ID_Categ, Denumire, Durata, Pret FROM Serviciu";
			ResultSet rs = stmt.executeQuery(SQL);

			// readCategoryList();

			while (rs.next()) {
//				idCateg = rs.getInt("ID_Categ");
//				category = hmCategory.get(idCateg);
				servicesData.add(new Services(rs.getString("ID_Serv"), rs.getString("ID_Categ"),
						rs.getString("Denumire"), rs.getString("Durata"), rs.getString("Pret")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Categ, Denumire FROM Categorie";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				categoryData.add(new Category(rs.getString("ID_Categ"), rs.getString("Denumire"), ""));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void refreshButtonPressed() {

		tableView.getItems().clear();

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Serv, ID_Categ, Denumire, Durata, Pret FROM Serviciu";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				servicesData.add(new Services(rs.getString("ID_Serv"), rs.getString("ID_Categ"),
						rs.getString("Denumire"), rs.getString("Durata"), rs.getString("Pret")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void deleteButtonPressed() {
		Services selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {

			String SQL = "DELETE FROM Serviciu WHERE ID_Serv=" + selectedItem.getId();
			stmt.executeUpdate(SQL);

//			String SQL1 = "DELETE FROM Programare WHERE ID_Serv=" + selectedItem.getId();
//			stmt.executeUpdate(SQL1);

			System.out.println(SQL);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void addButtonPressed() {
		String name = nameTextField.getText();
		String time = timeTextField.getText();
		String price = priceTextField.getText();
		// String category = categoryChoiceBox.getValue().toString();

		int idCateg = (int) categoryHm.get(categoryChoiceBox.getSelectionModel().getSelectedItem().toString());

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "INSERT INTO [Serviciu](Denumire, ID_Categ, Durata, Pret)" + " VALUES('" + name + "','"
					+ idCateg + "','" + time + "','" + price + "')";
			stmt.executeUpdate(SQL);
			labelNewService.setVisible(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void hidelabelNewService() {
		labelNewService.setVisible(false);
	}

	@FXML
	private void findOutButtonPressed() {
		Services selectedItem = tableView.getSelectionModel().getSelectedItem();
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT COUNT(*) AS NrProgramari FROM Programare P INNER JOIN Serviciu S ON P.ID_Serv = S.ID_Serv "
					+ "WHERE S.ID_Serv =" + selectedItem.getId();
			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) {
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
		appointmentLabel.setVisible(false);
		findOutLabel2.setVisible(false);
		appointmentLabel2.setVisible(false);
	}

	@FXML
	private void findOutDuration() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("QUERY4.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 525, 300);
			Stage stage = new Stage();

			stage.setTitle("QUERY4");
			stage.setScene(scene);
			stage.show();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void findOutBeautician() {
		Services selectedItem = tableView.getSelectionModel().getSelectedItem();
		SelectedService.testInstance().idServ = Integer.parseInt(selectedItem.getId());

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT COUNT(*) AS NrProgCosm "
					+ "FROM Programare P INNER JOIN Serviciu S ON P.ID_Serv = S.ID_Serv INNER JOIN Cosmetician C ON C.ID_Cosm = P.ID_Cosm "
					+ "WHERE S.ID_Serv=" +SelectedService.testInstance().idServ+ "AND C.DataAngajarii = (SELECT MIN(DataAngajarii) FROM Cosmetician)";
			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				findOutLabel2.setText(rs.getString("NrProgCosm"));
			}
			findOutLabel2.setVisible(true);
			appointmentLabel2.setVisible(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}