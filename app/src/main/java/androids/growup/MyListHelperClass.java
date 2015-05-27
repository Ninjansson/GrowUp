package androids.growup;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

/**
 * Created by kim.jansson on 2015-05-20.

class MyListHelperClass extends ActionBarActivity {

    Context context;
    String filePath;
    File file;

    MyListHelperClass(Context context) {
        this.context = context;
        this.filePath = context.getFilesDir().getPath().toString() + "/mylist";
        this.file = new File(filePath);
    }

    public File getFile() {
        return this.file;
    }

    public boolean doesMyListExist() {
        return !this.file.exists() ? false : true;
    }

    public void createMyList() {
        try {
            this.file.createNewFile();

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

    public JSONArray getMyListMyPlantsArray() {
        JSONObject mainObject = getMyListMainObject();
        JSONArray myListMyPlantsArray = null;
        try {
            myListMyPlantsArray = mainObject.getJSONArray("myPlants");
            //plantArray.put(myObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myListMyPlantsArray;
    }

    private JSONObject getMyListMainObject() {
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

    public JSONObject createNewPlant(String my_name, String plant_name, int plant_id) {
        JSONObject myNewPlant = new JSONObject();
        try {
            long todaysDate = System.currentTimeMillis() / 1000L;
            myNewPlant.put("my_name", my_name);
            myNewPlant.put("plant", plant_name);
            myNewPlant.put("plant_date", todaysDate);
            myNewPlant.put("plant_id", plant_id);

        } catch (JSONException je) {
            je.printStackTrace();
        }
        return myNewPlant;
    }

    public void insertPlantIntoMyList(JSONObject thisPlant) {
        getMyListMyPlantsArray().put(thisPlant);
        try {
            FileOutputStream ops = new FileOutputStream(this.file, false);
            ops.write(thisPlant.toString().getBytes());
            ops.close();
            Toast.makeText(context, "Din planta är nu sparad.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/