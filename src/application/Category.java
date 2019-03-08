package application;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {
	public StringProperty  id;
	public StringProperty  name;
	public StringProperty  description;
	
	public Category() {
		this(null, null, null);
	}
	
//	public Category(String name, String id) {
//        this.name = new SimpleStringProperty(name);
//        this.id = new SimpleStringProperty(id);
//	}
	
	public Category(String name, String description) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
	}
	
	public Category(String id, String name, String description) {
		this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
	}
	
	public void setId(StringProperty id) {
		this.id = id;
	}
	
	public void setName(StringProperty name) {
		this.name = name;
	}
	
	public void setDescription(StringProperty description) {
		this.description = description;
	}
	
	public String getId() {
		return this.id.get();
	}
	
	public String getName() {
		return this.name.get();
	}
	
	public String getDescription() {
		return this.description.get();
	}
	
	 public StringProperty  idProperty() {
	        return id;
	    }
	
    public StringProperty  nameProperty() {
        return name;
    }
    
    public StringProperty  descriptionProperty() {
        return description;
    }
}
