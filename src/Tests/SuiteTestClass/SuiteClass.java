package Tests.SuiteTestClass;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Controller.ConquestController;
import Tests.Controller.CardControllerTest;
import Tests.Controller.ConquestControllerTest;
import Tests.Controller.TournamentControllerTest;
import Tests.Helper.CountryHelperTest;
import Tests.Helper.PlayerHelperTest;
import Tests.Helper.UIHelperTest;
import Tests.Model.CardTest;
import Tests.Model.FinishGameTest;
import Tests.Model.MapGeneratorTest;
import Tests.Model.MapTest;
import Tests.View.TournamentViewTest;

@RunWith(Suite.class)
@SuiteClasses({ ConquestControllerTest.class, MapTest.class, CountryHelperTest.class, PlayerHelperTest.class,
		UIHelperTest.class, CardTest.class, FinishGameTest.class, ConquestControllerTest.class,
		TournamentControllerTest.class, MapGeneratorTest.class, TournamentViewTest.class, CardControllerTest.class,
		ConquestControllerTest.class })
public class SuiteClass {

}
