package com.demo.productcatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.productcatalogue.R;
import com.demo.productcatalogue.database.ProductsDatabase;
import com.demo.productcatalogue.model.ProductModel;

public class ProductDetailActivity extends AppCompatActivity {
    ProductModel selectedProduct;
    TextView name,category,style,color,model,rate,quantity,totalPrice;
    Button incQuantity,decQuantity,addToCart;
    ProductsDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent previousScreen = getIntent();
        selectedProduct = previousScreen.getParcelableExtra("SelectedProduct");
        db = ProductsDatabase.getInstance(getApplicationContext());
        selectedProduct = db.productsDao().getProductById(selectedProduct.getProductId());
//        Toast.makeText(this,selectedProduct.getName(),Toast.LENGTH_SHORT).show();
        initViews();
    }

    private void initViews() {
        name = findViewById(R.id.tv_prod_detail_name);
        category = findViewById(R.id.tv_prod_detail_category);
        style = findViewById(R.id.tv_prod_detail_style);
        color = findViewById(R.id.tv_prod_detail_color);
        model = findViewById(R.id.tv_prod_detail_model);
        rate=findViewById(R.id.tv_price);
        totalPrice = findViewById(R.id.tv_total_price);
        name.setText(selectedProduct.getName());
        category.setText("Category: "+selectedProduct.getCategory());
        style.setText("Style: "+selectedProduct.getStyle());
        color.setText("Color: "+selectedProduct.getStyle());
        model.setText("Model: "+selectedProduct.getModel());
        rate.setText(String.valueOf(selectedProduct.getPrice()));
        quantity = findViewById(R.id.tv_quant);
        quantity.setText(String.valueOf(selectedProduct.getQuantity()));
        totalPrice.setText(String.valueOf(selectedProduct.getPrice() * Integer.parseInt(quantity.getText().toString().trim())));
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_quant_inc:
                selectedProduct.setQuantity(selectedProduct.getQuantity()+1);
                db.productsDao().updateQuantity(selectedProduct.getProductId(),selectedProduct.getQuantity());
                updatePrice();
                break;
            case  R.id.btn_quant_dec:
                if(selectedProduct.getQuantity()==1){
                    db.productsDao().deleteProduct(selectedProduct);
                    Toast.makeText(this,"Product Removed from cart",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    selectedProduct.setQuantity(selectedProduct.getQuantity()-1);
                    db.productsDao().updateQuantity(selectedProduct.getProductId(),selectedProduct.getQuantity());
                    updatePrice();
                }
                break;
            case R.id.btn_checkout:
                //go to checkout screen
                Intent checkout = new Intent(ProductDetailActivity.this,CheckoutActivity.class);
                startActivity(checkout);
                break;
        }
    }

    private void updatePrice() {
        quantity.setText(String.valueOf(selectedProduct.getQuantity()));
        totalPrice.setText(String.valueOf(selectedProduct.getPrice() * Integer.parseInt(quantity.getText().toString().trim())));
    }
}
