package com.example.alnoorstore.ui.add;

import android.content.Context;
import android.os.AsyncTask;
import com.example.alnoorstore.model.Product;
import com.example.alnoorstore.database.StoreDatabase;

public class AddProductInteractor implements AddProductContract.Interactor{

    private StoreDatabase storeDatabase;

    public AddProductInteractor(Context context) {
        storeDatabase = StoreDatabase.getInstance(context);
    }

    @Override
    public void addProduct(OnFinishedListener onFinishedListener, Product product) {

        new AddProductAsyncTask(onFinishedListener).execute(product);
    }

    private class AddProductAsyncTask extends AsyncTask<Product, Void, Long> {

        private OnFinishedListener onFinishedListener;

        public AddProductAsyncTask(OnFinishedListener onFinishedListener){
            this.onFinishedListener = onFinishedListener;
        }

        @Override
        protected Long doInBackground(Product... products) {

            return storeDatabase.productDao().insertProduct(products[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            super.onPostExecute(id);

            onFinishedListener.onFinished(id);
        }

    }

}
