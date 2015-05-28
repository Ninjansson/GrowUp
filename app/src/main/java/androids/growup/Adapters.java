package androids.growup;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androids.growup.gson.Category;
import androids.growup.gson.Plant;

/**
 * Class to handle everything that has anything to do with adapters
 */
public class Adapters {

    /**
     * Populates the list under every individual category page with their respective plants
     */
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

    /**
     * Populates the listview in MainActivity.java with categories
     */
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

    public class MyPlantsAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater mInflater;
        JSONArray mJsonArray;

        public MyPlantsAdapter(Context context, LayoutInflater inflater) {
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
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder holder;

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

            String name = "";

            if (jsonObject.has("my_name")) {
                name = jsonObject.optString("my_name");
            }

            holder.my_plant_name.setText(name);
            holder.plant_id_tw.setText(String.valueOf(jsonObject.optInt("plant_id")));
            holder.plant_id_tw.setVisibility(View.INVISIBLE);

            holder.button_delete.setOnClickListener(new View.OnClickListener() {
                // TODO: Fix this!
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    JSONObject myList = getMainObjectFromMyList();
                    JSONArray plantArray = null;
                    try {
                        plantArray = myList.getJSONArray("myPlants");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    plantArray.remove(position);

                    Context context = mContext;
                    String filePath = context.getFilesDir().getPath().toString() + "/mylist";
                    File file = new File(filePath);

                    file.delete();
                    try {
                        file.createNewFile();

                        JSONObject mainObject = new JSONObject();
                        mainObject.put("myPlants", plantArray);
                        FileOutputStream fos = new FileOutputStream(file, false);
                        fos.write(mainObject.toString().getBytes());
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateData(plantArray);
                    Toast.makeText(mContext, "Plantan raderad.", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        private JSONObject getMainObjectFromMyList() {
            Context context = mContext;
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

        // Updating the adapters dataset
        public void updateData(JSONArray jsonArray) {
            mJsonArray = jsonArray;
            notifyDataSetChanged();
        }

        // Class for holding data about the items from myList
        private class ViewHolder {
            public TextView my_plant_name, plant_id_tw;
            public Button button_delete;
        }
    }

    public class InspirationAdapter extends BaseAdapter {
        private List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public InspirationAdapter(Context context) {
            inflater = LayoutInflater.from(context);

<<<<<<< HEAD
            items.add(new Item("Plantera på höjden med pall", R.drawable.inspo01));
            items.add(new Item("Plantera vertikalt med krukor", R.drawable.inspo02));
            items.add(new Item("Plantera i skofickor", R.drawable.inspo03));
            items.add(new Item("Olda i zinkhinkar", R.drawable.inspo04));
            items.add(new Item("Fäst lådor på spaljé", R.drawable.inspo05));
            items.add(new Item("Upp och ned tomater", R.drawable.inspo06));
            items.add(new Item("Gro ny ingefära", R.drawable.inspo07));
            items.add(new Item("Så ditt eget avocadoträd", R.drawable.inspo08));
            items.add(new Item("Använd din annanasskrutt", R.drawable.inspo09));
            items.add(new Item("Örtmix i konservburkar", R.drawable.inspo10));
            items.add(new Item("Förkultivera i äggskal", R.drawable.inspo11));
            items.add(new Item("Gör drivhus av dina äggkartonger", R.drawable.inspo12));
            items.add(new Item("Gro om din lök", R.drawable.inspo13));
            items.add(new Item("Förnya morotsblasten", R.drawable.inspo14))
            ;items.add(new Item("Så tar du jordgubbsfrön", R.drawable.inspo15));
            items.add(new Item("Örthylla på vägg", R.drawable.inspo16));
=======
            items.add(new Item("Plantera p� h�jden med pall", R.drawable.inspo01));
            items.add(new Item("Plantera vertikalt med krukor", R.drawable.inspo02));
            items.add(new Item("Plantera i skofickor", R.drawable.inspo03));
            items.add(new Item("Odla i zinkhinkar", R.drawable.inspo04));
            items.add(new Item("F�st l�dor p� spalj�", R.drawable.inspo05));
            items.add(new Item("Upp och ned tomater", R.drawable.inspo06));
            items.add(new Item("Gro ny ingef�ra", R.drawable.inspo07));
            items.add(new Item("S� ditt eget avocadotr�d", R.drawable.inspo08));
            items.add(new Item("Anv�nd din annanasskrutt", R.drawable.inspo09));
            items.add(new Item("�rtmix i konservburkar", R.drawable.inspo10));
            items.add(new Item("F�rkultivera i �ggskal", R.drawable.inspo11));
            items.add(new Item("G�r drivhus av dina �ggkartonger", R.drawable.inspo12));
            items.add(new Item("Gro om din l�k", R.drawable.inspo13));
            items.add(new Item("F�rnya morotsblasten", R.drawable.inspo14))
            ;items.add(new Item("S� tar du jordgubbsfr�n", R.drawable.inspo15));
            items.add(new Item("�rthylla p� v�gg", R.drawable.inspo16));
>>>>>>> Ampelie
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            ImageView picture;
            TextView name;

            if (v == null) {
                v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView) v.getTag(R.id.picture);
            name = (TextView) v.getTag(R.id.text);
            Item item = (Item) getItem(i);
            picture.setImageResource(item.drawableId);
            name.setText(item.name);

            return v;
        }

        private class Item {
            final String name;
            final int drawableId;

            Item(String name, int drawableId) {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
    }
}