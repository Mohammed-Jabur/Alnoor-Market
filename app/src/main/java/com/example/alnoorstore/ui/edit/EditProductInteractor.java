package com.example.alnoorstore.ui.edit;

import android.content.Context;
import android.os.AsyncTask;

import com.example.alnoorstore.R;
import com.example.alnoorstore.model.Product;
import com.example.alnoorstore.database.StoreDatabase;

public class EditProductInteractor implements EditProductContract.Interactor{

    private Context context;
    private StoreDatabase storeDatabase;

    public EditProductInteractor(Context context) {
        this.context = context;
        storeDatabase = StoreDatabase.getInstance(context);
    }

    @Override
    public void updateProduct(OnFinishedListener onFinishedListener, Product product) {

        new UpdateProductAsyncTask(onFinishedListener).execute(product);
    }

    @Override
    public void deleteProduct(OnFinishedListener onFinishedListener, Product product) {

        new DeleteProductAsyncTask(onFinishedListener).execute(product);
    }

    @Override
    public void getProduct(OnFinishedListener onFinishedListener, String productNumber) {

        new getProductAsyncTask(onFinishedListener).execute(productNumber);
    }

    private class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private OnFinishedListener onFinishedListener;

        public UpdateProductAsyncTask(OnFinishedListener onFinishedListener){
            this.onFinishedListener = onFinishedListener;
        }

        @Override
        protected Void doInBackground(Product... products) {

            storeDatabase.productDao().updateProduct(products[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            onFinishedListener.onFinished(context.getResources().getString(R.string.update_state));
        }
    }

    private class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private OnFinishedListener onFinishedListener;

        public DeleteProductAsyncTask(OnFinishedListener onFinishedListener){
            this.onFinishedListener = onFinishedListener;
        }

        @Override
        protected Void doInBackground(Product... products) {

            storeDatabase.productDao().deleteProduct(products[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            onFinishedListener.onFinished(context.getResources().getString(R.string.delete_state));
        }
    }

    private class getProductAsyncTask extends AsyncTask<String, Void, Product>{

        private OnFinishedListener onFinishedListener;

        public getProductAsyncTask(OnFinishedListener onFinishedListener){
            this.onFinishedListener = onFinishedListener;
        }

        @Override
        protected Product doInBackground(String... strings) {

            return storeDatabase.productDao().getProduct(strings[0]);
        }

        @Override
        protected void onPostExecute(Product product) {
            super.onPostExecute(product);

            onFinishedListener.onFinished(product);
        }
    }
}
