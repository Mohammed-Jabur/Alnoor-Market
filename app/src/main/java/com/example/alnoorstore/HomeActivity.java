package com.example.alnoorstore;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.example.alnoorstore.ui.add.AddProductActivity;
import com.example.alnoorstore.ui.edit.EditProductActivity;
import com.example.alnoorstore.ui.inventory.InventoryActivity;
import com.example.alnoorstore.ui.search.FindProductActivity;
import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    private MaterialButton addProductBtn, editProductBtn, findProductBtn, viewInventoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addProductBtn = findViewById(R.id.add_product_btn);
        editProductBtn = findViewById(R.id.edit_product_btn);
        findProductBtn = findViewById(R.id.find_product_btn);
        viewInventoryBtn = findViewById(R.id.inventory_btn);


        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setAlpha(0.4f);
                startActivity(new Intent(HomeActivity.this, AddProductActivity.class));
            }
        });

        editProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setAlpha(0.4f);
                startActivity(new Intent(HomeActivity.this, EditProductActivity.class));
            }
        });

        findProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setAlpha(0.4f);
                startActivity(new Intent(HomeActivity.this, FindProductActivity.class));
            }
        });

        viewInventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setAlpha(0.4f);
                startActivity(new Intent(HomeActivity.this, InventoryActivity.class));
            }
        });

        addProductBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                changeOpacity(view, motionEvent);
                return false;
            }
        });

        editProductBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                changeOpacity(view, motionEvent);
                return false;
            }
        });

        findProductBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                changeOpacity(view, motionEvent);
                return false;
            }
        });

        viewInventoryBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                changeOpacity(view, motionEvent);
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        addProductBtn.setAlpha(1.0f);
        editProductBtn.setAlpha(1.0f);
        findProductBtn.setAlpha(1.0f);
        viewInventoryBtn.setAlpha(1.0f);
        super.onStart();
    }

    private void changeOpacity(View view, MotionEvent motionEvent){
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setAlpha(0.4f);
                break;
            case MotionEvent.ACTION_UP:
                view.setAlpha(1.0f);
                break;
        }
    }
}