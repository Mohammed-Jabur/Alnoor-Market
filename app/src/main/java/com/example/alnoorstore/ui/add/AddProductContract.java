package com.example.alnoorstore.ui.add;

import android.widget.ArrayAdapter;
import com.example.alnoorstore.model.Product;
import java.util.Map;

public interface AddProductContract {

    interface View{

        void setupSpinner(ArrayAdapter adapter);

        void startScanner();

        Map<String, String> getProductDetails();

        void resetUI();
    }

    interface Interactor{

        interface OnFinishedListener{

            void onFinished(long id);
        }

        void addProduct(AddProductContract.Interactor.OnFinishedListener onFinishedListener, Product product);
    }

    interface Presenter{

        void setupSpinner();

        void scanBarcode();

        void addProduct(Map<String, String> productDetails);
    }
}
