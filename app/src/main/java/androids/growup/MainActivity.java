/*

"Pugs pugs pugs pugs..."
// Amelie Hellnurse

                          _..___
                      _..xxxxxxxllllxxxx...___
                   _.ssssssxxxxxxxxsssxxxxxxxxLlllxxx..._
               _.ssssSSSSsssssSSSSSSSSSsxxxxxxxXxxxXxxxXxlll++._
          _.sdSSSSSSSSSSSSSSSSSSSSSSsxxxxxxxXxxxXxxxXxxxXxxxxx+++.
       .dSSSSS$$$$$S$$SSSSSSS$$888SsxxxXxxxxXxxxXxxxxXxxxXxxxxxxxxx.
      j$$$$SS$$$$$$$$$$$S$SS$$888sxxxxXxxxxXxxxxXxxxxXxxXxxXxxxxxxxxx.
      $$$$SS$$$$$$$$$$$$$$$$$$88xxxxXXxxxXxxxxxXXxxxxXxxxXxxxXxxxxxxxx.
      Y$$$$SS$$$$$$$$$$$$$$$$8SsxxxxXxxXXxxxxxXXxxxxxxXxxxXxxxXxxxxS$xxh.
       `$$$S$S$$$$$$$$$$$$$$$SsxxxxxxxxxxxxxxxXxxxxxxxXxxxXxxxXXxxxS$$Sxx.
        .$$$SS$$$$$$$$$$$$$$SsxxxxxxxxxxxxxxxXxxxxxxxxXxxxXXxxXxxXxsS$$$xx.
        xSS$$$S$$$$$$$$$$$$SsxXxxxxxxxxxxxxxXxxxxxxxxxxXxxxxXXXxxxXxS$$$$xx.
       .+xSS$$$$$$$$$$$$$$$SxxxxxxxXxxxxxxxxxXXXxxxxxxxXXxxxXxxxxxxxsS$$$$xx.
      .++++SS$$$$$$$$$$$$$$SxXxxxxxxxxxxxxxxxxxXXXxxxxxxXxxxXxxxssSxsS$$$$$xx
     .+++++xxSS$$$$$$$$$$$SxxxxxXxxxxxxxxxssSSxxxxXxxxxxxxxXXxxsSx$Ssx$$$$$Sx.
    .++++xxxxxxSS$$$$$$$$SxxxxsxxxxxxxxssS$$$SSsxxxxSsxxxssxxxsSsxS$SsS$$$$$Xx.
   .++++x++xxxxxxSS$$$$SxxxxxsSssxxxxxxxxsS$$$SssxxsSSsxsSSssSSsxxS$$SsS$$$$$xx
   ++++++x+x++xxxxxxxxxxxxxssS$$SssssssssSS$$$$$SssSSSSsS$$SSSsxxsS$$SssS$$$$xx
  .+++x++xxxx++xxxxxxxXxxxxsS$$$$SSSyysSS$$$$$$$$$$$$$$$$$$$$$$SSyS$$$$S$$$$$xx
 .++++++x+xx+x++xxxxxxxXxxsS$$$$$$d8,,n88b$$S$$$$$SSS$$$$$$$SS$$d,8b$$$$$$$$$xx
.++++++x+xxXx++xxxxxxxxXxxxsS$$S$$$Y##880P$$$$$$$$SSSSSSSSSSSSSSY##P$$s'Y$$$$x'
++++++++xxxxXx++xxxxxxxXxxxxsS$SS$$$$$$$$$$$$$$SSS$$$$d8####b$$SS$$$Ssx  `"""'
+++++x+x+xxxXXx+x+xxxxxxXxxxxS$$$S$$$$$$$$S$$$SS$$$d#8$$8$8$8##8bSS$$sx
++++x++xxxxxxXXXx+++xxxxxXxxxxS$S$$$$$$$$$$$SS$$d#8$$8$8$$8$8$$8#8b$Sx'
+x+++xxxxxxxxXxXxx++xxxxxxXxxxxS$$$$$$S$$$SS$$888$$$$$8$8#8$$$$$8#P$S'
+++x+xxxxxxxxxXxXxxx+xxxxxxXXxxsS$$$$$$$SS$$$88$$$$$$S$SS#SS$$$$$Y$$S           Woof woof bitches!
+x++xxxxxxxxxxxXxXxxxxxxxxxxXXxssS$$$SSS$$$88$$$$$$SS$$SS#$SS$$$$$$$Sl
+xxxxXxxxxxxxxxxXxXxxxxxxxxxXXxXxsSSS$$$$68$$$$$$$S$$SSS$#$$$$S$$$$$$$
xxxxxxxxxXxxxxxxxxxXXXxxxxxxxxxXXxsS$$$$$9$$$$$$$$$$$$$$d#$$$$$$$$$$$$
xxxxxxxxxxxxxxxxxxxxXxXXxxxxxxxxxXXS$$$$$$$$$$$$$$$$$$d88##6$$$$$$$$$'
xxxxxxxXxxxXxxxxxxxxxxXxxxxxxxxxXXXSS$$$$$$$$$$$$$$$$d8888##b$$$$$$S'
xxXxxxxxxxXxxxXxxxxxxxxxxXxxxxxxxXXXXS$$$$$$$$$$$$$d88888888##b$$$P'
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxXXSS$$$$$$$$$$$$$Y8888P$$$$Y"'
xXxxxxXxxxxxxXxxxxxXxxxxxxxxxxxxxXxxxxXXsSS$$$$$$$$$$$$SSSS._
xxxxxxxxxxxxxxxXxxxxxxxxxxxxxxxxxxxXxxxxxXXSSSSSSSSSSSssxx+++;
xxxxxxxxxxxxxxxxxxXxXxxxXxxxxxxxx+xxXxxxxxxxsssssssssxxxxxx+'
xxxxxXxxxxxxXxxxxxxxxxXxxXxxxxxx+xxxxxXXxxxxxxxxxxxxxxxxxxx.
xxXxxxxxxxxxxxxxxxXxxxxxxXxxxXxxxx+xxx+xxXXxxxxxxxxxxxxxxXxx.
xxxxxxxxxxxxxxxxxxxxxxXxxxxxxxxxxxx+x+xxxxxXXxxXxxxxxxxXxx++xx
xxxxxxxxxxXxxxxxxxXxxxxxxxxxxxxxxx+xxx+xx+xxxXxxxxxxxxX+++++xx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxXxxxXxxxX++x++++.
xxxxXxxxxxxxxxxxxxXxxxxxxxxxxxxxxxx+xxxx++xxxxxXxxxXX+++++++++.
xxxxxxxxxxxxxXxxxxxxxxxXxxxxxxxxxxxxxxxxxxx+xxxx+xxX+x++++++++x.
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx+xxxxx++xxxXxx+++++xx++xx.
xxxxxxxxXxxxxxxxxxxxxxxxxxxxxxxxXxxxxxxxxxxxxxx+xXxx+x++++x+xxxxx.
xxxxxxxxxxxxXxxxxxXxxxxxxxxXxxxxxxxxxxxxxxxxXxxxXXxxxxx++xxxxx+xxxx.
xxxxXxxxxxxxxxxxxxxxxxxxxxxxxxxxxXxxxxxxXxxxxxxxXxxxxx++++++++xxx+xxxx.
xxxxxxxxxxxxxxxxxxxxxxXxxxxxxxxxxxxxxxxxxxxxxxxxXxxxxxxx++++++xx+xxx+xxx.
        */
package androids.growup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

/**
 * Handles our start page.
 */
public class MainActivity extends Activity {
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
            ex.printStackTrace();
        }

        Adapters adapters = new Adapters();
        JSONHelpers categories = new JSONHelpers(getAssets());
        List<Category> cats = categories.getAllCategories();

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
