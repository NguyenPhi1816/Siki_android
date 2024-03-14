package com.example.siki.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.R;
import com.example.siki.activities.ProductDetailActivity;
import com.example.siki.activities.ProductListActivity;
import com.example.siki.database.ProductDatabase;
import com.example.siki.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View productView;
        if (view == null) {
            productView = View.inflate(viewGroup.getContext(), R.layout.product_item, null);
        } else productView = view;

        //Bind sữ liệu phần tử vào View
        Product product = productList.get(position);
        ((TextView) productView.findViewById(R.id.productName)).setText(String.format("Tên SP: %s", product.getName()));
        ((TextView) productView.findViewById(R.id.productPrice)).setText(String.format("Giá SP: %s", product.getProductPrice().getPrice()));

        ImageView myView = productView.findViewById(R.id.productImage);
        Picasso.get().load(product.getImagePath()).into(myView);

        Button btnEdit = productView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });
        ProductDatabase productDatabase = new ProductDatabase(context);
        productDatabase.open();
        Button btnDelete = productView.findViewById(R.id.btnDelete);
        Dialog dialog = new Dialog(context);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.dialog_delete);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                Button btnYes = dialog.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Product sp = new Product();
                        sp.setId(product.getId());
                        productDatabase.deleteProduct(sp);
                        dialog.dismiss();
                        Intent intent = new Intent(context, ProductListActivity.class);
                        context.startActivity(intent);
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_LONG).show();
                    }
                });

                Button btnNo = dialog.findViewById(R.id.btnNo);
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return productView;
    }


}
