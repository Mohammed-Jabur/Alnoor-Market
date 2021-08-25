package com.example.alnoorstore.ui.inventory;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.alnoorstore.model.Product;
import com.example.alnoorstore.model.ProductAdapter;
import com.example.alnoorstore.R;
import com.example.alnoorstore.ui.edit.EditProductActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class InventoryActivity extends AppCompatActivity implements InventoryContract.View{

    private View view;
    private ListView productLV;
    private ProductAdapter productAdapter;
    private InventoryPresenter inventoryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        productLV = findViewById(R.id.product_list_view);
        inventoryPresenter = new InventoryPresenter(this, new InventoryInteractor(getApplicationContext()));

        productLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(InventoryActivity.this, EditProductActivity.class);
                intent.putExtra("ProductNumber", productAdapter.getItem(i).getNumber());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        inventoryPresenter.getProducts();
    }

    @Override
    public void updateUI(List<Product> products) {
        productAdapter = new ProductAdapter(InventoryActivity.this, products);
        productLV.setAdapter(productAdapter);
    }
}