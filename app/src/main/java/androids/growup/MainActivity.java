package androids.growup;

import android.app.ProgressDialog;
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


public class MainActivity extends ActionBarActivity {
    private static final String QUERY_URL = "http://kimjansson.se/GrowUp/categories/all";
    private ArrayList<Category> listCategories;
    private ListView catList;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }

        MyListHelperClass help = new MyListHelperClass(getApplicationContext());
        //Log.d("motherfucker", "Does the MyList exist? => " + help.doesMyListExist());

        listCategories = new ArrayList<>();
        Category kryddor = new Category(1, R.drawable.icon_tomato, "Kryddor");
        Category fruktobar = new Category(2, R.drawable.icon_apple, "Frukter & bär");
        Category gronsaker = new Category(3, R.drawable.icon_carrot, "Grönsaker");
        Category atbarablommor = new Category(4, R.drawable.icon_apple, "Ätbara blommor");

        listCategories.add(kryddor);
        listCategories.add(fruktobar);
        listCategories.add(gronsaker);
        listCategories.add(atbarablommor);

        catList = (ListView) findViewById(R.id.categories);

        ListCategoriesAdapter adapter = new ListCategoriesAdapter(this, listCategories);
        catList.setAdapter(adapter);

        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = listCategories.get(position);
                int cat_id = category.getCat_id();
                String cat_name = category.getCat_name();
                Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);
                categoryIntent.putExtra("cat_id", cat_id);
                categoryIntent.putExtra("cat_name", cat_name);

                // startActivity(categoryIntent);


                dialog = ProgressDialog.show(MainActivity.this, "Laddar", "Vänligen vänta");
                dialogThread thread = new dialogThread();
                thread.start();
                startActivity(categoryIntent);
                overridePendingTransition(R.animator.animation_1, R.animator.animation_2);

            }
        });
    }

    private class dialogThread extends Thread {
        public void run() {
            try {
                Thread.sleep(1000);
                dialog.cancel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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
