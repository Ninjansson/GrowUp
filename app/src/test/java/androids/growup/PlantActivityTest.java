package androids.growup;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;
import android.view.Menu;
import android.view.MenuItem;

public class PlantActivityTest extends TestCase {

    PlantActivity plantActivity = new PlantActivity();
    Menu menu;

    @SmallTest
    public void testPlantActivity() {
        assertNotNull(plantActivity);
    }

    @SmallTest
    public void testonCreateOptionsMenu() {
        assertTrue(plantActivity.onCreateOptionsMenu(menu));
    }
    @SmallTest
    public void testCheckHabitat() {
        assertEquals("Ute", plantActivity.checkHabitat(0));
        assertEquals("Inne", plantActivity.checkHabitat(1));
        assertEquals("Ute och Inne", plantActivity.checkHabitat(2));
    }

    @SmallTest
    public void testCheckDifficulty() {
        assertEquals("#00FF00", plantActivity.checkDifficulty(1));
        assertEquals("#FFCC00", plantActivity.checkDifficulty(2));
        assertEquals("#FF0000", plantActivity.checkDifficulty(3));
    }
}
