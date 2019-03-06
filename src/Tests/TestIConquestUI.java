package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Manager.ConquestUI;

public class TestIConquestUI {

	@Test
	public void testGetNumberOfPlayer() {
		ConquestUI conquestUI = new ConquestUI();
		
		int playerNumber = conquestUI.getNumberOfPlayer();
		
		assertEquals(new int[] {2, 3, 4, 5} , playerNumber);
		
	}

}
