package com.example.brobot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class DailyJournalFragment extends Fragment {

    ImageView addNewJournal;
    RecyclerView journalsRecycler;
    public static ArrayList<Journal> journalsList = new ArrayList<Journal>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_daily_journal,container,false);
        addNewJournal=view.findViewById(R.id.addNewJounal_ic);
        journalsRecycler=view.findViewById(R.id.journalList);

        if (journalsList.size()==0) {
            journalsList.add(0, new Journal("temp", "This is just temp text", new Date()));
            journalsList.add(0, new Journal("temp 2", "This is just temp text", new Date()));
        }
        RecyclerView.Adapter adapter=new DailyJournalListAdapter(getActivity(),journalsList);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setStackFromEnd(true);
        journalsRecycler.setLayoutManager(lm);
        journalsRecycler.setAdapter(adapter);

        addNewJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NewJournalActivity.class);
                startActivity(intent);
            }
        });


        return view;

    }
}
