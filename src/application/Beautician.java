package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Beautician {
	public StringProperty id;
	public StringProperty firstName;
	public StringProperty lastName;
	public StringProperty CNP;
	public StringProperty street;
	public StringProperty number;
	public StringProperty city;
	public StringProperty county;
	public StringProperty sex;
	public StringProperty phoneNr;
	public StringProperty employmentDate;
	public StringProperty birthDate;
	public StringProperty salary;
	
	public Beautician() {
		this(null, null, null, null, null, null, null, null, null, null, null, null, null);
	}
	
	public Beautician(String id, String fname, String lname, String phone) {
		this.id = new SimpleStringProperty(id);
		this.firstName = new SimpleStringProperty(fname);
		this.lastName = new SimpleStringProperty(lname);
		this.phoneNr = new SimpleStringProperty(phone);
	}
	
	public Beautician(String id, String fname, String lname, String CNP, String street, String number, String city,
			String county, String sex, String phone, String employmentDate, String birthDate, String salary) {
		
		this.id = new SimpleStringProperty(id);
		this.firstName = new SimpleStringProperty(fname);
		this.lastName = new SimpleStringProperty(lname);
		this.CNP = new SimpleStringProperty(CNP);
		this.street = new SimpleStringProperty(street);
		this.number = new SimpleStringProperty(number);
		this.city = new SimpleStringProperty(city);
		this.county = new SimpleStringProperty(county);
		this.sex = new SimpleStringProperty(sex);
		this.phoneNr = new SimpleStringProperty(phone);
		this.employmentDate = new SimpleStringProperty(employmentDate);
		this.birthDate = new SimpleStringProperty(birthDate);
		this.salary = new SimpleStringProperty(salary);
	}
	
	public String getId() {
		return this.id.get();
	}
	
	public String getlName() {
		return this.lastName.get();
	}
	
	public String getfName() {
		return this.firstName.get();
	}
	
	public String getCNP() {
		return this.CNP.get();
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
		return this.phoneNr.get();
	}
	
	public String getEmploymentDate() {
		return this.employmentDate.get();
	}
	
	public String getBirthDate() {
		return this.birthDate.get();
	}
	
	public String getSalary() {
		return this.salary.get();
	}
	
	
	public StringProperty fnameProperty() {
        return firstName;
    }
	
	public StringProperty lnameProperty() {
        return lastName;
    }
	
	public StringProperty cnpProperty() {
        return CNP;
    }
	
	public StringProperty streetProperty() {
        return street;
    }
	
	public StringProperty numberProperty() {
        return number;
    }
	
	public StringProperty cityProperty() {
        return city;
    }
	
	public StringProperty countyProperty() {
        return county;
    }
	
	public StringProperty sexProperty() {
        return sex;
    }
	
	public StringProperty phoneProperty() {
        return phoneNr;
    }
	
	public StringProperty employmentDateProperty() {
        return employmentDate;
    }
	
	public StringProperty birthDateProperty() {
        return birthDate;
    }
	
	public StringProperty salaryProperty() {
        return salary;
    }
}
