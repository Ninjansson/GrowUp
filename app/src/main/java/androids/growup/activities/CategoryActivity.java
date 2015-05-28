package androids.growup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import androids.growup.Adapters;
import androids.growup.JSONHelpers;
import androids.growup.MainActivity;
import androids.growup.R;
import androids.growup.gson.Category;
import androids.growup.gson.Plant;

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
public class CategoryActivity extends ActionBarActivity {
    private ArrayList<Plant> listPlants;
    private ListView plantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Set title to this category name
        setTitle(this.getIntent().getExtras().getString("cat_name").toUpperCase());

        Adapters adapters = new Adapters();
        JSONHelpers categories = new JSONHelpers(getAssets());

        List<Category> cats = categories.getAllCategories();

        plantsList = (ListView) findViewById(R.id.category_plants);
        listPlants = new ArrayList<>();

        for (Category category : cats) {
            if (category.cat_id == this.getIntent().getExtras().getInt("cat_id")) {
                for (Plant plant : category.plants) {
                    listPlants.add(plant);
                }
            }
        }

        Adapters.PlantsAdapter plantsAdapter = adapters.new PlantsAdapter(this, listPlants);
        plantsList.setAdapter(plantsAdapter);

        plantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plant plant = listPlants.get(position);
                int plant_id = plant.plant_id;
                String plant_name = plant.name;

                Intent plantIntent = new Intent(CategoryActivity.this, PlantActivity.class);
                // Sends parameters to PlantActivity.java
                plantIntent.putExtra("plant_id", plant_id);
                plantIntent.putExtra("plant_name", plant_name);
                plantIntent.putExtra("plant", plant);

                startActivity(plantIntent);

                overridePendingTransition(R.animator.animation_1, R.animator.animation_2);
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
}
