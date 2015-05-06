package androids.growup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PlantActivity extends ActionBarActivity {

    private TextView plant_name, latin_name, plant_info, plant_habitat;
    private int habitat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        plant_name = (TextView) findViewById(R.id.plant_name);
        latin_name = (TextView) findViewById(R.id.latin_name);
        plant_info = (TextView) findViewById(R.id.plant_info);
        plant_habitat = (TextView) findViewById(R.id.plant_habitat);

        plant_name.setText(this.getIntent().getExtras().getString("name"));
        latin_name.setText(this.getIntent().getExtras().getString("latin_name"));
        plant_info.setText(this.getIntent().getExtras().getString("info"));
        plant_habitat.setText(checkHabitat(this.getIntent().getExtras().getInt("habitat")));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    private String checkHabitat(int habitat) {
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
            default:
                return super.onOptionsItemSelected(item);
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
