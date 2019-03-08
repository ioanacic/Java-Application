package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProfileController {
	@FXML
	private Label streetLabel;
	@FXML
	private TextField fnameTextField;
	@FXML
	private TextField lnameTextField;
	@FXML
	private TextField streetTextField;
	@FXML
	private TextField cityTextField;
	@FXML
	private TextField phoneTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;

	Integer ID_user;

	@FXML
	private void initialize() {

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT ID_Client, ID_user, Nume, Prenume, Strada, Numar, Oras, Judet, Sex, Telefon, DataInregistrarii,"
					+ "DataNasterii FROM [Client] WHERE ID_Client =" + Singleton.getInstance().idClient;

			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				ID_user = rs.getInt("ID_user");
				fnameTextField.setText(rs.getString("Prenume"));
				lnameTextField.setText(rs.getString("Nume"));
				streetTextField.setText(rs.getString("Strada"));
				cityTextField.setText(rs.getString("Oras"));
				phoneTextField.setText(rs.getString("Telefon"));
			}

			String SQL1 = "SELECT Username, Password FROM [User] WHERE ID_user =" + ID_user;

			ResultSet rs1 = stmt.executeQuery(SQL1);

			if (rs1.next()) {
				usernameTextField.setText(rs1.getString("Username"));
				passwordTextField.setText(rs1.getString("Password"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void savechangesButtonPressed() {
		String fname = fnameTextField.getText();
		String lname = lnameTextField.getText();
		String street = streetTextField.getText();
		String city = cityTextField.getText();
		String phone = phoneTextField.getText();
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "UPDATE [Client] SET Nume='" + lname + "' WHERE ID_Client =" + Singleton.getInstance().idClient;
			String SQL1 = "UPDATE [Client] SET Prenume='" + fname + "' WHERE ID_Client =" + Singleton.getInstance().idClient;
			String SQL2 = "UPDATE [Client] SET Strada='" + street + "' WHERE ID_Client =" + Singleton.getInstance().idClient;
			String SQL3 = "UPDATE [Client] SET Oras='" + city + "' WHERE ID_Client =" + Singleton.getInstance().idClient;
			String SQL4 = "UPDATE [Client] SET Telefon='" + phone + "' WHERE ID_Client =" + Singleton.getInstance().idClient;
			String SQL5 = "UPDATE [User] SET Password='" + password + "' WHERE ID_user =" + ID_user;
			String SQL6 = "UPDATE [User] SET Username='" + username + "' WHERE ID_user =" + ID_user;

			stmt.executeUpdate(SQL);
			stmt.executeUpdate(SQL1);
			stmt.executeUpdate(SQL2);
			stmt.executeUpdate(SQL3);
			stmt.executeUpdate(SQL4);
			stmt.executeUpdate(SQL5);
			stmt.executeUpdate(SQL6);

			Stage stage = new Stage();
			stage = (Stage) passwordTextField.getScene().getWindow();
			stage.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}