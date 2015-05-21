package androids.growup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kim Jansson on 2015-04-12.
 */

public class JSONPlantAdapter extends BaseAdapter {
    private static final String IMAGE_URL_BASE = "http://kimjansson.se/GrowUp/imgs/plantpage/";
    private static final String TAG = "MotherFucker";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONPlantAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        // your particular dataset uses String IDs
        // but you have to put something in this method
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.single_plant_view, null);

            holder = new ViewHolder();
            holder.plant_icon = (ImageView) convertView.findViewById(R.id.plant_icon);
            holder.plant_name = (TextView) convertView.findViewById(R.id.plant_name);
            holder.latin_name = (TextView)convertView.findViewById(R.id.latin_name);
            holder.plant_how_to = (TextView)convertView.findViewById(R.id.plant_how_to);
            holder.plant_usage = (TextView)convertView.findViewById(R.id.plant_usage);
            holder.plant_habitat = (TextView)convertView.findViewById(R.id.plant_habitat);
            holder.plant_link = (TextView)convertView.findViewById(R.id.plant_link);
            holder.plant_difficulty = (TextView)convertView.findViewById(R.id.plant_difficulty);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JSONObject jsonObject = (JSONObject) getItem(position);

        // If so, grab the Cover ID out from the object
        int img = jsonObject.optInt("id");
        // Construct the image URL (specific to API)
        String imageURL = IMAGE_URL_BASE + "plant_icon_" + img + ".png";

        // Use Picasso to load the image
        // Temporarily have a placeholder in case it's slow to load
        Picasso.with(mContext).load(imageURL).into(holder.plant_icon);

        // Send these Strings to the TextViews for display
        try {
            holder.plant_name.setText(jsonObject.optString("name"));
            holder.latin_name.setText(jsonObject.getString("latin_name"));
            holder.plant_how_to.setText(jsonObject.getString("how_to"));
            holder.plant_usage.setText(jsonObject.getString("plant_usage"));
            holder.plant_habitat.setText(jsonObject.getString("habitat"));
            holder.plant_link.setText(jsonObject.getString("link"));
            holder.plant_difficulty.setText(checkDifficulty(jsonObject.getInt("difficulty")));
        } catch (JSONException e) {
            Log.d(TAG, "ALLT GICK ÅT HELVETE!!!");
        }

        return convertView;
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


    public void updateData(JSONArray jsonArray) {
        // update the adapter's dataset
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    // this is used so you only ever have to do
// inflation and finding by ID once ever per View
    private static class ViewHolder {
        public ImageView plant_icon;
        public TextView plant_name, latin_name, plant_how_to, plant_usage, plant_habitat, plant_link, plant_difficulty;
    }
}