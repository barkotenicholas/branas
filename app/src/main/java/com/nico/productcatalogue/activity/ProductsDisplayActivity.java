package com.nico.productcatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nico.productcatalogue.R;
import com.nico.productcatalogue.SharedPreferences.SharedPreferenceUtil;
import com.nico.productcatalogue.adapters.ProductsAdapter;
import com.nico.productcatalogue.database.ProductsDatabase;
import com.nico.productcatalogue.model.ProductModel;
import java.util.List;

public class ProductsDisplayActivity extends AppCompatActivity implements com.nico.productcatalogue.adapters.ProductsAdapter.ItemClicked {

    private static final String TAG = "ProductsDisplayActivity";
    RecyclerView productsRecycler;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<ProductModel> productList;
    ProductsDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_display);
        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        FetchProductAsyncTask asyncTask = new FetchProductAsyncTask();
        asyncTask.execute();
    }

    private void initViews() {
        productsRecycler = findViewById(R.id.prod_recycler);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
    }

    @Override
    public void onClicked(int position) {
        Intent productDetailsScreen = new Intent(ProductsDisplayActivity.this,ProductDetailActivity.class);
        productDetailsScreen.putExtra("SelectedProduct",productList.get(position));
        startActivity(productDetailsScreen);
    }

    private class FetchProductAsyncTask extends AsyncTask<Void,Void,List<ProductModel>>{

        @Override
        protected List<ProductModel> doInBackground(Void... voids) {
            db = ProductsDatabase.getInstance(getApplicationContext());
            productList = db.productsDao().fetchAllProduct();
            return null;
        }

        @Override
        protected void onPostExecute(List<ProductModel> productModels) {
            super.onPostExecute(productModels);
            if(productList.size()==0){
                SharedPreferenceUtil.getInstance(getApplicationContext()).setDBInitilized(false);
                Toast.makeText(getApplicationContext(),"Your Cart is Empty",Toast.LENGTH_SHORT).show();
//                return;
                finish();
            }
            if(productList.size()>0) {
//                dialog.hide();
                for(ProductModel m: productList)
                    Log.d(TAG,m.getName()+" ");
                adapter = new ProductsAdapter(ProductsDisplayActivity.this, productList);
                productsRecycler.setLayoutManager(layoutManager);
                productsRecycler.setAdapter(adapter);

            }
        }
    }
}
