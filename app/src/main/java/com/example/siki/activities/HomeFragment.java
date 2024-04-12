package com.example.siki.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.siki.Adapter.GridListSPApdapter;
import com.example.siki.Adapter.TopSPAdapter;
import com.example.siki.R;
import com.example.siki.customgridview.ExpandableHeightGridView;
import com.example.siki.model.Product;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context context;
    ArrayList<Product> Topsp = new ArrayList<>();
    RecyclerView viewlsp ;
    ExpandableHeightGridView gridView ;
    GridListSPApdapter gridlistviewsp ;
    TopSPAdapter topSPAdapter ;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        addControl(view);
        addData();
        gridView.setExpanded(true);
        topSPAdapter = new TopSPAdapter(Topsp) ;
        viewlsp.setAdapter(topSPAdapter);
        LinearLayoutManager mLayoutM = new LinearLayoutManager(context) ;
        mLayoutM.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewlsp.setLayoutManager(mLayoutM);
        viewlsp.setItemAnimator(new DefaultItemAnimator());
        viewlsp.setAdapter(topSPAdapter);
        gridlistviewsp = new GridListSPApdapter(context, R.layout.layout_gridcardsp, Topsp) ;
        gridView.setAdapter(gridlistviewsp);
        return view;
    }

    private void addData() {
        Topsp.add(new Product(1L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(2L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(3L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(4L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(5L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(6L,"Card 4090","",4800000d,200,null));
    }
    private void addControl(View view) {
        viewlsp = view.findViewById(R.id.viewtopsp) ;
        gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridlistsp) ;
    }
}