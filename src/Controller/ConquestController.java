package Controller;

public class ConquestController {

    private static ConquestController instance;
    
    
    public static ConquestController getInstance()
    {
        if(instance==null)
        {
            instance=new ConquestController();
            return instance;
        }
        else
        {
            return instance;
        }
             
    }
	
}
