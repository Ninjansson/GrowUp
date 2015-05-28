package androids.growup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androids.growup.activities.AboutUsActivity;
import androids.growup.activities.CategoryActivity;
import androids.growup.activities.InspoActivity;
import androids.growup.activities.MyPageActivity;
import androids.growup.activities.SettingsActivity;
import androids.growup.gson.Category;


public class MainActivity extends Activity {
    private static final String QUERY_URL = "http://kimjansson.se/GrowUp/categories/all";
    private ArrayList<Category> listCategories;
    private ListView catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }

        Adapters adapters = new Adapters();
        JSONHelpers categories = new JSONHelpers(getAssets());
        List<Category> cats = categories.getAll();

        catList = (ListView) findViewById(R.id.categories);
        listCategories = new ArrayList<>();

        for (Category category : cats) {
            listCategories.add(category);
        }

        Adapters.CategoriesAdapter catAdapter = adapters.new CategoriesAdapter(this, listCategories);
        catList.setAdapter(catAdapter);

        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = listCategories.get(position);
                int cat_id = category.cat_id;
                String cat_name = category.cat_name;

                Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);
                categoryIntent.putExtra("cat_id", cat_id);
                categoryIntent.putExtra("cat_name", cat_name);

                startActivity(categoryIntent);
                overridePendingTransition(R.animator.animation_1, R.animator.animation_2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_inspo:
                startActivity(new Intent(this, InspoActivity.class));
                return true;
            case R.id.menu_aboutus:
                startActivity(new Intent(this, AboutUsActivity.class));
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.menu_my_page:
                startActivity(new Intent(this, MyPageActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
