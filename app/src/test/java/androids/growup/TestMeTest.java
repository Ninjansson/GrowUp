package androids.growup;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

public class TestMeTest extends TestCase {

    TestMe testMe = new TestMe();
    PlantActivity plantActivity = new PlantActivity();

    public void testByThree() throws Exception {
        assertEquals(true, testMe.byThree(30));
    }

    public void testByFive() throws Exception {
        assertEquals(true, testMe.byThree(30));
        assertEquals(false, testMe.byThree(40));
    }

    public void testbyThreeAndFive() throws Exception {
        assertEquals(true, testMe.byThree(30));
    }

    public void testCheckHabitat() throws Exception {
        assertEquals("Ute", plantActivity.checkHabitat(0));
        assertEquals("Inne", plantActivity.checkHabitat(1));
        assertEquals("Ute och Inne", plantActivity.checkHabitat(2));
    }
}