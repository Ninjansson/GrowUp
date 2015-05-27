package androids.growup;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ameliehellners on 19/05/2015.
 */
public class Category {

    @SerializedName("id")
    public int cat_id;

    public String cat_name;

    public String cat_img;

    public List<Plant> plants;
}