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
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {
	
	ObservableList<String> sexList = FXCollections.observableArrayList("F", "M");

	Integer idUser;
	
	@FXML
	private TextField fnameTextField;
	@FXML
	private TextField lnameTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private ChoiceBox sexChoiceBox;

	@FXML
	private void initialize() {
		sexChoiceBox.setItems(sexList);
	}

	@FXML
	private void saveButtonPressed() {
		String fname = fnameTextField.getText();
		String lname = lnameTextField.getText();
		String email = emailTextField.getText();
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String sex = sexChoiceBox.getValue().toString();

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "INSERT INTO [User](Username, Password, Nume, Prenume, Email,  Sex)" + " VALUES('" + username
					+ "','" + password + "','" + lname + "','" + fname + "','" + email + "','" + sex + "')";
			stmt.executeUpdate(SQL); // nu astept niciun rasp, doar fac update la BD
			// stmt.executeQuery(SQL); // astept sa primesc ceva din BD
			
			String SQL2 = "SELECT ID_user FROM [User] WHERE username='" +username+ "'";
			ResultSet rs = stmt.executeQuery(SQL2);
			
			if(rs.next()) {
				idUser = rs.getInt("ID_user");
			}
			
			String SQL1 = "INSERT INTO [Client](ID_user, Nume, Prenume, Sex)"
							+ " VALUES('" +idUser+ "','" + lname + "','" + fname +  "','" + sex + "')";
			stmt.executeUpdate(SQL1);			
			
			Stage stage = new Stage();
			stage = (Stage) passwordTextField.getScene().getWindow();
			stage.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}