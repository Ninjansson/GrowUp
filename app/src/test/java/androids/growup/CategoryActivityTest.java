package androids.growup;

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

    }
}