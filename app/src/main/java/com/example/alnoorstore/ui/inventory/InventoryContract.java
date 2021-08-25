package com.example.alnoorstore.ui.inventory;

import com.example.alnoorstore.model.Product;
import java.util.List;

public interface InventoryContract {

    interface View{

        void updateUI(List<Product> products);
    }

    interface Interactor{

        interface OnFinishedListener{

            void onFinished(List<Product> products);
        }

        void getProducts(InventoryContract.Interactor.OnFinishedListener onFinishedListener);
    }

    interface Presenter{

        void getProducts();
    }
}
