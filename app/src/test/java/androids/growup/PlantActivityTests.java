package androids.growup;

import android.os.Bundle;
import android.view.Menu;

import junit.framework.TestCase;

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

}
