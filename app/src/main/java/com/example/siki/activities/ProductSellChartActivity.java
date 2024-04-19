package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.siki.Adapter.StatisticalAdapter;
import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.database.OrderDetailDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.SikiDatabaseHelper;
import com.example.siki.database.StatisticalQuery;
import com.example.siki.database.UserDataSource;
import com.example.siki.enums.OrderStatus;
import com.example.siki.model.Product;
import com.example.siki.model.StatisticalModel;
import com.example.siki.model.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ProductSellChartActivity extends AppCompatActivity {

    LineChart mplineChart;
    StatisticalAdapter adapter;
    ListView listViewStatistical;
    Button btnshowChart, btnShowList, btnBack;
    LinearLayout layout_list, layout_chart;
    TextView tvSold, tvThangSelected, tvNamSelected, tvThangClicker, tvNamClicker, tvProductName;
    ImageView imageProduct;

    long productid;
    private List<String> xValue = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sell_chart);
        productid = 1;
        SetControl();
        SetEvent();
        SetDefaul();
        loadProduct(productid);
        SQLiteDatabase db;
        SikiDatabaseHelper helper;
        helper= new SikiDatabaseHelper(this);
        db = helper.getWritableDatabase();
    }

    private void loadProduct(long id) {
        ProductDatabase productDatabase = new ProductDatabase(this);
        productDatabase.open();
        Product product = productDatabase.findById(id);
        productDatabase.close();
        tvProductName.setText(product.getName());
        Picasso.get().load(product.getImagePath()).into(imageProduct);
    }

    private void SetDefaul() {
        ChartSetting();
        ChartSettingMonthData();
        addDataMonth();
        statisticalDataMonth();
        layout_chart.setVisibility(View.GONE);
        tvNamSelected.setBackgroundColor(Color.WHITE);
    }

    private void SetEvent() {
        tvThangClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartSettingMonthData();
                addDataMonth();
                statisticalDataMonth();
                tvThangSelected.setBackgroundColor(getColor(R.color.main_color));
                tvNamSelected.setBackgroundColor(Color.WHITE);
            }
        });
        tvNamClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartSettingYearData();
                addDataYear();
                statisticalDataYear();
                tvNamSelected.setBackgroundColor(getColor(R.color.main_color));
                tvThangSelected.setBackgroundColor(Color.WHITE);
            }
        });
        btnshowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_list.setVisibility(View.GONE);
                layout_chart.setVisibility(View.VISIBLE);
            }
        });
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_chart.setVisibility(View.GONE);
                layout_list.setVisibility(View.VISIBLE);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void SetControl() {
        mplineChart = findViewById(R.id.lineChart);
        listViewStatistical = findViewById(R.id.listStatistical);
        btnshowChart = findViewById(R.id.btnShow_chart);
        btnShowList = findViewById(R.id.btnShow_list);
        layout_list = findViewById(R.id.layout_list);
        layout_chart = findViewById(R.id.layout_chart);
        tvSold = findViewById(R.id.tvProductSold);
        tvThangSelected = findViewById(R.id.thangSelected);
        tvNamSelected = findViewById(R.id.namSelected);
        tvThangClicker = findViewById(R.id.tvThangClicker);
        tvNamClicker = findViewById(R.id.tvNamClicker);
        imageProduct = findViewById(R.id.image_prodcut);
        tvProductName = findViewById(R.id.product_name);
        btnBack = findViewById(R.id.btn_back);
    }

    private void statisticalDataMonth() {
        adapter = new StatisticalAdapter(this, product_month_data(), "sp");
        listViewStatistical.setAdapter(adapter);
        AtomicReference<Double> sum = new AtomicReference<>((double) 0);
        product_month_data().forEach(o -> sum.updateAndGet(v ->v+o.getQuantity()));
        tvSold.setText(String.format("Thống kê được %s sản phẩm", String.valueOf(sum.get())));
    }

    private void statisticalDataYear() {
        adapter = new StatisticalAdapter(this, product_year_data(), "sp");
        listViewStatistical.setAdapter(adapter);
        AtomicReference<Double> sum = new AtomicReference<>(0d);
        product_year_data().forEach(o -> sum.updateAndGet(v->v+o.getQuantity()));
        tvSold.setText(String.format("Thống kê được %s sản phẩm", String.valueOf(sum.get())));
    }

    private void ChartSetting() {
        YAxis yAxis = mplineChart.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = mplineChart.getLegend();

        List<LegendEntry> legendEntries = new ArrayList<>();
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "Số lượng bán được";
        legendEntry.formColor = Color.BLUE;

        legendEntries.add(legendEntry);
        legend.setCustom(legendEntries);
        legend.setTextSize(15);
        legend.setFormSize(15);

        mplineChart.setDrawGridBackground(false);
        mplineChart.setDrawBorders(true);
        mplineChart.setBorderColor(Color.RED);
        mplineChart.setBorderWidth(5);
    }

    private void ChartSettingMonthData() {
        xValue = product_month_data().stream().map(StatisticalModel::getTitle).collect(Collectors.toList());
        XAxis xAxis = mplineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);
    }

    private void ChartSettingYearData() {
        xValue = product_year_data().stream().map(StatisticalModel::getTitle).collect(Collectors.toList());
        XAxis xAxis = mplineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);
    }

    private ArrayList<Entry> product_year_data_chart() {
        List<StatisticalModel> listYearData = product_year_data();
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i= 0; i<listYearData.size();i++)
            entries.add(new Entry(i, listYearData.get(i).getQuantity().floatValue()));
        return entries;
    }

    private List<StatisticalModel> product_year_data() {
        List<StatisticalModel> data = new ArrayList<>();
        StatisticalQuery db = new StatisticalQuery(this);
        db.open();
        data = db.getProductSellYearData(productid); //Id san pham
        return data;
    }


    private ArrayList<Entry> product_month_data_chart() {
        List<StatisticalModel> listMonthData = product_month_data();
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i= 0; i<listMonthData.size();i++)
            entries.add(new Entry(i, listMonthData.get(i).getQuantity().floatValue()));
        return entries;
    }

    private List<StatisticalModel> product_month_data() {
        List<StatisticalModel> data = new ArrayList<>();
        StatisticalQuery db = new StatisticalQuery(this);
        db.open();
        data = db.getProductSellMonthData(productid); //Id san pham
        return data;
    }

    private void addDataMonth() {
        LineDataSet lineDataSet1 = new LineDataSet(product_month_data_chart(), "Sô lượng sản phẩm");
        lineDataSet1.setLineWidth(3);
        lineDataSet1.setFormSize(15f);
        lineDataSet1.setColor(Color.BLUE);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet1);

        LineData lineData = new LineData(iLineDataSets);


        mplineChart.setData(lineData);
        mplineChart.invalidate();


    }

    private void addDataYear() {
        LineDataSet lineDataSet1 = new LineDataSet(product_year_data_chart(), "Số lượng sản phẩm");
        lineDataSet1.setLineWidth(3);
        lineDataSet1.setFormSize(15f);
        lineDataSet1.setColor(Color.BLUE);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet1);

        LineData lineData = new LineData(iLineDataSets);


        mplineChart.setData(lineData);
        mplineChart.invalidate();
    }

    private void initData(){
        UserDataSource userDataSource = new UserDataSource(this);
        userDataSource.open();
        userDataSource.insertUser(new User(1,"Huy","Nguyen","32F10","01234567890","male","06-04-2002","none","a@gmail.com"));
        userDataSource.close();

        ProductDatabase productDatabase = new ProductDatabase(this);
        productDatabase.open();
        productDatabase.addProduct(new Product(1L, "Iphone","https://th.bing.com/th/id/OIP.lochyvMcAayefCqjuf0cagHaHa?rs=1&pid=ImgDetMain",10000000d, 100, null));
        productDatabase.close();

        OrderDataSource orderDataSource = new OrderDataSource(this);
        orderDataSource.open();
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","25-01-2020", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","25-01-2021", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","25-01-2022", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","25-12-2023", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","07-01-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","06-02-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","12-03-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","17-04-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","23-05-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","23-06-2024", OrderStatus.Success.toString(),1);
        orderDataSource.close();

        OrderDetailDatasource orderDetailDatasource = new OrderDetailDatasource(this);
        orderDetailDatasource.open();
        orderDetailDatasource.save(1L,1L,100, 10000000d);
        orderDetailDatasource.save(1L,2L,200, 12000000d);
        orderDetailDatasource.save(1L,3L,250, 14000000d);
        orderDetailDatasource.save(1L,4L,515, 50000000d);
        orderDetailDatasource.save(1L,5L,135, 34000000d);
        orderDetailDatasource.save(1L,6L,125, 23000000d);
        orderDetailDatasource.save(1L,7L,15, 45000000d);
        orderDetailDatasource.save(1L,8L,923, 45000000d);
        orderDetailDatasource.save(1L,9L,275, 14000000d);
        orderDetailDatasource.save(1L,10L,150, 24000000d);
        orderDetailDatasource.close();
    }
}