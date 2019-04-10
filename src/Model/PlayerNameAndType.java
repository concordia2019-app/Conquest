package Model;

/**
 * A model with two attributes, Player name and Player type
 * 
 * @author FarzadShamriz
 *
 */
public class PlayerNameAndType {

	private String PlayerName;
	private PlayerType playerType;

	public PlayerNameAndType(String playerName, PlayerType playerType) {
		this.PlayerName = playerName;
		this.playerType = playerType;
	}

	public String getPlayeName() {
		return this.PlayerName;
	}

	public PlayerType getPlayerType() {
		return this.playerType;
	}

}
