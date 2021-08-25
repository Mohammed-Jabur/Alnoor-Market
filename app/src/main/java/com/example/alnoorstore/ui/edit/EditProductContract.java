package com.example.alnoorstore.ui.edit;

import android.widget.ArrayAdapter;
import com.example.alnoorstore.model.Product;
import java.util.Map;

public interface EditProductContract {

    interface View{

        void setupSpinner(ArrayAdapter adapter);

        void startScanner();

        void updateUI(Map<String, String> productDetails);

        Map<String, String> getProductDetails();

        void resetUI();

        void finishUI();
    }

    interface Interactor{

        interface OnFinishedListener{

            void onFinished(String stateMessage);

            void onFinished(Product product);
        }

        void updateProduct(OnFinishedListener onFinishedListener, Product product);

        void deleteProduct(OnFinishedListener onFinishedListener, Product product);

        void getProduct(OnFinishedListener onFinishedListener, String productNumber);
    }

    interface Presenter{

        void setupSpinner();

        void scanBarcode();

        void updateProduct(Map<String, String> productDetails);

        void deleteProduct();

        void getProduct(String productNumber);
    }
}
