package com.example.siki.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.Adapter.PaymentRecycleAdapter;
import com.example.siki.R;
import com.example.siki.database.CartDatasource;
import com.example.siki.database.OrderDataSource;
import com.example.siki.database.OrderDetailDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Cart;
import com.example.siki.model.User;
import com.example.siki.utils.PriceFormatter;
import com.example.siki.variable.GlobalVariable;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentActivity extends AppCompatActivity {
    private List<Cart> selectingCarts = new ArrayList<>() ;
    private TextView paymentTotal, tv_payment_userAddress, tv_payment_note, tv_title,
            tv_payment_fullname, tv_payment_phonenumber;
    private Button btn_note_cancel, btn_note_confirm, btn_payment_success;
    private EditText ed_note;
    private RecyclerView paymentRecycle;
    private ImageView iv_edit_address, iv_note;
    private Button btn_back_to_cart, btn_payment_createOrder;

    private List<String> paymentTypes = new ArrayList<>();

    private ArrayAdapter paymentTypeAdapter;
    private Spinner spinner_paymentType;



    private UserDataSource userDataSource;
    private PaymentRecycleAdapter paymentRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setControl();
        setEvent();
    }

    private void setEvent() {
        userDataSource = new UserDataSource(this);
        userDataSource.open();
        paymentTotal.setText(getTotalPricePayment());
        btn_back_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                intent.putExtra("fragment", R.id.nav_cart);
                startActivity(intent);
            }
        });

        iv_edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable globalVariable = (GlobalVariable) getApplication();
                Intent intent = new Intent(PaymentActivity.this, AddressForm.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", globalVariable.getAuthUser());
                intent.putExtra("user", bundle);
                startActivity(intent);
            }
        });

        btn_payment_createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

        iv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentNote = tv_payment_note.getText().toString().trim();
                showCustomDialog(currentNote);
            }
        });

        paymentRecycleAdapter = new PaymentRecycleAdapter(selectingCarts);
        paymentRecycle.setAdapter(paymentRecycleAdapter);
        paymentRecycle.setLayoutManager(new GridLayoutManager(this, 1));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveOrder() {
        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        OrderDataSource orderDataSource = new OrderDataSource(this);
        orderDataSource.open();
        OrderDetailDatasource orderDetailDatasource = new OrderDetailDatasource(this);
        orderDetailDatasource.open();
        ProductDatabase productDatabase = new ProductDatabase(this);
        productDatabase.open();
        CartDatasource cartDatasource = new CartDatasource(this);
        cartDatasource.open();
        if (globalVariable.getAuthUser() != null) {
            User currentUser = globalVariable.getAuthUser();
            String receiverPhoneNumber = currentUser.getPhoneNumber().trim();
            String receiverAddress= currentUser.getAddress();
            String receiverName = currentUser.getFirstName().concat(" ").concat(currentUser.getLastName());
            String note = tv_payment_note.getText().toString().trim();
            String userId = currentUser.getId();
            Long orderId = orderDataSource.createOrder(receiverPhoneNumber, receiverAddress, receiverName, note, userId);
            if (orderId != -1) {
                selectingCarts.forEach(cart -> {
                    orderDetailDatasource.save(cart.getProduct().getId(),
                            orderId, cart.getQuantity(), cart.getProduct().getPrice());
                    int updateQuantity = cart.getProduct().getQuantity() - cart.getQuantity();
                    productDatabase.updateQuantityProduct(cart.getProduct().getId(), updateQuantity);
                    cartDatasource.remove(cart.getId());
                });
                showSuccessMessage(orderId);

            }
        }
    }
    private void showConfirmDialog () {
        PopupDialog.getInstance(this)
                .standardDialogBuilder()
                .createIOSDialog()
                .setHeading("Thanh toán")
                .setDescription("Bạn có chắc chắn muốn đặt hàng không?")
                .build(new StandardDialogActionListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onPositiveButtonClicked(Dialog dialog) {
                        saveOrder();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void navigateToUserOrderDetailActivity(Long orderId) {
        Intent intent = new Intent(PaymentActivity.this, UserOrderDetailActivity.class);
        intent.putExtra("order_id", orderId); // Đặt orderId vào Intent
        startActivity(intent);
    }

    private void showSuccessMessage(Long orderId) {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading("Thành công")
                .setDescription("Bạn đã đặt hàng thành công")
                .setActionButtonText("Xem đơn hàng")
                .build(dialog -> {
                    dialog.dismiss();
                    navigateToUserOrderDetailActivity(orderId);
                })
                .show();
    }

    private void redirectToHomePage() {
        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
        intent.putExtra("fragment", R.id.nav_home);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserAddress();
    }

    private void getUserAddress() {
        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        userDataSource = new UserDataSource(this);
        userDataSource.open();

        if (globalVariable.getAuthUser() != null) {
            User currentUser = globalVariable.getAuthUser();
            String ho = currentUser.getFirstName();
            String ten = currentUser.getLastName();
            String address = currentUser.getAddress();
            String sdt = currentUser.getPhoneNumber();

            String fullName = ho.concat(" ").concat(ten);
            tv_payment_userAddress.setText(address);
            tv_payment_fullname.setText(fullName);
            tv_payment_phonenumber.setText(sdt);

            selectingCarts.clear();
            CartDatasource cartDatasource = new CartDatasource(this);
            cartDatasource.open();
            ProductDatabase productDatabase = new ProductDatabase(this);
            productDatabase.open();
            List<Cart> cartList = cartDatasource.findByUser(currentUser.getId(), productDatabase, userDataSource);
            cartList.forEach(cart -> {
                if (cart.isChosen()) {
                    selectingCarts.add(cart);
                }
            });
            paymentTotal.setText(getTotalPricePayment());
        }
    }

    private String getTotalPricePayment() {
        if (selectingCarts.size() > 0) {
            double total = selectingCarts.stream()
                    .mapToDouble(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                    .reduce(0.0, (accumulator, price) -> accumulator + price);
            return PriceFormatter.formatDouble(total);
        }
        return "";
    }

    private void setControl () {
        tv_payment_phonenumber = findViewById(R.id.tv_payment_phonenumber);
        tv_payment_fullname = findViewById(R.id.tv_payment_fullname);
        tv_payment_note = findViewById(R.id.tv_payment_note);
        iv_note = findViewById(R.id.iv_note);
        spinner_paymentType = findViewById(R.id.spinner_paymentType);
        btn_back_to_cart = findViewById(R.id.btn_back_to_cart);
        paymentRecycle = findViewById(R.id.rv_payment);
        paymentTotal = findViewById(R.id.tv_payment_total);
        iv_edit_address = findViewById(R.id.iv_edit_address);
        tv_payment_userAddress = findViewById(R.id.tv_payment_address);
        btn_payment_createOrder = findViewById(R.id.btn_payment_createOrder);
        Intent intent = getIntent();
        Bundle selectingCartBundle = intent.getBundleExtra("selectingCarts");
        if (selectingCartBundle != null) {
            selectingCarts.clear();
            selectingCarts.addAll((Collection<? extends Cart>) selectingCartBundle.getSerializable("selectingCarts"));
        }
        initPaymentTypeData();
        paymentTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, paymentTypes);
        spinner_paymentType.setAdapter(paymentTypeAdapter);
    }
    private void initPaymentTypeData() {
        paymentTypes.add("Tiền mặt");
        paymentTypes.add("Chuyển khoản");
    }

    private void showCustomDialog(String currentNote) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.layout_dialog_update_note);
        btn_note_cancel = dialog.findViewById(R.id.btn_note_cancel);
        btn_note_confirm = dialog.findViewById(R.id.btn_note_confirm);
        ed_note = dialog.findViewById(R.id.ed_note);
        if (currentNote != "") {
            ed_note.setText(currentNote);
        }
        btn_note_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = ed_note.getText().toString().trim();
                if (note != "") {
                    tv_payment_note.setText(note);
                } else {

                }
                dialog.dismiss();
            }
        });
        btn_note_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}