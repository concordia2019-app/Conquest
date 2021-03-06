package Model;

/**
 * This class is using for returning attack status
 * 
 * @author FarzadShamriz
 *
 */
public class AttackResponse {

	// attackStatus is a boolean property that return true (when attacker is wining)
	// and false (when attacker is loosing)
	private boolean attackStatus;

	// restOfArmies is an integer property to return the rest of armies of attack
	private int restOfArmies;

	public AttackResponse() {
	}

	public void setRestOfArmies(int restOfArmies) {
		this.restOfArmies = restOfArmies;
	}

	public void setAttackStatus(Boolean attackStatus) {
		this.attackStatus = attackStatus;
	}

	public int getRestOfArmies() {
		return this.restOfArmies = restOfArmies;
	}

	public boolean getAttackStatus() {
		return this.attackStatus = attackStatus;
	}
}
