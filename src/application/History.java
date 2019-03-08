package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class History {
	public StringProperty service;
	public StringProperty beautician;
	public StringProperty date;
	public StringProperty time;
	
	public History() {
		this(null, null, null, null);
	}
	
	public History(String service, String beautician, String date, String time) {
		this.service = new SimpleStringProperty(service);
		this.beautician = new SimpleStringProperty(beautician);
		this.date = new SimpleStringProperty(date);
		this.time = new SimpleStringProperty(time);
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
	
	public String getTime() {
		return this.time.get();
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
    
    public StringProperty  timeProperty() {
        return time;
    }
}
