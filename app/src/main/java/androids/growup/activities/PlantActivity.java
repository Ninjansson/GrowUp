package androids.growup.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

import androids.growup.MainActivity;
import androids.growup.R;
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
 *//
public class PlantActivity extends ActionBarActivity {

    private static String THIS_PLANT;
    private static int PLANT_ID;
    public ImageView plant_icon, diff_icon;
    public TextView plant_name, latin_name, plant_info, plant_how_to, plant_usage, plant_good_to_know,
            plant_harvest, plant_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        // Gets the Plant object sent as an extra from CategoryActivity.java
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
        diff_icon = (ImageView) findViewById(R.id.diff_icon);
        plant_icon = (ImageView) findViewById(R.id.plant_icon);

        plant_name.setText(plant.name);
        latin_name.setText(plant.latin_name);
        plant_info.setText(plant.info);
        plant_how_to.setText(plant.how_to);
        plant_usage.setText(plant.plant_usage);
        plant_good_to_know.setText(plant.good_to_know);
        plant_harvest.setText(plant.harvest);
        plant_link.setText(plant.link);

        // Sets the difficulty icon
        diff_icon.setImageResource(difficultyIcon(plant.difficulty));

        //int icon = getResources().getIdentifier("plant_page_oregano", "drawable", getPackageName());
        //plant_icon.setImageResource(icon);

        final Button open_popup = (Button) findViewById(R.id.button_open_popup);
        open_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    /**
     * Returns the correct int depending on the input.
     * @param difficulty int
     * @return The corrent format of the image we want to use.
     */
    public int difficultyIcon(int difficulty) {
        int img = 0;
        switch(difficulty) {
            case 1:
                img = getResources().getIdentifier("ic_easy", "drawable", getPackageName());
                 break;
            case 2:
                img = getResources().getIdentifier("ic_medium", "drawable", getPackageName());
                break;
            case 3:
                img = getResources().getIdentifier("ic_hard", "drawable", getPackageName());
                break;
            default:
                break;
        }
        return img;
    }

    // Shows an input dialog when we choose "Lägg till planta" under an individual plants page.
    protected void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(PlantActivity.this);
        View promptView = layoutInflater.inflate(R.layout.popup_add_plant, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlantActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText sp_name = (EditText) promptView.findViewById(R.id.sp_name);
        sp_name.setHint("Skriv in valfritt namn på din nya planta (max 20 tecken).");

        // Setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("SPARA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String input = sp_name.getText().toString();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();

                        // If input is empty we create a name based on the plants name plus date.
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
                                dialog.cancel();
                            }
                        });

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.setTitle("Ny planta");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.show();
    }

    /**
     * Saves a plant to the file named myList
     * @param my_name Name of plant to save. Either user input or default name.
     */
    private void saveToMyList(String my_name) {
        Context context = getApplicationContext();
        String filePath = context.getFilesDir().getPath().toString() + "/mylist";
        JSONObject myObject = new JSONObject();
        File file = new File(filePath);

        // Check if file exists. Not really needed but we keep it anyway. Why? Because SCREW YOU thats why!
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

        // Retrieves the main JSON object from our list.
        JSONObject mainObject = getMainObjectFromMyList();
        try {
            // Retrieves the myPlants array.
            JSONArray plantArray = mainObject.getJSONArray("myPlants");
            plantArray.put(myObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream ops = new FileOutputStream(file, false);
            ops.write(mainObject.toString().getBytes());
            ops.close();
            Toast.makeText(getApplicationContext(), "Din planta är nu sparad.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getMainObjectFromMyList() {
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