package com.example.brobot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NewJournalActivity extends AppCompatActivity {

    Button discard,save,date;
    EditText title,text;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);
        context=this;

        discard= findViewById(R.id.discardNewJournal);
        save=findViewById(R.id.saveNewJournal);
        title=findViewById(R.id.newJournalTitle);
        text=findViewById(R.id.newJournalText);
        date=findViewById(R.id.newJournalDatePicker);

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        month+=1;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(cldr.getTimeInMillis());
        date.setText(dateString);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
//                month+=1;
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(Objects.requireNonNull(context), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth, 0, 0);

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = formatter.format(c.getTimeInMillis());
                        date.setText(dateString);
                    }
                }, year, month, day);

                picker.show();; }
        });



        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().length()>0 || text.getText().length()>0){

                    new AlertDialog.Builder(context)
                        .setTitle("Discard Journal")
                        .setMessage("Do you really want to discard the journal?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
                else{
                    finish();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.length()>0){

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date newDate=formatter.parse(date.getText().toString());
                        Journal journal=new Journal( title.getText().toString(),text.getText().toString(),newDate);
                        DailyJournalFragment.journalsList.add(journal);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
                else{
                    Toast.makeText(context, "Journal Title is compulsory",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
