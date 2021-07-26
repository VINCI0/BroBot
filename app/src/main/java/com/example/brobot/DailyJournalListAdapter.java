package com.example.brobot;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
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
import androidx.room.Database;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

import static com.example.brobot.DailyJournalFragment.adapter;
import static com.example.brobot.DailyJournalFragment.journalsRecycler;

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

        void updateJournal(){
            for (int i = 0;i< DailyJournalFragment.journalsList.size();i++){
                PostJournal(DailyJournalFragment.journalsList.get(i),i);
            }
            //Database.DailyJournalFragment.journalsList.sort(Comparator.comparing(Journal::getDate));

            // DailyJournalFragment.journalsList.sort();
        }
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
                        journals.get(getBindingAdapterPosition()).setText(journalText.getText().toString());
                        DailyJournalFragment.journalsList.get(getBindingAdapterPosition()).setText(journalText.getText().toString());

                        updateJournal();
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
                        DailyJournalFragment.journalsList.get(getBindingAdapterPosition()).setText(journalText.getText().toString());


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
                            formatter = new SimpleDateFormat("dd/MM/yyyy");
                            try {

                                Date tempDate = formatter.parse(journalDate.getText().toString());
                                journals.get(getAbsoluteAdapterPosition()).setDate(tempDate);
                                Toast.makeText(context, tempDate.toString(),
                                        Toast.LENGTH_LONG).show();
                                DailyJournalFragment.journalsList.get(getAbsoluteAdapterPosition()).setDate(tempDate);
                                adapter.notifyDataSetChanged();
                                journalsRecycler.smoothScrollToPosition(adapter.getItemCount());
                                updateJournal();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, year, month, day);



                    picker.show();



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

    void PostJournal(Journal journal,int idx){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
        String url = LoginActivity.serverAddress + "/api/users/journal/";
        final String userID = chatActivity.fuser.getUid();
        //Log.d("USER NAME ", chatActivity.fuser.getEmail());
        JSONObject postparams = new JSONObject();
        try {

            postparams.put("user", userID);
            postparams.put("title", journal.title);
            postparams.put("text", journal.text);
            postparams.put("timestamp", journal.date.getTime());
            postparams.put("is_deleted", false);
            postparams.put("index", idx);

        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonObjectRequest MyJsonRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    response.get("msg_text").toString();
                    adapter.notifyDataSetChanged();
                    journalsRecycler.smoothScrollToPosition(adapter.getItemCount());
                    System.out.println(" dd");
                } catch (Exception e) {
                    e.printStackTrace();

                }
                Log.d("CATCH", "RES");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyRequestQueue.add(MyJsonRequest);
        //set timeout to 15 seconds
        MyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
