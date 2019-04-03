/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

public class BenovolentPlayer extends Player {

	public BenovolentPlayer(int playerID, String playerName, int[] countryID) {
		super(playerID, playerName, countryID);
	}

	@Override
	/**
	 * This type of player never attack
	 */
	public ArrayList<Country> attackPlayer(ArrayList<Country> countryList) {
		Map map = Map.getInstance();
		return map.getCountries();
	}

	@Override
	public void movePlayer(ArrayList<Country> countryList) {
		//TODO find the weaker country
		
		//TODO find the adj of weaker country 
		
		//TODO armies to weeker one
	}
}
