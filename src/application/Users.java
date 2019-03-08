package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Users {
	public StringProperty id;
	public StringProperty username;
	public StringProperty password;
	public StringProperty permission;
	public StringProperty lname;
	public StringProperty fname;
	public StringProperty email;
	
	public Users() {
		this(null, null, null, null, null, null, null);
	}
	
	public Users(String id, String username, String password, String permission, String lname, String fname, String email) {
		this.id = new SimpleStringProperty(id);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.permission = new SimpleStringProperty(permission);
		this.lname = new SimpleStringProperty(lname);
		this.fname = new SimpleStringProperty(fname);
		this.email = new SimpleStringProperty(email);
	}
	
	public String getId() {
		return this.id.get();
	}
	
	public String getUsername() {
		return this.username.get();
	}
	
	public String getPassword() {
		return this.password.get();
	}
	
	public String getPermission() {
		return this.permission.get();
	}
	
	public String getLname() {
		return this.lname.get();
	}
	
	public String getFname() {
		return this.fname.get();
	}
	
	public String getEmail() {
		return this.email.get();
	}
	
	
	public StringProperty fnameProperty() {
        return fname;
    }
	
	public StringProperty lnameProperty() {
        return lname;
    }
	
	public StringProperty usernameProperty() {
        return username;
    }
	
	public StringProperty passwordProperty() {
        return password;
    }
	
	public StringProperty permissionProperty() {
        return permission;
    }
	
	public StringProperty emailProperty() {
        return email;
    }
}
