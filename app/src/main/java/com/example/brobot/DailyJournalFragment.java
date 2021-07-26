package com.example.brobot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class DailyJournalFragment extends Fragment {

    ImageView addNewJournal;
    static RecyclerView journalsRecycler;
    static RecyclerView.Adapter adapter;
    public static ArrayList<Journal> journalsList = new ArrayList<Journal>();
    // hash

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_daily_journal,container,false);
        addNewJournal=view.findViewById(R.id.addNewJounal_ic);
        journalsRecycler=view.findViewById(R.id.journalList);

        if (journalsList.size()==0) {
           GetJournals();
        }else{
            journalsList.clear();
            GetJournals();
        }

        adapter=new DailyJournalListAdapter(getActivity(),journalsList);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setStackFromEnd(true);
        journalsRecycler.setLayoutManager(lm);
        journalsRecycler.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        journalsRecycler.smoothScrollToPosition(adapter.getItemCount());

        addNewJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NewJournalActivity.class);
                startActivity(intent);
            }
        });


        return view;

    }
    void GetJournals(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
        final String userID = chatActivity.fuser.getUid();
        String url = LoginActivity.serverAddress + "/api/users/journal/" + userID;
        // Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
        Log.d("UIDD",userID);
        JsonArrayRequest MyJsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject journal = response.getJSONObject(i);
                        String title = (String) journal.get("title");
                        String text = (String) journal.get("text");
                        Date date=new Date((long) journal.get("timestamp"));
                        Journal journal_obj = new Journal(title,
                                text,date);
                        System.out.println(title+text);
                      //  Toast.makeText(getActivity(), journal_obj.title+" "+journal_obj.text, Toast.LENGTH_SHORT).show();
                        journalsList.add(journal_obj);
                        adapter.notifyDataSetChanged();
                        journalsRecycler.smoothScrollToPosition(adapter.getItemCount());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


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
