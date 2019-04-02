package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amirhossein
 */
public class TournamentController 
{
    
    private static TournamentController instance;
    /*
    *  creating in instance from the object singleton
    */
    public static TournamentController getTournamentControllerInstance ()
    {
        if(instance==null)
            instance= new TournamentController();
        return instance;
    }
    
}
