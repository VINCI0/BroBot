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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
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


    ArrayList<Message> messagesList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view= inflater.inflate(R.layout.fragment_chat,container,false);

        recyclerView= view.findViewById(R.id.recyclerViewMessages);
        adapter=new MessageListJavaAdapter(getActivity(),messagesList,"2","1");

        LinearLayoutManager lm=new LinearLayoutManager(getActivity());
        lm.setStackFromEnd(true);

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(adapter.getItemCount());

        send=view.findViewById(R.id.fbMessageSend);
        messageContent=view.findViewById(R.id.etMessage);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (messageContent.getText().length()==0) {
                    Toast.makeText(getActivity(), "Empty Message!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String msg = messageContent.getText().toString();
                    long timestamp = System.currentTimeMillis() / 1000;
                    messagesList.add(new Message("1", "2", msg, timestamp));
                    if (messageContent.length() > 0) {
                        TextKeyListener.clear(messageContent.getText());
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
//                    PostMessage(msg, timestamp);
                }
            }
        });


        return view;
    }

//    void PostMessage(final String msg, final Long timestamp) {
//
//        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
////        String url = StartActivity.serverAddress + "/api/users/message/";
//        final String userID = fuser.getUid();
//        Log.d("UID", userID);
//        JSONObject postparams = new JSONObject();
//        try {
//
//            postparams.put("user", userID);
//            postparams.put("msg_text", msg);
//            postparams.put("timestamp", timestamp);
//            postparams.put("is_bot", false);
//            Log.d("TRY", postparams.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//
//        }
//        JsonObjectRequest MyJsonRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//
//                    Log.d("RESSS", response.get("msg_text").toString() + "RES");
//                    messagesList.add(new
//                            Message("2", "1", response.get("msg_text").toString(),
//                            Long.parseLong(response.get("timestamp").toString())));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.d("CATCH", e.getMessage());
//                }
//                Log.d("CATCH", "RES");
//                adapter.notifyDataSetChanged();
//                recyclerView.smoothScrollToPosition(adapter.getItemCount());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        MyRequestQueue.add(MyJsonRequest);
//        //set timeout to 15 seconds
//        MyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }
//
//
//    Boolean GetMessageList() {
//
//        final Boolean[] messagesAdded = {false};
//        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
//        String url = StartActivity.serverAddress + "/api/users/message/";
//
//        JsonArrayRequest MyJsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response.length() > 0)
//                    messagesAdded[0] = true;
//
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject message = response.getJSONObject(i);
//                        String msgText = message.get("msg_text").toString();
//                        Log.d("GETMESSAGES", message.get("msg_text").toString());
//                        Long timeStamp = Long.parseLong(message.get("timestamp").toString());
//                        Boolean isBot = Boolean.parseBoolean(message.get("is_bot").toString());
//                        if (isBot)
//                            messagesList.add(new Message("2", "1", msgText, timeStamp));
//                        else
//                            messagesList.add(new Message("1", "2", msgText, timeStamp));
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        MyRequestQueue.add(MyJsonRequest);
//        //set timeout to 15 seconds
//        MyJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        return messagesAdded[0];
//    }



}
