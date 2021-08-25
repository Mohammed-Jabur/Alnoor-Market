package com.example.alnoorstore.ui.inventory;

import com.example.alnoorstore.model.Product;
import java.util.List;

public class InventoryPresenter implements InventoryContract.Presenter, InventoryContract.Interactor.OnFinishedListener{

    private InventoryContract.View inventoryView;
    private InventoryContract.Interactor inventoryInteractor;

    public InventoryPresenter(InventoryContract.View inventoryView, InventoryContract.Interactor inventoryInteractor) {
        this.inventoryView = inventoryView;
        this.inventoryInteractor = inventoryInteractor;
    }

    @Override
    public void onFinished(List<Product> products) {
        inventoryView.updateUI(products);
    }

    @Override
    public void getProducts() {
        inventoryInteractor.getProducts(this);
    }
}
