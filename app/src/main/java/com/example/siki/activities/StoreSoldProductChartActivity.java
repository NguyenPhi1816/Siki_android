package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
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
import com.example.siki.database.StoreDataSource;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class StoreSoldProductChartActivity extends AppCompatActivity {


    LineChart mplineChart;
    Button btn_pdf, btnShowList, btnBack;
    TextView tvThangSelected, tvNamSelected, tvThangClicker, tvNamClicker, tvStoreName;
    ImageView imageStore;
    long storeid;
    private List<String> xValue = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_sold_product_chart);
        storeid = 2;
        SetControl();
        SetEvent();
        SetDefaul();
        initData();
        SQLiteDatabase db;
        SikiDatabaseHelper helper;
        helper= new SikiDatabaseHelper(this);
        db = helper.getWritableDatabase();
    }

    private void SetDefaul() {
        ChartSetting();
        ChartSettingMonthData();
        addDataMonth();
        tvNamSelected.setBackgroundColor(Color.WHITE);
    }

    private void SetEvent() {
        tvThangClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartSettingMonthData();
                addDataMonth();
                tvThangSelected.setBackgroundColor(getColor(R.color.main_color));
                tvNamSelected.setBackgroundColor(Color.WHITE);
            }
        });
        tvNamClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartSettingYearData();
                addDataYear();
                tvNamSelected.setBackgroundColor(getColor(R.color.main_color));
                tvThangSelected.setBackgroundColor(Color.WHITE);
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
        tvStoreName = findViewById(R.id.store_name);
        btnShowList = findViewById(R.id.btnShow_list);
        tvThangSelected = findViewById(R.id.thangSelected);
        tvNamSelected = findViewById(R.id.namSelected);
        tvThangClicker = findViewById(R.id.tvThangClicker);
        tvNamClicker = findViewById(R.id.tvNamClicker);
        imageStore = findViewById(R.id.image_store);
        btnBack = findViewById(R.id.btn_back);
        btn_pdf = findViewById(R.id.btnXuat_pdf);
    }

    private void ChartSetting() {
        YAxis yAxis = mplineChart.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = mplineChart.getLegend();

        List<LegendEntry> legendEntries = new ArrayList<>();
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "Thống kê trong 6 tháng gần nhất";
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
        StatisticalQuery statisticalQuery =  new StatisticalQuery(this);
        statisticalQuery.open();
        xValue = statisticalQuery.getAllMonthOrderSuccess(storeid);
        XAxis xAxis = mplineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);
    }

    private void ChartSettingYearData() {
        StatisticalQuery statisticalQuery =  new StatisticalQuery(this);
        statisticalQuery.open();
        xValue = statisticalQuery.getAllYearOrderSuccess(storeid);
        XAxis xAxis = mplineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);
    }

    private void addDataMonth() {
        StatisticalQuery statisticalQuery =  new StatisticalQuery(this);
        statisticalQuery.open();
        List<String> monthOder = statisticalQuery.getAllMonthOrderSuccess(storeid);
        List<Long> listID = statisticalQuery.getAllIDProductStore(storeid);
        List<Integer> colorLines = generateUniqueColors(listID.size());
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();

        for (int i = 0;i<listID.size();i++){
            ArrayList<Entry> entries = new ArrayList<Entry>();

            List<StatisticalModel> listMonthData = statisticalQuery.getProductSellMonthData(listID.get(i));
            for (int i_entr = 0;i_entr<monthOder.size();i_entr++){
                if (monthOder.get(i_entr).equals(listMonthData.get(i_entr).getTitle())) {
                    entries.add(new Entry(i_entr, listMonthData.get(i).getQuantity().floatValue()));
                }
                else {
                    entries.add(new Entry(i_entr, 0));
                }
            }

            ProductDatabase productDatabase = new ProductDatabase(this);
            productDatabase.open();
            Product product =  productDatabase.findById(listID.get(i));
            productDatabase.close();
            LineDataSet lineDataSet1 = new LineDataSet(entries, product==null?"NULL":product.getName());
            lineDataSet1.setLineWidth(3);
            lineDataSet1.setFormSize(15f);
            lineDataSet1.setColor(colorLines.get(i));
            iLineDataSets.add(lineDataSet1);
        }

        LineData lineData = new LineData(iLineDataSets);


        mplineChart.setData(lineData);
        mplineChart.invalidate();


    }

    private void addDataYear() {
        StatisticalQuery statisticalQuery =  new StatisticalQuery(this);
        statisticalQuery.open();
        List<String> yearOder = statisticalQuery.getAllYearOrderSuccess(storeid);
        List<Long> listID = statisticalQuery.getAllIDProductStore(storeid);
        List<Integer> colorLines = generateUniqueColors(listID.size());
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();

        for (int i = 0;i<listID.size();i++){
            ArrayList<Entry> entries = new ArrayList<Entry>();

            List<StatisticalModel> listYearData = statisticalQuery.getProductSellYearData(listID.get(i));
            for (int i_entr = 0;i_entr<yearOder.size();i_entr++){
                if (yearOder.get(i_entr).equals(listYearData.get(i_entr).getTitle())) {
                    entries.add(new Entry(i_entr, listYearData.get(i).getQuantity().floatValue()));
                }
                else {
                    entries.add(new Entry(i_entr, 0));
                }
            }

            ProductDatabase productDatabase = new ProductDatabase(this);
            productDatabase.open();
            Product product =  productDatabase.findById(listID.get(i));
            productDatabase.close();
            LineDataSet lineDataSet1 = new LineDataSet(entries, product==null?"NULL":product.getName());
            lineDataSet1.setLineWidth(3);
            lineDataSet1.setFormSize(15f);
            lineDataSet1.setColor(colorLines.get(i));
            iLineDataSets.add(lineDataSet1);
        }
        LineData lineData = new LineData(iLineDataSets);


        mplineChart.setData(lineData);
        mplineChart.invalidate();
    }

    private void initData(){
        UserDataSource userDataSource = new UserDataSource(this);
        userDataSource.open();
        userDataSource.insertUser(new User(1,"Huy","Nguyen","32F10","01234567890","male","06-04-2002","none","a@gmail.com"));
        userDataSource.close();

        StoreDataSource storeDataSource = new StoreDataSource(this);
        storeDataSource.open();
        storeDataSource.createStore(2L, "HuyStore",1L,"Shop pro",null,"open",null);

        ProductDatabase productDatabase = new ProductDatabase(this);
        productDatabase.open();
        productDatabase.addProduct(new Product(1L, "Iphone","https://th.bing.com/th/id/OIP.lochyvMcAayefCqjuf0cagHaHa?rs=1&pid=ImgDetMain",10000000d, 100, null));
        productDatabase.addProduct(new Product(2L, "Samsum","https://th.bing.com/th/id/OIP.lochyvMcAayefCqjuf0cagHaHa?rs=1&pid=ImgDetMain",10000000d, 100, null));
        productDatabase.addProduct(new Product(3L, "Nokia","https://th.bing.com/th/id/OIP.lochyvMcAayefCqjuf0cagHaHa?rs=1&pid=ImgDetMain",10000000d, 100, null));
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
        orderDetailDatasource.save(2L,1L,90, 120000000d);
        orderDetailDatasource.save(3L,1L,20, 13000000d);

        orderDetailDatasource.save(1L,2L,200, 12000000d);
        orderDetailDatasource.save(2L,2L,150, 125000000d);

        orderDetailDatasource.save(1L,3L,250, 14000000d);
        orderDetailDatasource.save(3L,3L,25, 13500000d);

        orderDetailDatasource.save(1L,4L,515, 50000000d);
        orderDetailDatasource.save(2L,4L,65, 123400000d);

        orderDetailDatasource.save(1L,5L,135, 34000000d);
        orderDetailDatasource.save(2L,5L,120, 9000000d);
        orderDetailDatasource.save(3L,5L,200, 850000d);

        orderDetailDatasource.save(1L,6L,125, 23000000d);
        orderDetailDatasource.save(2L,6L,120, 9000000d);
        orderDetailDatasource.save(3L,6L,200, 850000d);

        orderDetailDatasource.save(1L,7L,15, 45000000d);
        orderDetailDatasource.save(2L,7L,65, 123400000d);

        orderDetailDatasource.save(1L,8L,923, 45000000d);
        orderDetailDatasource.save(3L,8L,200, 850000d);

        orderDetailDatasource.save(1L,9L,275, 14000000d);
        orderDetailDatasource.save(2L,9L,120, 9000000d);

        orderDetailDatasource.save(1L,10L,150, 24000000d);
        orderDetailDatasource.save(2L,10L,120, 9000000d);

        orderDetailDatasource.close();
    }

    private List<Integer> generateUniqueColors(int count) {
        Set<Integer> colorSet = new HashSet<>();
        Random random = new Random();

        while (colorSet.size() < count) {
            int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            colorSet.add(color);
        }

        return new ArrayList<>(colorSet);
    }
}