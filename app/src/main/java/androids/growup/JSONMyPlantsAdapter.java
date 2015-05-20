package androids.growup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by Kim Jansson on 2015-04-12.
 */

public class JSONMyPlantsAdapter extends BaseAdapter {
    private static final String IMAGE_URL_BASE = "http://kimjansson.se/GrowUp/imgs/plant_icons/";
    private static final String TAG = "GrowUpMotherFucker";
    private static final int PLANT_TO_REMOVE = 0;

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONMyPlantsAdapter(Context context, LayoutInflater inflater) {
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
        int plantan = 0;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_my_plants, null);

            holder = new ViewHolder();
            holder.my_plant_name = (TextView) convertView.findViewById(R.id.my_plant_name);
            holder.plant_id_tw = (TextView) convertView.findViewById(R.id.plant_id_tw);
            holder.button_delete = (Button) convertView.findViewById(R.id.button_delete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final JSONObject jsonObject = (JSONObject) getItem(position);

        // Grab the title and author from the JSON
        String name = "";

        if (jsonObject.has("my_name")) {
            name = jsonObject.optString("my_name");
        }

        // Send these Strings to the TextViews for display
        holder.my_plant_name.setText(name);
        holder.plant_id_tw.setText(String.valueOf(jsonObject.optInt("plant_id")));
        holder.plant_id_tw.setVisibility(View.INVISIBLE);
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("motherfucker", "PLANT ID => " + jsonObject.optInt("plant_id"));
                Toast.makeText(mContext, "Plantan är raderad.", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void updateData(JSONArray jsonArray) {
        // update the adapter's dataset
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    // this is used so you only ever have to do
// inflation and finding by ID once ever per View
    private static class ViewHolder {
        public TextView my_plant_name, plant_id_tw;
        public Button button_delete;
    }
}