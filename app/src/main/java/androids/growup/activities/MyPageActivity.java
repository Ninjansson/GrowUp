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

import androids.growup.Adapters;
import androids.growup.MainActivity;
import androids.growup.R;

/**
 *Copyright [AppGrowUp] [Amelie Hellners, Lee Carlsson, Kim Jansson, Mia Gruvman]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and limitations under the License.
 */
public class MyPageActivity extends ActionBarActivity {

    ListView my_plants_list;
    Adapters adapter;
    private Adapters.MyPlantsAdapter myPlantsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        TextView my_page_information = (TextView) findViewById(R.id.my_page_information);

        my_plants_list = (ListView) findViewById(R.id.my_plants_list);
        adapter = new Adapters();
        /*
        Adapters.PlantsAdapter plantsAdapter = adapters.new PlantsAdapter(this, listPlants);
        plantsList.setAdapter(plantsAdapter);*/
        myPlantsAdapter = adapter.new MyPlantsAdapter(this, getLayoutInflater());
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

    // Gets main object from our mylist file
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