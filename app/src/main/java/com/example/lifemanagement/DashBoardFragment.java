package com.example.lifemanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


import androidx.annotation.RequiresApi;


public class DashBoardFragment extends Fragment {
    BarChart barChart;
    HorizontalBarChart horizontalBarChart;
    SQLiteChitieuControl sqLiteChitieuControl;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        // Assign Variable here
        sqLiteChitieuControl = new SQLiteChitieuControl(getContext());
        horizontalBarChart = view.findViewById(R.id.horiBarchart);
        barChart = view.findViewById(R.id.barChart);

        // Preaparing Data
        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(currentTime);
        final String month1 = String.valueOf(formattedDate.charAt(3)) + String.valueOf(formattedDate.charAt(4));
        final String year1 =  String.valueOf(formattedDate.charAt(6)) +
                        String.valueOf(formattedDate.charAt(7)) +
                        String.valueOf(formattedDate.charAt(8)) +
                        String.valueOf(formattedDate.charAt(9));
        String month2,month3,month4,month5;
        final String year2,year3,year4,year5;
        if(month1.equals("01")){
            month2 = "12";
            year2 = String.valueOf(Integer.parseInt(year1) - 1 < 10);
        }
        else {
            month2 = String.valueOf(Integer.parseInt(month1) - 1);
            if(month2.length() < 2) month2 = "0" + month2;
            year2 = year1;
        }
        if(month2.equals("01")){
            month3 = "12";
            year3 = String.valueOf(Integer.parseInt(year2) - 1);
        }
        else {
            month3 = String.valueOf(Integer.parseInt(month2) - 1);
            if(month3.length() < 2) month3 = "0" + month3;
            year3 = year2;
        }
        if(month3.equals("01")){
            month4 = "12";
            year4 = String.valueOf(Integer.parseInt(year3) - 1);
        }
        else {
            month4 = String.valueOf(Integer.parseInt(month3) - 1);
            if(month4.length() < 2) month4 = "0" + month4;
            year4 = year3;
        }
        if(month4.equals("01")){
            month5 = "12";
            year5 = String.valueOf(Integer.parseInt(year4) - 1);
        }
        else {
            month5 = String.valueOf(Integer.parseInt(month4) - 1);
            if(month5.length() < 2) month5 = "0" + month5;
            year5 = year4;
        }

        final String finalMonth5 = month5;
        final String finalMonth4 = month4;
        final String finalMonth3 = month3;
        final String finalMonth2 = month2;
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                float val = e.getX();
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                String type = "Thu nhập";
                String date = month1 + '-' + year1;
                if(val > 0 && val < 0.5) {type = "Thu nhập";date = finalMonth5 + "-" + year5;}
                if(val > 0.5 && val < 1) {type = "Chi tiêu";date = finalMonth5 + "-" + year5;}
                if(val > 1 && val < 1.5) {type = "Thu nhập";date = finalMonth4 + "-" + year4;}
                if(val > 1.5 && val < 2) {type = "Chi tiêu";date = finalMonth4 + "-" + year4;}
                if(val > 2 && val < 2.5) {type = "Thu nhập";date = finalMonth3 + "-" + year3;}
                if(val > 2.5 && val < 3) {type = "Chi tiêu";date = finalMonth3 + "-" + year3;}
                if(val > 3 && val < 3.5) {type = "Thu nhập";date = finalMonth2 + "-" + year2;}
                if(val > 3.5 && val < 4) {type = "Chi tiêu";date = finalMonth2 + "-" + year2;}
                if(val > 4 && val < 4.5) {type = "Thu nhập";date = month1 + "-" + year1;}
                if(val > 4.5 && val < 5) {type = "Chi tiêu";date = month1 + "-" + year1;}

