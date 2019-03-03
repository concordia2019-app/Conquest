package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Date: Feb 15-2019
 * <p>
 * This class is for initializing the country class and putting the map values
 * into it.
 * 
 * @author AHasheminezhad
 */

public class Editor {
	

	public ArrayList<Country> setCountries(ArrayList<Country> country) {
		
		country.add(new Country("Norway", 1, 1, 0, new int[] { 2, 3, 20 }, 1, "Player 1"));
		country.add(new Country("Denmark", 2, 1, 0, new int[] { 1, 3, 4 }, 1, "Player 1"));
		country.add(new Country("Sweden", 3, 1, 0, new int[] { 1, 2, 14 }, 1, "Player 1"));
		country.add(new Country("Germany", 4, 2, 0, new int[] { 2, 5, 7, 8, 9, 10, 14, 16 }, 2, "Player 2"));
		country.add(new Country("Netherlands", 5, 0, 2, new int[] { 4, 6, 7 }, 2, "Player 2"));
		country.add(new Country("UK", 6, 2, 0, new int[] { 5, 7, 9 }, 2, "Player 2"));
		country.add(new Country("Belgium", 7, 2, 0, new int[] { 4, 5, 6, 8, 9 }, 2, "Player 2"));
		country.add(new Country("Luxemburg", 8, 2, 0, new int[] { 4, 7, 9 }, 2, "Player 2"));
		country.add(new Country("France", 9, 2, 0, new int[] { 4, 6, 7, 8, 10, 11, 12, 18 }, 2, "Player 2"));
		country.add(new Country("Switzerland", 10, 2, 0, new int[] { 4, 9, 11, 16 }, 2, "Player 2"));
		country.add(new Country("Italy", 11, 2, 0, new int[] { 9, 10, 16 }, 2, "Player 2"));
		country.add(new Country("Spain", 12, 3, 0, new int[] { 9, 13 }, 3, "Player 3"));
		country.add(new Country("Portugal", 13, 3, 0, new int[] { 12 }, 3, "Player 3"));
		country.add(new Country("Poland", 14, 4, 0, new int[] { 3, 4, 15, 18 }, 4, "Player 4"));
		country.add(new Country("Czechoslovakia", 15, 4, 0, new int[] { 14, 16, 17, 18 }, 4, "Player 4"));
		country.add(new Country("Austria", 16, 4, 0, new int[] { 4, 10, 11, 15, 17, 19 }, 4, "Player 4"));
		country.add(new Country("Hungary", 17, 4, 0, new int[] { 15, 16, 18, 19 }, 4, "Player 4"));
		country.add(new Country("Romania", 18, 4, 0, new int[] { 9, 14, 15, 17, 19 }, 4, "Player 4"));
		country.add(new Country("Yugoslavia", 19, 4, 0, new int[] { 16, 17, 18, 20 }, 4, "Player 4"));
		country.add(new Country("Albania", 20, 4, 0, new int[] { 1, 19 }, 4, "Player 4"));		
		return country;
	}



}