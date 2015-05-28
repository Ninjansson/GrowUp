package androids.growup;

import android.content.Intent;
import android.widget.ListView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.ArrayList;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.fest.assertions.api.ANDROID.assertThat;
import static org.robolectric.Shadows.shadowOf;

<<<<<<< HEAD

=======
<<<<<<< HEAD
    public void testImageView(){
  //      assertEquals("icon_apple", R.id.plant_link, R.drawable.icon_apple);

    }
=======
>>>>>>> master

>>>>>>> Ampelie
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "../app/AndroidManifest.xml")
public class MainActivityTest {

        private MainActivity mainActivity;
        private ListView listView;

        @Before
        public void setUp() throws Exception {
            mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
            listView = (ListView) mainActivity.findViewById(R.id.categories);
        }

        @Test
        public void simpleTest() throws Exception {
            assertThat(mainActivity).isNotNull();
        }

        @Test
         public void testListView() {
            ListCategoriesAdapter adapter;
            ArrayList<Category> listCategories = new ArrayList<>();

            Category kryddor = new Category(1, R.drawable.icon_tomato, "Kryddor");
            Category fruktobar = new Category(2, R.drawable.icon_apple, "Frukter & bär");
            Category gronsaker = new Category(3, R.drawable.icon_carrot, "Grönsaker");
            Category atbarablommor = new Category(4, R.drawable.icon_apple, "Ätbara blommor");

            listCategories.add(kryddor);
            listCategories.add(fruktobar);
            listCategories.add(gronsaker);
            listCategories.add(atbarablommor);


            adapter = new ListCategoriesAdapter(mainActivity.getApplicationContext(), listCategories);
            listView.setAdapter(adapter);

            assertEquals("Kryddor", listView.getItemAtPosition(0), kryddor);
            assertEquals("Frukter & bär", listView.getItemAtPosition(1), fruktobar);
            assertEquals("Grönsaker", listView.getItemAtPosition(2), gronsaker);
            assertEquals("Ätbara blommor", listView.getItemAtPosition(3), atbarablommor);
        }
}