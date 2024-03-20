package com.example.siki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.database.CartDatasource;
import com.example.siki.model.Cart;
import com.example.siki.model.Category;
import com.example.siki.model.Product;
import com.example.siki.model.Store;

import java.util.List;
import java.util.Map;

public class ProductListForCustomerRecycleAdapter extends RecyclerView.Adapter<ProductListForCustomerRecycleAdapter.ProductHolder> {

    private List<Product> productList ;

    private Context context;

    public ProductListForCustomerRecycleAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_customer_item, parent, false);
        return new ProductListForCustomerRecycleAdapter.ProductHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = productList.get(position);
        holder.iv_product.setImageResource(R.drawable.samsung);
        holder.tv_productName.setText(product.getName());
        holder.tv_productPrice.setText("120,000d");
        holder.tv_productQuantity.setText("Con lai 4sp");
        holder.btn_product_add2Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        ImageView iv_product;
        TextView tv_productName, tv_productPrice, tv_productQuantity;
        Button btn_product_add2Cart;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            iv_product = itemView.findViewById(R.id.iv_product);
            tv_productName = itemView.findViewById(R.id.tv_productName);
            tv_productPrice = itemView.findViewById(R.id.tv_productPrice);
            tv_productQuantity = itemView.findViewById(R.id.tv_productQuantity);
            btn_product_add2Cart = itemView.findViewById(R.id.btn_product_add2Cart);
        }
    }
}
