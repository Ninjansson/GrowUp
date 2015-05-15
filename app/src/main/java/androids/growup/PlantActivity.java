package androids.growup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class PlantActivity extends ActionBarActivity {

    private static final String PREFS_NAME = "SETTINGS";
    private TextView plant_name, latin_name, plant_info, plant_how_to, plant_usage, plant_habitat, plant_difficulty;
    public final static String SAVE_PLANT = "androids.growup.save_plant";

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
        plant_how_to = (TextView) findViewById(R.id.plant_how_to);
        plant_usage = (TextView) findViewById(R.id.plant_usage);
        plant_habitat = (TextView) findViewById(R.id.plant_habitat);
        plant_difficulty = (TextView) findViewById(R.id.plant_difficulty);

        plant_name.setText(this.getIntent().getExtras().getString("name").toUpperCase());
        latin_name.setText(this.getIntent().getExtras().getString("latin_name"));
        plant_info.setText(this.getIntent().getExtras().getString("info"));
        plant_how_to.setText(this.getIntent().getExtras().getString("how_to"));
        plant_usage.setText(this.getIntent().getExtras().getString("usage"));
        plant_habitat.setText(checkHabitat(this.getIntent().getExtras().getInt("habitat")));
        //plant_difficulty.setBackgroundColor(checkDifficulty(this.getIntent().getExtras().getInt("difficulty")));
        plant_difficulty.setBackgroundColor(Color.parseColor(checkDifficulty(this.getIntent().getExtras().getInt("difficulty"))));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        final TextView plant = (TextView) findViewById(R.id.plant_name);
        Button save = (Button) findViewById(R.id.button_savePlant);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plantName = plant.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra(SAVE_PLANT, plantName);
                //startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Plantan är tillagd", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public String checkDifficulty(int difficulty) {
        String output = "";
        switch(difficulty) {
            case 1:
                output = "#00FF00";
                break;
            case 2:
                output = "#FFCC00";
                break;
            case 3:
                output = "#FF0000";
                break;
            default:
                break;
        }
        return output;
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
            case R.id.menu_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
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
