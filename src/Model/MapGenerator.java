package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;

import View.MapView;

/**
 * This class read and write a map from the JSON file.
 * 
 * @author Pegah
 *
 */
public class MapGenerator {

	Scanner input;

	public MapGenerator() {
		input = new Scanner(System.in);
	}

	/**
	 * This method get the JSON file path and put the details of data to the
	 * JSONArray.
	 * 
	 * @param name this parameter is a country name.
	 * @return (JSONObject) response.
	 */
	public JSONObject getObjectbyName(String name, String filePath) {
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

	/**
	 * This method prints the error message, if any error occurs.
	 * 
	 * @param exceptionErrorMessage it is the error message parameter.
	 */
	private void printException(String exceptionErrorMessage) {
		if (exceptionErrorMessage == null || exceptionErrorMessage.isEmpty()) {
			System.out.println("Check your file. There is something wrong in the content of the file.");
			System.out.println("Check and press enter");
			input.nextLine();
		} else {
			System.out.print(exceptionErrorMessage);
			System.out.println("Check and press enter");
			input.nextLine();
		}
	}

	/**
	 * This method read the map from the uploaded JSON file.
	 * 
	 * @param filePath this is a path of the JSON file.
	 * @return (ArrayList<Country>) importedCountries.
	 */
	public ArrayList<Country> MapReader(String filePath) {

		ArrayList<Country> importedCountries = new ArrayList<Country>();
		try {
			JSONObject allCountryDetailsResponse = getObjectbyName("Countries", filePath);
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
			} else {}
			MapView map = new MapView();
			map.printMainMap(importedCountries);
		} catch (NumberFormatException e) {
			printException(e.getMessage());
		} catch (Exception e) {
			printException(e.getMessage());
		}
		return importedCountries;

	}

	/**
	 * This method writes and update the inputs to JSON file.
	 * 
	 * @param countries this parameter is a list of countries.
	 * @return (String) obj.
	 */
	public static String writeMap(ArrayList<Country> countries, String filePath) {
		Gson gson = new Gson();
		String obj = gson.toJson(countries);
		File file = new File(filePath);
		try {
			if (file.createNewFile()) {
				int writeStatus = fileWriter(file, obj);
				if(writeStatus == 1) {
					System.out.println("File is created!");
				}else {
					System.out.println("Error while creating the file!");
				}
			} else {
				file.delete();
				int writeStatus = fileWriter(file,obj);
				if(writeStatus == 1) {
					System.out.println("File is updated!");
				}else {
					System.out.println("Error while updating the file!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * This function writes the file.
	 * @param file this is the input file object.
	 * @param obj this is the input object to write into the file.
	 * @return (int) status.
	 * @throws IOException 
	 */
	public static int fileWriter(File file, String obj) throws IOException {
		int status = 0;
		try {
			FileWriter writer = new FileWriter(file);
			status = 1;
			writer.write(obj);
			writer.close();

		} catch (IOException e) {
			status = 0;
			e.printStackTrace();
		}
		return status;
	}
}
