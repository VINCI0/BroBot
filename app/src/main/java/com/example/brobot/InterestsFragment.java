package com.example.brobot;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InterestsFragment extends Fragment implements View.OnClickListener {
    ArrayList<Button> interests = new ArrayList<>();
    static ArrayList<Boolean> is_interest = new ArrayList<>() ;
;
    public ArrayList<Button> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Button> interests) {
        this.interests = interests;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //give ids to
        if (is_interest.size()==0) {
            for (int i = 0; i < 30; i++) {
                is_interest.add(false);
            }
        }
        GetInterests();
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
            if (is_interest.get(i).equals(true)){
                interests.get(i).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                interests.get(i).setTextColor(getResources().getColor(R.color.colorLightFont));
                interests.get(i).setTag("1");
            }else{
                interests.get(i).setTag("0");
                interests.get(i).setBackgroundColor(getResources().getColor(R.color.colorLightFont));
                interests.get(i).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }





        return view;
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT);


        if (v.getTag()==null) {

            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            Button b = (Button) v;
            b.setTextColor(getResources().getColor(R.color.colorLightFont));
            v.setTag("1");
            Toast.makeText(getActivity(), b.getText()+" Added To Interest", Toast.LENGTH_SHORT).show();

            is_interest.set(interests.indexOf(b),true);
            PostInterest(b.getText().toString(), true);

        }
        else{
        final int status = Integer.parseInt((String) v.getTag());

        if (status == 1) {
//            means selected

            v.setBackgroundColor(getResources().getColor(R.color.colorLightFont));
            Button b = (Button) v;
            b.setTextColor(getResources().getColor(R.color.colorPrimary));
            v.setTag("0");
            Toast.makeText(getActivity(), b.getText()+" Removed  From Interest", Toast.LENGTH_SHORT).show();
            is_interest.set(interests.indexOf(b),false);
            PostInterest(b.getText().toString(), false);

        }
        else {
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            Button b = (Button) v;
            b.setTextColor(getResources().getColor(R.color.colorLightFont));
            v.setTag("1");
            Toast.makeText(getActivity(), b.getText()+" Added To Interest", Toast.LENGTH_SHORT).show();
            is_interest.set(interests.indexOf(b),true);
            PostInterest(b.getText().toString(), true);
        }

        }

    }

    void GetInterests(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
        final String userID = chatActivity.fuser.getUid();
        String url = LoginActivity.serverAddress + "/api/users/interest/" + userID;
        // Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
        Log.d("UIDD",userID);
        JsonArrayRequest MyJsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject interest = response.getJSONObject(i);
                        String title = (String) interest.get("title");
                        Boolean isInterest = (Boolean) interest.get("is_interest");
                        is_interest.set(interests.indexOf(title),isInterest);

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

    void PostInterest(final String interest, final Boolean is_interest) {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
        String url = LoginActivity.serverAddress + "/api/users/interest/";
        final String userID = chatActivity.fuser.getUid();

        JSONObject postparams = new JSONObject();
        try {
            postparams.put("user", userID);
            postparams.put("title", interest);
            postparams.put("is_interest", is_interest);

            Log.d("TRY", postparams.toString());
        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonObjectRequest MyJsonRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.get("title").toString());

                } catch (JSONException e) {

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




        //        v.setBackgroundResource(R.color.colorPrimaryDark);
//        v.setBackground(v.getBackground());
         //   v.setBackground(R.drawable.rounded_edit_text_selected);

    }
