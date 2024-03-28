package com.example.siki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.siki.R;
import com.example.siki.model.Product;

import java.util.List;

public class GridListSPApdapter extends ArrayAdapter<Product> {
    List<Product> lsp ;
    int layout_id;
    public GridListSPApdapter(@NonNull Context context, int resource, @NonNull List<Product> lsp){
        super(context,resource,lsp);
        this.lsp = lsp ;
        layout_id = resource ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView ;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
            v = inflater.inflate(layout_id,null) ;
        }
        ImageView imageView = v.findViewById(R.id.gridimagesp) ;
        TextView textViewname = v.findViewById(R.id.gridtextnamesp) ;
        TextView textViewSL = v.findViewById(R.id.gridtextslsp) ;
        TextView textViewPrice = v.findViewById(R.id.gridtextgiasp) ;

        Product sp = lsp.get(position) ;

        imageView.setImageResource(R.drawable.card);
        textViewname.setText(sp.getName());
        textViewSL.setText(String.valueOf(sp.getQuantity()));
        textViewPrice.setText(String.valueOf(sp.getPrice()));


        return v;
    }
}
