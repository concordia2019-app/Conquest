package Tests.SuiteTestClass;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Controller.ConquestController;
import Tests.Controller.ConquestControllerTest;
import Tests.Helper.CountryHelperTest;
import Tests.Helper.PlayerHelperTest;
import Tests.Helper.UIHelperTest;
import Tests.Model.CardTest;
import Tests.Model.FinishGameTest;
import Tests.Model.MapGeneratorTest;
import Tests.Model.MapTest;

@RunWith(Suite.class)
@SuiteClasses({ ConquestControllerTest.class, MapTest.class, CountryHelperTest.class, PlayerHelperTest.class,
		UIHelperTest.class, CardTest.class, FinishGameTest.class, ConquestControllerTest.class,
		MapGeneratorTest.class })
public class SuiteClass {

}
