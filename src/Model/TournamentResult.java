package Model;

public class TournamentResult {

	private int GameIndex;
	private int TurnIndex;
	private int MapIndex;
	private String WinnerName;

	public String getWinnerName() {
		return this.WinnerName;
	}

	public int getGameIndex() {
		return this.GameIndex;
	}

	public int getMapIndex() {
		return this.MapIndex;
	}
	
	public int getTurnIndex() {
		return this.TurnIndex;
	}

	public void setWinnerName(String winnerName) {
		this.WinnerName = winnerName;
	}

	public void setGameIndex(int gameIndex) {
		this.GameIndex = gameIndex;
	}

	public void setMapIndex(int mapIndex) {
		this.MapIndex = mapIndex;
	}
	
	public void setTurnIndex(int turnIndex) {
		this.TurnIndex = turnIndex;
	}
}
