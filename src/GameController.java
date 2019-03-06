/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amirhossein
 */
public class GameController {
    
    
    
    private static GameController instance;
    
    
    public static GameController getInstance()
    {
        if(instance==null)
        {
            instance=new GameController();
            return instance;
        }
        else
        {
            return instance;
        }
             
    }
    
    public void SetAttack()
    {
        System.out.println("hi");
    }
    
}
