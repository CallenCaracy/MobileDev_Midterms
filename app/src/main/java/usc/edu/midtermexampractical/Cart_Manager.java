package usc.edu.midtermexampractical;

import java.util.ArrayList;
import java.util.List;

public class Cart_Manager {
    private static Cart_Manager instance;
    private List<Add_To_Cart> cartItems;;

    private Cart_Manager() {
        cartItems = new ArrayList<>();
    }
    public static Cart_Manager getInstance() {
        if (instance == null) {
            instance = new Cart_Manager();
        }
        return instance;
    }

    public void addToCart(Add_To_Cart item) {
        cartItems.add(item);
    }

    public List<Add_To_Cart> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Add_To_Cart item : cartItems) {
            total += item.getPrice() * item.getQty(); // price * quantity
        }
        return total;
    }
}
