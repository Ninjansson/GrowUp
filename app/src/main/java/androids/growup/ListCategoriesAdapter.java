package androids.growup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ameliehellners on 19/05/2015.
 */
public class ListCategoriesAdapter extends BaseAdapter {
    Context context;

    protected List<Category> listCategories;
    LayoutInflater inflater;

    public ListCategoriesAdapter(Context context, List<Category> listCategories) {
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
        return listCategories.get(position).getCat_img();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.row_categories,
                    parent, false);

            holder.img_cat_logo = (ImageView) convertView.findViewById(R.id.img_cat_logo);
            holder.text_category = (TextView) convertView.findViewById(R.id.text_category);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Category category = listCategories.get(position);
        holder.text_category.setText(category.getCat_name());
        holder.img_cat_logo.setImageResource(category.getCat_img());

        return convertView;
    }

    private class ViewHolder {
        TextView text_category;
        ImageView img_cat_logo;
    }

}