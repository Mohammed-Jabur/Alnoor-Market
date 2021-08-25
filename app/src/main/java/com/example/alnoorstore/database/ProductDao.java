package com.example.alnoorstore.database;

import com.example.alnoorstore.model.Product;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProductDao {

    @Query("Select * from Product")
    List<Product> getProductList();

    @Query("Select * from Product Where Number LIKE :content OR Name LIKE :content")
    Product getProduct(String content);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);
}
