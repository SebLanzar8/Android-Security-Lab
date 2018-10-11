package damnvuln.exercise.lab.vulntestlab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import damnvuln.exercise.lab.vulntestlab.adapter.ConnectionListAdapter;
import damnvuln.exercise.lab.vulntestlab.adapter.MessageListAdapter;
import damnvuln.exercise.lab.vulntestlab.generalPurpouse.Message;

public class MessagesActivity extends AppCompatActivity implements CallAPIComp{

    private RecyclerView messageList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Message> messages = new ArrayList<>();
    private JSONArray response;

    private EditText msg;
    private int id;

    @Override
    public void saveResponse(Object response){
        if(response instanceof JSONArray) {
            this.response = (JSONArray) response;

            try {
                messages = new ArrayList<>();
                for (int i = 0; i < this.response.length(); i++) {
                    Message msg = new Message();
                    msg.setMessage(this.response.getJSONObject(i).getString("message"));
                    msg.setId(this.response.getJSONObject(i).getString("idConversation"));
                    msg.setIdSender(this.response.getJSONObject(i).getInt("idSender"));
                    messages.add(msg);

                }

                if (messageList == null) initList();
                else updateList();

            } catch (Exception e) {

                CallAPI.launchRequest("getMessage/" + id, this);
                e.printStackTrace();
            }
        }else if(response instanceof JSONObject){

            CallAPI.launchRequest("getMessage/"+id, this);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout_constraint);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

      //  CallAPI.launchRequest("getMessage/"+id, this);

        msg = (EditText) findViewById(R.id.newMsg);


    }

    @Override
    protected void onResume() {
        super.onResume();

        CallAPI.launchRequest("getMessage/"+id, this);

    }

    private void initList(){

        messageList = (RecyclerView)  findViewById(R.id.messagesRecycleList);
        messageList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        messageList.setLayoutManager(mLayoutManager);

        mAdapter = new MessageListAdapter( messages  );
        messageList.setAdapter(mAdapter);
    }

    public void sendMessage(View v){
        HashMap<String, String> argument = new HashMap<>();
        argument.put("idConversation",String.valueOf( id));
        argument.put("message", msg.getText().toString());

        CallAPI.launchRequest("sendMessage", argument, this);
    }

    public void updateList(){


        mAdapter = new MessageListAdapter( messages  );
        messageList.setAdapter(mAdapter);
    }
}
