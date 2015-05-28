package androids.growup;

import android.content.res.AssetManager;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import androids.growup.gson.Categories;
import androids.growup.gson.Category;

/**
 * Handles all output from our json file.
 * Well, it should anyway...
 * TODO: Make sure it does you ignorant bitch!
 */
public class JSONHelpers {

    public AssetManager manager;
    public JSONHelpers(AssetManager assets) {
        this.manager = assets;
    }

    // Returns a list from our json file with all the categories.
    public List<Category> getAllCategories() {
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