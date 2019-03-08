package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import com.sun.javafx.css.converters.StringConverter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController {
	
	@FXML
	private Label labelLogout;
	
	// buton logout din ecranul de admin
	@FXML
	private void logoutButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("LoginInterface.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 400, 250);
			Stage stage = new Stage();

			stage.setTitle("Login");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
			stage.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// buton accesare ecran categorii
	@FXML
	private void categoriesButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("CategoriesADMIN.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 800, 450);
			Stage stage = new Stage();

			stage.setTitle("Categories");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// buton accesare ecran cosmeticieni
	@FXML
	private void beauticiansButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("BeauticiansADMIN.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
			Stage stage = new Stage();

			stage.setTitle("Beauticians");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// buton accesare ecran servicii
	@FXML
	private void servicesButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ServicesADMIN.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 1000, 950);
			Stage stage = new Stage();

			stage.setTitle("Services");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// buton accesare ecran clienti
	@FXML
	private void clientsButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ClientsADMIN.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 1260, 810);
			Stage stage = new Stage();

			stage.setTitle("Clients");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//buton accesare ecran useri
	@FXML
	private void usersButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("Users.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 730, 600);
			Stage stage = new Stage();

			stage.setTitle("Users");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//buton accesare ecran programari
	@FXML
	private void appointmentButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("AppointmentADMIN.fxml"));

			Scene scene = new Scene(fxmlLoader.load(), 750, 450);
			Stage stage = new Stage();

			stage.setTitle("Appointment");
			stage.setScene(scene);
			stage.show();

			stage = (Stage) labelLogout.getScene().getWindow();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}