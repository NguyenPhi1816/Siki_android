package com.example.siki.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.API.CartApi;
import com.example.siki.R;
import com.example.siki.activities.fragment.CartFragment;
import com.example.siki.database.CartDatasource;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.utils.PriceFormatter;
import com.example.siki.variable.GlobalVariable;
import com.saadahmedev.popupdialog.PopupDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRecycleAdapter extends RecyclerView.Adapter<CartRecycleAdapter.CartHolder> {
    private List<Cart> cartList ;

    private Context context;

    private CartFragment cartFragment;

    private GlobalVariable globalVariable;

    public CartRecycleAdapter(List<Cart> cartList, Context context, CartFragment cartFragment, GlobalVariable globalVariable) {
        this.cartList = cartList;
        this.context = context;
        this.cartFragment = cartFragment;
        this.globalVariable = globalVariable;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCart = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartHolder(viewCart);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = cartList.get(position);
        Product product = cart.getProduct();
        int currentQuantity = cart.getQuantity();
        holder.btn_cart_minus.setEnabled(cart.getQuantity() > 1);
        holder.btn_cart_minus.setHovered(cart.getQuantity() > 1);
        holder.cartCheckbox.setChecked(cart.isChosen());
        Picasso.get().load(product.getImagePath()).into(holder.cartImage);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(PriceFormatter.formatDouble(product.getPrice() * cart.getQuantity()));
        holder.tv_cart_quantity.setText(currentQuantity+"");
        CartDatasource cartDatasource = new CartDatasource(context);
        cartDatasource.open();

        holder.cartCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.cartCheckbox.isChecked();
                holder.cartCheckbox.setChecked(isChecked);
                CartApi.cartApi.updateCartSelection(cart.getId(), isChecked, globalVariable.getAccess_token()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        cartFragment.readDb();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT);
                    }
                });
            }
        });
        holder.btn_cart_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_cart_quantity.setText((currentQuantity + 1)+"");

                CartApi.cartApi.updateCartQuantity(cart.getId(), currentQuantity + 1, globalVariable.getAccess_token()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        cartFragment.readDb();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT);

                    }
                });
            }
        });

        holder.btn_cart_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_cart_quantity.setText((currentQuantity - 1)+"");
                CartApi.cartApi.updateCartQuantity(cart.getId(), currentQuantity - 1, globalVariable.getAccess_token()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        cartFragment.readDb();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT);

                    }
                });
            }
        });

        holder.delete_total_carts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xoá sản phẩm").setMessage("Bạn có muốn xóa sản phẩm đang chọn?")
                        .setCancelable(true).setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                CartApi.cartApi.deleteCartById(cart.getId(), globalVariable.getAccess_token()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        showAlertMessage("Xóa sản phẩm thành công");
                                        cartFragment.readDb();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT);

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

    }

    private void showAlertMessage(String message) {
        PopupDialog.getInstance(context)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading("Thành công")
                .setDescription(message)
                .build(Dialog::dismiss)
                .show();
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartHolder extends RecyclerView.ViewHolder{

        ImageView cartImage;
        TextView productName, productPrice;

        Button btn_cart_minus, tv_cart_quantity, btn_cart_add, delete_total_carts;

        CheckBox cartCheckbox;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            cartCheckbox = itemView.findViewById(R.id.cb_cart);
            cartImage = itemView.findViewById(R.id.cart_image);
            productName = itemView.findViewById(R.id.cart_name);
            productPrice = itemView.findViewById(R.id.cart_price);
            btn_cart_minus = itemView.findViewById(R.id.btn_cart_minus);
            tv_cart_quantity = itemView.findViewById(R.id.tv_cart_quantity);
            btn_cart_add = itemView.findViewById(R.id.btn_cart_add);
            delete_total_carts = itemView.findViewById(R.id.delete_total_carts);
        }
    }
}
