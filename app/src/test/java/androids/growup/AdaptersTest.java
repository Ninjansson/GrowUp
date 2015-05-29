package androids.growup;

import android.content.Context;
import android.content.Intent;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.LayoutInflater;
import android.widget.ListView;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import androids.growup.activities.MyPageActivity;
import androids.growup.gson.Category;
import androids.growup.gson.Plant;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
public class AdaptersTest extends TestCase {

    @Test
    public void clickingLogin_shouldStartLoginActivity() {
        
    }
}