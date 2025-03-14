package usc.edu.midtermexampractical;

import java.io.Serializable;

public class Add_To_Cart implements Serializable {
    private final String name;
    private final double price;
    private final String desc;
    private final double rating;
    private final int qty;
    private final int imageResource;

    public Add_To_Cart(String name, double price, String desc, double rating, int qty, int imageResource) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.rating = rating;
        this.qty = qty;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public int getQty() {
        return qty;
    }

    public double getRating() {
        return rating;
    }

    public int getImageResource() {
        return imageResource;
    }
}
