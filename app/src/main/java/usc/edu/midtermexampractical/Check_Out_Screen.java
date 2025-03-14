package usc.edu.midtermexampractical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class Check_Out_Screen extends AppCompatActivity {
    private ListView cartListView;
    private TextView totalPriceText;
    private Button checkoutButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_out_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cartListView = findViewById(R.id.checkoutList);
        totalPriceText = findViewById(R.id.totalCost);
        checkoutButton = findViewById(R.id.buttonpaynow);
        backButton = findViewById(R.id.buttonback);

        List<Add_To_Cart> cartItems = Cart_Manager.getInstance().getCartItems();
        CartAdapter adapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(adapter);
        updateTotal(cartItems);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Check_Out_Screen.this, Home_Screen.class);
                startActivity(back);
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkout = new Intent(Check_Out_Screen.this, Check_Out_Screen.class);

                Cart_Manager.getInstance().clearCart();
                updateTotal(Cart_Manager.getInstance().getCartItems());

                startActivity(checkout);
            }
        });
    }

    private void updateTotal(List<Add_To_Cart> cartItems) {
        double total = 0;
        for (Add_To_Cart item : cartItems) {
            total += item.getPrice() * item.getQty();
        }
        totalPriceText.setText(String.format("Total: PHP %.2f", total));
    }

}