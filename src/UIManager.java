import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is a class to show messages below
 * <ul>
 * <li>Showing the map with related adjacencies</li>
 * <li>showing the menu to select map, attach or move of player</li>
 * <li>showing the list of countries and adjacencies</li>
 * </ul>
 * 
 * @author F.S
 *
 */
public class UIManager {

	private String StartGameMenuMessage = "** Conquest Game **\r\n1.Start Game with Default Map. \r\n2.Start Game with Load Map \r\n3.Quit";
	private String AttackQuestion = "Do you want to Attack?(Y/N)";
	private String MoveQuestion = "Do you want to Move?(Y/N)";
	private String WrongInputString = "Your input is not acceptable.";
	private String AttackIsFinished = "The attack is finished.";
	private String SelectYourCountryID = "Please select your country ID.";
	private String InputNumberOfPlayers = "Enter bumber of players [2..5]:";
	private String ErrorEnteredValue = "Entered value is not acceptable.";
	private String ErrorInputNumberOfPlayers = "Your input number is not acceptable. Please enter a number between 2..5";
	private Scanner scanner;

	private Integer PlayerNumber;
	private ArrayList<String> PlayerNames = new ArrayList<String>();
	UiHelper uiHelper;
	private Map map = new Map();
	private Editor editor = new Editor();

	public ConquestUI() {
		scanner = new Scanner(System.in);
		uiHelper = new UiHelper();
	}

	/**
	 * <p>
	 * This method is printing the start menu of the game
	 * </p>
	 * <p>
	 * The content of the menu is in the private field
	 * </p>
	 * <p>
	 * Private field name is <strong>StartGameMenuMessage</strong>.
	 * </p>
	 */
	@Override
	public void conquestUIShowStartMenu() {
		String startMenuInput;
		while (true) {
			System.out.println(StartGameMenuMessage);
			startMenuInput = scanner.nextLine();
			if ((uiHelper.tryParseInt(startMenuInput)
					&& (Integer.parseInt(startMenuInput) >= 1 && Integer.parseInt(startMenuInput) < 4))) {
				Integer parsedInputValue = Integer.parseInt(startMenuInput);
				switch (parsedInputValue) {
				case 1:
					map.MainMap(editor);
					PlayerNumber = getNumberOfPlayer();
					ArrayList<String> playerNames = getPlayernames(PlayerNumber);
					editor.assigningPlayerCountries(playerNames, PlayerNumber);
					break;
				case 2:
					System.out.println("Loading new map.");
					System.out.println();
					conquestUIGetNodes();
					break;
				case 3:
					System.out.println("quit.");
					System.exit(0);
					break;

				default:
					System.out.println("Entered value is not acceptable.[0..2]");
					break;
				}

				break;
			} else
				System.out.println("Entered value is not acceptable.[0..2]");
		}

	}

	/**
	 * <p>
	 * This method give the path of note file to to load them in the map, then read
	 * the graphs and information about map and countries.
	 * </p>
	 * 
	 * @throws FileNotFoundException
	 */
	@Override
	public void conquestUIGetNodes() {
		System.out.println("Enter the path of your map: ");
		String nodePath = scanner.nextLine();
		String nodeFileContent;
		if (!nodePath.toLowerCase().contains(".txt")) {
			nodePath += ".txt";
		}
		try {
			FileReader fileReader = new FileReader(nodePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((nodeFileContent = bufferedReader.readLine()) != null) {
				System.out.println(nodeFileContent);
				System.out.println("I can detect that there is another line of text.");
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + nodePath + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + nodePath + "'");
		}
	}

	/**
	 * This method just ask from player if player want to attack Y => to agree that
	 * want to attack N => to skip the attack
	 */
	@Override
	public void conquestUIAttackQuestion() {
		while (true) {
			System.out.println(AttackQuestion);
			String attackDecision = scanner.nextLine();
			if (attackDecision.toLowerCase() == "n") {
				// TODO go to move question
				break;
			} else if (attackDecision.toLowerCase() == "y") {
				// TODO go to attack
				break;
			} else {
				System.out.println(WrongInputString);
			}
		}
	}

	/**
	 * This method just ask from player if player want to move Y => to agree that
	 * want to move N => to skip the move
	 */
	@Override
	public void conquestUIMove() {
		while (true) {
			System.out.println(MoveQuestion);
			String attackDecision = scanner.nextLine();
			if (attackDecision.toLowerCase() == "n") {
				// TODO turn player
				break;
			} else if (attackDecision.toLowerCase() == "y") {
				// TODO go to move
				break;
			} else {
				System.out.println(WrongInputString);
			}
		}
	}

	/**
	 * This method shows that the attack is finished and call the move method
	 */
	@Override
	public void conquestUIFinish() {
		System.out.println(AttackIsFinished);
		conquestUIMove();
	}

	/**
	 * This method give the country Id from player and check the validity of it.
	 * Then call the attack method.
	 * 
	 * @param playerCountries this is a list of countries
	 */
	@Override
	public void conquestUIGetCountryIdForAttack(List<Country> playerCountries) {
		for (int i = 1; i <= playerCountries.size(); i++) {
			System.out.println(i + playerCountries.get(i).getCountryName());
		}
		System.out.println(SelectYourCountryID);
		String countryId = scanner.nextLine();
		// TODO Implement Attack and call it
	}

	/**
	 * Show the whole map in console. Content map converted to a table which can be
	 * shown in the console
	 */
	@Override
	public void showMap() {
		// TODO Auto-generated method stub

	}

	/**
	 * numberOfPlayer method will ask the player to enter an integer for number of
	 * players and save the number for the next part to ask the player for their
	 * names. if the player enter a number more than 5 or less than 2 he will get an
	 * error for entering a wrong number.
	 * 
	 * @return num is number of players that player entered.
	 */
	@Override
	public int getNumberOfPlayer() {
		while (true) {
			System.out.print(InputNumberOfPlayers);
			int num = scanner.nextInt();
			if (num < 1 || num >= 5) {
				System.out.println(ErrorInputNumberOfPlayers);
			} else
				return num;
		}
	}

	/**
	 * This method check the count of armies which player want to leave in moving
	 * 
	 * @return an object which contains CountryId and Number of left armies in that
	 *         country
	 */
	@Override
	public LeftArmiesResponse numberOfArmiesToleave(Country country) {
		while (true) {
			System.out.println("Enter number of armies which you want to leave: 0");
			String leftArmies = scanner.nextLine();
			Integer currentArmiyNumbers = country.getArmy();
			if (uiHelper.tryParseInt(leftArmies)) {
				Integer inputArmyNumbers = Integer.parseInt(leftArmies);
				if (inputArmyNumbers < currentArmiyNumbers) {
					return new LeftArmiesResponse(country.getCountryID(), inputArmyNumbers);
				}
			}
			System.out.println(ErrorEnteredValue);
		}
	}

	/**
	 * nameOfPlayer method will get the players name and show the in console.
	 * 
	 * @param n is a number that player must enter the names of the players.
	 * @return Names that player enter names for each player.
	 */
	public ArrayList<String> getPlayernames(int n) {
		System.out.format("Enter %d names of your players: \n", n);
		for (int i = 0; i < n; i++) {
			System.out.format("Enter name of player %d: ", i + 1);
			PlayerNames.add(scanner.next());
		}
		return PlayerNames;
	}
}
