package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	private boolean readFileStatus = true;

	public MapGenerator() {
		input = new Scanner(System.in);
	}

	public boolean getReadMapStatus() {
		return this.readFileStatus;
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
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			// we put the details of an object to the data response.
			response.put("data", (JSONArray) jsonObject.get(name));
			response.put("status", 1);

		} catch (FileNotFoundException e) {
			printException(e.getMessage());
		} catch (IOException e) {
			printException(e.getMessage());
		} catch (ParseException e) {
			printException(e.getMessage());
		} catch (Exception e) {
			printException(e.getMessage());
		}
		return response;
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
			// input.nextLine();
		} else {
			System.out.print(exceptionErrorMessage);
			System.out.println("Check and press enter");
			// input.nextLine();
		}
	}

	/**
	 * This method read the map from the uploaded JSON file.
	 * 
	 * @param filePath this is a path of the JSON file.
	 * @return (ArrayList<Country>) importedCountries.
	 */
	public ArrayList<Country> mapReader(String filePath) {

		ArrayList<Country> importedCountries = new ArrayList<Country>();
		try {

			JSONObject allCountryDetailsResponse = getObjectbyName("Countries", filePath);
			if ((int) allCountryDetailsResponse.get("status") == 1) {
				JSONArray allCountryDetails = (JSONArray) allCountryDetailsResponse.get("data");
				Object[] jsonCountries = allCountryDetails.toArray();
				for (int i = 0; i < allCountryDetails.size(); i++) {
					JSONObject countryDetails = (JSONObject) jsonCountries[i];
					String name = (String) countryDetails.get("countryName");
					String playername = (String) countryDetails.get("playerName");
					Integer playerId = Integer.parseInt(countryDetails.get("playerID").toString());
					Integer id = Integer.parseInt(countryDetails.get("countryID").toString());
					Integer continentId = Integer.parseInt(countryDetails.get("continentID").toString());
					Integer army = Integer.parseInt(countryDetails.get("army").toString());
					JSONArray adjacentCountriesId = (JSONArray) countryDetails.get("adjacentCountriesID");
					int[] numbers = new int[adjacentCountriesId.size()];
					// Extract numbers from JSON array.
					int[] adjCountries = new int[numbers.length];
					for (int j = 0; j < adjacentCountriesId.size() - 1; ++j) {
						adjCountries[j] = (Integer.parseInt((adjacentCountriesId.get(j)).toString()));
					}
					Country c = new Country(name, id, continentId, army, adjCountries, playerId, playername);
					importedCountries.add(c);
				}
			} else {
				readFileStatus = false;
			}
			MapView map = new MapView();
			map.printMainMap(importedCountries);
		} catch (NumberFormatException e) {
			readFileStatus = false;
			printException(e.getMessage());
		} catch (Exception e) {
			readFileStatus = false;
			printException(e.getMessage());
		}
		return importedCountries;

	}

	public ArrayList<Player> assignePlayers(String filePath) {
		ArrayList<Player> importedPlayers = new ArrayList<Player>();
		try {
			JSONObject allplayerDetailsResponse = getObjectbyName("Players", filePath);
			if ((int) allplayerDetailsResponse.get("status") == 1) {
				JSONArray allPlayeryDetails = (JSONArray) allplayerDetailsResponse.get("data");
				Object[] jsonPlayers = allPlayeryDetails.toArray();
				for (int i = 0; i < allPlayeryDetails.size(); i++) {
					JSONObject playerDetails = (JSONObject) jsonPlayers[i];
					String name = (String) playerDetails.get("playerName");
					Integer id = Integer.parseInt(playerDetails.get("playerID").toString());
					JSONArray countriesId = (JSONArray) playerDetails.get("countryID");
					int[] numbers = new int[countriesId.size()];
					int[] countryId = new int[numbers.length];
					for (int j = 0; j < countriesId.size() - 1; ++j) {
						countryId[j] = (Integer.parseInt((countriesId.get(j)).toString()));
					}
					boolean allowToGetCard = Boolean.valueOf((boolean) playerDetails.get("allowToGetCard"));
					JSONArray cardLists = (JSONArray) playerDetails.get("cards");
					ArrayList<Card> cardList = new ArrayList<Card>();
					for (int k = 0; k < cardLists.size() - 1; ++k) {
						Card c = new Card(CardType.valueOf((cardLists.get(k)).toString()));
						cardList.add(c);
					}
					Player p = new Player(id, name, countryId);
					p.setAllowingStatus(allowToGetCard);
					p.setCards(cardList);
					importedPlayers.add(p);
				}
			} else {
				readFileStatus = false;
			}
		} catch (NumberFormatException e) {
			readFileStatus = false;
			printException(e.getMessage());
		} catch (Exception e) {
			readFileStatus = false;
			printException(e.getMessage());
		}
		// System.out.println(importedPlayers);
		return importedPlayers;
	}

	public static String savePlayers(ArrayList<Player> players, String filePath) {
		JSONArray playersArray = new JSONArray();
		for (int i = 0; i < players.size(); i++) {
			JSONObject playerObject = new JSONObject();
			int playerId = players.get(i).getPlayerID();
			String playerName = players.get(i).getPlayerName();
			int[] countriesId = players.get(i).getCountryID();
			boolean allowToGetCards = players.get(i).getAllowingCardStatus();
			ArrayList<Card> cardLists = players.get(i).getCards();
			JSONArray cards = new JSONArray();
			for (int j = 0; j < cardLists.size() - 1; ++j) {
				String cardType = cardLists.get(j).getCardType().toString();
				cards.add(cardType);
			}
			playerObject.put("playerID", playerId);
			playerObject.put("playerName", playerName);
			playerObject.put("countryID", countriesId);
			playerObject.put("allowToGetCard", allowToGetCards);
			playerObject.put("cards", cards);
			playersArray.add(playerObject);
		}
		Gson gson = new Gson();
		String obj = "{\"Players\":" + gson.toJson(playersArray) + "}";
		File file = new File(filePath);
		try {
			if (file.createNewFile()) {
				int writeStatus = fileWriter(file, obj);
				if (writeStatus == 1) {
					System.out.println("File is created!");
				} else {
					System.out.println("Error while creating the file!");
				}
			} else {
				file.delete();
				int writeStatus = fileWriter(file, obj);
				if (writeStatus == 1) {
					System.out.println("File is updated!");
				} else {
					System.out.println("Error while updating the file!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * This method writes and update the inputs to JSON file.
	 * 
	 * @param countries this parameter is a list of countries.
	 * @return (String) obj.
	 */
	public static String writeMap(ArrayList<Country> countries, String filePath) {
		Gson gson = new Gson();
		String obj = "{\"Countries\":" + gson.toJson(countries) + "}";
		File file = new File(filePath);
		try {
			if (file.createNewFile()) {
				int writeStatus = fileWriter(file, obj);
				if (writeStatus == 1) {
					System.out.println("File is created!");
				} else {
					System.out.println("Error while creating the file!");
				}
			} else {
				file.delete();
				int writeStatus = fileWriter(file, obj);
				if (writeStatus == 1) {
					System.out.println("File is updated!");
				} else {
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
	 * 
	 * @param file this is the input file object.
	 * @param obj  this is the input object to write into the file.
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
