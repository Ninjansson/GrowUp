package androids.growup;

import junit.framework.TestCase;

public class PlantActivityTest extends TestCase {

    PlantActivity plantActivity = new PlantActivity();

    public void testCheckHabitat() throws Exception {
        assertEquals("Ute", plantActivity.checkHabitat(0));
        assertEquals("Inne", plantActivity.checkHabitat(1));
        assertEquals("Ute och Inne", plantActivity.checkHabitat(2));
    }
}