package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CategoriesControllerADMIN {

	@FXML
	private TableView<Category> tableView;
	@FXML
	private TableColumn<Category, String> nameColumn;
	@FXML
	private TableColumn<Category, String> descriptionColumn;

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField descriptionTextField;
	@FXML
	private Label labelNewCategory;
	@FXML
	private Label findOutLabel;
	@FXML
	private Label ronLabel;

	private ObservableList<Category> categoryData = FXCollections.observableArrayList();

	// initializeaza tabelul cu valorile citite si face editabile campurile date
	@FXML
	private void initialize() { // se apeleaza in mom in care se deschide fereastra //PRIMA INTOTDEAUNA EVER
		getDatabaseData();
		tableView.setItems(categoryData);

		// Initialize the person table with the two columns.
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

		tableView.setEditable(true);
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		nameColumn.setOnEditCommit(event -> {
			Category c = event.getRowValue();
			c.name.setValue(event.getNewValue());
			updateDatabase(c);
		});

		descriptionColumn.setOnEditCommit(event -> {
			Category c = event.getRowValue();
			c.description.setValue(event.getNewValue());
			updateDatabase(c);
		});
	}

	// functia de modificare a unei celule selectate
	private void updateDatabase(Category category) {
		// update DB

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL1 = "UPDATE [Categorie] SET Denumire ='" + category.getName() + "' WHERE ID_Categ="
					+ category.getId();
			String SQL2 = "UPDATE [Categorie] SET Descriere='" + category.getDescription() + "' WHERE ID_Categ="
					+ category.getId();

			stmt.executeUpdate(SQL1);
			stmt.executeUpdate(SQL2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// popularea TableView-ului din baza de date
	private void getDatabaseData() {
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Categ, Denumire, Descriere FROM Categorie";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				categoryData.add(
						new Category(rs.getString("ID_Categ"), rs.getString("Denumire"), rs.getString("Descriere")));
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
			String SQL = "SELECT ID_Categ, Denumire, Descriere FROM Categorie";
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				categoryData.add(
						new Category(rs.getString("ID_Categ"), rs.getString("Denumire"), rs.getString("Descriere")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// buton de stergere a randului selectat
	@FXML
	private void deleteButtonPressed() {
		Category selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
//			String SQL = "DELETE  FROM Serviciu WHERE ID_Categ=" + selectedItem.getId();
//			stmt.executeUpdate(SQL);

			String SQL3 = "DELETE FROM Categorie WHERE ID_Categ=" + selectedItem.getId();
			stmt.executeUpdate(SQL3);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// buton adaugare a unei noi categorii
	@FXML
	private void addButtonPressed() {
		String name = nameTextField.getText();
		String description = descriptionTextField.getText();

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "INSERT INTO [Categorie](Denumire, Descriere)" + " VALUES('" + name + "','" + description
					+ "')";
			stmt.executeUpdate(SQL);
			System.out.println(SQL);
			labelNewCategory.setVisible(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void hidelabelNewCategory() {
		labelNewCategory.setVisible(false);
	}
	
	@FXML
	private void findOutButtonPressed() {
		Category selectedItem = tableView.getSelectionModel().getSelectedItem();
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT SUM(Pret) AS PretTotal FROM Serviciu S INNER JOIN Categorie C ON C.ID_Categ = S.ID_Categ "
					+ "WHERE C.ID_Categ= " +selectedItem.getId();
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