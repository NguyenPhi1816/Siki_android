package com.example.siki.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.activities.ListProductForCustomerActivity;
import com.example.siki.activities.fragment.HomeFragment;
import com.example.siki.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryForHomePageRecycleAdapter extends RecyclerView.Adapter<CategoryForHomePageRecycleAdapter.CategoryHolder> {

   private List<Category> categoryList = new ArrayList<>();

    private Context context;

    private HomeFragment homeFragment;

    public CategoryForHomePageRecycleAdapter(List<Category> categoryList, Context context, HomeFragment homeFragment) {
        this.categoryList = categoryList;
        this.context = context;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCategory = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category_item, parent, false);
        return new CategoryHolder(viewCategory);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = categoryList.get(position);
        holder.tv_home_category_name.setText(category.getName());
        Picasso.get().load(category.getImage()).into(holder.iv_home_category);
        // Todo : handle move to list product for customer
        holder.ln_home_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToListProductByCategory(category.getId());
            }
        });

    }

    private void redirectToListProductByCategory(Long categoryId) {
        Intent intent = new Intent(context, ListProductForCustomerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("categoryId", categoryId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }



    class CategoryHolder extends RecyclerView.ViewHolder {
        ImageView iv_home_category;

        LinearLayout ln_home_category;
        TextView tv_home_category_name;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            ln_home_category = itemView.findViewById(R.id.ln_home_category);
            iv_home_category = itemView.findViewById(R.id.iv_home_category);
            tv_home_category_name = itemView.findViewById(R.id.tv_home_category_name);
        }
    }
}
