package com.example.siki.activities.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.siki.R;


public class ProfileFragment extends Fragment {
    Context context;

    public ProfileFragment(Context context) {
        this.context = context;
    }

    Button btnBack, btnCart, btnHoVaTen, btnNickname, btnNgaySinh, btnGioiTinh, btnQuocTich, btnSDT, btnEmail, btnDoiMK;
    TextView tvHoVaTen, tvNickName, tvNgaySinh, tvGioiTinh, tvQuocTich, tvSDT, tvEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setControl(view);
        return view;
    }
    private void setControl(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        btnCart = view.findViewById(R.id.btnCart);
        btnHoVaTen = view.findViewById(R.id.btnHoVaTen);
        btnNickname = view.findViewById(R.id.btnNickname);
        btnNgaySinh = view.findViewById(R.id.btnNgaySinh);
        btnGioiTinh = view.findViewById(R.id.btnGioiTinh);
        btnQuocTich = view.findViewById(R.id.btnQuocTich);
        btnSDT = view.findViewById(R.id.btnSDT);
        btnEmail = view.findViewById(R.id.btnEmail);
        btnDoiMK = view.findViewById(R.id.btnDoiMK);

        tvHoVaTen = view.findViewById(R.id.tvHoVaTen);
        tvNickName = view.findViewById(R.id.tvNickname);
        tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
        tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
        tvQuocTich = view.findViewById(R.id.tvQuocTich);
        tvSDT = view.findViewById(R.id.tvSDT);
        tvEmail = view.findViewById(R.id.tvEmail);
    }
}