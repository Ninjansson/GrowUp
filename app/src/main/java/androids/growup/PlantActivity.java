package androids.growup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class PlantActivity extends ActionBarActivity {

    private static final String PREFS_NAME = "SETTINGS";
    private TextView plant_name, latin_name, plant_info, plant_habitat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        final String plantName = this.getIntent().getExtras().getString("name");

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean thisPlant = settings.getBoolean(plantName, false);
        final Switch pushNotices = (Switch) findViewById(R.id.toggle_push);
        pushNotices.setChecked(thisPlant);

        pushNotices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean x = pushNotices.isChecked();
                editorCommitChanges();
                //Log.i("TEST MOTHERFUCKER", "SWITCH for plant " + plantName + " set to " + x);
            }
        });

        setTitle(this.getIntent().getExtras().getString("name").toUpperCase());

        plant_name = (TextView) findViewById(R.id.plant_name);
        latin_name = (TextView) findViewById(R.id.latin_name);
        plant_info = (TextView) findViewById(R.id.plant_info);
        plant_habitat = (TextView) findViewById(R.id.plant_habitat);

        plant_name.setText(this.getIntent().getExtras().getString("name").toUpperCase());
        latin_name.setText(this.getIntent().getExtras().getString("latin_name"));
        plant_info.setText(this.getIntent().getExtras().getString("info"));
        plant_habitat.setText(checkHabitat(this.getIntent().getExtras().getInt("habitat")));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    private void editorCommitChanges() {
        //Log.i("TEST MOTHERFUCKER", "In PlantActivity.editorCommitChanges() - Probably fucking things up");

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        Switch pushNotices = (Switch) findViewById(R.id.toggle_push);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        String plantName = this.getIntent().getExtras().getString("name");
        editor.putBoolean(plantName, pushNotices.isChecked());

        // Commit the edits!
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.i("TEST MOTHERFUCKER", "In PlantActivity.onStop - Fucking things up");
        editorCommitChanges();
    }

    public String checkHabitat(int habitat) {
        return habitat == 0 ? "Ute" : habitat == 1 ? "Inne" : "Ute och Inne";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_aboutus:
                startActivity(new Intent(this, AboutUsActivity.class));
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.menu_my_page:
                startActivity(new Intent(this, MyPageActivity.class));
            default:
                Intent catIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                catIntent.putExtra("cat_id", this.getIntent().getExtras().getInt("cat_id"));
                catIntent.putExtra("cat_name", this.getIntent().getExtras().getString("cat_name"));
                startActivityForResult(catIntent, 0);
                return true;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_plant, container, false);
            return rootView;
        }
    }
}
