package com.example.siki.activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TotalProductMonthSold extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    Spinner spn;
    BarChart barChart;
    List<String> dataMonth = new ArrayList<>();
    Button pdfGen;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_product_month_sold);
        setControl();
        SQLiteDatabase db;
        SikiDatabaseHelper helper;
        helper = new SikiDatabaseHelper(this);
        db = helper.getWritableDatabase();
//        initData();
        setEvent();
        chartSetting();
        generateChart();
    }

    private void chartSetting() {
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);
        yAxis.setTextSize(14f);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setTextSize(14f);
        barChart.getLegend().setEnabled(false);
        barChart.setExtraBottomOffset(10f);
    }

    private void generateChart() {
        List<String> xValues = new ArrayList<>();
        String month = spn.getSelectedItem().toString();
        StatisticalQuery statisticalQuery = new StatisticalQuery(this);
        statisticalQuery.open();
        List<StatisticalModel> data = statisticalQuery.getProductSoldByMonth(month);
        statisticalQuery.close();
        barChart.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i<data.size();i++) {
            entries.add(new BarEntry(i, data.get(i).getQuantity().floatValue()));
            xValues.add(data.get(i).getTitle());
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Product");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextSize(14f);


        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(true);

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        barChart.invalidate();
    }

    public void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        Bitmap bitmapLogo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap scaleLogo = Bitmap.createScaledBitmap(bitmapLogo, 90, 90, false);

        canvas.drawBitmap(scaleLogo, 5, 10, paint);

        paint.setTextSize(24f);
        paint.setColor(Color.BLUE);
        canvas.drawText("TRANG THƯƠNG MẠI ĐIỆN TỬ SIKI", 90, 45, paint);

        paint.setTextSize(16f);
        paint.setColor(Color.BLACK);
        canvas.drawText("THỐNG KÊ SẢN PHẨM BÁN DƯỢC THÁNG "+spn.getSelectedItem().toString(),100,100, paint);

        Bitmap bitmap = Bitmap.createScaledBitmap(barChart.getChartBitmap(), 550,650, false);

        canvas.drawBitmap(bitmap, 10, 150, paint);

        pdfDocument.finishPage(page);

        File file = new File(getExternalFilesDir(null), "MyDDF.pdf");
        try {

            pdfDocument.writeTo(new FileOutputStream(file));


            Toast.makeText(this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {

            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private void setEvent() {
        StatisticalQuery statisticalQuery = new StatisticalQuery(this);
        statisticalQuery.open();
        dataMonth = statisticalQuery.getAllMonthOrderSuccess();
        statisticalQuery.close();
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dataMonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateChart();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pdfGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF();
            }
        });
    }

    private void setControl() {
        spn = findViewById(R.id.spnMonth);
        barChart = findViewById(R.id.bar_chart);
        pdfGen = findViewById(R.id.xuatpdf);
    }

    private void initData(){
        UserDataSource userDataSource = new UserDataSource(this);
        userDataSource.open();
        userDataSource.insertUser(new User(1,"Huy","Nguyen","32F10","01234567890","male","06-04-2002","none","a@gmail.com"));
        userDataSource.close();

        ProductDatabase productDatabase = new ProductDatabase(this);
        productDatabase.open();
        productDatabase.addProduct(new Product(1L, "Iphone","https://th.bing.com/th/id/OIP.lochyvMcAayefCqjuf0cagHaHa?rs=1&pid=ImgDetMain",10000000d, 100, null));
        productDatabase.addProduct(new Product(2L, "Samsum","https://th.bing.com/th/id/OIP.lochyvMcAayefCqjuf0cagHaHa?rs=1&pid=ImgDetMain",10000000d, 100, null));
        productDatabase.addProduct(new Product(3L, "Nokia","https://th.bing.com/th/id/OIP.lochyvMcAayefCqjuf0cagHaHa?rs=1&pid=ImgDetMain",10000000d, 100, null));
        productDatabase.close();

        OrderDataSource orderDataSource = new OrderDataSource(this);
        orderDataSource.open();
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","25-01-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","25-02-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","25-06-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","25-05-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","07-01-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","06-02-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","12-03-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","17-04-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","23-05-2024", OrderStatus.Success.toString(),1);
        orderDataSource.createOrder("01234567890","32F10","Nguyen Thanh Huy","Ok","23-06-2024", OrderStatus.Success.toString(),1);
        orderDataSource.close();

        OrderDetailDatasource orderDetailDatasource = new OrderDetailDatasource(this);
        orderDetailDatasource.open();
        orderDetailDatasource.save(1L,1L,133, 10000000d);
        orderDetailDatasource.save(2L,1L,111, 10000000d);
        orderDetailDatasource.save(3L,1L,112, 10000000d);

        orderDetailDatasource.save(1L,2L,200, 12000000d);
        orderDetailDatasource.save(2L,2L,343, 10000000d);

        orderDetailDatasource.save(1L,3L,250, 14000000d);
        orderDetailDatasource.save(2L,3L,234, 10000000d);
        orderDetailDatasource.save(3L,3L,231, 10000000d);

        orderDetailDatasource.save(1L,4L,515, 50000000d);

        orderDetailDatasource.save(1L,5L,135, 34000000d);
        orderDetailDatasource.save(3L,5L,100, 10000000d);
        orderDetailDatasource.save(2L,5L,100, 10000000d);

        orderDetailDatasource.save(1L,6L,125, 23000000d);
        orderDetailDatasource.save(2L,6L,122, 10000000d);
        orderDetailDatasource.save(3L,6L,133, 10000000d);

        orderDetailDatasource.save(2L,7L,15, 45000000d);
        orderDetailDatasource.save(3L,7L,100, 10000000d);
        orderDetailDatasource.save(1L,7L,144, 10000000d);

        orderDetailDatasource.save(1L,8L,923, 45000000d);
        orderDetailDatasource.save(2L,8L,34, 45000000d);
        orderDetailDatasource.save(3L,8L,123, 45000000d);

        orderDetailDatasource.save(3L,9L,275, 14000000d);
        orderDetailDatasource.save(2L,9L,111, 10000000d);
        orderDetailDatasource.save(1L,9L,222, 10000000d);

        orderDetailDatasource.save(3L,10L,150, 24000000d);
        orderDetailDatasource.save(1L,10L,100, 10000000d);
        orderDetailDatasource.close();
    }
}