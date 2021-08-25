package com.example.alnoorstore.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.alnoorstore.R;
import com.example.alnoorstore.ui.edit.EditProductActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.zxing.Result;
import java.util.Map;

public class FindProductActivity extends AppCompatActivity implements FindProductContract.View, ZXingScannerView.ResultHandler{

    private EditText numberET;
    private ImageButton scanBtn;
    private Button searchBtn;
    private TextView numberTV, nameTV, priceTV, categoryTV;
    private MaterialCardView productCard;
    private RelativeLayout scannerContainer;
    private ZXingScannerView scannerView;
    private ImageView cancelImg;
    private ToneGenerator toneGenerator;
    private InputMethodManager inputMethodManager;

    private FindProductPresenter viewProductPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_product);

        numberET = findViewById(R.id.product_number_et_s);
        scanBtn = findViewById(R.id.scan_btn_s);
        searchBtn = findViewById(R.id.search_btn);
        numberTV = findViewById(R.id.product_number_tv);
        nameTV = findViewById(R.id.product_name_tv);
        priceTV = findViewById(R.id.product_price_tv);
        categoryTV = findViewById(R.id.product_category_tv);
        productCard = findViewById(R.id.product_card);
        scannerContainer = findViewById(R.id.scanner_view_container_s);
        scannerView = findViewById(R.id.qr_scanner_view_s);
        cancelImg = findViewById(R.id.cancel_img_s);

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
        inputMethodManager = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        viewProductPresenter = new FindProductPresenter(this, new FindProductInteractor(getApplicationContext()));

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewProductPresenter.scanBarcode();
            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scannerView.stopCamera();
                scannerContainer.setVisibility(View.GONE);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputMethodManager.hideSoftInputFromWindow(numberET.getWindowToken(), 0);
                viewProductPresenter.getProduct(numberET.getText().toString().trim());
            }
        });

        productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate();
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
    protected void onStart() {
        super.onStart();
        if(!(numberTV.getText().toString().isEmpty())) {
            viewProductPresenter.getProduct(numberTV.getText().toString());
        }
    }

    @Override
    public void handleResult(Result result) {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 300);
        viewProductPresenter.getProduct(result.getText());
        numberET.setText(result.getText());
        scannerView.stopCamera();
        scannerContainer.setVisibility(View.GONE);
    }

    @Override
    public void startScanner() {
        scannerContainer.setVisibility(View.VISIBLE);
        scannerView.setResultHandler(FindProductActivity.this);
        scannerView.startCamera();
    }

    @Override
    public void showProductCard(Map<String, String> productDetails) {
        productCard.setVisibility(View.VISIBLE);

        numberTV.setText(productDetails.get("productNumber"));
        nameTV.setText(productDetails.get("productName"));
        priceTV.setText(productDetails.get("productPrice"));
        categoryTV.setText(productDetails.get("productCategory"));
    }

    @Override
    public void hideProductCard(String message) {
        productCard.setVisibility(View.GONE);
        Toast.makeText(FindProductActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigate() {
        Intent intent = new Intent(FindProductActivity.this, EditProductActivity.class);
        intent.putExtra("ProductNumber", numberTV.getText());
        startActivity(intent);
    }
}
