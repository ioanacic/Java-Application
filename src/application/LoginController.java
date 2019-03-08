package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private Label errorLabelUser;
	@FXML
	private Label errorLabelPassword;

	// functionare buton login
	@FXML
	private void loginButtonPressed() {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		// Create a variable for the connection string.
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Salon;integratedSecurity=true";

		// verifica daca username-ul dat este asociat unui user cu permisiune de client
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT Password, Permisiune, ID_user FROM [User] WHERE Username='" + username
					+ "' AND Permisiune!=1";

			ResultSet rs = stmt.executeQuery(SQL);

			// if true it means there was at least one result => user exists in database
			if (rs.next()) {
				if (rs.getString("Password").equals(password)) {

					Integer idUser = rs.getInt("ID_user");

					SQL = "SELECT ID_Client FROM [Client] WHERE ID_user=" + idUser;
					rs = stmt.executeQuery(SQL);

					// salveaza in Singleton id-ul user-ului/clientului curent ca sa il putem folosi si sa fie cunocut
					// in toate ecranele care vor urma
					if (rs.next()) {
						Singleton.getInstance().idClient = rs.getInt("ID_Client");
					}

					try {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("TestSecondScreen.fxml"));

						Scene scene = new Scene(fxmlLoader.load(), 750, 400);
						Stage stage = new Stage();

						// stage.setTitle("New Window");
						stage.setScene(scene);
						stage.show();

						// FARA astea pt register
						// stie ca acolo unde e parola e si "obiectul" (fereastra) in care am lucrat si
						// o inchide
						stage = (Stage) passwordTextField.getScene().getWindow();
						stage.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					errorLabelPassword.setVisible(true);
				}

			} else {
				errorLabelUser.setVisible(true); // o fac vizibila pe TRUE
			}
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// verifica daca username-ul dat este asociat unui user cu permisiune de administrator
		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQLAdmin = "SELECT Password, Permisiune FROM [User] WHERE Username='" + username
					+ "' AND Permisiune=1";

			ResultSet rsAdmin = stmt.executeQuery(SQLAdmin);

			// if true it means there was at least one result => user exists in database
			if (rsAdmin.next()) {
				if (rsAdmin.getString("Password").equals(password)) {
					try {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("User-ADMIN.fxml"));

						Scene scene = new Scene(fxmlLoader.load(), 680, 450);
						Stage stage = new Stage();

						stage.setScene(scene);
						stage.show();
						stage = (Stage) passwordTextField.getScene().getWindow();
						stage.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					errorLabelPassword.setVisible(true);
					// errorLabel.setText("Parola este incorecta!");
				}

			} else {
				errorLabelUser.setVisible(true); // o fac vizibila pe TRUE
				// errorLabel.setText("Userul nu exista!");
			}
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// eroare user inexistent
	@FXML
	public void hideErrorUser() {
		errorLabelUser.setVisible(false);
	}

	// eroare parola gresita
	@FXML
	public void hideErrorPassword() { // o ascund pe FALSE
		errorLabelPassword.setVisible(false);
	}

	// buton register - creare user nou
	@FXML
	private void registerButtonPressed() {
		try {
			FXMLLoader fxmlLoader2 = new FXMLLoader();
			fxmlLoader2.setLocation(getClass().getResource("RegisterInterface.fxml"));

			Scene scene2 = new Scene(fxmlLoader2.load(), 400, 500);
			Stage stage2 = new Stage();

			stage2.setTitle("Register");
			stage2.setScene(scene2);
			stage2.show();

			stage2 = (Stage) passwordTextField.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
