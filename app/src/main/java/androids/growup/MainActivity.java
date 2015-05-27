package androids.growup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
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

        Adapter adapter = new Adapter();
        Adapter.JSONHelpers categories = adapter.new JSONHelpers(getAssets());
        List<Category> cats = categories.getAll();

        catList = (ListView) findViewById(R.id.categories);
        listCategories = new ArrayList<>();

        for (Category category : cats) {
            listCategories.add(category);
        }

        Adapter.CategoriesAdapter catAdapter = adapter.new CategoriesAdapter(this, listCategories);
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
