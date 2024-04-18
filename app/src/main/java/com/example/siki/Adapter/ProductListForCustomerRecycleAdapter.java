package com.example.siki.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.activities.LoginActivity;
import com.example.siki.database.CartDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Product;
import com.example.siki.utils.PriceFormatter;
import com.example.siki.variable.GlobalVariable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListForCustomerRecycleAdapter extends RecyclerView.Adapter<ProductListForCustomerRecycleAdapter.ProductHolder> {

    private List<Product> productList ;

    private final String quantityFormat = "Còn lại %d";
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
        Picasso.get().load(product.getImagePath()).into(holder.iv_product);
        holder.tv_productName.setText(product.getName());
        holder.tv_productPrice.setText(PriceFormatter.formatDouble(product.getPrice()));
        holder.tv_productQuantity.setText(String.format(quantityFormat, product.getQuantity()));
        CartDatasource cartDatasource = new CartDatasource(context);
        cartDatasource.open();
        UserDataSource userDataSource = new UserDataSource(context);
        userDataSource.open();
        ProductDatabase productDatabase = new ProductDatabase(context);
        productDatabase.open();
        holder.btn_product_add2Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: link activity
               /* GlobalVariable globalVariable = new GlobalVariable();
                if (globalVariable.getAuthUser() != null) {
                    Integer userId = globalVariable.getAuthUser().getId();
                    long isAddSuccess = cartDatasource.addToCart(product.getId(), userId, userDataSource, productDatabase);
                    if (isAddSuccess != -1) {
                        String message = "Thêm vào giỏ hàng thành công!";
                        showSuccessMessage(context, message);
                    }
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(context, intent, null);
                }*/
                // Test add to cart
                Integer userId = 1;
                long isAddSuccess = cartDatasource.addToCart(product.getId(), userId, userDataSource, productDatabase);
                if (isAddSuccess != -1) {
                    String message = "Thêm vào giỏ hàng thành công!";
                    showSuccessMessage(context, message);
                }
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
    private void showSuccessMessage(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_success_dialog);
        Button btn_payment_success = dialog.findViewById(R.id.btn_payment_success);
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText(message);
        btn_payment_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
