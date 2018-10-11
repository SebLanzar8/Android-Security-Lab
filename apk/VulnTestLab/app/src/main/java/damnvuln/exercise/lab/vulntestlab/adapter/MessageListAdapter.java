package damnvuln.exercise.lab.vulntestlab.adapter;


import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import damnvuln.exercise.lab.vulntestlab.CallAPIComp;
import damnvuln.exercise.lab.vulntestlab.MainActivity;
import damnvuln.exercise.lab.vulntestlab.R;
import damnvuln.exercise.lab.vulntestlab.generalPurpouse.Message;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> implements CallAPIComp {

    private ArrayList<Message> dataSet;
    private JSONArray response;

    @Override
    public void saveResponse(Object response){
        this.response = (JSONArray) response;

        try{

            for (int i = 0; i < this.response.length(); i++) {
                Log.d("response", this.response.getJSONObject(i).getString("id"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (TableLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);

        MessageViewHolder vh = new MessageViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        //holder.user.setText(dataSet.get(position).getIdSender());
        holder.message.setText(dataSet.get(position).getMessage());

            if(dataSet.get(position).getIdSender() == MainActivity.getId()){
                holder.message.setGravity(Gravity.RIGHT);
                holder.message.setBackgroundColor(Color.rgb(150, 150, 150));
            }else{
                holder.message.setGravity(Gravity.LEFT);
                holder.message.setBackgroundColor(Color.rgb(100, 100, 100));
            }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView user;
        public TextView message;
        public MessageViewHolder(View v){
            super(v);
            user = (TextView) v.findViewById(R.id.usernameMessage);
            message = (TextView) v.findViewById(R.id.messageText);
        }
    }

    public MessageListAdapter(ArrayList<Message> dataSet){
        this.dataSet = dataSet;
    }
}
