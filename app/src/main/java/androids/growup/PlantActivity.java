package androids.growup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlantActivity extends AppCompatActivity {

    private static String THIS_PLANT;
    private static int PLANT_ID;
    public ImageView plant_icon, diff_icon;
    public TextView plant_name, latin_name, plant_info, plant_how_to, plant_usage, plant_good_to_know,
            plant_harvest, plant_link, plant_difficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        Plant plant = (Plant) this.getIntent().getExtras().getSerializable("plant");
        setTitle(plant.name);
        THIS_PLANT = plant.name;
        PLANT_ID = plant.plant_id;

        plant_name = (TextView) findViewById(R.id.plant_name);
        latin_name = (TextView) findViewById(R.id.latin_name);
        plant_info = (TextView) findViewById(R.id.plant_info);
        plant_how_to = (TextView) findViewById(R.id.plant_how_to);
        plant_usage = (TextView) findViewById(R.id.plant_usage);
        plant_good_to_know = (TextView) findViewById(R.id.plant_good_to_know);
        plant_harvest = (TextView) findViewById(R.id.plant_harvest);
        plant_link = (TextView) findViewById(R.id.plant_link);
        //plant_difficulty = (TextView) findViewById(R.id.plant_name);

        plant_name.setText(plant.name);
        latin_name.setText(plant.latin_name);
        plant_info.setText(plant.info);
        plant_how_to.setText(plant.how_to);
        plant_usage.setText(plant.plant_usage);
        plant_good_to_know.setText(plant.good_to_know);
        plant_harvest.setText(plant.harvest);
        plant_link.setText(plant.link);
       // plant_difficulty.setText(plant.difficulty);

        final Button open_popup = (Button) findViewById(R.id.button_open_popup);
        open_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    protected void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(PlantActivity.this);
        View promptView = layoutInflater.inflate(R.layout.popup_add_plant, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlantActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText sp_name = (EditText) promptView.findViewById(R.id.sp_name);
        sp_name.setHint("Skriv in valfritt namn på din nya planta (max 20 tecken).       ");
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("SPARA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String input = sp_name.getText().toString();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();

                        if (input.equals("")) {
                            input = THIS_PLANT + " | " + dateFormat.format(date);

                        } else {
                            input = sp_name.getText().toString();
                        }
                        saveToMyList(input);
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
        alert.setTitle("Ny planta");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.show();
    }

    private void saveToMyList(String my_name) {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";
        JSONObject myObject = new JSONObject();
        File file = new File(filePath);

        if (!file.exists()) {
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
            myObject.put("id", todaysDate);
            myObject.put("my_name", my_name);
            myObject.put("plant", this.getIntent().getExtras().getString("name"));
            myObject.put("plant_date", todaysDate);
            myObject.put("plant_id", PLANT_ID);

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

    private String getData() {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";
        StringBuilder finalString = new StringBuilder();

        try {
            FileInputStream inStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String oneLine;

            while ((oneLine = bufferedReader.readLine()) != null) {
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
}
