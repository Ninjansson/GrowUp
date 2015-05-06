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

public class JSONCategoriesAdapter extends BaseAdapter {
    private static final String IMAGE_URL_BASE = "http://kimjansson.se/GrowUp/imgs/";
    private static final String TAG = "GrowUpMotherFucker";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONCategoriesAdapter(Context context, LayoutInflater inflater) {
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
            convertView = mInflater.inflate(R.layout.row_categories, null);

            holder = new ViewHolder();
            holder.img_cat_logo = (ImageView) convertView.findViewById(R.id.img_cat_logo);
            holder.text_category = (TextView) convertView.findViewById(R.id.text_category);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JSONObject jsonObject = (JSONObject) getItem(position);

        // If so, grab the Cover ID out from the object
        String img = jsonObject.optString("cat_icon");
        // Construct the image URL (specific to API)
        String imageURL = IMAGE_URL_BASE + img;

        // Use Picasso to load the image
        // Temporarily have a placeholder in case it's slow to load
        Picasso.with(mContext).load(imageURL).into(holder.img_cat_logo);

        // Grab the title and author from the JSON
        String cat_name = "";

        if (jsonObject.has("cat_name")) {
            cat_name = jsonObject.optString("cat_name");
        }

        // Send these Strings to the TextViews for display
        holder.text_category.setText(cat_name);

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
        public ImageView img_cat_logo;
        public TextView text_category;
    }
}