package usc.edu.midtermexampractical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View adapter, ViewGroup parent) {
        if (adapter == null) {
            adapter = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        Product product = productList.get(position);

        ImageView productImage = adapter.findViewById(R.id.product_image);
        TextView productName = adapter.findViewById(R.id.product_name);
        TextView productPrice = adapter.findViewById(R.id.product_price);
        TextView productRating = adapter.findViewById(R.id.product_rating);
        TextView productCategory = adapter.findViewById(R.id.product_category);
        TextView productSkinType = adapter.findViewById(R.id.product_skin_type);

        productName.setText(product.getName());
        productPrice.setText("P" + product.getPrice());
        productRating.setText(String.valueOf(product.getRating()));
        productImage.setImageResource(product.getImage());
        productCategory.setText(product.getCategory());
        productSkinType.setText(product.getSkinType());

        return adapter;
    }

    public void updateProductList(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged();
    }
}
