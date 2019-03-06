package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Manager.ConquestUI;

public class TestIConquestUI {

    @Test
	public void testCheckPlayerNumber() {
		ConquestUI conquestUI = new ConquestUI(); 
		assertEquals(conquestUI.checkPlayerNumber(3), false);
		
	}
}
