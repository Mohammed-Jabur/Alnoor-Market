package com.example.alnoorstore.ui.search;

import com.example.alnoorstore.model.Product;
import java.util.Map;

public interface FindProductContract {

    interface View{

       void startScanner();

       void showProductCard(Map<String, String> productDetails);

       void hideProductCard(String message);
    }

    interface Interactor{

        interface OnFinishedListener{

            void onFinished(Product product);
        }

        void getProduct(FindProductContract.Interactor.OnFinishedListener onFinishedListener, String content);
    }

    interface Presenter{

        void scanBarcode();

        void getProduct(String content);
    }
}
