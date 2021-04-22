package com.example.brobot;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MoodFragment extends Fragment {

    DatePickerDialog picker;
    Button btnPickDate;
    GraphView graph;
    Boolean isDataSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mood_tracker, container, false);
        btnPickDate = view.findViewById(R.id.btpickerDate);
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        month+=1;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(cldr.getTimeInMillis());
        btnPickDate.setText(dateString);


//        btnPickDate.setText(day+"/"+month+"/"+year);

//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        final String dateString = formatter.format(cldr.getTimeInMillis());
//        btnPickDate.setText(dateString);

        graph = view.findViewById(R.id.graph_moodTracker);

//        On Create Graph here
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(messageEmotion());
//        series.setDrawDataPoints(true);
        series.setTitle(btnPickDate.getText().toString());

        //        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, Math.random()),
//                new DataPoint(1, Math.random()),
//                new DataPoint(2, Math.random()),
//                new DataPoint(3, Math.random()),
//                new DataPoint(4, Math.random())
//        });


    //        Graph Settings
        graph.setTitle("Emotion Scores Between -1 & 1");
        graph.addSeries(series);

        graph.getViewport().setMinY(-1);
        graph.getViewport().setMaxY(1);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxX(30);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setWidth(170);
        graph.getViewport().setScrollable(true);
        graph.getGridLabelRenderer().setLabelsSpace(5);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isDataSet=false;
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                month+=1;
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth, 0, 0);

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = formatter.format(c.getTimeInMillis());
                        btnPickDate.setText(dateString);
                    }
                }, year, month, day);

                picker.setOnDismissListener( new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        //indicate dialog is date picker not Cancelled i.e date change

//                        Toast.makeText(getActivity(), "Date Picked", Toast.LENGTH_SHORT).show();

                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(messageEmotion());

//                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                                new DataPoint(0, Math.random()),
//                                new DataPoint(1, Math.random()),
//                                new DataPoint(2, Math.random()),
//                                new DataPoint(3, Math.random()),
//                                new DataPoint(4, Math.random())
//                        });
                        series.setTitle(btnPickDate.getText().toString());
                        series.setDrawDataPoints(true);
                        graph.removeAllSeries();
                        graph.addSeries(series);


                    }

                });
                picker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(getActivity(), "Date Not Picked", Toast.LENGTH_SHORT).show();

                    }
                });

                picker.show();

                ;

            }
        });

        return view;
    }

    private DataPoint[] messageEmotion() {



        ArrayList<Message> messages=ChatFragment.messagesList;
        ArrayList<Float> scores = new ArrayList<>();

        for (int i =0;i<messages.size();i++){

            if (messages.get(i).getDate().equals(btnPickDate.getText().toString())){
                if (messages.get(i).getCompoundScore()!=-6.0)
                    scores.add(messages.get(i).getCompoundScore());
            }
        }

        DataPoint[] values = new DataPoint[scores.size()];     //creating an object of type DataPoint[] of size 'n'
         if (scores.size()<=0){
             Toast.makeText(getActivity(),"No mood score history for this day", Toast.LENGTH_SHORT).show();

         }


        for(int i=0;i<values.length;i++){


            DataPoint v = new DataPoint(i,scores.get(i));
            values[i] = v;
        }

        return values;
    }

}