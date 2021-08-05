package com.example.alnoorstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    EditText numberET, nameET, priceET, categoryET;
    Button addBtn, updateBtn, deleteBtn, viewBtn;
    ListView listView;
    StoreDatabase storeDatabase;

    ArrayAdapter adapter;
    ArrayList<Product> products;
    ArrayList<String> items;

    ToneGenerator toneGenerator;
    //BarcodeDetector barcodeDetector;
    TextView barcodeTV;
    ImageButton btnCamera;
    //SurfaceView surfaceView;
    /*CameraSource cameraSource;
    private String barcodeData;*/

    private static final int REQUEST_CAMERA_PERMISSION = 201;

    ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberET = findViewById(R.id.number_et);
        nameET = findViewById(R.id.name_et);
        priceET = findViewById(R.id.price_et);
        categoryET = findViewById(R.id.category_et);

        addBtn = findViewById(R.id.add_btn_1);
        updateBtn = findViewById(R.id.update_btn_1);
        deleteBtn = findViewById(R.id.delete_btn_1);
        viewBtn = findViewById(R.id.view_btn_1);

        listView = findViewById(R.id.list);

        ///////////////////////////////////////////////////////////////////

        barcodeTV = findViewById(R.id.barcode_tv);
        btnCamera = findViewById(R.id.camera_btn);
        //surfaceView = findViewById(R.id.surface_view);
        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);

        scannerView = findViewById(R.id.scanner_view);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*surfaceView.setVisibility(View.VISIBLE);
                initialiseCamera();*/

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    scannerView.setVisibility(View.VISIBLE);
                    scannerView.setResultHandler(MainActivity.this);
                    scannerView.startCamera();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new
                            String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }

            }
        });

        ///////////////////////////////////////////////////////////////////


        storeDatabase = StoreDatabase.getInstance(getApplicationContext());

        products = new ArrayList<>();
        items = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                //view.setActivated(true);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AddProductAsyncTask().execute(new Product(0,
                        numberET.getText().toString(),
                        nameET.getText().toString(),
                        Integer.parseInt(priceET.getText().toString()),
                        categoryET.getText().toString()));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, products.get(listView.getCheckedItemPosition()).getId() + "", Toast.LENGTH_SHORT).show();

                new UpdateProductAsyncTask().execute(new Product(products.get(listView.getCheckedItemPosition()).getId(),
                        numberET.getText().toString(),
                        nameET.getText().toString(),
                        Integer.parseInt(priceET.getText().toString()),
                        categoryET.getText().toString())
                );
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //listView.getSelectedItemPosition();

                new DeleteProductAsyncTask().execute(/*new Product(0,
                        numberET.getText().toString(),
                        nameET.getText().toString(),
                        Integer.parseInt(priceET.getText().toString()),
                        categoryET.getText().toString())*/
                        products.get(listView.getCheckedItemPosition()));

                //Toast.makeText(MainActivity.this, listView.getCheckedItemPosition() + "", Toast.LENGTH_SHORT).show();
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ViewProductAsyncTask().execute();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //cameraSource.release();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 300);
        barcodeTV.setText(result.getText());
        scannerView.stopCamera();
        scannerView.setVisibility(View.GONE);
    }

    private class AddProductAsyncTask extends AsyncTask<Product, Void, Void> {

        @Override
        protected Void doInBackground(Product... products) {

            storeDatabase.productDao().insertProduct(products[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void> {

        @Override
        protected Void doInBackground(Product... products) {

            storeDatabase.productDao().updateProduct(products[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void> {

        @Override
        protected Void doInBackground(Product... products) {

            storeDatabase.productDao().deleteProduct(products[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private class ViewProductAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            //products.addAll((ArrayList) storeDatabase.productDao().getProductList());

            items.clear();

            products = (ArrayList<Product>) storeDatabase.productDao().getProductList();

            for (Product product : products) {
                String item = product.getId() + "  " + product.getNumber() + "  " + product.getName()
                        + "  " + product.getPrice() + "  " + product.getCategory();

                items.add(item);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapter.notifyDataSetChanged();
        }

    }

    /*public void initialiseCamera(){

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(500, 500)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                cameraSource.stop();
                //surfaceView.setVisibility(View.GONE);
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {

                    barcodeData = barcodes.valueAt(0).displayValue;
                    barcodeTV.setText(barcodeData);
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 300);

                    surfaceView.setVisibility(View.GONE);
                    cameraSource.release();

                    *//*barcodeTV.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeTV.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeTV.setText(barcodeData);
                                toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {
                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeTV.setText(barcodeData);
                                toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            }
                        }
                    });*//*

                }
            }
        });
    }*/
}