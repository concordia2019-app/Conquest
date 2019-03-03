package Helper;

import java.util.ArrayList;
import Model.Country;

/**
 * Parsing and calculation that needs in UI class will be proceeded in this
 * class
 * 
 * @author F.S
 *
 */
public class UIHelper {
	/**
	 * <p>
	 * This method try to parse input string to integer
	 * </p>
	 * <ul>
	 * <li>If the String can be parsed to Integer then return true.</li>
	 * <li>Else false is returned.</li>
	 * </ul>
	 * 
	 * @param Value input String
	 * @return Parsing status
	 */
	public boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * This method will return country which user requested by Id
	 * 
	 * @param countryList for searching
	 * @param countryId   Id of country to search
	 * @return
	 */
	public Country getCountryById(ArrayList<Country> countryList, Integer countryId) {

		for (Country country : countryList) {
			Integer countryItemId = country.getCountryID();
			if (countryItemId == countryId) {
				return country;
			}
		}
		return null;
	}
	
	/**
	 * This method check in int is find in the array
	 * @param listId the list for searching
	 * @param id the value or id to search
	 * @return if it find, then return true, else return false;
	 */
	public boolean isIdExistInList(int[] listId, int id) {
		for (int i = 0; i < listId.length; i++) {
			if (listId[i] == id) {
				return true;
			}
		}
		return false;
	}
}
