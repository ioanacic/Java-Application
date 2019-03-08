package application;

class SelectedUser
{ 
    // static variable single_instance of type Singleton 
    private static SelectedUser single_instance = null; 
  
    // variable of type String 
    public Integer idClient; 
    public String street;
  
    // private constructor restricted to this class itself 
    private SelectedUser() 
    { 
        idClient = 0;
        street = null;
    } 
  
    // static method to create instance of Singleton class 
    public static SelectedUser testInstance() 
    { 
        if (single_instance == null) 
            single_instance = new SelectedUser(); 
  
        return single_instance; 
    } 
}