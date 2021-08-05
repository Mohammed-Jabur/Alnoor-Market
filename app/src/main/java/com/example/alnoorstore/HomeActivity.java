package com.example.alnoorstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    private MaterialButton addProductBtn, deleteProductBtn, viewProductBtn, viewInventoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addProductBtn = findViewById(R.id.add_product_btn);
        deleteProductBtn = findViewById(R.id.delete_product_btn);
        viewProductBtn = findViewById(R.id.view_product_btn);
        viewInventoryBtn = findViewById(R.id.view_all_product_btn);


        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        deleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewInventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}