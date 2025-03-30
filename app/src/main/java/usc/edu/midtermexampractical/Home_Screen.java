package usc.edu.midtermexampractical;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import java.io.IOException;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class Home_Screen extends AppCompatActivity {
    private RecyclerView featuredRecyclerView, popularRecyclerView;
    private ProductAdapter featuredAdapter, popularAdapter;
    private List<Product> featuredProducts, popularProducts;
    private Button normalSkin, drySkin, oilySkin, combinationSkin, rankedCategory, hotCategory, lovedCategory, secretCategory;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Initialize RecyclerViews
        featuredRecyclerView = findViewById(R.id.featuredProductsList);
        popularRecyclerView = findViewById(R.id.popularProductsList);

        // Initialize filter buttons
        normalSkin = findViewById(R.id.normalSkin);
        drySkin = findViewById(R.id.drySkin);
        oilySkin = findViewById(R.id.oilySkin);
        combinationSkin = findViewById(R.id.combinationSkin);
        rankedCategory = findViewById(R.id.rankedCategory);
        hotCategory = findViewById(R.id.hotCategory);
        lovedCategory = findViewById(R.id.lovedCategory);
        secretCategory = findViewById(R.id.secretCategory);

        // Initialize product lists
        featuredProducts = new ArrayList<>();
        popularProducts = new ArrayList<>();

        // Populate featured products
        loadProductsFromJSON();

        // Set LayoutManagers for horizontal scrolling (or change to vertical if desired)
        featuredRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Create adapters with a click listener for product details
        featuredAdapter = new ProductAdapter(this, featuredProducts, product -> openProductDetails(product));
        popularAdapter = new ProductAdapter(this, popularProducts, product -> openProductDetails(product));

        // Set adapters
        featuredRecyclerView.setAdapter(featuredAdapter);
        popularRecyclerView.setAdapter(popularAdapter);

        // Filter button listeners
        normalSkin.setOnClickListener(view -> filterBySkinType("Normal"));
        drySkin.setOnClickListener(view -> filterBySkinType("Dry"));
        oilySkin.setOnClickListener(view -> filterBySkinType("Oily"));
        combinationSkin.setOnClickListener(view -> filterBySkinType("Combination"));
        rankedCategory.setOnClickListener(view -> filterByCategory("Ranked"));
        hotCategory.setOnClickListener(view -> filterByCategory("Hot"));
        lovedCategory.setOnClickListener(view -> filterByCategory("Loved"));
        secretCategory.setOnClickListener(view -> filterByCategory("Secret"));
    }

    private void openProductDetails(Product product) {
        Intent intent = new Intent(Home_Screen.this, Product_Details_Screen.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    private void filterBySkinType(String skinType) {
        List<Product> filteredFeaturedProducts = new ArrayList<>();
        List<Product> filteredPopularProducts = new ArrayList<>();

        for (Product product : featuredProducts) {
            if (product.getSkinType().equals(skinType) || skinType.equals("All")) {
                filteredFeaturedProducts.add(product);
            }
        }

        for (Product product : popularProducts) {
            if (product.getSkinType().equals(skinType) || skinType.equals("All")) {
                filteredPopularProducts.add(product);
            }
        }

        featuredAdapter.updateProductList(filteredFeaturedProducts);
        popularAdapter.updateProductList(filteredPopularProducts);

        Toast.makeText(Home_Screen.this, "Filtered by: " + skinType, Toast.LENGTH_SHORT).show();
    }

    private void filterByCategory(String category) {
        List<Product> filteredFeaturedProducts = new ArrayList<>();
        List<Product> filteredPopularProducts = new ArrayList<>();

        for (Product product : featuredProducts) {
            if (product.getCategory().equals(category)) {
                filteredFeaturedProducts.add(product);
            }
        }

        for (Product product : popularProducts) {
            if (product.getCategory().equals(category)) {
                filteredPopularProducts.add(product);
            }
        }

        featuredAdapter.updateProductList(filteredFeaturedProducts);
        popularAdapter.updateProductList(filteredPopularProducts);

        Toast.makeText(Home_Screen.this, "Filtered by Category: " + category, Toast.LENGTH_SHORT).show();
    }

    private String loadJSONFromAsset(String filename) {
        String json;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private void loadProductsFromJSON() {
        String json = loadJSONFromAsset("products.json");
        if (json == null) {
            Toast.makeText(this, "Failed to load JSON data", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray productsArray = jsonObject.getJSONArray("Products");

            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject productObj = productsArray.getJSONObject(i);

                String name = productObj.getString("name");
                double price = productObj.getDouble("price");
                String description = productObj.getString("description");
                double rating = productObj.getDouble("rating");
                String skinType = productObj.getString("skinType");
                String category = productObj.getString("category");

                int imageResId = getResources().getIdentifier(
                        productObj.getString("image").replace("R.drawable.", ""),
                        "drawable", getPackageName()
                );

                Product product = new Product(name, price, description, rating, imageResId, skinType, category);

                // Add to correct list
                if (category.equals("Hot") || category.equals("Loved")) {
                    featuredProducts.add(product);
                } else {
                    popularProducts.add(product);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }

}
