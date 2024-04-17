package com.example.siki.activities.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.Adapter.StoreRecycleAdapter;
import com.example.siki.R;
import com.example.siki.activities.PaymentActivity;
import com.example.siki.database.CartDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Cart;
import com.example.siki.model.User;
import com.example.siki.utils.PriceFormatter;
import com.example.siki.variable.GlobalVariable;
import com.saadahmedev.popupdialog.PopupDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartFragment extends Fragment {
    private List<Cart> cartList = new ArrayList<>();
    private Context context ;
    private TextView tv_cart_totalPrice;
    private  CheckBox cb_cart_total;
    private Button btn_delete_total_carts, btn_cart_order;
    private RecyclerView storeRecycle;

    private AlertDialog.Builder builder;

    private UserDataSource userDataSource   ;
    private CartDatasource cartDatasource;

    private ProductDatabase productDatabase;

    private GlobalVariable globalVariable = new GlobalVariable();
    private Map<String, List<Cart>> storeProductMap = new HashMap<>();
    private final String cartMessage = "Tất cả %d sản phẩm";

    private final String btnOrderMessage = "Mua hàng (%d)";

    private StoreRecycleAdapter storeAdapter;
    public CartFragment(Context context, List<Cart> cartList, Map<String, List<Cart>> storeProductMap) {
        this.context = context;
        this.cartList = cartList;
        this.storeProductMap = storeProductMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    private void setEvent() {
        cartDatasource = new CartDatasource(context);
        cartDatasource.open();
        //Todo: get all cart that is selected -> get total price
        tv_cart_totalPrice.setText(getTotalOfCartIsSelected());


        List<Cart> selectingCarts = cartList.stream().map(cart -> {
            if (cart.isChosen()) {
                return cart;
            }
            return null;
        }).collect(Collectors.toList());

        cb_cart_total.setText(String.format(cartMessage, selectingCarts.size()));
        cb_cart_total.setChecked(isAllSelected(cartList));
        btn_cart_order.setText(String.format(btnOrderMessage, selectingCarts.size()));
        storeAdapter = new StoreRecycleAdapter(storeProductMap, context, this);
        storeRecycle.setAdapter(storeAdapter);
        storeRecycle.setLayoutManager(new GridLayoutManager(context, 1));
        btn_cart_order.setEnabled(cartList.size() > 0);
        cb_cart_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = cb_cart_total.isChecked();
                cb_cart_total.setChecked(isChecked);
                if (globalVariable.getAuthUser() != null) {
                    cartDatasource.updateSelectedCartByUser(globalVariable.getAuthUser().getId() , isChecked);
                    readDb();
                    storeAdapter.notifyDataSetChanged();
                }
            }
        });

        btn_delete_total_carts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Xoá sản phẩm").setMessage("Bạn có muốn xóa sản phẩm đang chọn?")
                        .setCancelable(true).setPositiveButton("Xac nhan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CharSequence text = "Hello toast!";
                                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                                User user = globalVariable.getAuthUser();
                                if (user != null) {
                                    cartDatasource.removeByUserId(user.getId());
                                }
                                readDb();
                                storeAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Huy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });
        btn_cart_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectingCarts.size() > 0) {
                    Intent intent = new Intent(context, PaymentActivity.class);
                    Bundle bundle = new Bundle();
                    List<Cart> selectingCarts = cartList.stream()
                            .filter(Cart::isChosen) // Keep only carts where isChosen is true
                            .collect(Collectors.toList());
                    bundle.putSerializable("selectingCarts", (Serializable) selectingCarts);
                    intent.putExtra("selectingCarts", bundle);
                    startActivity(intent);
                }else {
                    String message = "You must choose at least one product !";
                    showAlertMessage(message);
                }
            }
        });

    }

    private void showAlertMessage(String message) {
        PopupDialog.getInstance(context)
                .statusDialogBuilder()
                .createWarningDialog()
                .setHeading("Alert")
                .setDescription(message)
                .build(Dialog::dismiss)
                .show();
    }
    public void readDb() {
        userDataSource = new UserDataSource(context);
        userDataSource.open();
        productDatabase = new ProductDatabase(context);
        productDatabase.open();

        // fake data
        User user = userDataSource.getUserById(1);
        globalVariable.setAuthUser(user);

        if (globalVariable.getAuthUser() != null) {
            // Get cart by user who login successful
            User currentUser = globalVariable.getAuthUser();
            cartList.clear();
            CartDatasource cartDatasource = new CartDatasource(context);
            cartDatasource.open();
            cartList.addAll(cartDatasource.findByUser(currentUser.getId(), productDatabase, userDataSource));
            storeProductMap = cartList.stream()
                    .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getStore().getName()));

            // Set value for component when data
            tv_cart_totalPrice.setText(getTotalOfCartIsSelected());
            btn_cart_order.setEnabled(cartList.size() > 0);
            int selectingCart = (int) cartList.stream().filter(Cart::isChosen).count();
            cb_cart_total.setText(String.format(cartMessage, cartList.size()));
            cb_cart_total.setChecked(isAllSelected(cartList));
            btn_cart_order.setText(String.format(btnOrderMessage, selectingCart));
            storeAdapter = new StoreRecycleAdapter(storeProductMap, context, this);
            storeRecycle.setAdapter(storeAdapter);
            storeAdapter.notifyDataSetChanged();
        }
    }

    private boolean isAllSelected(List<Cart> cartList) {
        if (cartList.size() == 0) {
            return false ;
        }else {
            boolean check = true ;
            for (Cart cart : cartList ) {
                if (!cart.isChosen()) {
                    check = false;
                    break;
                }
            }
            return check;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        readDb();
    }

    private void setControl (View view) {
        cb_cart_total = view.findViewById(R.id.cb_cart_total);
        storeRecycle = view.findViewById(R.id.cart_recycleView);
        tv_cart_totalPrice = view.findViewById(R.id.tv_cart_totalPrice);
        btn_delete_total_carts = view.findViewById(R.id.btn_delete_total_carts);
        btn_cart_order = view.findViewById(R.id.btn_cart_order);
        builder = new AlertDialog.Builder(context);
    }


    private String getTotalOfCartIsSelected() {
        double totalPrice = 0 ;
        if (cartList.size() > 0) {
            for (Cart cart: cartList) {
                if (cart.getProduct() != null && cart.isChosen()) {
                    totalPrice+= (cart.getProduct().getPrice() * cart.getQuantity()) ;
                }
            }
        }
        return PriceFormatter.formatDouble(totalPrice);
    }
}