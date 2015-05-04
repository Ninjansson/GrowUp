package androids.growup;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParsePosition;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "GrowUpMotherFucker";
    private ListView catList;
    private JSONAdapter catAdapter;

    private static final String QUERY_URL = "http://kimjansson.se/GrowUp/categories/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catList = (ListView) findViewById(R.id.categories);
        catAdapter = new JSONAdapter(this, getLayoutInflater());
        catList.setAdapter(catAdapter);
        populateCategoriesList();
    }

    private void queryCategories() {
        // Create a client to perform networking
        AsyncHttpClient client = new AsyncHttpClient();

        // Have the client get a JSONArray of data
        // and define how to respond
        client.get(QUERY_URL,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject) {}

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {}
                });
    }

    private void populateCategoriesList() {
        AsyncHttpClient cat_client = new AsyncHttpClient();

        cat_client.get(QUERY_URL,
                new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(JSONObject cat_object) {
                    int cat_found = cat_object.optJSONArray("categories").length();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean isNumeric(String str)
    {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    private void showToast(String output) {
        Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
    }
}
