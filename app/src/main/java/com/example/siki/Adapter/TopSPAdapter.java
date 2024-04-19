package com.example.siki.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopSPAdapter extends RecyclerView.Adapter<TopSPAdapter.ViewHolder> {
    private List<Product> lsp ;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemtopsp,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product sp = lsp.get(position) ;
        Picasso.get().load(sp.getImagePath()).into(holder.srcimage); // set ảnh
        holder.namesp.setText(sp.getName());
        holder.slsp.setText("SL:"+String.valueOf(sp.getQuantity()));
        holder.pricesp.setText(String.format("%svnđ", String.valueOf(sp.getPrice())));

    }

    @Override
    public int getItemCount() {
        return lsp.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView srcimage;
        TextView namesp;
        TextView slsp;
        TextView pricesp;
        ViewHolder(View view) {
            super(view);
            srcimage = view.findViewById(R.id.imagesp) ;
            namesp = view.findViewById(R.id.textnamesp) ;
            slsp = view.findViewById(R.id.textslsp) ;
            pricesp = view.findViewById(R.id.textgiasp) ;
        }
    }
    public TopSPAdapter(List<Product> toplsp) {
        this.lsp = toplsp ;
    }

}
