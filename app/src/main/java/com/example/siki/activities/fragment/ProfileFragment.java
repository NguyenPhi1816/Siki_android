package com.example.siki.activities.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siki.R;
import com.example.siki.activities.ChangePasswordActivity;
import com.example.siki.activities.HomeActivity;
import com.example.siki.activities.OrderEditActivity;
import com.example.siki.activities.OrderManagementActivity;
import com.example.siki.activities.UserOrderDetailActivity;
import com.example.siki.activities.UserOrdersActivity;
import com.example.siki.model.Account;
import com.example.siki.model.User;
import com.example.siki.service.AccountService;
import com.example.siki.variable.GlobalVariable;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    Context context;
    GlobalVariable globalVariable;
    User authUser;

    public ProfileFragment(Context context, GlobalVariable globalVariable) {
        this.context = context;
        this.globalVariable = globalVariable;
    }

    Button btnBack, btnHoVaTen, btnNgaySinh, btnGioiTinh, btnSDT, btnEmail, btnDoiMK, btnUserOrders
            ,btn_my_order;
    ImageView ivUserImage;
    TextView tvHoVaTen, tvNgaySinh, tvGioiTinh, tvSDT, tvEmail;
    private Button logoutButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setControl(view);

        authUser = globalVariable.getAuthUser();

        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToChangePassView();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });

        btnUserOrders.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                redirectToUserOrdersView(authUser.getId());
            }
        });

        btn_my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectOrderManagement();
            }
        });

        /*Picasso.get().load(authUser.getAvatar()).resize(200, 200).into(ivUserImage);*/
        ivUserImage.setImageResource(R.drawable.samsung);
        tvHoVaTen.setText(authUser.getFirstName() + " " + authUser.getLastName());
        tvNgaySinh.setText(authUser.getDateOfBirth());
        tvGioiTinh.setText(authUser.getGender());
        tvSDT.setText(authUser.getPhoneNumber());
        tvEmail.setText(authUser.getEmail());
        return view;
    }

    private void redirectOrderManagement() {
        Intent intent = new Intent(context, OrderManagementActivity.class);
        startActivity(intent);
    }

    private void setControl(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        btnHoVaTen = view.findViewById(R.id.btnHoVaTen);
        btnNgaySinh = view.findViewById(R.id.btnNgaySinh);
        btnGioiTinh = view.findViewById(R.id.btnGioiTinh);
        btnSDT = view.findViewById(R.id.btnSDT);
        btnEmail = view.findViewById(R.id.btnEmail);
        btnDoiMK = view.findViewById(R.id.btnDoiMK);
        logoutButton = view.findViewById(R.id.logout_btn);
        ivUserImage = view.findViewById(R.id.user_image);
        tvHoVaTen = view.findViewById(R.id.tvHoVaTen);
        tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
        tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
        tvSDT = view.findViewById(R.id.tvSDT);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnUserOrders = view.findViewById(R.id.btnUserOrders);
        btn_my_order = view.findViewById(R.id.btn_my_order);
    }

    private void redirectToChangePassView () {
        Account account = new AccountService(context).getAccountByPhoneNumber(authUser.getPhoneNumber());
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        intent.putExtra("forgotPasswordAccount", account);
        startActivity(intent);
    }

    private void redirectToUserOrdersView (String userId) {
        Intent intent = new Intent(context, UserOrdersActivity.class);
        intent.putExtra("user_id", userId); // Đặt orderId vào Intent
        startActivity(intent);
    }

    private void logout() {
        globalVariable.setAuthenticationInfor(null, false);
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }
}