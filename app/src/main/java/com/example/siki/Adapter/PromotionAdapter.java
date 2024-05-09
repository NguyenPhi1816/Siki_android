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
import com.example.siki.activities.PromotionDetailActivity;
import com.example.siki.activities.PromotionListActivity;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.database.PromotionDataSource;
import com.example.siki.model.Category;
import com.example.siki.model.Product;
import com.example.siki.model.Promotion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class PromotionAdapter extends BaseAdapter {
    private Context context;
    private List<Promotion> promotionList;

    public PromotionAdapter(Context context, List<Promotion> promotionList) {
        this.context = context;
        this.promotionList = promotionList;
    }

    @Override
    public int getCount() {
        return promotionList.size();
    }

    @Override
    public Object getItem(int i) {
        return promotionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return promotionList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View promotionView;
        if (view == null) {
            promotionView = View.inflate(viewGroup.getContext(), R.layout.promotion_item, null);
        } else promotionView = view;

        //Bind sữ liệu phần tử vào View
        Promotion promotion = promotionList.get(i);
        ((TextView) promotionView.findViewById(R.id.promotionName)).setText(promotion.getName());
        ((TextView) promotionView.findViewById(R.id.promotionReason)).setText(promotion.getReason());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String endDate = sdf.format(promotion.getEndDate());
        ((TextView) promotionView.findViewById(R.id.promotionDate)).setText(String.format("HSD: %s", endDate));
        ImageView myView = promotionView.findViewById(R.id.promotionImage);
        Picasso.get().load(promotion.getImagePath()).into(myView);


        Button btnEdit = promotionView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PromotionDetailActivity.class);
                intent.putExtra("promotion", promotion);
                context.startActivity(intent);
            }
        });
        PromotionDataSource promotionDataSource = new PromotionDataSource(context);
        promotionDataSource.open();
        Button btnDelete = promotionView.findViewById(R.id.btnDelete);
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
                        Promotion pro = new Promotion();
                        pro.setId(promotion.getId());
                        promotionDataSource.deletePromotion(pro);
                        dialog.dismiss();
                        Intent intent = new Intent(context, PromotionListActivity.class);
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
        return promotionView;
    }
}
