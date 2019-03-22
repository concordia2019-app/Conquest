package Helper;

import java.util.ArrayList;

import Model.Country;
import Model.Editor;
import Model.Map;
import Model.Player;

/**
 * This class is created for most of calculations and processing of Player
 * class.
 * 
 * @author FarzadShamriz
 *
 */
public class PlayerHelper {

	/**
	 * This method is calculating number of armies for increase and decrease form and to a country
	 * @param fromCountryId ID of source country
	 * @param toCountryId ID of target country
	 * @param armyNumbToMove number of armies to decrease or increase
	 * @return
	 */
	public boolean calculationOfMovement(int fromCountryId, int toCountryId, int armyNumbToMove) {
		UIHelper uiHelper = new UIHelper();
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		Country sourceCountry = uiHelper.getCountryById(countries, fromCountryId);
		int sourceCountryArmies = sourceCountry.getArmy();
		Country targegCountry = uiHelper.getCountryById(countries, toCountryId);

		if ((sourceCountryArmies - 1) < armyNumbToMove) {
			return false;
		} else {
			decreaseCountryArmies(fromCountryId, armyNumbToMove);
			increaseCountryArmies(toCountryId, armyNumbToMove);
			return true;
		}

	}
        /**
        * Create and add a countryid to the player array and increase the countriesID
        * @param countrID the id of country 
        * @param playerID the id of player
        * @param players it is the array of players for modifications
        */
        public Player[] addCountryIDToPlayer( Player[] players, int countryID , int playerID)
        {
              if (players== null)  
                   return null; 
              
            Player[] tempPlayers=new Player[players.length];
            
            for(int i=0 ; i<players.length;i++)
            {
             if(players[i].getPlayerID()== playerID)  
              {
                 Player player= players[i];
                  
                 int[] countriesID= new int[player.getCountryID().length+1];
                 for(int j=0 ; j<player.getCountryID().length;j++)
                  {
                      countriesID[j]=player.getCountryID()[j];
                  }
               countriesID[player.getCountryID().length]=countryID;
               player.setCountryId(countriesID);
               tempPlayers[i]=player;
              }
             else
                 tempPlayers[i]=players[i];
             }
            return tempPlayers;
        }
        
        public Player[] removeCountryIDToPlayer( Player[] players, int countryID , int playerID)
        {
            if (players== null)  
                   return null; 
              
            Player[] tempPlayers=new Player[players.length];
            
            for(int i=0 ; i<players.length;i++)
            {
             if(players[i].getPlayerID()== playerID)  
              {
                  Player player= players[i];
                 
                 int[] countriesID= new int[player.getCountryID().length-1];
                 int countryIndex=0;
                 for(int j=0 ; j<player.getCountryID().length;j++)
                  {
                      if( player.getCountryID()[j]!= countryID)
                      {
                           countriesID[countryIndex]=player.getCountryID()[j];
                           countryIndex++;
                       }
                  }
               player.setCountryId(countriesID);
               tempPlayers[i]=player;
              }
             else
                 tempPlayers[i]=players[i];
             }
            return tempPlayers;
            
        }
	/**
	 * This method increase armies of country which retrieved by Cid (Country ID)
	 * @param Cid Country ID
	 * @param armiesNumber Number of Armies for increasing
	 */
	public void increaseCountryArmies(int Cid, int armiesNumber) {
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		for (Country country : countries) {
			int currentCountryId = country.getCountryID();
			int currentCountryArmies = country.getArmy();
			if (currentCountryId == Cid) {
				int calculatedArmies = currentCountryArmies + armiesNumber;
				country.setArmy(calculatedArmies);
			}
		}
	}

	/**
	 * This method decrease armies of country which retrieved by Cid (Country ID)
	 * @param Cid Country ID
	 * @param armiesNumber Number of Armies for decreasing
	 */
	public void decreaseCountryArmies(int Cid, int armiesNumber) {
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		for (Country country : countries) {
			int currentCountryId = country.getCountryID();
			int currentCountryArmies = country.getArmy();
			if (currentCountryId == Cid) {
				int calculatedArmies = currentCountryArmies - armiesNumber;
				country.setArmy(calculatedArmies);
			}
		}
	}

}