                intent.putExtra("TYPE",type);
                intent.putExtra("DATE",date);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        int[] valuesThu = {0,0,0,0,0};
        int[] valuesChi = {0,0,0,0,0};
        long tongthunhap = 0;
        long tongchitieu = 0;
        ArrayList<Chitieu> chitieuArrayList = sqLiteChitieuControl.getAllNoteChitieus1();
        for(int i = 0 ; i < chitieuArrayList.size() ; i ++) {
            if(chitieuArrayList.get(i).getLoaichitieu().equals("Thu nhập")) tongthunhap += chitieuArrayList.get(i).getGiatri();
            else tongchitieu += chitieuArrayList.get(i).getGiatri();
            if(chitieuArrayList.get(i).getNgayghi().contains(month1 + "-" + year1)){
                if(chitieuArrayList.get(i).getLoaichitieu().equals("Thu nhập")) valuesThu[0] += chitieuArrayList.get(i).getGiatri();
                else  valuesChi[0] += chitieuArrayList.get(i).getGiatri();
            }
            else if(chitieuArrayList.get(i).getNgayghi().contains(month2 + "-" + year2)){
                if(chitieuArrayList.get(i).getLoaichitieu().equals("Thu nhập")) valuesThu[1] += chitieuArrayList.get(i).getGiatri();
                else  valuesChi[1] += chitieuArrayList.get(i).getGiatri();
            }
            else if(chitieuArrayList.get(i).getNgayghi().contains(month3 + "-" + year3)){
                if(chitieuArrayList.get(i).getLoaichitieu().equals("Thu nhập")) valuesThu[2] += chitieuArrayList.get(i).getGiatri();
                else  valuesChi[2] += chitieuArrayList.get(i).getGiatri();
            }
            else if(chitieuArrayList.get(i).getNgayghi().contains(month4 + "-" + year4)){
                if(chitieuArrayList.get(i).getLoaichitieu().equals("Thu nhập")) valuesThu[3] += chitieuArrayList.get(i).getGiatri();
                else  valuesChi[3] += chitieuArrayList.get(i).getGiatri();
            }
            else if(chitieuArrayList.get(i).getNgayghi().contains(month5 + "-" + year5)){
                if(chitieuArrayList.get(i).getLoaichitieu().equals("Thu nhập")) valuesThu[4] += chitieuArrayList.get(i).getGiatri();
                else  valuesChi[4] += chitieuArrayList.get(i).getGiatri();
            }
        }
        final String[] months = new String[] {month5 + "/" + year5,month4 + "/" + year4,month3 + "/" + year3,month2 + "/" + year2,month1 + "/" + year1};
        setBarChartAllData(valuesThu,valuesChi,months);
        setHorizontalBarChartAllData(tongchitieu,tongthunhap);
         // Call functions here
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setHorizontalBarChartAllData(long tongchi, long tongthu){
        horizontalBarChart.setDrawBarShadow(false);
        Description description = new Description();
        description.setText("");
        horizontalBarChart.setDescription(description);
        horizontalBarChart.getLegend().setEnabled(false);
        horizontalBarChart.setPinchZoom(false);
        horizontalBarChart.setDrawValueAboveBar(false);

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setLabelCount(3);

        final String[] values = {"Chi tiêu", "Thu nhập", "Số dư"};
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return values[(int)value];
            }
        });

        YAxis yLeft = horizontalBarChart.getAxisLeft();
        yLeft.setAxisMinimum(0f);
        yLeft.setEnabled(false);
        YAxis yRight = horizontalBarChart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);


        //Set bar entries and add necessary formatting
        //Add a list of bar entries
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, tongchi));
        entries.add(new BarEntry(1f, tongthu));
        entries.add(new BarEntry(2f, tongthu - tongchi));
        BarDataSet barDataSet = new BarDataSet(entries, "Bar Data Set");
        barDataSet.setColors(
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryRed),
                ContextCompat.getColor(getContext(), R.color.colorPrimaryGreen),
                ContextCompat.getColor(getContext(), R.color.colorforList));

        BarData barData = new BarData(barDataSet);
        horizontalBarChart.setData(barData);
        horizontalBarChart.invalidate();
        //Add animation to the graph
        horizontalBarChart.animateY(1000);
    }
    private void setBarChartAllData(int[] valuesThu, int[] valuesChi,final String[] months) {
        barChart.setDrawBarShadow(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        /////////////////////////////////////////
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0,valuesThu[4]));
        barEntries.add(new BarEntry(1,valuesThu[3]));
        barEntries.add(new BarEntry(2,valuesThu[2]));
        barEntries.add(new BarEntry(3,valuesThu[1]));
        barEntries.add(new BarEntry(4,valuesThu[0]));
        barEntries.add(new BarEntry(5,0));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Thu nhập ");
        barDataSet.setColors(Color.GREEN);

        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        barEntries1.add(new BarEntry(0,valuesChi[4]));
        barEntries1.add(new BarEntry(1,valuesChi[3]));
        barEntries1.add(new BarEntry(2,valuesChi[2]));
        barEntries1.add(new BarEntry(3,valuesChi[1]));
        barEntries1.add(new BarEntry(4,valuesChi[0]));

        BarDataSet barDataSet1 = new BarDataSet(barEntries1,"Chi tiêu ");
        barDataSet1.setColors(Color.RED);

        BarData barData = new BarData(barDataSet,barDataSet1);

        barChart.setData(barData);
        float groupSpace = 0.1f;
        float barSpace = 0.02f;
        float barWidth = 0.43f;

        barData.setBarWidth(barWidth);
        barChart.groupBars(0,groupSpace,barSpace);

        // Assign X Propety
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if(value >=0 && value < 1) return months[0];
                else if(value >=1 && value < 2) return months[1];
                else if(value >=2 && value < 3) return months[2];
                else if(value >=3 && value < 4) return months[3];
                else if(value >=4 && value < 5) return months[4];
                return "";
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(0);
        xAxis.setEnabled(true);
        xAxis.setLabelCount(5);

        // Assign Y Propety
        YAxis yLeft = barChart.getAxisLeft();
        yLeft.setAxisMinimum(0f);
        yLeft.setEnabled(false);
        YAxis yRight = barChart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(true);
        yRight.setEnabled(false);


        /////////////////////////////////
        barChart.animateY(1000);
        barChart.invalidate();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
