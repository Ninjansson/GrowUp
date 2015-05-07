package androids.growup;

<<<<<<< HEAD
import junit.framework.TestCase;

public class CategoryActivityTest extends TestCase {

    public void testOnCreate() throws Exception {

    }

    public void testOnCreateOptionsMenu() throws Exception {

    }

    public void testOnOptionsItemSelected() throws Exception {

=======
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;


public class CategoryActivityTest extends ActivityInstrumentationTestCase2<CategoryActivity> {

    CategoryActivity cat;

    public CategoryActivityTest() {
        super(CategoryActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cat = getActivity();
    }

    @SmallTest
    public void testListView() {
        ListView list = (ListView) cat.findViewById(R.id.category_plants);
        assertNotNull("Testing ListView", list);
>>>>>>> origin/master
    }
}