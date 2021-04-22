package com.example.brobot;

import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    EditText messageContent;
    RecyclerView recyclerView;
    MessageListJavaAdapter adapter;
    FloatingActionButton send;
    public FirebaseAuth mAuth;
    TextView username;
    FirebaseUser fuser;


    public static ArrayList<Message> messagesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        adapter = new MessageListJavaAdapter(getActivity(), messagesList, "2", "1");
        recyclerView = view.findViewById(R.id.recyclerViewMessages);

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setStackFromEnd(true);

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

//        adapter.notifyDataSetChanged();
//        recyclerView.smoothScrollToPosition(adapter.getItemCount());

        send = view.findViewById(R.id.fbMessageSend);
        messageContent = view.findViewById(R.id.etMessage);

        GetMessageList(fuser.getUid());

//        adapter.notifyDataSetChanged();
//        recyclerView.smoothScrollToPosition(adapter.getItemCount());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (messageContent.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Empty Message!", Toast.LENGTH_SHORT).show();
                } else {
                    String msg = messageContent.getText().toString();
                    long timestamp = System.currentTimeMillis() / 1000;
                    messagesList.add(new Message("1", "2", msg, timestamp));
                    if (messageContent.length() > 0) {
                        TextKeyListener.clear(messageContent.getText());
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    Message m = new Message("2", "1", "typing...", timestamp);
                    messagesList.add(m);
                    adapter.notifyDataSetChanged();
//                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    PostMessage(msg, timestamp);

                }
            }
        });


        return view;
    }

    void PostMessage(final String msg, final Long timestamp) {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
        String url = LoginActivity.serverAddress + "/api/users/message/";
        final String userID = fuser.getUid();
        Log.d("UID", userID);
        JSONObject postparams = new JSONObject();
        try {

            postparams.put("user", userID);
            postparams.put("msg_text", msg);
            postparams.put("timestamp", timestamp);
            postparams.put("is_bot", false);
            Log.d("TRY", postparams.toString());
        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonObjectRequest MyJsonRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    messagesList.remove(messagesList.size() - 1);
                    messagesList.add(new
                            Message("2", "1", response.get("msg_text").toString(),
                            Long.parseLong(response.get("timestamp").toString())));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("CATCH", e.getMessage());
                }
                Log.d("CATCH", "RES");
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
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


    void GetMessageList(final String user) {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
        String url = LoginActivity.serverAddress + "/api/users/message/" + user;
        // Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
        JsonArrayRequest MyJsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject message = response.getJSONObject(i);
                        String msgText = message.get("msg_text").toString();
                        //             Log.d("GETMESSAGES", message.get("msg_text").toString());
                        Long timeStamp = Long.parseLong(message.get("timestamp").toString());
                        Boolean isBot = Boolean.parseBoolean(message.get("is_bot").toString());
                        Float moodScore = Float.parseFloat(message.get("compound_score").toString());

                        if (isBot)
                            messagesList.add(new Message("2", "1", msgText, timeStamp,moodScore));
                        else
                            messagesList.add(new Message("1", "2", msgText, timeStamp,moodScore));

                        adapter.notifyDataSetChanged();
//                        recyclerView.smoothScrollToPosition(adapter.getItemCount());

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
