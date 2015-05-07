package androids.growup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;


public class SettingsActivity extends ActionBarActivity {
    private static final String PREFS_NAME = "SETTINGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final ToggleButton togglePushNotices = (ToggleButton) findViewById(R.id.settings_push_toggle);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean settingsPush = settings.getBoolean("settings_toggle_push_notices", false);
        togglePushNotices.setChecked(settingsPush);

        togglePushNotices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //boolean x = pushNotices.isChecked();
                //editorCommitChanges();
                ToggleButton togglePushNotices = (ToggleButton) findViewById(R.id.settings_push_toggle);
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("settings_toggle_push_notices", togglePushNotices.isChecked());
                // Commit the edits!
                editor.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_aboutus:
                startActivity(new Intent(this, AboutUsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}