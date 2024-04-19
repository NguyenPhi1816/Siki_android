package com.example.siki.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.Adapter.GridListSPApdapter;
import com.example.siki.Adapter.ListViewSearchApdapter;
import com.example.siki.Adapter.TopSPAdapter;
import com.example.siki.R;
import com.example.siki.customgridview.ExpandableHeightGridView;
import com.example.siki.database.ProductDatabase;
import com.example.siki.model.Product;

import java.util.ArrayList;

public class TrangChuActivity extends AppCompatActivity {

    ArrayList<Product> Topsp = new ArrayList<>();
    RecyclerView viewlsp ;
    ExpandableHeightGridView gridView ;
    GridListSPApdapter gridlistviewsp ;

    ListViewSearchApdapter listViewSearchApdapter;
    SearchView searchView;
    ListView lvSearch;
    NestedScrollView scrollMain;
    ArrayList<Product> allProduct = new ArrayList<>();
    ArrayList<Product> lvSearch_list_fillter = new ArrayList<>();
    boolean focus = false;
    TopSPAdapter topSPAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);
        intitDatabaseData();
        addControl();
        addTop10NewData();
        addAllProduct();
        addEvent();
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

        lvSearch_list_fillter = allProduct;
        listViewSearchApdapter = new ListViewSearchApdapter(this, lvSearch_list_fillter);
        lvSearch.setAdapter(listViewSearchApdapter);
        lvSearch.setVisibility(View.GONE);
    }

    private void addEvent() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                listViewSearchApdapter.filter(text);
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (focus) {
                    lvSearch.setVisibility(View.GONE);
                    scrollMain.setVisibility(View.VISIBLE);
                    focus = false;
                }
                else {
                    listViewSearchApdapter.filter("");
                    lvSearch.setVisibility(View.VISIBLE);
                    scrollMain.setVisibility(View.GONE);
                    focus = true;
                }
            }
        });
    }

    private void addTop10NewData() {
        ProductDatabase db = new ProductDatabase(this);
        db.open();
        Topsp.addAll(db.readTop10New());
        db.close();
    }

    private void addAllProduct(){
        ProductDatabase db = new ProductDatabase(this);
        db.open();
        allProduct.addAll(db.readDb());
        db.close();
    }

    private void intitDatabaseData(){
        ProductDatabase db = new ProductDatabase(this);
        db.open();
        db.addProduct(new Product(1L,"Card 4090","https://product.hstatic.net/200000722513/product/1024_db714ed2cb1e4e6fa1d6ebec4cd92fb9_af81e6f9d5294638a9587f509a69c660_1024x1024.jpg",4800000d,200, null));
        db.addProduct(new Product(2L,"Iphone 15pro","https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/iphone-card-40-iphone15prohero-202309_FMT_WHH?wid=508&hei=472&fmt=p-jpg&qlt=95&.v=1693086369818",1500000d,200, null));
        db.addProduct(new Product(3L,"Can cau ca","https://img.lazcdn.com/g/p/0c2a6e13ce8653ce5268d9c9d28d5823.jpg_720x720q80.jpg",200000d,200,null));
        db.addProduct(new Product(4L,"May hut bui","tekavietnam.vn/Uploads/may-hut-bui-electrolux-zap9910-Electrolux%20ZAP9910.PNG",1200000d,200, null));
        db.addProduct(new Product(5L,"Bep hong ngoai","https://bizweb.dktcdn.net/thumb/grande/100/304/653/products/hong-ngoai-80dm-jpg.jpg?v=1543307446560",600000d,200, null));
        db.addProduct(new Product(6L,"Laptop","https://i5.walmartimages.com/seo/HP-Stream-14-Laptop-Intel-Celeron-N4000-4GB-SDRAM-32GB-eMMC-Office-365-1-yr-Royal-Blue_4f941fe6-0cf3-42af-a06c-7532138492fc_2.cb8e85270e731cb1ef85d431e49f0bf2.jpeg",1200000d,200, null));
        db.close();
    }

    private void addControl() {
        viewlsp = findViewById(R.id.viewtopsp) ;
        gridView = (ExpandableHeightGridView) findViewById(R.id.gridlistsp) ;
        lvSearch = findViewById(R.id.listSearch);
        searchView = findViewById(R.id.search);
        scrollMain = findViewById(R.id.scrollMain);
    }
}