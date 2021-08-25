package com.example.alnoorstore.ui.search;

import android.content.Context;
import android.os.AsyncTask;
import com.example.alnoorstore.model.Product;
import com.example.alnoorstore.database.StoreDatabase;

public class FindProductInteractor implements FindProductContract.Interactor{

    private StoreDatabase storeDatabase;

    public FindProductInteractor(Context context){
        storeDatabase = StoreDatabase.getInstance(context);
    }

    @Override
    public void getProduct(OnFinishedListener onFinishedListener, String content) {
        new getProductAsyncTask(onFinishedListener).execute(content);
    }

    private class getProductAsyncTask extends AsyncTask<String, Void, Product> {

        private OnFinishedListener onFinishedListener;

        public getProductAsyncTask(OnFinishedListener onFinishedListener){
            this.onFinishedListener = onFinishedListener;
        }

        @Override
        protected Product doInBackground(String... strings) {
            if(strings[0].isEmpty()){
                return null;
            }
            return storeDatabase.productDao().getProduct("%" + strings[0] + "%");
        }

        @Override
        protected void onPostExecute(Product product) {
            super.onPostExecute(product);

            onFinishedListener.onFinished(product);
        }
    }
}
