package com.example.brobot;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class InterestsFragment extends Fragment implements View.OnClickListener {
    ArrayList<Button> interests = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //give ids to


        View view = inflater.inflate(R.layout.fragment_interests, container, false);
        interests.add((Button) view.findViewById(R.id.interest_1));
        interests.add((Button) view.findViewById(R.id.interest_2));
        interests.add((Button) view.findViewById(R.id.interest_3));
        interests.add((Button) view.findViewById(R.id.interest_4));
        interests.add((Button) view.findViewById(R.id.interest_5));
        interests.add((Button) view.findViewById(R.id.interest_6));
        interests.add((Button) view.findViewById(R.id.interest_7));
        interests.add((Button) view.findViewById(R.id.interest_8));
        interests.add((Button) view.findViewById(R.id.interest_9));
        interests.add((Button) view.findViewById(R.id.interest_10));
        interests.add((Button) view.findViewById(R.id.interest_11));
        interests.add((Button) view.findViewById(R.id.interest_12));
        interests.add((Button) view.findViewById(R.id.interest_13));
        interests.add((Button) view.findViewById(R.id.interest_14));
        interests.add((Button) view.findViewById(R.id.interest_15));
        interests.add((Button) view.findViewById(R.id.interest_16));
        interests.add((Button) view.findViewById(R.id.interest_17));
        interests.add((Button) view.findViewById(R.id.interest_18));
        interests.add((Button) view.findViewById(R.id.interest_19));
        interests.add((Button) view.findViewById(R.id.interest_20));
        interests.add((Button) view.findViewById(R.id.interest_21));
        interests.add((Button) view.findViewById(R.id.interest_22));
        interests.add((Button) view.findViewById(R.id.interest_23));
        interests.add((Button) view.findViewById(R.id.interest_24));
        interests.add((Button) view.findViewById(R.id.interest_25));
        interests.add((Button) view.findViewById(R.id.interest_26));
        interests.add((Button) view.findViewById(R.id.interest_27));
        interests.add((Button) view.findViewById(R.id.interest_28));
        interests.add((Button) view.findViewById(R.id.interest_29));
        interests.add((Button) view.findViewById(R.id.interest_30));


        for (int i = 0; i < 30; i++) {

            interests.get(i).setOnClickListener(this);
        }


        return view;
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT);


        if (v.getTag()){
        final int status = Integer.parseInt((String) v.getTag());

        if (status == 1) {
//            means selected

            v.setBackgroundColor(getResources().getColor(R.color.colorLightFont));
            Button b = (Button) v;
            b.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        }
        else {
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            Button b = (Button) v;
            b.setTextColor(getResources().getColor(R.color.colorLightFont));

        }

    }



        //        v.setBackgroundResource(R.color.colorPrimaryDark);
//        v.setBackground(v.getBackground());
         //   v.setBackground(R.drawable.rounded_edit_text_selected);

    }
