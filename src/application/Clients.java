package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Clients {
	public StringProperty id;
	public StringProperty lname, fname;
	public StringProperty street, number, city, county, sex, phone;
	public StringProperty registerDate, birthDate;
	
	public Clients() {
		this(null, null, null, null, null, null, null, null, null, null, null);
	}
	
	public Clients(String id, String lname, String fname, String street, String number, String city, String county, String sex, String phone,
			String registerDate, String birthDate) {
		this.id = new SimpleStringProperty(id);
		this.lname = new SimpleStringProperty(lname);
		this.fname = new SimpleStringProperty(fname);
		this.street = new SimpleStringProperty(street);
		this.number = new SimpleStringProperty(number);
		this.city = new SimpleStringProperty(city);
		this.county = new SimpleStringProperty(county);
		this.sex = new SimpleStringProperty(sex);
		this.phone = new SimpleStringProperty(phone);
		this.registerDate = new SimpleStringProperty(registerDate);
		this.birthDate = new SimpleStringProperty(birthDate);
	}
	
	public String getId() {
		return this.id.get();
	}
	
	public String getLName() {
		return this.lname.get();
	}
	
	public String getFName() {
		return this.fname.get();
	}
	
	public String getStreet() {
		return this.street.get();
	}
	
	public String getNumber() {
		return this.number.get();
	}
	
	public String getCity() {
		return this.city.get();
	}
	
	public String getCounty() {
		return this.county.get();
	}
	
	public String getSex() {
		return this.sex.get();
	}
	
	public String getPhone() {
		return this.phone.get();
	}
	
	public String getRegisterDate() {
		return this.registerDate.get();
	}
	
	public String getBirthDate() {
		return this.birthDate.get();
	}
	
	public StringProperty  lnameProperty() {
        return lname;
    }
	
	public StringProperty  fnameProperty() {
        return fname;
    }
	
	public StringProperty  streetProperty() {
        return street;
    }
	
	public StringProperty  numberProperty() {
        return number;
    }
	
	public StringProperty  cityProperty() {
        return city;
    }
	
	public StringProperty  countyProperty() {
        return county;
    }
	
	public StringProperty  sexProperty() {
        return sex;
    }
	
	public StringProperty  phoneProperty() {
        return phone;
    }
	
	public StringProperty  registerDateProperty() {
        return registerDate;
    }
	
	public StringProperty  birthDateProperty() {
        return birthDate;
    }
}