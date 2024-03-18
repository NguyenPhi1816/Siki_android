package com.example.siki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.database.CartDatasource;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.Store;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StoreRecycleAdapter extends RecyclerView.Adapter<StoreRecycleAdapter.StoreHolder> {

    private Map<Store, List<Cart>> stores;

    private Context context;

    public StoreRecycleAdapter(Map<Store, List<Cart>> stores, Context context) {
        this.stores = stores;
        this.context = context;
    }

    @NonNull
    @Override
    public StoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        return new StoreRecycleAdapter.StoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreHolder holder, int position) {
        Map.Entry<Store, List<Cart>> store = (Map.Entry<Store, List<Cart>>) getItem(position);
        holder.cb_shopId.setChecked(true);
        holder.cb_shopId.setText(store.getKey().getName());
        List<Cart> cartList = store.getValue();
        CartRecycleAdapter cartAdapter = new CartRecycleAdapter(cartList, holder.itemView.getContext());
        holder.rv_shopItem.setAdapter(cartAdapter);
        holder.rv_shopItem.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(),1));
        CartDatasource cartDatasource = new CartDatasource(context);
        cartDatasource.open();
        holder.cb_shopId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cartList.forEach(cart -> {
                    cartDatasource.updateSelectedCart(cart.getId(), isChecked);
                });
            }
        });
    }

    public Map.Entry<Store, List<Cart>> getItem(int position) {
        int start = 0 ;

        for (Map.Entry<Store, List<Cart>> entry : stores.entrySet()) {
            if (start == position) {
                return entry;
            }
            start++;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    class StoreHolder extends RecyclerView.ViewHolder{
        CheckBox cb_shopId;
        RecyclerView  rv_shopItem;
        public StoreHolder(@NonNull View itemView) {
            super(itemView);
            cb_shopId = itemView.findViewById(R.id.cb_shopId);
            rv_shopItem = itemView.findViewById(R.id.rv_shopItem);
        }
    }
}
