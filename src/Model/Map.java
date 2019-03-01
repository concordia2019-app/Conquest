package Model;

import java.util.Arrays;

public class Map {
	public void MainMap(Editor editor) {

		String table = "|%-14d|%-16s|%-15d|%-16d|%-28s|%n";

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+%n");
		System.out.format(
				"| Country's ID | Country's name | No. of armies | Continent's ID |   Adjacent countries' ID   |%n");
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+%n");

		for (int i = 0; i < editor.getCountry().size(); i++)
			System.out.format(table, editor.getCountry().get(i).getCountryID(),
					editor.getCountry().get(i).getCountryName(), editor.getCountry().get(i).getArmy(),
					editor.getCountry().get(i).getContinentID(),
					Arrays.toString(editor.getCountry().get(i).getAdjacentCountriesID()));

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+%n");
	}

	public String generateTheMap() {
		return null;
	}

//	public void AttackMap(Editor editor, Player player) {
//		String table = "|%-14d|%-39s|%-24d|%-41s|%-23d|%n";
//
//		System.out.println("This is player no. " + player.getPlayerID() + " - " + player.getPlayerName() + " turn to attack!");
//
//		System.out.format(
//				"+--------------+---------------------------------------+------------------------+-----------------------------------------+-----------------------+%n");
//		System.out.format(
//				"| Country's ID | No. of armies inside player's country | Opponent's country' ID | No. of armies inside opponent's country | Opponent's player' ID |%n");
//		System.out.format(
//				"+--------------+---------------------------------------+------------------------+-----------------------------------------+-----------------------+%n");
//
//		for(int i = 0; i < player.getCountryID().length; i++ ) 
//			System.out.format(table, player.getCountryID().get(i), editor.getCountry().get(i).getArmy(),
//					editor.getCountry().get(i).getArmy(), Arrays.toString(editor.getCountry().get(i).getAdjacentCountriesID()));
//	}

	public void MoveMap(Editor editor, Player player) {

		String table = "|%-14d|%-39d|%-33d|%-42d|%n";

		System.out.println(
				"This is player no. " + player.getPlayerID() + " - " + player.getPlayerName() + " turn to move!");

		System.out.format(
				"+--------------+---------------------------------------+---------------------------------+------------------------------------------+%n");
		System.out.format(
				"| Country's ID | No. of armies inside player's country | Player's adjacent countries' ID | No. of armies inside adjacence's country |%n");
		System.out.format(
				"+--------------+---------------------------------------+---------------------------------+------------------------------------------+%n");

	}
}