package androids.growup;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class InspoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspo);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));


    }

    private class MyAdapter extends BaseAdapter {
        private List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);

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
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_inspo, menu);
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
                case R.id.menu_home:
                    startActivity(new Intent(this, MainActivity.class));
                    return true;
                case R.id.menu_my_page:
                    startActivity(new Intent(this, MyPageActivity.class));
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
     }


