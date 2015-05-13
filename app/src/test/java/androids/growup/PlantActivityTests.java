package androids.growup;

import android.os.Bundle;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;
import android.view.Menu;
import android.view.MenuItem;

public class PlantActivityTests extends TestCase {

    PlantActivity plantActivity = new PlantActivity();
    Menu menu;
    Bundle saveInstanceState;

    public void testPlantActivity() throws Exception {
        assertNotNull(plantActivity);
    }

    public void testonCreateOptionsMenu() throws Exception {
        assertTrue(plantActivity.onCreateOptionsMenu(menu));
    }

    public void testCheckHabitat() throws Exception {
        assertEquals("Ute", plantActivity.checkHabitat(0));
        assertEquals("Inne", plantActivity.checkHabitat(1));
        assertEquals("Ute och Inne", plantActivity.checkHabitat(2));
    }

    public void testCheckDifficulty() throws Exception {
        assertEquals("#00FF00", plantActivity.checkDifficulty(1));
        assertEquals("#FFCC00", plantActivity.checkDifficulty(2));
        assertEquals("#FF0000", plantActivity.checkDifficulty(3));
    }
}
