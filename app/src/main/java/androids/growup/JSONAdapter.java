package androids.growup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kim Jansson on 2015-04-12.
 */
public class JSONAdapter extends BaseAdapter{
    private static final String TAG = "GrowUpMotherFucker";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONAdapter(Context context, LayoutInflater inflater) {
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

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {
            Log.d(TAG, "ConvertView == null");
            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.row_categories, null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            //holder.img_cat_logo = (ImageView) convertView.findViewById(R.id.img_cat_logo);
            holder.text_category = (TextView) convertView.findViewById(R.id.text_category);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {
            Log.d(TAG, "ConvertView != null");
            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }
        // Get the current book's data in JSON form
        JSONObject jsonObject = (JSONObject) getItem(position);

        // If so, grab the Cover ID out from the object
        //String isbn = jsonObject.optString("isbn");
        // Construct the image URL (specific to API)
        //String imageURL = IMAGE_URL_BASE + isbn + "/" + isbn + ".jpg/record";

        // Use Picasso to load the image
        // Temporarily have a placeholder in case it's slow to load
        //Picasso.with(mContext).load(imageURL).into(holder.img_cat_logo);


        // Grab the title and author from the JSON
        String cat_name = "";

        if (jsonObject.has("cat_name")) {
            Log.d(TAG, "CAT NAME YO " + jsonObject.optString("cat_name"));
            cat_name = jsonObject.optString("cat_name");
        } else {
            Log.e(TAG, "NO CAT NAME WTF!?");
        }

        // Send these Strings to the TextViews for display
        holder.text_category.setText(cat_name);

        return convertView;
    }

    // this is used so you only ever have to do
// inflation and finding by ID once ever per View
    private static class ViewHolder {
        public ImageView img_cat_logo;
        public TextView text_category;
    }

    public void updateData(JSONArray jsonArray) {
        // update the adapter's dataset
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}