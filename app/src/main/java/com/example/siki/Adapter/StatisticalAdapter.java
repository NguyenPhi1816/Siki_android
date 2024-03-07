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

import java.util.List;

public class StatisticalAdapter extends ArrayAdapter<StatisticalModel> {


    public StatisticalAdapter(@NonNull Context context, List<StatisticalModel> listdata) {
        super(context, R.layout.layout_statistical, listdata);
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
            title.setText(String.format("Tháng %s", data.getTitle()));
            value.setText(String.format("Bán được %s sản phẩm", String.valueOf(data.getQuantity())));
        }
        return convertView;
    }
}
