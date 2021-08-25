package com.example.alnoorstore.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.alnoorstore.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;

    public ProductAdapter(@NonNull Context context, @NonNull List<Product> products) {
        super(context, 0, products);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_product, null);
        }

        Product product = getItem(position);

        TextView numberTV = (TextView) listItem.findViewById(R.id.product_number_tv_inv);
        numberTV.setText(product.getNumber());

        TextView nameTV = (TextView) listItem.findViewById(R.id.product_name_tv_inv);
        nameTV.setText(product.getName());

        TextView priceTV = (TextView) listItem.findViewById(R.id.product_price_tv_inv);
        priceTV.setText(String.valueOf(product.getPrice()));

        TextView categoryTV = (TextView) listItem.findViewById(R.id.product_category_tv_inv);
        categoryTV.setText(product.getCategory());

        return listItem;
    }
}
