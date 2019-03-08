package application;

class SelectedBeautician
{ 
    // static variable single_instance of type Singleton 
    private static SelectedBeautician single_instance = null; 
  
    // variable of type String 
    public Integer idCosm; 
  
    // private constructor restricted to this class itself 
    private SelectedBeautician() 
    { 
    	idCosm = 0; 
    } 
  
    // static method to create instance of Singleton class 
    public static SelectedBeautician testInstance() 
    { 
        if (single_instance == null) 
            single_instance = new SelectedBeautician(); 
  
        return single_instance; 
    } 
}