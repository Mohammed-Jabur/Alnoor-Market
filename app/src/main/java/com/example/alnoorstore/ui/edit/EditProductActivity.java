package com.example.alnoorstore.ui.edit;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import com.example.alnoorstore.R;
import com.google.zxing.Result;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class EditProductActivity extends AppCompatActivity implements EditProductContract.View, ZXingScannerView.ResultHandler{

    private EditText numberET, nameET, priceET;
    private ImageButton scanBtn;
    private Spinner categorySP;
    private String selectedCategory;
    private ToneGenerator toneGenerator;
    private RelativeLayout scannerContainer;
    private ImageView cancelImg;
    private ZXingScannerView scannerView;
    private EditProductContract.Presenter editProductPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Intent intent = getIntent();
        String productNumber = intent.getStringExtra("ProductNumber");

        numberET = findViewById(R.id.product_number_et);
        nameET = findViewById(R.id.product_name_et);
        priceET = findViewById(R.id.product_price_et);
        scanBtn = findViewById(R.id.scan_btn);
        categorySP = findViewById(R.id.product_category_sp);
        scannerContainer = findViewById(R.id.scanner_view_container);
        scannerView = findViewById(R.id.qr_scanner_view);
        cancelImg = findViewById(R.id.cancel_img);
        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);

        editProductPresenter = new EditProductPresenter(this, new EditProductInteractor(getApplicationContext()));

        editProductPresenter.setupSpinner();

        if(productNumber != null){
            editProductPresenter.getProduct(productNumber);
        }

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProductPresenter.scanBarcode();
            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scannerView.stopCamera();
                scannerContainer.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
        scannerContainer.setVisibility(View.GONE);
    }

    @Override
    public void handleResult(Result result) {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 300);
        editProductPresenter.getProduct(result.getText());
        numberET.setText(result.getText());
        scannerView.stopCamera();
        scannerContainer.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_update:
                editProductPresenter.updateProduct(getProductDetails());
                return true;
            case R.id.action_delete:
                editProductPresenter.deleteProduct();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void resetUI() {
        numberET.setText("");
        nameET.setText("");
        priceET.setText("");
        categorySP.setSelection(0);
    }

    @Override
    public void finishUI() {
        finish();
    }

    @Override
    public void updateUI(Map<String, String> productDetails) {
            numberET.setText(productDetails.get("productNumber"));
            nameET.setText(productDetails.get("productName"));
            priceET.setText(productDetails.get("productPrice"));
            selectedCategory = productDetails.get("productCategory");
            categorySP.setSelection(Integer.parseInt(productDetails.get("categorySelection")));
    }

    @Override
    public void setupSpinner(ArrayAdapter adapter) {
        categorySP.setAdapter(adapter);
        categorySP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = (String) parent.getItemAtPosition(position);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = (String) parent.getItemAtPosition(0);
            }
        });
    }

    @Override
    public Map<String, String> getProductDetails() {
        String productNumber = numberET.getText().toString();
        String productName = nameET.getText().toString();
        String productPrice = priceET.getText().toString();
        String productCategory = selectedCategory;

        Map<String, String> productDetails = new HashMap<>();
        productDetails.put("productNumber", productNumber);
        productDetails.put("productName", productName);
        productDetails.put("productPrice", productPrice);
        productDetails.put("productCategory", productCategory);

        return productDetails;
    }

    @Override
    public void startScanner() {
        scannerContainer.setVisibility(View.VISIBLE);
        scannerView.setResultHandler(EditProductActivity.this);
        scannerView.startCamera();
    }
}