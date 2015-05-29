package androids.growup.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kim.jansson on 2015-05-26.
 */
public class Plant implements Serializable {
    @SerializedName("id")
    public int plant_id;

    public String name;

    public String illustration_img;

    public String plant_img;

    public String latin_name;

    public String info;

    public String how_to;

    public String plant_usage;

    public int difficulty;

    public String link;

    public String good_to_know;

    public String harvest;
}
