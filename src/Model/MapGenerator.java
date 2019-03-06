package Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MapGenerator {

	Scanner input;

	public MapGenerator() {
		input = new Scanner(System.in);
	}

	/**
	 * This method get the JSON file path and put the details of data to the
	 * JSONArray.
	 * 
	 * @param name country name.
	 * @return (JSONObject) response
	 */
	public JSONObject getObjectbyName(String name, String filePath) {
		if (filePath.isEmpty())
			filePath = System.getProperty("user.dir") + "\\bin\\ResourceProject\\CountrySample.json";
		JSONObject response = new JSONObject();
		response.put("status", 0);
		response.put("data", "");
		while (true) {
			try {
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(filePath));
				JSONObject jsonObject = (JSONObject) obj;
				// we put the details of an object to the data response.
				response.put("data", (JSONArray) jsonObject.get(name));
				response.put("status", 1);
				return response;
			} catch (FileNotFoundException e) {
				printException(e.getMessage());
			} catch (IOException e) {
				printException(e.getMessage());
			} catch (ParseException e) {
				printException(e.getMessage());
			} catch (Exception e) {
				printException(e.getMessage());
			}

		}
	}

	private void printException(String exceptionErrorMessage) {
		if (exceptionErrorMessage == null || exceptionErrorMessage.isEmpty()) {
			Integer eneteredVal = -1;
			System.out.println("Check and press enter or press 0 to exit.");
			System.out.println("Put your file in " + System.getProperty("user.dir")
					+ "\\bin\\ResourceProject\\CountrySample.json");
			eneteredVal = Integer.parseInt(input.next());
			if (eneteredVal == 0) {
				System.exit(0);
			}
		} else {
			Integer eneteredVal = -1;
			System.out.print(exceptionErrorMessage);
			System.out.println("Check and press enter or press 0 to exit.");
			System.out.println("Put your file in " + System.getProperty("user.dir")
					+ "\\bin\\ResourceProject\\CountrySample.json");
			eneteredVal = Integer.parseInt(input.next());
			if (eneteredVal == 0) {
				System.exit(0);
			}
		}
	}

	/**
	 * This method read the map from the uploaded file
	 * 
	 * @param filePath
	 * @return
	 */
	public ArrayList<Country> mapReader(String filePath) {

		ArrayList<Country> importedCountries = new ArrayList<Country>();
		try {
			JSONObject allCountryDetailsResponse = getObjectbyName("Countries",filePath);
			if ((int) allCountryDetailsResponse.get("status") == 1) {
				JSONArray allCountryDetails = (JSONArray) allCountryDetailsResponse.get("data");
				Object[] jsonCountries = allCountryDetails.toArray();
				for (int i = 0; i < allCountryDetails.size(); i++) {
					JSONObject countryDetails = (JSONObject) jsonCountries[i];
					String name = (String) countryDetails.get("name");
					Integer id = Integer.parseInt(countryDetails.get("id").toString());
					Integer continentId = Integer.parseInt(countryDetails.get("continent").toString());
					Integer army = Integer.parseInt(countryDetails.get("numberOfArmies").toString());
					JSONArray adjacentCountriesId = (JSONArray) countryDetails.get("adjacentCountries");
					int[] numbers = new int[adjacentCountriesId.size()];
					// Extract numbers from JSON array.
					int[] adjCountries = new int[numbers.length];
					for (int j = 0; j < adjacentCountriesId.size() - 1; ++j) {
						adjCountries[j] = (Integer.parseInt((adjacentCountriesId.get(j)).toString()));
					}
					Country c = new Country(name, id, continentId, army, adjCountries, 0, "");
					importedCountries.add(c);
				}
			} else {
			}
		} catch (NumberFormatException e) {
			printException(e.getMessage());
		} catch (Exception e) {
			printException(e.getMessage());
		}
		return importedCountries;

	}

}
