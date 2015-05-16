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
import org.json.JSONObject;

/**
 * Created by Kim Jansson on 2015-04-12.
 */

public class JSONCategoryPlantsAdapter extends BaseAdapter {
    private static final String IMAGE_URL_BASE = "http://kimjansson.se/GrowUp/imgs/";
    private static final String TAG = "GrowUpMotherFucker";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONCategoryPlantsAdapter(Context context, LayoutInflater inflater) {
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
            convertView = mInflater.inflate(R.layout.row_category_plants, null);

            holder = new ViewHolder();
            holder.plant_img = (ImageView) convertView.findViewById(R.id.plant_img);
            holder.plant_name = (TextView) convertView.findViewById(R.id.plant_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JSONObject jsonObject = (JSONObject) getItem(position);

        //Log.d(TAG, "Object" + jsonObject);

        // If so, grab the Cover ID out from the object
        int img = jsonObject.optInt("id");
        // Construct the image URL (specific to API)
        String imageURL = IMAGE_URL_BASE + "plant_" + img + ".png";

        // Use Picasso to load the image
        // Temporarily have a placeholder in case it's slow to load
        Picasso.with(mContext).load(imageURL).into(holder.plant_img);

        // Grab the title and author from the JSON
        String name = "";

        if (jsonObject.has("name")) {
            name = jsonObject.optString("name");
        }

        // Send these Strings to the TextViews for display
        holder.plant_name.setText(name);

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
        public ImageView plant_img;
        public TextView plant_name;
    }
}