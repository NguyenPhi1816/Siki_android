package com.example.siki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.siki.R;
import com.example.siki.model.StatisticalModel;

import java.text.DecimalFormat;
import java.util.List;

public class StatisticalAdapter extends ArrayAdapter<StatisticalModel> {

    private String styte;

    public StatisticalAdapter(@NonNull Context context, List<StatisticalModel> listdata, String styte) {
        super(context, R.layout.layout_statistical, listdata);
        this.styte = styte;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StatisticalModel data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_statistical, parent, false);
        }

        TextView title = convertView.findViewById(R.id.tvTitle);
        TextView value = convertView.findViewById(R.id.tvValue);

        if (data !=null) {
            title.setText(data.getTitle());
            if (styte.equals("sp")) {
                value.setText(String.format("Bán được %s sản phẩm", String.valueOf(data.getQuantity())));
            }
            if (styte.equals("vnd")){
                DecimalFormat decimalFormat = new DecimalFormat("#,### VND");
                value.setText(String.format("Tổng giá trị bán được %s", decimalFormat.format(data.getQuantity())));
            }
        }
        return convertView;
    }
}
