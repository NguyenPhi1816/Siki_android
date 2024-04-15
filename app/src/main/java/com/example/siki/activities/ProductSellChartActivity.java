package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.siki.Adapter.StatisticalAdapter;
import com.example.siki.R;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.StatisticalQuery;
import com.example.siki.model.StatisticalModel;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ProductSellChartActivity extends AppCompatActivity {

    LineChart mplineChart;
    StatisticalAdapter adapter;
    ListView listViewStatistical;
    Button btnshowChart, btnShowList;
    LinearLayout layout_list, layout_chart;
    TextView tvSold, tvThangSelected, tvNamSelected, tvThangClicker, tvNamClicker;

    private List<String> xValue = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sell_chart);
        SetControl();
        SetEvent();
        SetDefaul();
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
    }

    private void statisticalDataMonth() {
        adapter = new StatisticalAdapter(this, product_month_data());
        listViewStatistical.setAdapter(adapter);
        AtomicReference<Double> sum = new AtomicReference<>((double) 0);
        product_month_data().forEach(o -> sum.updateAndGet(v -> o.getQuantity()));
        tvSold.setText(String.format("Thống kê được %s sản phẩm", String.valueOf(sum.get())));
    }

    private void statisticalDataYear() {
        adapter = new StatisticalAdapter(this, product_year_data());
        listViewStatistical.setAdapter(adapter);
        AtomicReference<Double> sum = new AtomicReference<>((double) 0);
        product_year_data().forEach(o -> sum.updateAndGet(v->o.getQuantity()));
        tvSold.setText(String.format("Thống kê được %s sản phẩm", String.valueOf(sum)));
    }

    private void ChartSetting() {
        YAxis yAxis = mplineChart.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = mplineChart.getLegend();

        List<LegendEntry> legendEntries = new ArrayList<>();
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "Product 1";
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
        xValue = Arrays.asList("11-2023", "12-2023", "1-2024", "2-2024", "3-2024", "4-2024");
        XAxis xAxis = mplineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);
    }

    private void ChartSettingYearData() {
        xValue = Arrays.asList("2020", "2021", "2022", "2023");
        XAxis xAxis = mplineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);
    }

    private ArrayList<Entry> product_year_data_chart() {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(0, 400));
        entries.add(new Entry(1, 324));
        entries.add(new Entry(2, 1234));
        entries.add(new Entry(3, 900));

        return entries;
    }

    private List<StatisticalModel> product_year_data() {
        List<StatisticalModel> data = new ArrayList<>();
        StatisticalQuery db = new StatisticalQuery(this);
        db.open();
        data = db.getProductSellYearData(1); //Id san pham
        return data;
    }


    private ArrayList<Entry> product_month_data_chart() {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(0, 31));
        entries.add(new Entry(1, 25));
        entries.add(new Entry(2, 11));
        entries.add(new Entry(3, 8));
        entries.add(new Entry(4, 43));
        entries.add(new Entry(5, 21));

        return entries;
    }

    private List<StatisticalModel> product_month_data() {
        List<StatisticalModel> data = new ArrayList<>();
        StatisticalQuery db = new StatisticalQuery(this);
        db.open();
        data = db.getProductSellMonthData(1); //Id san pham
        return data;
    }

    private void addDataMonth() {
        LineDataSet lineDataSet1 = new LineDataSet(product_month_data_chart(), "Product 3");
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
        LineDataSet lineDataSet1 = new LineDataSet(product_year_data_chart(), "Product 1");
        lineDataSet1.setLineWidth(3);
        lineDataSet1.setFormSize(15f);
        lineDataSet1.setColor(Color.BLUE);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet1);

        LineData lineData = new LineData(iLineDataSets);


        mplineChart.setData(lineData);
        mplineChart.invalidate();
    }
}