package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {
	public StringProperty id;
	public StringProperty client;
	public StringProperty service;
	public StringProperty beautician;
	public StringProperty date;
	public StringProperty hour;
	
	public Appointment() {
		this(null, null, null, null, null, null);
	}
	
	public Appointment(String id, String client, String service, String beautician, String date, String hour) {
		this.id = new SimpleStringProperty(id);
		this.client = new SimpleStringProperty(client);
		this.service = new SimpleStringProperty(service);
		this.beautician = new SimpleStringProperty(beautician);
		this.date = new SimpleStringProperty(date);
		this.hour = new SimpleStringProperty(hour);
	}
	
	public String getId() {
		return this.id.get();
	}
	
	public String getClient() {
		return this.client.get();
	}
	
	public String getService() {
		return this.service.get();
	}
	
	public String getBeautician() {
		return this.beautician.get();
	}
	
	public String getDate() {
		return this.date.get();
	}
	
	public String getHour() {
		return this.hour.get();
	}
	
	
	public StringProperty  idProperty() {
        return id;
    }
    
    public StringProperty  clientProperty() {
        return client;
    }
    
	public StringProperty  serviceProperty() {
        return service;
    }
	
	public StringProperty  beauticianProperty() {
        return beautician; 
    }
	
	public StringProperty  dateProperty() {
        return date;
    }
    
    public StringProperty  hourProperty() {
        return hour;
    }
}
