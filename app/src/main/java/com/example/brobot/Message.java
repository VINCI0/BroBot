package com.example.brobot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Message {
    String senderId;
    String receiverId;
    String text;
    Long timestamp;
    Float compoundScore;

    public Float getCompoundScore() {
        return compoundScore;
    }

    public void setCompoundScore(Float compoundScore) {
        this.compoundScore = compoundScore;
    }

    public Message(String senderId, String receiverId, String text, Long timestamp, Float compoundScore) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = timestamp;
        this.compoundScore = compoundScore;
    }

    public Message(String senderId, String receiverId, String text, Long timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = timestamp;
        Random rand = new Random();
        int i1 = rand.nextInt(2-0) + 0;
        Float r = ((float) Math.random());
        if (i1==0) {
            r = ((float) Math.random());
        }else{
            r = ((float) Math.random())*-1;
        }
        this.compoundScore= Float.valueOf(r);

    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(timestamp*1000));
       // Date date = new Date(timestamp);
        return dateString;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return  Objects.equals(senderId, message.senderId) &&
                Objects.equals(receiverId, message.receiverId) &&
                Objects.equals(text, message.text) &&
                Objects.equals(timestamp, message.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, receiverId, text, timestamp);
    }
}
