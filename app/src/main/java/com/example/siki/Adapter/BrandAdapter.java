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

import com.example.siki.API.BrandApiService;
import com.example.siki.API.CategoryApiService;
import com.example.siki.API.retrofit.RetrofitClient;
import com.example.siki.R;
import com.example.siki.activities.BrandDetailActivity;
import com.example.siki.activities.BrandListActivity;
import com.example.siki.activities.CategoryDetailActivity;
import com.example.siki.activities.CategoryListActivity;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.model.Brand;
import com.example.siki.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandAdapter extends BaseAdapter {
    private Context context;
    private List<Brand> brandList;

    public BrandAdapter(Context context, List<Brand> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Object getItem(int i) {
        return brandList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return brandList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View brandView;
        if (view == null) {
            brandView = View.inflate(viewGroup.getContext(), R.layout.brand_item, null);
        } else brandView = view;

        //Bind sữ liệu phần tử vào View
        Brand brand = brandList.get(position);
        ((TextView) brandView.findViewById(R.id.brandId)).setText(String.format("Id: %s", brand.getId()));
        ((TextView) brandView.findViewById(R.id.brandName)).setText(String.format("Tên thương hiệu: %s", brand.getName()));
        ImageView myView = brandView.findViewById(R.id.brandLogo);
        Picasso.get().load(brand.getLogo()).into(myView);


        Button btnEdit = brandView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrandDetailActivity.class);
                intent.putExtra("brand", brand);
                context.startActivity(intent);
            }
        });

        Button btnDelete = brandView.findViewById(R.id.btnDelete);
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

                        callApi(brand.getId());

                        Intent intent = new Intent(context, BrandListActivity.class);
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
        return brandView;
    }

    private void callApi(Integer id) {
        BrandApiService brandApiService = RetrofitClient.getRetrofitInstance().create(BrandApiService.class);
        brandApiService.deleteBrand(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 400) {
                    Toast.makeText(context, "Không thể xóa thương hiệu", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Brand call api delete failed!");
            }
        });
    }


}
