package androids.growup.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import androids.growup.notifications.AlarmReceiver;
import androids.growup.MainActivity;
import androids.growup.R;


public class SettingsActivity extends ActionBarActivity {
    private static final String PREFS_NAME = "SETTINGS";
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        // DROPDOWN
        // Populate dropdownlist with options for when we want pushnotices
        Spinner dropdown = (Spinner) findViewById(R.id.settings_push_when);
        if (getPlantsJSONArrayFromMyList() != null) {
            dropdown.setEnabled(true);

            String[] items = new String[]
                    {
                            "Aldrig",
                            "Varje dag",
                            "Varannan dag",
                            "En gång i veckan"
                    };

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            dropdown.setAdapter(adapter);

            int spinnerPos = settings.getInt("settings_push_when", 1);
            dropdown.setSelection(spinnerPos);

            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();

                    editor.putInt("settings_push_when", position);
                    editor.commit();
                     /* START TIMER*/
                    Intent alarmIntent = new Intent(SettingsActivity.this, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 0, alarmIntent, 0);
                    toggleAlarm();
                    /**END TIMER */

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else {
            dropdown.setEnabled(false);
        }


         // TOGGLE BUTTON
        final ToggleButton togglePushNotices = (ToggleButton) findViewById(R.id.settings_push_toggle);

        // If there are plants in our list...
        if (getPlantsJSONArrayFromMyList() != null) {
            togglePushNotices.setEnabled(true);

            boolean settingsPush = settings.getBoolean("settings_toggle_push_notices", false);
            togglePushNotices.setChecked(settingsPush);

            togglePushNotices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ToggleButton togglePushNotices = (ToggleButton) findViewById(R.id.settings_push_toggle);
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("settings_toggle_push_notices", togglePushNotices.isChecked());
                    // Commit the edits!
                    editor.commit();

                    /* START TIMER*/
                    Intent alarmIntent = new Intent(SettingsActivity.this, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 0, alarmIntent, 0);
                    toggleAlarm();
                    /**END TIMER */
                }
            });
        } else {
            togglePushNotices.setEnabled(false);
            togglePushNotices.setChecked(false);
        }
    }

    private int checkMillis(int alarmWhen) {
        int millis = 0;
        int everyDay = 86400000;
        switch (alarmWhen) {
            case 1:
                millis = everyDay;
                break;
            case 2:
                millis = everyDay * 2;
                break;
            case 3:
                millis = everyDay * 7;
                break;
            default:
                break;
        }

        return millis;
    }

    private void toggleAlarm() {
        SharedPreferences settings = getSharedPreferences("SETTINGS", 0);
        boolean isItOn = settings.getBoolean("settings_toggle_push_notices", false);
        int alarmWhen = settings.getInt("settings_push_when", 1);

        if ((isItOn) && (alarmWhen != 0)) {
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int interval = checkMillis(alarmWhen);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 00);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        } else {
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
        }
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
            case R.id.menu_my_page:
                startActivity(new Intent(this, MyPageActivity.class));
                return true;
            case R.id.menu_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.menu_inspo:
                startActivity(new Intent(this, InspoActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public JSONObject getPlantsJSONArrayFromMyList() {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";
        StringBuilder finalString = new StringBuilder();
        JSONObject mainObject = null;

        try {
            FileInputStream inStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String oneLine;
            while ((oneLine = bufferedReader.readLine()) != null) {
                finalString.append(oneLine);
            }

            mainObject = new JSONObject(finalString.toString());
            bufferedReader.close();
            inStream.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainObject;
    }
}