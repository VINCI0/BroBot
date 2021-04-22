package com.example.brobot;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
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
        btnPickDate.setText(day+"/"+month+"/"+year);
        graph = view.findViewById(R.id.graph_moodTracker);

//        On Create Graph here

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);




        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isDataSet=false;
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                btnPickDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                            }, year, month, day);

                picker.setOnDismissListener( new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        //indicate dialog is cancelled/gone
                        Toast.makeText(getActivity(), "Date Picked", Toast.LENGTH_SHORT).show();

                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                                new DataPoint(0, 5),
                                new DataPoint(1, 10),
                                new DataPoint(2, 15),
                                new DataPoint(3, 10),
                                new DataPoint(4, 5)
                        });
                        series.setTitle(btnPickDate.getText().toString());
                        graph.removeAllSeries();
                        graph.setTitle("Emotion Scores Between -1 & 1");
                        graph.setTitleColor(R.color.colorPrimary);
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

}