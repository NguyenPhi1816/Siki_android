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
import com.example.siki.activities.CategoryDetailActivity;
import com.example.siki.activities.CategoryListActivity;
import com.example.siki.activities.ProductDetailActivity;
import com.example.siki.activities.ProductListActivity;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.database.ProductDatabase;
import com.example.siki.model.Category;
import com.example.siki.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categoryList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View categoryView;
        if (view == null) {
            categoryView = View.inflate(viewGroup.getContext(), R.layout.category_item, null);
        } else categoryView = view;

        //Bind sữ liệu phần tử vào View
        Category category = categoryList.get(position);
        ((TextView) categoryView.findViewById(R.id.categoryId)).setText(String.format("Id: %s", category.getId()));
        ((TextView) categoryView.findViewById(R.id.categoryName)).setText(String.format("Tên loại: %s", category.getName()));
        ((TextView) categoryView.findViewById(R.id.categoryDescription)).setText(String.format("Mô tả: %s", category.getDescription()));
        ImageView myView = categoryView.findViewById(R.id.categoryImage);
        Picasso.get().load(category.getImagePath()).into(myView);

        Button btnEdit = categoryView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetailActivity.class);
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });
        CategoryDatabase categoryDatabase = new CategoryDatabase(context);
        categoryDatabase.open();
        Button btnDelete = categoryView.findViewById(R.id.btnDelete);
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
                        Category ca = new Category();
                        ca.setId(category.getId());
                        categoryDatabase.deleteProduct(ca);
                        dialog.dismiss();
                        Intent intent = new Intent(context, CategoryListActivity.class);
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
        return categoryView;
    }


}
