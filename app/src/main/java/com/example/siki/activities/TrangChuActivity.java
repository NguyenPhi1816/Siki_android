package com.example.siki.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.Adapter.GridListSPApdapter;
import com.example.siki.Adapter.TopSPAdapter;
import com.example.siki.R;
import com.example.siki.customgridview.ExpandableHeightGridView;
import com.example.siki.model.Product;

import java.util.ArrayList;

public class TrangChuActivity extends AppCompatActivity {

    ArrayList<Product> Topsp = new ArrayList<>();
    RecyclerView viewlsp ;
    ExpandableHeightGridView gridView ;
    GridListSPApdapter gridlistviewsp ;

    TopSPAdapter topSPAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);
        addControl();
        addData();
        gridView.setExpanded(true);
        topSPAdapter = new TopSPAdapter(Topsp) ;
        viewlsp.setAdapter(topSPAdapter);
        LinearLayoutManager mLayoutM = new LinearLayoutManager(getApplicationContext()) ;
        mLayoutM.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewlsp.setLayoutManager(mLayoutM);
        viewlsp.setItemAnimator(new DefaultItemAnimator());
        viewlsp.setAdapter(topSPAdapter);
        gridlistviewsp = new GridListSPApdapter(this,R.layout.layout_gridcardsp,Topsp) ;
        gridView.setAdapter(gridlistviewsp);



    }
    private void addData() {
        Topsp.add(new Product(1L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(2L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(3L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(4L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(5L,"Card 4090","",4800000d,200,null));
        Topsp.add(new Product(6L,"Card 4090","",4800000d,200,null));
    }
    private void addControl() {
        viewlsp = findViewById(R.id.viewtopsp) ;
        gridView = (ExpandableHeightGridView) findViewById(R.id.gridlistsp) ;
    }
}