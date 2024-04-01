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
import com.example.siki.activities.CartActivity;
import com.example.siki.database.CartDatasource;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.Store;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StoreRecycleAdapter extends RecyclerView.Adapter<StoreRecycleAdapter.StoreHolder> {

    private Map<String, List<Cart>> stores;

    private Context context;

    public StoreRecycleAdapter(Map<String, List<Cart>> stores, Context context) {
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
        Map.Entry<String, List<Cart>> store =  getItem(position);

        // Todo: Check is all cart is selected then set selection for cb_shop < doing
        holder.cb_shopId.setChecked(isAllSelected(store.getValue()));
        holder.cb_shopId.setText(store.getKey());
        List<Cart> cartList = store.getValue();
        CartRecycleAdapter cartAdapter = new CartRecycleAdapter(cartList, context);
        holder.rv_shopItem.setAdapter(cartAdapter);
        holder.rv_shopItem.setLayoutManager(new GridLayoutManager(context,1));
        CartDatasource cartDatasource = new CartDatasource(context);
        cartDatasource.open();
        holder.cb_shopId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.cb_shopId.isChecked();
                holder.cb_shopId.setChecked(isChecked);
                cartList.forEach(cart -> {
                    cartDatasource.updateSelectedCart(cart.getId(), isChecked);
                    cart.setChosen(isChecked);
                });
                if (context instanceof CartActivity) {
                    ((CartActivity)context).readDb();
                }
            }
        });


    }



    private boolean isAllSelected(List<Cart> cartList) {
        boolean check = true ;
        for (Cart cart : cartList ) {
            if (!cart.isChosen()) {
                check = false;
                break;
            }
        }
        return check;

    }

    public Map.Entry<String, List<Cart>> getItem(int position) {
        int start = 0 ;
        for (Map.Entry<String, List<Cart>> entry : stores.entrySet()) {
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
