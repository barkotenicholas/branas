package com.demo.productcatalogue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.productcatalogue.R;
import com.demo.productcatalogue.model.ProductModel;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> productList;
    ItemClicked activity;
    public interface ItemClicked{
        public void onClicked(int position);
    }
    public ProductsAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
        activity = (ItemClicked) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(productList.get(position));
        holder.tvProductName.setText(productList.get(position).getName());
        if(position==0 ){
            holder.tvGroupTitle.setText(productList.get(position).getCategory());
        }
        else {
            if(productList.get(position-1).getCategory().equals(productList.get(position).getCategory())){
                holder.tvGroupTitle.setText("");
//                holder.tvGroupTitle.setVisibility(View.INVISIBLE);
//                holder.tvGroupTitle.setVisibility(View.GONE);
            }else{
                holder.tvGroupTitle.setText(productList.get(position).getCategory());
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList==null?0:productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName,tvGroupTitle;
        ConstraintLayout panel;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_prod_name);
            tvGroupTitle = itemView.findViewById(R.id.tv_group_title);
            panel = itemView.findViewById(R.id.productNamePanel);
            panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onClicked(productList.indexOf((ProductModel)itemView.getTag()));
                }
            });
        }
    }
}
