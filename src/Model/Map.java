package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {
	
	private ArrayList<Country> countries = new ArrayList<Country>();
	
	public void MainMap() {

		String table = "|%-14d|%-16s|%-15d|%-16d|%-28s|%-15s|%n";


		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
		System.out.format(
				"| Country's ID | Country's name | No. of armies | Continent's ID |   Adjacent countries' ID   | Player's name |%n");
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
			
		for(int i = 0; i < countries.size(); i++) 
			System.out.format(table, showCountryID(i), showCountryName(i), showArmy(i), showContinentID(i), 
					Arrays.toString(showAdjacentCountriesID(i)), showPlayerName(i));
		
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
	}

	public void AttackMap(Editor editor, Player player) {
		String table = "|%-14d|%-39s|%-24d|%-41s|%-23d|%n";

		System.out.println("This is player no. " + player.getPlayerID() + " - " + player.getPlayerName() + " turn to attack!");

		System.out.format(
				"+--------------+---------------------------------------+------------------------+-----------------------------------------+-----------------------+%n");
		System.out.format(
				"| Country's ID | No. of armies inside player's country | Opponent's country' ID | No. of armies inside opponent's country | Opponent's player' ID |%n");
		System.out.format(
				"+--------------+---------------------------------------+------------------------+-----------------------------------------+-----------------------+%n");

		for(int i = 0; i < player.getCountryID().; i++ ) 
			System.out.format(table, player.getCountryID().get(i), editor.getCountry().get(i).getArmy(),
					editor.getCountry().get(i).getArmy(), Arrays.toString(editor.getCountry().get(i).getAdjacentCountriesID()));
	}

	public void MoveMap(Editor editor, Player player) {

		String table = "|%-14d|%-39d|%-33d|%-42d|%n";

		System.out.println("This is player no. " + player.getPlayerID() + " - " + player.getPlayerName() + " turn to move!");

		System.out.format(
				"+--------------+---------------------------------------+---------------------------------+------------------------------------------+%n");
		System.out.format(
				"| Country's ID | No. of armies inside player's country | Player's adjacent countries' ID | No. of armies inside adjacence's country |%n");
		System.out.format(
				"+--------------+---------------------------------------+---------------------------------+------------------------------------------+%n");
	}
	
	public void setCountries() {
		Editor editor = new Editor();
		editor.setCountries(countries);
	}
	
	public int showCountryID(int counter) {
		return countries.get(counter).getCountryID();
	}
	
	public String showCountryName(int counter) {
		return countries.get(counter).getCountryName();
	}
	
	public int showArmy(int counter) {
		return countries.get(counter).getArmy();
	}
	
	public int showContinentID(int counter) {
		return countries.get(counter).getContinentID();
	}
	
	public int[] showAdjacentCountriesID(int counter) {
		return countries.get(counter).getAdjacentCountriesID();
	}
	
	public String showPlayerName(int counter) {
		return countries.get(counter).getPlayerName();
	}
	
	public void setPlayer(int countryID, int playerID, String playerName) {
		countries.get(countryID).setPlayer(playerID, playerName);
	}
	
	public void setArmy(int countryID, int army) {
		countries.get(countryID).setArmy(army);
	}
}