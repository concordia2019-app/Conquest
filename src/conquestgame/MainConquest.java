package conquestgame;

import Model.*;
import View.ConquestUI;

public class MainConquest {

	public static void main(String[] args) {
		Editor editor = new Editor();
		Map map = Map.getInstance();
		editor.setCountries(map.CountriesList);
		ConquestUI uiManager = ConquestUI.getInstance();
		uiManager.conquestUIShowStartMenu();
	}

}
