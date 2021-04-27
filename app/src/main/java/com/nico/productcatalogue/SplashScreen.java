package com.nico.productcatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.nico.productcatalogue.SharedPreferences.SharedPreferenceUtil;
import com.nico.productcatalogue.activity.ProductsDisplayActivity;
import com.nico.productcatalogue.database.ProductsDatabase;
import com.nico.productcatalogue.model.ProductModel;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        showSplashScreen();
    }

    private void showSplashScreen() {
        //if DB has no any record
        if(!SharedPreferenceUtil.getInstance(getApplicationContext()).isDBInitilized()){
            InsertProductAsyncTask asyncTask = new InsertProductAsyncTask();    //insert records in DB
            asyncTask.execute(getApplicationContext());
            SharedPreferenceUtil.getInstance(getApplicationContext()).setDBInitilized(true);    //update shared preference
        }
        else {
            goToNextScreen();
        }
    }

    private  class InsertProductAsyncTask extends AsyncTask<Context,Void,Void> {

        private void insertProducts(Context context) {
            ProductsDatabase db = ProductsDatabase.getInstance(context);
            ProductModel p1 = new ProductModel(0,"Fancy Shirt","Casual","Shirt","V-Neck","Red",1100.0f,1);
            db.productsDao().addProduct(p1);
            ProductModel p2 = new ProductModel(0,"Cozy Jeans","Formal","Jeans","Low Cut","Blue",800.0f,1);
            db.productsDao().addProduct(p2);
            ProductModel p3 = new ProductModel(0,"Comfy Shoes","Sports","Shoes","No lace","Black",1500.0f,1);
            db.productsDao().addProduct(p3);
            ProductModel p4 = new ProductModel(0,"New Shirt","Formal","Shirt","Round neck","Blue",1800.0f,1);
            db.productsDao().addProduct(p4);
            ProductModel p5 = new ProductModel(0,"Blue Jeans","Casual","Jeans","S2","Black",1300.0f,1);
            db.productsDao().addProduct(p5);
            ProductModel p6 = new ProductModel(0,"Strong Shoe","Formal","Shoes","S2","Black",600.0f,1);
            db.productsDao().addProduct(p6);
        }

        @Override
        protected Void doInBackground(Context... contexts) {
            insertProducts(contexts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent nextScreen = new Intent(SplashScreen.this, ProductsDisplayActivity.class);
            startActivity(nextScreen);
            finish();
        }


    }
    private void goToNextScreen() {
        Intent nextScreen = new Intent(SplashScreen.this, ProductsDisplayActivity.class);
        startActivity(nextScreen);
        finish();
    }
}
