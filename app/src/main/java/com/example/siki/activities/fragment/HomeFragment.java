package com.example.siki.activities.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siki.API.CategoryApi;
import com.example.siki.API.OrderApi;
import com.example.siki.API.ProductApi;
import com.example.siki.Adapter.CategoryForHomePageRecycleAdapter;
import com.example.siki.Adapter.GridListSPApdapter;
import com.example.siki.Adapter.ListViewSearchApdapter;
import com.example.siki.Adapter.StoreRecycleAdapter;
import com.example.siki.Adapter.TopSPAdapter;
import com.example.siki.R;
import com.example.siki.customgridview.ExpandableHeightGridView;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.database.ProductDatabase;
import com.example.siki.dto.category.CategoryDto;
import com.example.siki.dto.product.ProductDto;
import com.example.siki.dto.product.ProductVariantDto;
import com.example.siki.model.Category;
import com.example.siki.model.Order;
import com.example.siki.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private Context context;
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

    List<Category> categoryList = new ArrayList<>();
    boolean focus = false;
    TopSPAdapter topSPAdapter ;
    CategoryForHomePageRecycleAdapter categoryForHomePageRecycleAdapter;

    RecyclerView rc_home_category;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        /*EdgeToEdge.enable(this);*/
        addControl(view);
        addTop10NewData();
        setCategoryList();
        addAllProduct();
        addEvent();
        categoryForHomePageRecycleAdapter = new CategoryForHomePageRecycleAdapter(categoryList, context, this);
        rc_home_category.setAdapter(categoryForHomePageRecycleAdapter);
        rc_home_category.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));


        gridView.setExpanded(true);
        topSPAdapter = new TopSPAdapter(Topsp) ;
        viewlsp.setAdapter(topSPAdapter);
        LinearLayoutManager mLayoutM = new LinearLayoutManager(context) ;
        mLayoutM.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewlsp.setLayoutManager(mLayoutM);
        viewlsp.setItemAnimator(new DefaultItemAnimator());
        viewlsp.setAdapter(topSPAdapter);

        gridlistviewsp = new GridListSPApdapter(context,R.layout.layout_gridcardsp, allProduct) ;
        gridView.setAdapter(gridlistviewsp);
        listViewSearchApdapter = new ListViewSearchApdapter(context, lvSearch_list_fillter);
        lvSearch.setAdapter(listViewSearchApdapter);
        lvSearch.setVisibility(View.GONE);

        return view;
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
        OrderApi.orderApi.getTop10Products().enqueue(new Callback<List<ProductVariantDto>>() {
            @Override
            public void onResponse(Call<List<ProductVariantDto>> call, Response<List<ProductVariantDto>> response) {
                Topsp.clear();
                List<ProductVariantDto> productVariantDtos = response.body();
                List<Product> products = productVariantDtos.stream().map(productVariantDto -> new Product(productVariantDto)).collect(Collectors.toList());
                Topsp.addAll(products);
                allProduct.addAll(products);
                lvSearch_list_fillter.addAll(products);
                topSPAdapter.notifyDataSetChanged();
                gridlistviewsp.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ProductVariantDto>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setCategoryList() {
        CategoryApi.categoryApi.findAllParents().enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                List<CategoryDto> categoryDtos = response.body();
                List<Category> categories = categoryDtos.stream().map(categoryDto -> {
                    Category category = new Category();
                    category.setId(categoryDto.getId());
                    category.setName(categoryDto.getName());
                    category.setDescription("");
                    category.setImage(categoryDto.getImage());
                    return category;
                }).collect(Collectors.toList());

                categoryList.addAll(categories);
                categoryForHomePageRecycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void addAllProduct(){

      /*  ProductApi.productApi.getRecommendProducts().enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                allProduct.clear();
                List<ProductDto> productDtos = response.body();
                List<Product> products = productDtos.stream().map(productDto -> new Product(productDto)).collect(Collectors.toList());

                gridlistviewsp.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT);
            }
        });*/
    }

    private void addControl(View view) {
        rc_home_category = view.findViewById(R.id.rc_home_category);
        viewlsp = view.findViewById(R.id.viewtopsp) ;
        gridView =  view.findViewById(R.id.gridlistsp) ;
        lvSearch = view.findViewById(R.id.listSearch);
        searchView = view.findViewById(R.id.search);
        scrollMain = view.findViewById(R.id.scrollMain);
    }
}