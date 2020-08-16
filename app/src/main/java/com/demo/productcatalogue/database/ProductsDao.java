package com.demo.productcatalogue.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.demo.productcatalogue.model.ProductModel;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addProduct(ProductModel productModel);

    @Delete
    public void deleteProduct(ProductModel productModel);

    @Update
    public void updateProduct(ProductModel productModel);

    @Query("SELECT * FROM product_table ORDER BY category")   //get all procucts
    public List<ProductModel> fetchAllProduct();

    @Query("SELECT * FROM product_table where productId=:Id")
    public ProductModel getProductById(int Id);  //get single product by id

    @Query("SELECT * FROM product_table where category=:pCategory")
    public List<ProductModel> getProductsByCategory(String pCategory); //get products by category

    @Query("SELECT * FROM product_table where model=:pModel AND style=:pStyle AND color=:pColor")
    public List<ProductModel> getProductsByCategory(String pModel, String pStyle, String pColor ); //get products by model, style, color

    @Query("UPDATE product_table SET quantity = :quantity where productId = :productId")
    public void updateQuantity(int productId, int quantity);
}
