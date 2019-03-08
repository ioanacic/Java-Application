package application;

class SelectedService
{ 
    // static variable single_instance of type Singleton 
    private static SelectedService single_instance = null; 
  
    // variable of type String 
    public Integer idServ; 
  
    // private constructor restricted to this class itself 
    private SelectedService() 
    { 
    	idServ = 0; 
    } 
  
    // static method to create instance of Singleton class 
    public static SelectedService testInstance() 
    { 
        if (single_instance == null) 
            single_instance = new SelectedService(); 
  
        return single_instance; 
    } 
}