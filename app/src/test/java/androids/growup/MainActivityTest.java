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
        public void setMainActivityTest() throws Exception {
            assertThat(mainActivity).isNotNull();
        }
}