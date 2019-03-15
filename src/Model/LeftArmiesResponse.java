package Model;

/**
 * This class is for return number of armies that player want to left in country
 * @author FarzadShamriz
 *
 */
public class LeftArmiesResponse {
	private Integer CountryId;
	private Integer ArmyNum;
	
	public LeftArmiesResponse(Integer countryId , Integer armyNum ) {
		this.ArmyNum = armyNum;
		this.CountryId = countryId;
	}
	
	public int getCountryId() {
		return this.CountryId;
	}
	
	public int getArmyNum() {
		return this.ArmyNum;
	}
}
