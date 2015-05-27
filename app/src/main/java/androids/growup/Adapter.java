package androids.growup;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Created by Ameliehellners on 19/05/2015.
 */

public class Adapter {

    // TODO: Denna ska inte vara hr! Eller?
    public class JSONHelpers {

        private AssetManager manager;

        JSONHelpers(AssetManager assets) {
            this.manager = assets;
        }

        public List<Category> getAll() {
            InputStream source = null;
            try {
                source = manager.open("ourPlants.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            Reader reader = new InputStreamReader(source);
            Categories categories = gson.fromJson(reader, Categories.class);

            return categories.categories;
        }
    }

    public class PlantsAdapter extends BaseAdapter {

        Context context;
        protected List<Plant> listPlants;
        LayoutInflater inflater;

        public PlantsAdapter(Context context, List<Plant> listPlants) {
            this.listPlants = listPlants;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        public int getCount() {
            return listPlants.size();
        }

        public Plant getItem(int position) {
            return listPlants.get(position);
        }

        public long getItemId(int position) {
            return listPlants.get(position).plant_id;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                convertView = this.inflater.inflate(R.layout.row_categories, parent, false);
                holder.plant_img = (ImageView) convertView.findViewById(R.id.img_cat_logo);
                holder.plant_name = (TextView) convertView.findViewById(R.id.text_category);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Plant plant = listPlants.get(position);
            holder.plant_name.setText(plant.name);

            int img = context.getResources().getIdentifier(plant.plant_img, "drawable", context.getPackageName());
            holder.plant_img.setImageResource(img);

            return convertView;
        }

        private class ViewHolder {
            TextView plant_name;
            ImageView plant_img;
        }
    }

    public class CategoriesAdapter extends BaseAdapter {
        Context context;

        protected List<Category> listCategories;
        LayoutInflater inflater;

        public CategoriesAdapter(Context context, List<Category> listCategories) {
            this.listCategories = listCategories;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        public int getCount() {
            return listCategories.size();
        }

        public Category getItem(int position) {
            return listCategories.get(position);
        }

        public long getItemId(int position) {
            return listCategories.get(position).cat_id;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                convertView = this.inflater.inflate(R.layout.row_categories, parent, false);
                holder.img_cat_logo = (ImageView) convertView.findViewById(R.id.img_cat_logo);
                holder.text_category = (TextView) convertView.findViewById(R.id.text_category);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Category category = listCategories.get(position);
            holder.text_category.setText(category.cat_name);

            int img = context.getResources().getIdentifier(category.cat_img, "drawable", context.getPackageName());
            holder.img_cat_logo.setImageResource(img);

            return convertView;
        }

        private class ViewHolder {
            TextView text_category;
            ImageView img_cat_logo;
        }
    }
}