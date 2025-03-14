package usc.edu.midtermexampractical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<Add_To_Cart> cartItems;

    public CartAdapter(Context context, List<Add_To_Cart> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item_checkout, parent, false);

            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.prodName);
            holder.productPrice = convertView.findViewById(R.id.prodPrice);
            holder.productQty = convertView.findViewById(R.id.qty);
            holder.productImage = convertView.findViewById(R.id.imgPlace);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Add_To_Cart item = cartItems.get(position);

        holder.productName.setText(item.getName());
        String formattedPrice = String.format("P %.2f", item.getPrice());
        holder.productPrice.setText(formattedPrice);
        holder.productQty.setText("Qty: " + item.getQty());
        holder.productImage.setImageResource(item.getImageResource());

        return convertView;
    }

    static class ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productQty;
        ImageView productImage;
    }
}
