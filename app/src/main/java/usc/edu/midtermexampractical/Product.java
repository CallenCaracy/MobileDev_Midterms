package usc.edu.midtermexampractical;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
    private final String name;
    private final double price;
    private final String description;
    private final double rating;
    private final int img;
    private final String skinType;
    private final String category;

    public Product(String name, double price, String description, double rating, int img, String skinType, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.img = img;
        this.skinType = skinType;
        this.category = category;
    }

    protected Product(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        description = in.readString();
        rating = in.readDouble();
        img = in.readInt();
        skinType = in.readString();
        category = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getSkinType() {
        return skinType;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public int getImage() {
        return img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeDouble(rating);
        dest.writeInt(img);
        dest.writeString(skinType);
        dest.writeString(category);
    }
}

