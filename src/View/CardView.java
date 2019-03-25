/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Card;
import Model.Country;
import Model.Player;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Amirhossein
 */
public class CardView {

	public void printCards(ArrayList<Player> players) {

		String table = "|%-14d|%-16s|%-15d|%-16d|%-28s|%-15s|%n";

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
		System.out.format("| Player Name | INFANTRY Cards |  CAVALRY Cards | ARTILLERY Cards |%n");
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");

// 		ArrayList<Country> playerCountries = getPlayerCorrespondingCountries(player.getCountryID(), countriesList);

//		for (int i = 0; i < players.size(); i++)
//			System.out.format(table, players.get(i).getPlayerName(), players.get(i).getCards()., i),
//					showArmy(playerCountries, i), showContinentID(playerCountries, i),
//					Arrays.toString(showAdjacentCountriesID(playerCountries, i)), showPlayerName(playerCountries, i));

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
	}

	public void printCardsPlayer(Player player) {

		ArrayList<Card> playercards = player.getCards();
		String table = "|%-14s|%-16s|%n";

		System.out.format("+--------------+----------------+|%n");
		System.out.format("| Player Name | Card type |%n");
		System.out.format("+--------------+----------------+|%n");
		for (Card cardItem : playercards) {
			System.out.format(table, player.getPlayerName(), cardItem.getCardType().name());
		}
		System.out.format("+--------------+----------------+|%n");
	}

}
