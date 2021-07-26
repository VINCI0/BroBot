package com.example.brobot;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DailyJournalListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    ArrayList<Journal> journals;

    DailyJournalListAdapter(Context context, ArrayList<Journal> journals){
    this.context=context;
    this.journals=journals;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JournalHolder(LayoutInflater.from(context).inflate(R.layout.journal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((JournalHolder) holder).bind(position);

    }

    @Override
    public int getItemCount() {
        return this.journals.size();
    }

    class JournalHolder extends RecyclerView.ViewHolder{

        Button journalDate;
        TextView journalTitle;
        EditText journalText;

        public JournalHolder(@NonNull final View itemView) {
            super(itemView);
            this.journalDate=itemView.findViewById(R.id.journalDate);
            this.journalTitle=itemView.findViewById(R.id.journal_name);
            this.journalText=itemView.findViewById(R.id.journal_text);

            this.journalTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "Clicked",
//                            Toast.LENGTH_LONG).show();
                    if (journalText.getVisibility()!=View.GONE){

                        journalText.setVisibility(View.GONE);
                        journalText.setEnabled(false);
                    }
                    else
                        journalText.setVisibility(View.VISIBLE);
                }
            });

            this.journalTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (journalText.getVisibility()!=View.GONE){
                    if (journalText.isEnabled())
                        journalText.setEnabled(false);
                    else
                        journalText.setEnabled(true);


                        journals.get(getBindingAdapterPosition()).setText(journalText.getText().toString());

                        return true;
                }
                    return false;
            }});
            this.journalDate.setOnClickListener(new View.OnClickListener() {
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
                            journalDate.setText(dateString);
                        }
                    }, year, month, day);



                    picker.show();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {

                        Date tempDate = formatter.parse(journalDate.getText().toString());
                        journals.get(getAbsoluteAdapterPosition()).setDate(tempDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        public void bind(int position) {
            Journal journal=journals.get(position);
            String date          = (String) DateFormat.format("dd/MM/yyyy",journal.date);
            this.journalDate.setText(date);
            this.journalTitle.setText(journal.title);
            this.journalText.setText(journal.text);
            this.journalText.setVisibility(View.GONE);
        }
    }
}
