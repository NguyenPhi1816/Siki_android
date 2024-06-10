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

import com.example.siki.API.CategoryApiService;
import com.example.siki.API.dto.CategoryDto;
import com.example.siki.API.retrofit.RetrofitClient;
import com.example.siki.R;
import com.example.siki.activities.CategoryAddActivity;
import com.example.siki.activities.CategoryDetailActivity;
import com.example.siki.activities.CategoryListActivity;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Picasso.get().load(category.getImage()).into(myView);


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

                        callApi(Math.toIntExact(category.getId()));

                        Intent intent = new Intent(context, CategoryListActivity.class);
                        context.startActivity(intent);
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

    private void callApi(Integer id) {
        CategoryApiService categoryApiService = RetrofitClient.getRetrofitInstance().create(CategoryApiService.class);
        categoryApiService.deleteCategory(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 400) {
                    Toast.makeText(context, "Không thể xóa loại sản phẩm", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Category call api delete failed!");
            }
        });
    }


}
