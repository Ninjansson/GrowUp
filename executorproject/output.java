package androids.growup.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class GrowUpTest extends ActivityInstrumentationTestCase2 {
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
    public GrowUpTest() throws ClassNotFoundException {
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
        //Wait for activity: 'androids.growup.MainActivity'
		solo.waitForActivity("MainActivity", 2000);
        //Set default small timeout to 10336 milliseconds
		Timeout.setSmallTimeout(10336);
        //Click on kryddor
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.CategoryActivity'
		assertTrue("CategoryActivity is not found!", solo.waitForActivity("CategoryActivity"));
        //Click on Oregano
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
        //Click on Lägg till i Mina sidor
		solo.clickOnView(solo.getView("button_open_popup"));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Empty Text View
		solo.clickOnView(solo.getView("sp_name"));
        //Enter the text: 'oregano'
		solo.clearEditText((android.widget.EditText) solo.getView("sp_name"));
		solo.enterText((android.widget.EditText) solo.getView("sp_name"), "oregano");
        //Click on SPARA
		solo.clickOnView(solo.getView(android.R.id.button1));
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
        //Click on Empty Text View
		solo.clickOnView(solo.getView("sp_name"));
        //Enter the text: 'basilika'
		solo.clearEditText((android.widget.EditText) solo.getView("sp_name"));
		solo.enterText((android.widget.EditText) solo.getView("sp_name"), "basilika");
        //Click on SPARA
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Press menu back key
		solo.goBack();
        //Press menu back key
		solo.goBack();
        //Click on frukt & bär
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.CategoryActivity'
		assertTrue("CategoryActivity is not found!", solo.waitForActivity("CategoryActivity"));
        //Click on Physalis (gyllenbär)
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
        //Press menu back key
		solo.goBack();
        //Click on Jordgubbar
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
        //Press menu back key
		solo.goBack();
        //Press menu back key
		solo.goBack();
        //Click on grönsaker
		solo.clickInList(3, 0);
        //Wait for activity: 'androids.growup.activities.CategoryActivity'
		assertTrue("CategoryActivity is not found!", solo.waitForActivity("CategoryActivity"));
        //Click on Morot
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
        //Press menu back key
		solo.goBack();
        //Click on Rädisa
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
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
        //Press menu back key
		solo.goBack();
        //Click on Ringblomma
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.PlantActivity'
		assertTrue("PlantActivity is not found!", solo.waitForActivity("PlantActivity"));
        //Press menu back key
		solo.goBack();
        //Click on Empty Text View
		solo.clickOnView(solo.getView("menu_home"));
        //Wait for activity: 'androids.growup.MainActivity'
		assertTrue("MainActivity is not found!", solo.waitForActivity("MainActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 5));
        //Click on INSTÄLLNINGAR
		solo.clickInList(4, 0);
        //Wait for activity: 'androids.growup.activities.SettingsActivity'
		assertTrue("SettingsActivity is not found!", solo.waitForActivity("SettingsActivity"));
        //Click on Av
		solo.clickOnView(solo.getView("settings_push_toggle"));
        //Click on Varje dag
		solo.clickOnView(solo.getView("settings_push_when"));
        //Click on Varje dag
		solo.clickOnView(solo.getView(android.R.id.text1, 1));
        //Click on Varje dag
		solo.clickOnView(solo.getView("settings_push_when"));
        //Click on Varannan dag
		solo.clickOnView(solo.getView(android.R.id.text1, 2));
        //Click on Varannan dag
		solo.clickOnView(solo.getView("settings_push_when"));
        //Click on Varje dag
		solo.clickOnView(solo.getView(android.R.id.text1, 1));
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
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 5));
        //Click on INSPIRATION
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.InspoActivity'
		assertTrue("InspoActivity is not found!", solo.waitForActivity("InspoActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 6));
        //Click on MIN SIDA
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.MyPageActivity'
		assertTrue("MyPageActivity is not found!", solo.waitForActivity("MyPageActivity"));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete", 1));
        //Click on Radera
		solo.clickOnView(solo.getView("button_delete"));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("menu_home"));
        //Wait for activity: 'androids.growup.MainActivity'
		assertTrue("MainActivity is not found!", solo.waitForActivity("MainActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 5));
        //Click on MIN SIDA
		solo.clickInList(1, 0);
        //Wait for activity: 'androids.growup.activities.MyPageActivity'
		assertTrue("MyPageActivity is not found!", solo.waitForActivity("MyPageActivity"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 0));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("menu_home"));
        //Wait for activity: 'androids.growup.MainActivity'
		assertTrue("MainActivity is not found!", solo.waitForActivity("MainActivity"));
        //Assert that: 'ImageView' is shown
		assertTrue("'ImageView' is not shown!", solo.waitForView(solo.getView("growup_logo")));
        //Click on Empty Text View
		solo.clickOnView(solo.getView("menu_home"));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageView.class, 5));
        //Click on INSPIRATION
		solo.clickInList(2, 0);
        //Wait for activity: 'androids.growup.activities.InspoActivity'
		assertTrue("InspoActivity is not found!", solo.waitForActivity("InspoActivity"));
        //Click on Plantera vertikalt med krukor
		solo.clickInList(2, 0);
        //Click on Plantera p  h jden med pall
		solo.clickInList(1, 0);
        //Click on Plantera i skofickor
		solo.clickInList(3, 0);
        //Click on Odla i zinkhinkar
		solo.clickInList(4, 0);
        //Click on Upp och ned tomater
		solo.clickInList(6, 0);
        //Click on F st l dor p  spalj
		solo.clickInList(5, 0);
	}
}
