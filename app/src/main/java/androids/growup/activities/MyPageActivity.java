package androids.growup.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import androids.growup.JSONMyPlantsAdapter;
import androids.growup.MainActivity;
import androids.growup.R;


public class MyPageActivity extends ActionBarActivity {

    ListView my_plants_list;
    private JSONMyPlantsAdapter myPlantsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        TextView my_page_information = (TextView) findViewById(R.id.my_page_information);

        my_plants_list = (ListView) findViewById(R.id.my_plants_list);
        myPlantsAdapter = new JSONMyPlantsAdapter(this, getLayoutInflater());
        my_plants_list.setAdapter(myPlantsAdapter);

        // Check if we have any items in our list
        JSONArray checkArray = null;
        try {
            checkArray = getJSONObjectFromMyList().getJSONArray("myPlants");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (checkArray.length() != 0) {
            my_page_information.setVisibility(View.GONE);
            populateMyPlantsList();
        } else {
            my_page_information.setText("Du har ännu inga plantor inlagda i din lista.");
        }

        my_plants_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView plant_id = (TextView) view.findViewById(R.id.plant_id_tw);

                Intent plantIntent = new Intent(MyPageActivity.this, PlantActivity.class);
                plantIntent.putExtra("plant_id", Integer.parseInt(plant_id.getText().toString()));
                startActivity(plantIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (my_plants_list.getCount() != 0) {

        } else {
            //Log.d("motherfucker", "PICK => " + my_plants_list.getCount() + " NAY!");
        }

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
        JSONObject myPlants = getJSONObjectFromMyList();
        JSONArray plantArray;

        try {
            plantArray = myPlants.getJSONArray("myPlants");
            myPlantsAdapter.updateData(plantArray);

            for (int i = 0; i < plantArray.length(); i++) {
                JSONObject x = plantArray.getJSONObject(i);
            }
            my_plants_list.setAdapter(myPlantsAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJSONObjectFromMyList() {
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