package com.example.alnoorstore.ui.inventory;

import android.content.Context;
import android.os.AsyncTask;
import com.example.alnoorstore.model.Product;
import com.example.alnoorstore.database.StoreDatabase;
import java.util.List;

public class InventoryInteractor implements InventoryContract.Interactor{

    private StoreDatabase storeDatabase;

    public InventoryInteractor(Context context){
        storeDatabase = StoreDatabase.getInstance(context);
    }

    @Override
    public void getProducts(OnFinishedListener onFinishedListener) {
        new getProductsAsyncTask(onFinishedListener).execute();
    }

    private class getProductsAsyncTask extends AsyncTask<Void, Void, List<Product>> {

        private OnFinishedListener onFinishedListener;

        public getProductsAsyncTask(OnFinishedListener onFinishedListener){
            this.onFinishedListener = onFinishedListener;
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {

            return storeDatabase.productDao().getProductList();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);

            onFinishedListener.onFinished(products);
        }
    }
}
