package usc.edu.midtermexampractical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Product_Details_Screen extends AppCompatActivity {
    Button backBtn;
    Button addCartBtn;
    Button checkoutBtn;
    Button qtyAddBtn;
    Button qtyMinusBtn;
    TextView prodName;
    TextView prodDesc;
    TextView prodRating;
    TextView prodPrice;
    TextView qtyCounter;
    ImageView img;
    int count = 1;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backBtn = findViewById(R.id.buttonbacktohome);
        addCartBtn = findViewById(R.id.buttonaddtocart);
        checkoutBtn = findViewById(R.id.buttoncheckout);
        qtyAddBtn = findViewById(R.id.buttonadd);
        qtyMinusBtn = findViewById(R.id.buttonminus);
        prodName = findViewById(R.id.namePlace);
        prodDesc = findViewById(R.id.desc);
        prodRating = findViewById(R.id.prodRatingPlace);
        prodPrice = findViewById(R.id.prodPricePlace);
        qtyCounter = findViewById(R.id.qtyNum);
        img = findViewById(R.id.imgPlace);

        Product selectedProduct = getIntent().getParcelableExtra("product");

        if (selectedProduct != null) {
            prodName.setText("Product Name: " + selectedProduct.getName());
            prodDesc.setText("Product Description: " + selectedProduct.getDescription());
            prodRating.setText("Rating: " + String.format("%.1f", selectedProduct.getRating()));
            prodPrice.setText("P " + selectedProduct.getPrice());
            img.setImageResource(selectedProduct.getImage());
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHome = new Intent(Product_Details_Screen.this, Home_Screen.class);
                startActivity(backHome);
            }
        });

        qtyAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                qtyCounter.setText(String.valueOf(count));
            }
        });

        qtyMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count--;
                    qtyCounter.setText(String.valueOf(count));
                }
            }
        });

        addCartBtn.setOnClickListener(v -> {
            if (selectedProduct != null) {
                String prodNameStr = selectedProduct.getName();
                double price = selectedProduct.getPrice();
                int quantity = count;
                double prodRating = selectedProduct.getRating();
                int imgResource = selectedProduct.getImage();
                String descStr = selectedProduct.getDescription();

                if (count > 0) {
                    Add_To_Cart cartItem = new Add_To_Cart(prodNameStr, price, descStr, prodRating, quantity, imgResource);
                    Cart_Manager.getInstance().addToCart(cartItem);
                    Toast.makeText(Product_Details_Screen.this, "Added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Product_Details_Screen.this, "Please select at least one item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkout = new Intent(Product_Details_Screen.this, Check_Out_Screen.class);

                checkout.putExtra("prodName", prodName.getText().toString());
                checkout.putExtra("prodPrice", getIntent().getDoubleExtra("prodPrice", 0.0));
                checkout.putExtra("img", getIntent().getIntExtra("img", 0));
                checkout.putExtra("qty", count);

                startActivity(checkout);
            }
        });
    }
}