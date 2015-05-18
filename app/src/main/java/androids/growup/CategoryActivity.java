package androids.growup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;


public class CategoryActivity extends ActionBarActivity {

    private static final String TAG = "GrowUpMotherFucker";
    private static final String QUERY_URL = "http://kimjansson.se/GrowUp/plants/cat/";
    private ListView catPlantsList;
    private JSONCategoryPlantsAdapter catPlantsAdapter;
    private int catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setTitle(this.getIntent().getExtras().getString("cat_name").toUpperCase());
        catId = this.getIntent().getExtras().getInt("cat_id");

        //Log.d(TAG, QUERY_URL + catId);

        catPlantsList = (ListView) findViewById(R.id.category_plants);
        catPlantsAdapter = new JSONCategoryPlantsAdapter(this, getLayoutInflater());
        catPlantsList.setAdapter(catPlantsAdapter);

        populateCategoryPlantsList();

        catPlantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject object = (JSONObject) catPlantsAdapter.getItem(position);
                //showToast("ID: " + object.optInt("id"));
                Intent plantIntent = new Intent(CategoryActivity.this, PlantActivity.class);
                plantIntent.putExtra("cat_id", catId);
                plantIntent.putExtra("cat_name", object.optString("cat_name"));

                plantIntent.putExtra("id", object.optInt("id"));
                plantIntent.putExtra("cat_name", object.optString("cat_name"));
                plantIntent.putExtra("name", object.optString("name"));
                plantIntent.putExtra("latin_name", object.optString("latin_name"));
                plantIntent.putExtra("info", object.optString("info"));
                plantIntent.putExtra("how_to", object.optString("how_to"));
                plantIntent.putExtra("habitat", object.optInt("habitat"));
                plantIntent.putExtra("plant_usage", object.optString("plant_usage"));
                plantIntent.putExtra("difficulty", object.optInt("difficulty"));
                plantIntent.putExtra("plant_link", object.optString("link"));
                plantIntent.putExtra("harvest", object.optString("harvest"));
                plantIntent.putExtra("good_to_know", object.optString("good_to_know"));

                Log.d(TAG, "Link => " + object.optString("link"));

                startActivity(plantIntent);

                overridePendingTransition(R.animator.animation_1, R.animator.animation_2);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    private void populateCategoryPlantsList() {
        AsyncHttpClient cat_client = new AsyncHttpClient();

        cat_client.get(QUERY_URL + catId,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject catPlantsObject) {
                        catPlantsAdapter.updateData(catPlantsObject.optJSONArray("plants"));
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        Log.d(TAG, "Failure connecting to whatever " + throwable + " " + error);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
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

    private void showToast(String output) {
        Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_category, container, false);
            return rootView;
        }
    }
}
