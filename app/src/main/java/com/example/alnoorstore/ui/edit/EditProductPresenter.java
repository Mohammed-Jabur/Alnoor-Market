package com.example.alnoorstore.ui.edit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.alnoorstore.model.Product;
import com.example.alnoorstore.R;
import java.util.HashMap;
import java.util.Map;
import androidx.core.app.ActivityCompat;

public class EditProductPresenter implements EditProductContract.Presenter, EditProductContract.Interactor.OnFinishedListener{

    private static final int REQUEST_CAMERA_PERMISSION = 201;

    private EditProductContract.View editProductView;
    private EditProductContract.Interactor editProductInteractor;
    private Product product;

    public EditProductPresenter(EditProductContract.View editProductView, EditProductContract.Interactor editProductInteractor){
        this.editProductView = editProductView;
        this.editProductInteractor = editProductInteractor;
    }

    @Override
    public void onFinished(String stateMessage) {
        Toast.makeText(((EditProductActivity) editProductView),
                (stateMessage.equals("Updated"))? R.string.update_product_message : R.string.delete_product_message,
                Toast.LENGTH_SHORT).show();

        product = null;

        editProductView.finishUI();
    }

    @Override
    public void onFinished(Product product) {
        this.product = product;

        Map<String, String> productDetails = new HashMap<>();
        productDetails.put("productNumber", product.getNumber());
        productDetails.put("productName", product.getName());
        productDetails.put("productPrice", String.valueOf(product.getPrice()));
        productDetails.put("productCategory", product.getCategory());

        int i = 0;
        for(String category : ((EditProductActivity) editProductView).getResources().getStringArray(R.array.array_category_options)){
            if(product.getCategory().equals(category)){
                productDetails.put("categorySelection", String.valueOf(i));
                break;
            }
            i++;
        }

        editProductView.updateUI(productDetails);
    }

    @Override
    public void setupSpinner() {
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(((EditProductActivity) editProductView),
                R.array.array_category_options, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        editProductView.setupSpinner(categoryAdapter);
    }

    @Override
    public void scanBarcode() {
        if (ActivityCompat.checkSelfPermission(((EditProductActivity) editProductView), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            editProductView.startScanner();
        } else {
            ActivityCompat.requestPermissions(((EditProductActivity) editProductView), new
                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void updateProduct(Map<String, String> productDetails) {
        if(product != null){
            Product editedProduct = new Product(product.getId(),
                    productDetails.get("productNumber"),
                    productDetails.get("productName"),
                    Float.parseFloat(productDetails.get("productPrice")),
                    productDetails.get("productCategory"));

            editProductInteractor.updateProduct(this, editedProduct);
        }else{
            Toast.makeText(((EditProductActivity)editProductView),
                    R.string.not_selected_product_message, Toast.LENGTH_SHORT).show();
            editProductView.resetUI();
        }
    }

    @Override
    public void deleteProduct() {
        if(product != null){
            editProductInteractor.deleteProduct(this, product);
        }else{
            Toast.makeText(((EditProductActivity)editProductView),
                    R.string.not_selected_product_message, Toast.LENGTH_SHORT).show();
            editProductView.resetUI();
        }
    }

    @Override
    public void getProduct(String productNumber) {
        editProductInteractor.getProduct(this, productNumber);
    }

}
