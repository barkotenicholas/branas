package com.nico.productcatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.nico.productcatalogue.R;
import com.nico.productcatalogue.database.ProductsDatabase;
import com.nico.productcatalogue.model.ProductModel;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    TextView tvCart,tvTax,tvPricePay;
    Button btnProceed,btnCancel;
    ProductsDatabase db;
    List<ProductModel> productList;
    float cartPrice;
    private BillingProcessor billingProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initviews();
        FetchProductAsyncTask asyncTask = new FetchProductAsyncTask();
        asyncTask.execute();

        billingProcessor = new BillingProcessor(this,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAimYOGnBuxZXnU5GCiXsaWdSFW3ToKhiEOB25l1GvbGAVKdOksfAfkWFbi3aFz39Xpl61Ef7K/0kmUcb2yYBA4olyW8rFhlpRtIi1s4oIm1ZIaWUZ730jnejctr8XWVEFFCtnLbh9gS1wuzB4txu5xM1mjs3rQAZ1jO7NL96s1wwoFm30a9iNPxsUcEHTF/Dho+ufvXKnAGu8/SqVm3erQFzL0sTST/AY4Yw4o2ViDxqqe2l69GlJgYu9T7ccf/ZahQM25bS4v71iD5LrRMwQjDc4528UbWn6iqJCsKeS8cCICc3Oj5CLTJ/Pb12DbvfKkbdf0/LwQpn8HDguH9zhCQIDAQAB" , null, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                Toast.makeText(CheckoutActivity.this,"purchased",Toast.LENGTH_LONG);

            }

            @Override
            public void onPurchaseHistoryRestored() {

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {

            }

            @Override
            public void onBillingInitialized() {

            }
        });


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
                createSub();
                break;
        }
    }

    private void createSub() {
        billingProcessor.subscribe(CheckoutActivitygit.this,"acup");

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
