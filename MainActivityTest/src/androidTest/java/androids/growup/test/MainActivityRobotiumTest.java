package androids.growup.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class MainActivityRobotiumTest extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "androids.growup.MainActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public MainActivityRobotiumTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Click on kryddor
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.CategoryActivity'
		assertTrue("CategoryActivity is not found!", solo.waitForActivity("CategoryActivity"));
        //Click on Oregano
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
        //Press menu back key
		solo.goBack();
        //Click on Basilika
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
        //Click on Lägg till i Mina sidor
		solo.clickOnView(solo.getView("button_open_popup"));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Enter the text: 'Ampelie'
		solo.clearEditText((android.widget.EditText) solo.getView("sp_name"));
		solo.enterText((android.widget.EditText) solo.getView("sp_name"), "Ampelie");
        //Click on SPARA
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 2));
        //Click on INSPIRATION
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.InspoActivity'
		assertTrue("InspoActivity is not found!", solo.waitForActivity("InspoActivity"));
        //Press menu back key
		solo.goBack();
        //Set default small timeout to 19233 milliseconds
		Timeout.setSmallTimeout(19233);
        //Click on Lägg till i Mina sidor
		solo.clickOnView(solo.getView("button_open_popup"));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Enter the text: 'Ampelie2'
		solo.clearEditText((android.widget.EditText) solo.getView("sp_name"));
		solo.enterText((android.widget.EditText) solo.getView("sp_name"), "Ampelie2");
        //Click on SPARA
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on Lägg till i Mina sidor
		solo.clickOnView(solo.getView("button_open_popup"));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Enter the text: 'Ampelie3'
		solo.clearEditText((android.widget.EditText) solo.getView("sp_name"));
		solo.enterText((android.widget.EditText) solo.getView("sp_name"), "Ampelie3");
        //Click on SPARA
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Press menu back key
		solo.goBack();
        //Press menu back key
		solo.goBack();
        //Click on ätbara blommor
		solo.clickInList(4, 0);
        //Wait for activity: 'androids.growup.activities.CategoryActivity'
		assertTrue("CategoryActivity is not found!", solo.waitForActivity("CategoryActivity"));
        //Click on Smörgåskrasse
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
        //Click on Lägg till i Mina sidor
		solo.clickOnView(solo.getView("button_open_popup"));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Enter the text: 'Ameplei'
		solo.clearEditText((android.widget.EditText) solo.getView("sp_name"));
		solo.enterText((android.widget.EditText) solo.getView("sp_name"), "Ameplei");
        //Click on SPARA
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 2));
        //Click on INSPIRATION
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.InspoActivity'
		assertTrue("InspoActivity is not found!", solo.waitForActivity("InspoActivity"));
        //Press menu back key
		solo.goBack();
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 2));
        //Click on INSTÄLLNINGAR
		solo.clickInList(4, 0);
        //Wait for activity: 'androids.growup.activities.SettingsActivity'
		assertTrue("SettingsActivity is not found!", solo.waitForActivity("SettingsActivity"));
        //Click on En gång i veckan
		solo.clickOnView(solo.getView("settings_push_when"));
        //Click on Varje dag
		solo.clickOnView(solo.getView(android.R.id.text1, 1));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 0));
        //Click on MIN SIDA
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.MyPageActivity'
		assertTrue("MyPageActivity is not found!", solo.waitForActivity("MyPageActivity"));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete", 3));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete", 2));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete", 4));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete", 4));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete", 5));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete", 3));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete", 3));
        //Press menu back key
		solo.goBack();
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 0));
        //Click on OM OSS
		solo.clickInList(3, 0);
        //Wait for activity: 'androids.growup.activities.AboutUsActivity'
		assertTrue("AboutUsActivity is not found!", solo.waitForActivity("AboutUsActivity"));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("menu_home"));
        //Wait for activity: 'androids.growup.MainActivity'
		assertTrue("MainActivity is not found!", solo.waitForActivity("MainActivity"));
	}
}
