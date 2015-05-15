package androids.growup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParsePosition;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "GrowUpMotherFucker";
    private static final String QUERY_URL = "http://kimjansson.se/GrowUp/categories/all";



    private ListView catList;
    private JSONCategoriesAdapter catAdapter;
    private PendingIntent pendingIntent;

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* START TIMER */
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        startTimer();
        /* END TIMER */


        catList = (ListView) findViewById(R.id.categories);
        catAdapter = new JSONCategoriesAdapter(this, getLayoutInflater());

        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject object = (JSONObject) catAdapter.getItem(position);
                //showToast("ID: " + object.optInt("id"));
                Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);
                categoryIntent.putExtra("cat_id", object.optInt("id"));
                categoryIntent.putExtra("cat_name", object.optString("cat_name"));

                startActivity(categoryIntent);

                overridePendingTransition(R.animator.animation_1, R.animator.animation_2);
            }
        });

        catList.setAdapter(catAdapter);
        populateCategoriesList();
    }

    private void startTimer() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 60000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        //Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    private void populateCategoriesList() {
        AsyncHttpClient cat_client = new AsyncHttpClient();

        cat_client.get(QUERY_URL,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject cat_object) {
                        catAdapter.updateData(cat_object.optJSONArray("categories"));
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        Log.d(TAG, "Failure connecting to whatever " + throwable + " " + error);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void showToast(String output) {
        Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
    }
}
