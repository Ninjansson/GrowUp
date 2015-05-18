package androids.growup;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.JsonHttpResponseHandler;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.json.JSONTokener;

        import java.io.BufferedReader;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;

public class MyPageActivity extends ActionBarActivity {

    ListView my_plants_list;
    private JSONMyPlantsAdapter myPlantsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        setTitle("Min sida");
        my_plants_list = (ListView) findViewById(R.id.my_plants_list);
        myPlantsAdapter = new JSONMyPlantsAdapter(this, getLayoutInflater());
        my_plants_list.setAdapter(myPlantsAdapter);

        populateMyPlantsList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_page, menu);

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

    private void populateMyPlantsList() {
        JSONObject myPlants = getPlantsJSONArrayFromMyList();
        JSONArray plantArray = null;
        try {
            plantArray = myPlants.getJSONArray("myPlants");
            //myPlantsAdapter.updateData(plantArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        myPlantsAdapter.updateData(plantArray);
        try {
            for(int i = 0; i < plantArray.length(); i ++ ) {
                JSONObject x = plantArray.getJSONObject(i);
                Log.d("motherfucker", "Name => " + x.getString("my_name"));
            }
  /*
            JSONArray loopIt = (JSONArray) new JSONTokener(plantArray.toString()).nextValue();
            Log.d("motherfucker", "ARRAY => " + loopIt);
            String[] stringarray = new String[plantArray.length()];

            for(int i = 0; i < loopIt.length(); i++) {
                stringarray[i] = loopIt.getString(i);
                Log.d("motherfucker", "STRING => " + loopIt.getString(i));
            }
*/
            //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_my_plants, stringarray);

            my_plants_list.setAdapter(myPlantsAdapter);
        } catch (JSONException je) {
            Log.e("motherfucker", "ERROR NÄR VI SKULLE LATTJA MED ARRAYEN!");
        }

        /*
        ListView list = (ListView) findViewById(...);
        String json = "[\"Country1\",\"Country2\",\"Country3\"]";
        try {
            JSONArray array = (JSONArray) new JSONTokener(json).nextValue();

            String[] stringarray = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                stringarray[i] = array.getString(i);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringarray);
            list.setAdapter(adapter);
        } catch (JSONException e) {
            // handle JSON parsing exceptions...
        }
        */
    }

    private JSONObject getPlantsJSONArrayFromMyList() {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";
        StringBuilder finalString = new StringBuilder();
        JSONObject mainObject = null;

        try {
            FileInputStream inStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String oneLine;
            while((oneLine = bufferedReader.readLine()) != null) {
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
