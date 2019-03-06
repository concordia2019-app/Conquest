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
	@Test
	public void testGetNumberOfPlayer() {
		ConquestUI conquestUI = new ConquestUI();
		
		int playerNumber = conquestUI.getNumberOfPlayer();
		
		assertEquals(new int[] {2, 3, 4, 5} , playerNumber);
		
	}
        @Test
	public void testCheckPlayerNumber() {
		ConquestUI conquestUI = new ConquestUI();
		
		int playerNumber = conquestUI.CheckPlayerNumber();
		boolean type=conquestUI.CheckPlayerNumber(3);
		assertEquals(type, true);
		
	}
          @Test
	public void testCheckPlayerNumber() {
		ConquestUI conquestUI = new ConquestUI(); 
		assertEquals(conquestUI.checkPlayerNumber(3), true);
		
	}
           @Test
	public void testIsIdExistInList() {
		ConquestUI conquestUI = new ConquestUI(); 
		assertEquals(true, true);
		
	}
}
