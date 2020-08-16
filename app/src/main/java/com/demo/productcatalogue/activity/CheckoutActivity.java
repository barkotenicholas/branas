package com.demo.productcatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.productcatalogue.R;
import com.demo.productcatalogue.adapters.ProductsAdapter;
import com.demo.productcatalogue.database.ProductsDatabase;
import com.demo.productcatalogue.model.ProductModel;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    TextView tvCart,tvTax,tvPricePay;
    Button btnProceed,btnCancel;
    ProductsDatabase db;
    List<ProductModel> productList;
    float cartPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initviews();
        FetchProductAsyncTask asyncTask = new FetchProductAsyncTask();
        asyncTask.execute();
    }

    private void initviews() {
        tvCart = findViewById(R.id.tv_cart_price);
        tvTax = findViewById(R.id.tv_tax);
        tvPricePay = findViewById(R.id.tv_pay_price);
        btnCancel = findViewById(R.id.btn_cancel);
        btnProceed = findViewById(R.id.btn_proceed);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_cancel:
                Intent home = new Intent(CheckoutActivity.this,ProductsDisplayActivity.class);
                startActivity(home);
                break;
            case R.id.btn_proceed:
                Toast.makeText(getApplicationContext(),"Your Order is checked out",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class FetchProductAsyncTask extends AsyncTask<Void,Void, List<ProductModel>> {

        @Override
        protected List<ProductModel> doInBackground(Void... voids) {
            db = ProductsDatabase.getInstance(getApplicationContext());
            productList = db.productsDao().fetchAllProduct();
            return null;
        }

        @Override
        protected void onPostExecute(List<ProductModel> productModels) {
            super.onPostExecute(productModels);
            if(productList.size()>0) {
//                dialog.hide();
                for(ProductModel m: productList)
                    cartPrice+=(m.getPrice()*m.getQuantity());
                tvCart.setText(String.valueOf(cartPrice));
                tvTax.setText(String.valueOf(cartPrice*0.18f)+"(18%)");
                tvPricePay.setText(String.valueOf(cartPrice + cartPrice*0.18f));
            }
            else{
//                dialog.hide();
                Toast.makeText(getApplicationContext(),"Problem fetching cart",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
