package com.example.brobot;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.ACCESSIBILITY_SERVICE;

public class ChatFragment extends Fragment {

    EditText messageContent;
    RecyclerView recyclerView;
    MessageListJavaAdapter adapter;
    FloatingActionButton send,record;
    public FirebaseAuth mAuth;
    TextView username;
    FirebaseUser fuser;
    MediaRecorder mediaRecorder;
    static int recording_id =0;

    public static ArrayList<Message> messagesList = new ArrayList<>();


    public void btnRecord(View view){

    }



    @SuppressLint("ClickableViewAccessibility")
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
        send = view.findViewById(R.id.fbMessageSend);
        messageContent = view.findViewById(R.id.etMessage);
        GetMessageList(fuser.getUid());
        record = view.findViewById(R.id.fbRecord);
        messageContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
                if (messageContent.getText().length()>0){
                    record.setVisibility(View.INVISIBLE);
                }else{
                    record.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
             //   tvDisplay.setText(s.toString());
            }
        });



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
                    PostMessage(msg, timestamp);
                }
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Speak your message to BroBot in English");
                try{
                    startActivityForResult(intent,
                            1);
                }catch (ActivityNotFoundException e){
                    Log.d("AUDIOTRANS",e.getMessage());
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK && data != null){
                    ArrayList<String> result =
                            data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String msg = "";
                    for (int i=0;i<result.size();i++){
                        msg+=result.get(i);
                    }
                    long timestamp = System.currentTimeMillis() / 1000;
                    if (msg.length()>0) {
                        messagesList.add(new Message("1", "2", msg, timestamp));

                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        Message m = new Message("2", "1", "typing...", timestamp);
                        messagesList.add(m);
                        adapter.notifyDataSetChanged();
                        PostMessage(msg, timestamp);
                    }else{
                        Toast.makeText(getActivity(),"Emptry Message!",Toast.LENGTH_SHORT);
                    }

                }
                break;
        }
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
