package conquestgame;
import Model.*;
import View.ConquestUI;

public class MainConquest {

	public static void main(String[] args) 
	{
		Editor editor = new Editor();
		Map map = new Map();
		editor.setCountries(map.CountriesList);
		ConquestUI uiManager = new ConquestUI();
		uiManager.conquestUIShowStartMenu();
	}

}
