package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Services {
	public StringProperty id;
	public StringProperty idCateg;
	public StringProperty name;
	public StringProperty time;
	public StringProperty price;
	
	public Services() {
		this(null, null, null, null, null);
	}
	
	public Services(String name, String time, String price) {
		this.name = new SimpleStringProperty(name);
		this.time = new SimpleStringProperty(time);
		this.price = new SimpleStringProperty(price);
	}
	
	public Services(String id, String idCateg, String name, String time, String price) {
		this.id = new SimpleStringProperty(id);
		this.idCateg = new SimpleStringProperty(idCateg);
		this.name = new SimpleStringProperty(name);
		this.time = new SimpleStringProperty(time);
		this.price = new SimpleStringProperty(price);
	}
	
	public String getId() {
		return this.id.get();
	}
	
	public String getIdCateg() {
		return this.idCateg.get();
	}
	
	public String getName() {
		return this.name.get();
	}
	
	public String getTime() {
		return this.time.get();
	}
	
	public String getPrice() {
		return this.price.get();
	}
	
	public StringProperty  idProperty() {
        return id;
    }
	
	public StringProperty  idCategProperty() {
        return idCateg;
    }
	
	public StringProperty  nameProperty() {
        return name;
    }
	
	public StringProperty  timeProperty() {
        return time;
    }
	
	public StringProperty  priceProperty() {
        return price;
    }
}
