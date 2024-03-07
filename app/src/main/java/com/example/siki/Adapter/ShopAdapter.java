package com.example.siki.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.siki.R;
import com.example.siki.model.Product;
import com.example.siki.model.Store;

import java.util.List;
import java.util.Map;

public class ShopAdapter extends BaseAdapter {

    private Map<Store, List<Product>> stores;

    public ShopAdapter(Map<Store, List<Product>> stores) {
        this.stores = stores;
    }

    @Override
    public int getCount() {
        return stores.size();
    }

    @Override
    public Object getItem(int position) {
        int start = 0 ;

        for (Map.Entry<Store, List<Product>> entry : stores.entrySet()) {
            if (start == position) {
                System.out.println(entry);
                return entry;
            }
            start++;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewStore;
        if (convertView == null) {
            viewStore = View.inflate(parent.getContext(), R.layout.store_item, null);
    } else viewStore = convertView;
    Map.Entry<Store, List<Product>> store = (Map.Entry<Store, List<Product>>) getItem(position);

        ((CheckBox) viewStore.findViewById(R.id.cb_shopId)).setChecked(false);
        ((CheckBox) viewStore.findViewById(R.id.cb_shopId)).setText(store.getKey().getName());
        System.out.println(store.getValue());
        List<Product>productList = store.getValue();
        CartAdapter cartAdapter = new CartAdapter(productList);
        ((ListView)  viewStore.findViewById(R.id.lv_shopItem)).setAdapter(cartAdapter);
        return viewStore;
    }
}
