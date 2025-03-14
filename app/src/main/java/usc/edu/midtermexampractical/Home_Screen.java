package usc.edu.midtermexampractical;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Home_Screen extends AppCompatActivity {
    private ListView featuredListView, popularListView;
    private ProductAdapter featuredAdapter, popularAdapter;
    private List<Product> featuredProducts, popularProducts;
    private Button normalSkin, drySkin, oilySkin, combinationSkin, rankedCategory, hotCategory, lovedCategory, secretCategory;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        featuredListView = findViewById(R.id.featuredProductsList);
        popularListView = findViewById(R.id.popularProductsList);
        normalSkin = findViewById(R.id.normalSkin);
        drySkin = findViewById(R.id.drySkin);
        oilySkin = findViewById(R.id.oilySkin);
        combinationSkin = findViewById(R.id.combinationSkin);
        rankedCategory = findViewById(R.id.rankedCategory);
        hotCategory = findViewById(R.id.hotCategory);
        lovedCategory = findViewById(R.id.lovedCategory);
        secretCategory = findViewById(R.id.secretCategory);

        featuredProducts = new ArrayList<>();
        popularProducts = new ArrayList<>();

        featuredProducts.add(new Product("Moisturizer", 180, "This moisturizes your skin", 4.5, R.drawable.moisturizer, "Dry", "Hot"));
        featuredProducts.add(new Product("Cleanser", 349, "Cleanses your delicate skin", 4.2, R.drawable.cleanser, "Oily", "Ranked"));
        featuredProducts.add(new Product("Face Cream", 220, "Light cream for your skin", 4.0, R.drawable.face_cream, "Normal", "Loved"));
        featuredProducts.add(new Product("Hydrating Gel", 280, "Hydrates and balances your skin", 4.6, R.drawable.hydrating_gel, "Combination", "Secret"));

        popularProducts.add(new Product("Foundation", 139, "This foundation is for you and your skin", 4.8, R.drawable.foundation, "Dry", "Ranked"));
        popularProducts.add(new Product("Lipstick", 200, "Your lips deserves more", 4.6, R.drawable.lipstick, "Normal", "Loved"));
        popularProducts.add(new Product("Toner", 350, "Gentle toner for soft skin", 4.3, R.drawable.toner, "Oily", "Hot"));
        popularProducts.add(new Product("Serum", 400, "Deep hydrating serum", 4.7, R.drawable.serum, "Combination", "Secret"));

        featuredAdapter = new ProductAdapter(this, featuredProducts);
        popularAdapter = new ProductAdapter(this, popularProducts);

        featuredListView.setAdapter(featuredAdapter);
        popularListView.setAdapter(popularAdapter);

        normalSkin.setOnClickListener(view -> filterBySkinType("Normal"));
        drySkin.setOnClickListener(view -> filterBySkinType("Dry"));
        oilySkin.setOnClickListener(view -> filterBySkinType("Oily"));
        combinationSkin.setOnClickListener(view -> filterBySkinType("Combination"));

        rankedCategory.setOnClickListener(view -> filterByCategory("Ranked"));
        hotCategory.setOnClickListener(view -> filterByCategory("Hot"));
        lovedCategory.setOnClickListener(view -> filterByCategory("Loved"));
        secretCategory.setOnClickListener(view -> filterByCategory("Secret"));

        featuredListView.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = featuredProducts.get(position);
            Intent intent = new Intent(Home_Screen.this, Product_Details_Screen.class);
            intent.putExtra("product", selectedProduct);
            startActivity(intent);
        });

        popularListView.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = popularProducts.get(position);
            Intent intent = new Intent(Home_Screen.this, Product_Details_Screen.class);
            intent.putExtra("product", selectedProduct);
            startActivity(intent);
        });


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
}