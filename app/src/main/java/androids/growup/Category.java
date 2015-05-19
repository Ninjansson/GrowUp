package androids.growup;

/**
 * Created by Ameliehellners on 19/05/2015.
 */
public class Category {
    private int cat_id;
    private int cat_img;
    private String cat_name;

    Category(int cat_id, int cat_img, String cat_name) {
        super();
        this.cat_id = cat_id;
        this.cat_img = cat_img;
        this.cat_name = cat_name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public int getCat_img(){
        return cat_img;
    }

    public String getCat_name() {
        return cat_name;
    }
}