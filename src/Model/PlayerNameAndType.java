package Model;

public class PlayerNameAndType {

	private String PlayerName;
	private PlayerType playerType;

	public PlayerNameAndType(String playerName, PlayerType playerType) {
		this.PlayerName = playerName;
		this.playerType = playerType;
	}

	public String PlayerNameAndType() {
		return this.PlayerName;
	}

	public PlayerType getPlayerType() {
		return this.playerType;
	}

}
