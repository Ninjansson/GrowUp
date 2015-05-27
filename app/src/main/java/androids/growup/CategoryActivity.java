package androids.growup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends ActionBarActivity {
    private ArrayList<Plant> listPlants;
    private ListView plantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setTitle(this.getIntent().getExtras().getString("cat_name").toUpperCase());

        Adapter adapter = new Adapter();
        Adapter.JSONHelpers categories = adapter.new JSONHelpers(getAssets());
        List<Category> cats = categories.getAll();

        plantsList = (ListView) findViewById(R.id.category_plants);
        listPlants = new ArrayList<>();

        for (Category category : cats) {
            if (category.cat_id == this.getIntent().getExtras().getInt("cat_id")) {
                for (Plant plant : category.plants) {
                    listPlants.add(plant);
                }
            }
        }

        Adapter.PlantsAdapter plantsAdapter = adapter.new PlantsAdapter(this, listPlants);
        plantsList.setAdapter(plantsAdapter);

        plantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plant plant = listPlants.get(position);
                int plant_id = plant.plant_id;
                String plant_name = plant.name;

                Intent plantIntent = new Intent(CategoryActivity.this, PlantActivity.class);
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
