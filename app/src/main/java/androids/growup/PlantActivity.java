package androids.growup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PlantActivity extends ActionBarActivity {

    private static final String PLANT_ICON = "http://kimjansson.se/GrowUp/imgs/plantpage/";
    private static final String PREFS_NAME = "SETTINGS";
    private static final String FILE_NAME = "GrowUpMinLista.xml";
    private TextView plant_name, latin_name, plant_info, plant_how_to, plant_usage, plant_habitat, plant_difficulty;
    private ImageView plant_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        setTitle(this.getIntent().getExtras().getString("name").toUpperCase());
        plant_name = (TextView) findViewById(R.id.plant_name);
        plant_icon = (ImageView) findViewById(R.id.plant_icon);
        latin_name = (TextView) findViewById(R.id.latin_name);
        plant_info = (TextView) findViewById(R.id.plant_info);
        plant_how_to = (TextView) findViewById(R.id.plant_how_to);
        plant_usage = (TextView) findViewById(R.id.plant_usage);
        plant_habitat = (TextView) findViewById(R.id.plant_habitat);
        plant_difficulty = (TextView) findViewById(R.id.plant_difficulty);

        plant_name.setText(this.getIntent().getExtras().getString("name").toUpperCase());

        String img = PLANT_ICON + "plant_icon_" + this.getIntent().getExtras().getInt("id") + ".png";
        Picasso.with(this).load(img).into(plant_icon);

        //Log.d("PLANTMOTHERFUCKER", "Img => " + img);

        latin_name.setText(this.getIntent().getExtras().getString("latin_name"));
        plant_info.setText(this.getIntent().getExtras().getString("info"));
        plant_how_to.setText(this.getIntent().getExtras().getString("how_to"));
        plant_usage.setText(this.getIntent().getExtras().getString("usage"));
        plant_habitat.setText(checkHabitat(this.getIntent().getExtras().getInt("habitat")));
        plant_difficulty.setBackgroundColor(Color.parseColor(checkDifficulty(this.getIntent().getExtras().getInt("difficulty"))));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        final Button open_popup = (Button) findViewById(R.id.button_open_popup);

        open_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

    }

    protected void showInputDialog() {
        final File file = new File("GrowUpLista.txt");
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(PlantActivity.this);
        View promptView = layoutInflater.inflate(R.layout.popup_add_plant, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlantActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText sp_name = (EditText) promptView.findViewById(R.id.sp_name);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("SPARA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String x = sp_name.getText().toString();
                        saveToMyList();
                    }
                })
                .setNegativeButton("AVBRYT",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                checkFile(file);
                                //dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void checkFile(File file) {

    }


    private void saveToMyList() {
        try
        {
            // Creates a trace file in the primary external storage space of the
            // current application.
            // If the file does not exists, it is created.
            File growUpFile = new File(this.getExternalFilesDir(null), FILE_NAME);
            if (!growUpFile.exists()) {
                growUpFile.createNewFile();
            }
            // Adds a line to the trace file
            BufferedWriter writer = new BufferedWriter(new FileWriter(growUpFile, true ));
            writer.write("Du kan vara en planta.");
            writer.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            MediaScannerConnection.scanFile(this, new String[]{growUpFile.toString()}, null, null);

        }
        catch (IOException e)
        {
            Log.e("SAVEFILEFUCKER", "Unable to write to the TraceFile.txt file.");
        }
    }

    public String checkDifficulty(int difficulty) {
        String output = "";
        switch(difficulty) {
            case 1:
                output = "#00FF00";
                break;
            case 2:
                output = "#FFCC00";
                break;
            case 3:
                output = "#FF0000";
                break;
            default:
                break;
        }
        return output;
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
            case R.id.menu_home:
                startActivity(new Intent(this, MainActivity.class));
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
