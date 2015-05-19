package androids.growup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kim Jansson on 2015-04-12.
 */

public class JSONMyPlantsAdapter extends BaseAdapter {
    private static final String IMAGE_URL_BASE = "http://kimjansson.se/GrowUp/imgs/plant_icons/";
    private static final String TAG = "GrowUpMotherFucker";

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

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_my_plants, null);

            holder = new ViewHolder();
            holder.my_plant_name = (TextView) convertView.findViewById(R.id.my_plant_name);
            holder.plant_id_tw = (TextView) convertView.findViewById(R.id.plant_id_tw);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JSONObject jsonObject = (JSONObject) getItem(position);

        // Grab the title and author from the JSON
        String name = "";

        if (jsonObject.has("my_name")) {
            name = jsonObject.optString("my_name") + " id => " + jsonObject.optInt("plant_id");
        }
        // Send these Strings to the TextViews for display
        holder.my_plant_name.setText(name);
        holder.plant_id_tw.setText(String.valueOf(jsonObject.optInt("plant_id")));

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
    }
}