package com.example.alnoorstore.ui.search;

import android.Manifest;
import android.content.pm.PackageManager;

import com.example.alnoorstore.R;
import com.example.alnoorstore.model.Product;
import java.util.HashMap;
import java.util.Map;
import androidx.core.app.ActivityCompat;

public class FindProductPresenter implements FindProductContract.Presenter, FindProductContract.Interactor.OnFinishedListener{

    private static final int REQUEST_CAMERA_PERMISSION = 201;

    private FindProductContract.View findProductView;
    private  FindProductContract.Interactor findProductInteractor;

    public FindProductPresenter(FindProductContract.View findProductView, FindProductContract.Interactor findProductInteractor) {
        this.findProductView = findProductView;
        this.findProductInteractor = findProductInteractor;
    }

    @Override
    public void onFinished(Product product) {
        if(product == null) {
            findProductView.hideProductCard(((FindProductActivity)findProductView).getResources().getString(R.string.not_found_product));
        }else {
            Map<String, String> productDetails = new HashMap<>();
            productDetails.put("productNumber", product.getNumber());
            productDetails.put("productName", product.getName());
            productDetails.put("productPrice", String.valueOf(product.getPrice()));
            productDetails.put("productCategory", product.getCategory());

            findProductView.showProductCard(productDetails);
        }
    }

    @Override
    public void scanBarcode() {
        if (ActivityCompat.checkSelfPermission(((FindProductActivity) findProductView), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            findProductView.startScanner();
        } else {
            ActivityCompat.requestPermissions(((FindProductActivity) findProductView),
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void getProduct(String content) {
        findProductInteractor.getProduct(this, content);
    }
}
