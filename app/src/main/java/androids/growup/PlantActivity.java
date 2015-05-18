package androids.growup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
/*
<<<<<<< HEAD
import android.graphics.Color;
import android.media.MediaScannerConnection;
=======
>>>>>>> origin/Mia
        */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
/*
<<<<<<< HEAD
import android.widget.ImageView;
=======
*/
import android.widget.ListView;
//>>>>>>> origin/Mia
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
/*
<<<<<<< HEAD

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlantActivity extends ActionBarActivity {

    private static final String PLANT_ICON = "http://kimjansson.se/GrowUp/imgs/plantpage/";
    private static final String PREFS_NAME = "SETTINGS";
    private static final String FILE_NAME = "GrowUpMinLista.xml";
    private TextView plant_name, latin_name, plant_info, plant_how_to, plant_usage, plant_habitat, plant_difficulty, plant_link, plant_good_to_know, plant_harvest;
    private ImageView plant_icon;
======= */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlantActivity extends ActionBarActivity {

    private static String THIS_PLANT;
    private static int PLANT_ID;
    private ListView plant_view;
    private JSONPlantAdapter plantAdapter;
//>>>>>>> origin/Mia


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        /*
<<<<<<< HEAD
        setTitle(this.getIntent().getExtras().getString("name").toUpperCase());
        plant_name = (TextView) findViewById(R.id.plant_name);
        plant_icon = (ImageView) findViewById(R.id.plant_icon);
        latin_name = (TextView) findViewById(R.id.latin_name);
        plant_info = (TextView) findViewById(R.id.plant_info);
        plant_how_to = (TextView) findViewById(R.id.plant_how_to);
        plant_usage = (TextView) findViewById(R.id.plant_usage);
        plant_habitat = (TextView) findViewById(R.id.plant_habitat);
        plant_link = (TextView) findViewById(R.id.plant_link);
        plant_difficulty = (TextView) findViewById(R.id.plant_difficulty);
        plant_good_to_know = (TextView) findViewById(R.id.plant_good_to_know);
        plant_harvest = (TextView) findViewById(R.id.plant_harvest);

        plant_name.setText(this.getIntent().getExtras().getString("name").toUpperCase());

        String img = PLANT_ICON + "plant_icon_" + this.getIntent().getExtras().getInt("id") + ".png";
        Picasso.with(this).load(img).into(plant_icon);

        //Log.d("PLANTMOTHERFUCKER", "Img => " + img);

        latin_name.setText(this.getIntent().getExtras().getString("latin_name"));
        plant_info.setText(this.getIntent().getExtras().getString("info"));
        plant_how_to.setText(this.getIntent().getExtras().getString("how_to"));
        plant_usage.setText(this.getIntent().getExtras().getString("usage"));
        plant_habitat.setText(checkHabitat(this.getIntent().getExtras().getInt("habitat")));
        plant_link.setText(this.getIntent().getExtras().getString("plant_link"));
        plant_good_to_know.setText(this.getIntent().getExtras().getString("good_to_know"));
        plant_harvest.setText(this.getIntent().getExtras().getString("harvest"));
        plant_difficulty.setBackgroundColor(Color.parseColor(checkDifficulty(this.getIntent().getExtras().getInt("difficulty"))));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
=======
*/
        PLANT_ID = this.getIntent().getExtras().getInt("plant_id");
        setTitle(this.getIntent().getExtras().getString("my_plant_name"));

        displayPlantContent();
//>>>>>>> origin/Mia

        final Button open_popup = (Button) findViewById(R.id.button_open_popup);

        open_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    private void displayPlantContent() {
        plant_view = (ListView) findViewById(R.id.plant_view);
        plantAdapter = new JSONPlantAdapter(this, getLayoutInflater());
        plant_view.setAdapter(plantAdapter);

        String url = "http://kimjansson.se/GrowUp/plants/" + PLANT_ID;
        AsyncHttpClient plantClient = new AsyncHttpClient();

        plantClient.get(url,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject plantObject) {
                        plantAdapter.updateData(plantObject.optJSONArray("plant"));
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        Log.d("motherfucker", "Failure connecting to whatever " + throwable + " " + error);
                    }
                });
    }

    protected void showInputDialog() {
        //final File file = new File("GrowUpLista.txt");
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(PlantActivity.this);
        View promptView = layoutInflater.inflate(R.layout.popup_add_plant, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlantActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText sp_name = (EditText) promptView.findViewById(R.id.sp_name);
        sp_name.setHint("Skriv in namnet på din nya planta.");
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("SPARA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String x = sp_name.getText().toString();
                        if (x.equals("")) {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = new Date();

                            x = THIS_PLANT + "[" + dateFormat.format(date) + "]";
                        }
                        saveToMyList(x);
                    }
                })
                .setNegativeButton("AVBRYT",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //checkFile();
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.setTitle("Ny planta.");
        alert.setIcon(R.drawable.icon_grow_up);
        alert.setMessage("Fett nice att du ska så en ny planta kompis! Jag, Amelie, håller tummarna för dig! OMG! #SWAGALICIOUS");
        alert.show();
    }

    private void checkFile() {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";
        try {
            FileInputStream inStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder finalString = new StringBuilder();
            String oneLine;

            while((oneLine = bufferedReader.readLine()) != null) {
                finalString.append(oneLine);
            }

            bufferedReader.close();
            inStream.close();
            inputStreamReader.close();

            //Log.d("motherfucker", "FILE => " + finalString.toString());
        } catch (IOException e) {
            //Log.e("ERRORMOTHERFUCKER", "IOEXCEPTION => " + e);
        }
    }

    private void saveToMyList(String my_name) {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";

        JSONObject myObject = new JSONObject();

        File file = new File(filePath);

        if(!file.exists()) {
            try {
                file.createNewFile();

                JSONObject mainObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                mainObject.put("myPlants", jsonArray);
                FileOutputStream fos = new FileOutputStream(file, false);
                fos.write(mainObject.toString().getBytes());
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Create new JSON object, populate it and put it into our list
        try {
            long todaysDate = System.currentTimeMillis() / 1000L;
            myObject.put("my_name", my_name);
            myObject.put("plant", this.getIntent().getExtras().getString("name"));
            myObject.put("plant_date", todaysDate);

        } catch (JSONException je) {
            je.printStackTrace();
        }

        JSONObject boo = getPlantsJSONArrayFromMyList();
        try {
            JSONArray plantArray = boo.getJSONArray("myPlants");
            plantArray.put(myObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream ops = new FileOutputStream(file, false);
            ops.write(boo.toString().getBytes());
            ops.close();
            Toast.makeText(getApplicationContext(), "Din planta är nu sparad.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getPlantsJSONArrayFromMyList() {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";
        StringBuilder finalString = new StringBuilder();
        JSONObject mainObject = null;

        try {
            FileInputStream inStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String oneLine;
            while((oneLine = bufferedReader.readLine()) != null) {
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

    private String getData() {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";
        StringBuilder finalString = new StringBuilder();

        try {
            FileInputStream inStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String oneLine;

            while((oneLine = bufferedReader.readLine()) != null) {
                finalString.append(oneLine);
            }

            bufferedReader.close();
            inStream.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalString.toString();
    }


    /*
    private void editorCommitChanges() {
        //Log.i("TEST MOTHERFUCKER", "In PlantActivity.editorCommitChanges() - Probably fucking things up");

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        Switch pushNotices = (Switch) findViewById(R.id.toggle_push);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        String plantName = this.getIntent().getExtras().getString("name");
        editor.putBoolean(plantName, pushNotices.isChecked());

        // Commit the edits!
        editor.commit();
    }
    */

    @Override
    protected void onStop() {
        super.onStop();
        //editorCommitChanges();
    }

    public String checkHabitat(int habitat) {
        return habitat == 0 ? "Ute" : habitat == 1 ? "Inne" : "Ute och Inne";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plant, menu);
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
                Intent catIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                catIntent.putExtra("cat_id", this.getIntent().getExtras().getInt("cat_id"));
                catIntent.putExtra("cat_name", this.getIntent().getExtras().getString("cat_name"));
                startActivityForResult(catIntent, 0);
                return true;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_plant, container, false);
            return rootView;
        }
    }
}
