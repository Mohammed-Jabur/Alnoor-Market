package com.example.alnoorstore.ui.add;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.alnoorstore.model.Product;
import com.example.alnoorstore.R;
import java.util.Map;
import androidx.core.app.ActivityCompat;

public class AddProductPresenter implements AddProductContract.Presenter, AddProductContract.Interactor.OnFinishedListener{

    private static final int REQUEST_CAMERA_PERMISSION = 201;

    private AddProductContract.View addProductView;
    private AddProductContract.Interactor addProductInteractor;

    public AddProductPresenter(AddProductContract.View addProductView, AddProductContract.Interactor addProductInteractor){
        this.addProductView = addProductView;
        this.addProductInteractor = addProductInteractor;
    }

    @Override
    public void onFinished(long id) {

        if(id == -1){
            Toast.makeText(((AddProductActivity)addProductView),
                    R.string.exist_product_message, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(((AddProductActivity) addProductView),
                    R.string.add_product_message, Toast.LENGTH_SHORT).show();
        }

        addProductView.resetUI();
    }

    @Override
    public void addProduct(Map<String, String> productDetails) {
        if(isValidEntity(productDetails)) {
            Product newProduct = new Product(0,
                    productDetails.get("productNumber"),
                    productDetails.get("productName"),
                    Float.parseFloat(productDetails.get("productPrice")),
                    productDetails.get("productCategory"));

            addProductInteractor.addProduct(this, newProduct);
        }else{
            Toast.makeText(((AddProductActivity) addProductView),
                    R.string.empty_field_message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setupSpinner() {
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(((AddProductActivity)addProductView),
                R.array.array_category_options, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        addProductView.setupSpinner(categoryAdapter);
    }

    @Override
    public void scanBarcode() {
        if (ActivityCompat.checkSelfPermission(((AddProductActivity)addProductView), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            addProductView.startScanner();
        }else{
            ActivityCompat.requestPermissions(((AddProductActivity)addProductView), new
                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    private boolean isValidEntity(Map<String, String> productDetails) {
        if(productDetails.get("productNumber").isEmpty()){
            return false;
        }
        if(productDetails.get("productName").isEmpty()){
            return false;
        }
        if(productDetails.get("productPrice").isEmpty()){
            return false;
        }
        if(productDetails.get("productCategory").isEmpty()){
            return false;
        }

        return true;
    }

}
