package Controller;

import java.util.ArrayList;
import java.util.Random;

import Model.Card;
import Model.CardType;
import Model.CardsCounter;
import Model.Country;
import Model.Map;

/**
 * 
 * @author AHasheminezhad
 */
public class CardController {

	/**
	 * Randomly return a new card and assign it
	 */
	public Card cardAssigner() {
		Random random = new Random();
		int number = random.nextInt(3);
		switch (number) {
		case 0:
			return new Card(CardType.INFANTRY);
		case 1:
			return new Card(CardType.CAVALRY);
		case 2:
			return new Card(CardType.ARTILLERY);
		}
		return new Card(CardType.ARTILLERY);
	}

	/**
	 * give the types of cards and counts them the
	 * 
	 * @param cards player cards which this function calculating
	 */
	public CardsCounter defineCardsType(ArrayList<Card> cards) {

		CardsCounter cardCounter = new CardsCounter();

		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).checkCardType(CardType.INFANTRY))
				cardCounter.increaseInfantrycounter();
			else if (cards.get(i).checkCardType(CardType.CAVALRY))
				cardCounter.increaseCavalrycounter();
			else if (cards.get(i).checkCardType(CardType.ARTILLERY))
				cardCounter.increaseArtillerycounter();
		}
		return cardCounter;
	}

	/**
	 * /** give the countries of player by playerId
	 * 
	 * @param playerId playerId is the id for finding player countries
	 */
	public ArrayList<Country> getPlayerCountries(int playerId) {
		ArrayList<Country> allCountries = Map.getInstance().getCountries();
		ArrayList<Country> countries = new ArrayList<Country>();
		for (int i = 0; i < allCountries.size(); i++) {
			if (allCountries.get(i).getPlayerID() == playerId) {
				countries.add(allCountries.get(i));
			}
		}
		return countries;
	}

	/**
	 * /** calculate armies count from the types of cards base on 3 matching
	 * 
	 * @param cards player cards which this function calculating
	 */
	public int calculateArmiesCount(CardsCounter cardsCounter, ArrayList<Card> cards) {

		int pairInfantry = (int) cardsCounter.getInfantrycounter() / 3;
		int pairCavalry = (int) cardsCounter.getCavalrycounter() / 3;
		int pairArtillery = (int) cardsCounter.getArtillerycounter() / 3;
		int armiesCount = (pairInfantry + pairCavalry + pairArtillery) * 5;

		return armiesCount;
	}

	public int calculateArmiesCount(ArrayList<Card> cards) {

		int pairInfantry = 0;
		int pairCavalry = 0;
		int pairArtillery = 0;
		int armiesCount = 0;
		for (Card cardItem : cards) {
			switch (cardItem.getCardType()) {
			case INFANTRY:
				pairInfantry++;
				break;
			case CAVALRY:
				pairCavalry++;
				break;
			case ARTILLERY:
				pairArtillery++;
				break;
			}
		}
		armiesCount = ((pairArtillery / 3) + (pairCavalry / 3) + (pairInfantry / 3) * 5);

		return armiesCount;
	}

	/**
	 * /** update cards count from the types of cards base on 3 matching
	 * 
	 * @param cards        player cards which this function calculating
	 * @param cardsCounter cards counter
	 */
	public ArrayList<Card> updateListCards(CardsCounter cardsCounter, ArrayList<Card> cards) {

		int pairInfantry = (int) cardsCounter.getInfantrycounter() / 3;
		int pairCavalry = (int) cardsCounter.getCavalrycounter() / 3;
		int pairArtillery = (int) cardsCounter.getArtillerycounter() / 3;
		int tempInfantry = pairInfantry * 3;
		int tempCavalry = pairCavalry * 3;
		int tempArtillery = pairArtillery * 3;

		for (int i = 0; i < cards.size(); i++)
			if (cards.get(i).checkCardType(CardType.INFANTRY) && tempInfantry >= 0) {
				tempInfantry--;
				cards.remove(i);
			}

		if (tempCavalry > 0)
			for (int i = 0; i < cards.size(); i++)
				if (cards.get(i).checkCardType(CardType.CAVALRY) && tempCavalry >= 0) {
					tempCavalry--;
					cards.remove(i);
				}
		if (tempArtillery > 0)
			for (int i = 0; i < cards.size(); i++)
				if (cards.get(i).checkCardType(CardType.ARTILLERY) && tempArtillery >= 0) {
					tempArtillery--;
					cards.remove(i);
				}
		return cards;
	}

}
