package damnvuln.exercise.lab.vulntestlab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import damnvuln.exercise.lab.vulntestlab.VolleyCookieFix.JsonObjectRequestCookie;
import damnvuln.exercise.lab.vulntestlab.adapter.ConnectionListAdapter;
import damnvuln.exercise.lab.vulntestlab.adapter.UserListAdapter;

public class ConversationActivity extends AppCompatActivity implements CallAPIComp{

    private RecyclerView conversationList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONArray response;
    private ArrayList<JSONObject> conversations = new ArrayList<>();

    private EditText newConv;

    @Override
    public void saveResponse(Object response){


        if(response instanceof JSONArray) {
            this.response = (JSONArray) response;
            try {

                conversations = new ArrayList<>();
                for (int i = 0; i < this.response.length(); i++) {

                    conversations.add(this.response.getJSONObject(i));
                }

                if (conversationList == null) initList();
                else updateList();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(response instanceof JSONObject){

            JSONObject mResponse = (JSONObject) response;
            try {
                switchToConversation(Integer.valueOf(mResponse.getString("id")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_layout_constraint);

   //     CallAPI.launchRequest("getConversation", this);
        newConv = (EditText) findViewById(R.id.userInput);

    }

    @Override
    protected void onResume() {
        super.onResume();

        CallAPI.launchRequest("getConversation", this);

    }

    private void initList(){

        conversationList = (RecyclerView)  findViewById(R.id.conversationRecycleList);
        conversationList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        conversationList.setLayoutManager(mLayoutManager);

        mAdapter = new ConnectionListAdapter( conversations  );
        conversationList.setAdapter(mAdapter);
    }

    public void newConversationOnClik(View v){

        HashMap<String, String> argument = new HashMap<>();
        argument.put("to",newConv.getText().toString());
        //argument.put("","");

        CallAPI.launchRequest("newConversation", argument, this);

    }
    public void openConversationOnClik(View v){

        String lasthope = v.getTag().toString();
        switchToConversation(Integer.valueOf(lasthope));
    }


    public void switchToConversation(int id){
        Intent intent = new Intent(this, MessagesActivity.class);
        Bundle b = new Bundle();
                b.putInt("id", id); //Your id
        intent.putExtras(b);
        startActivity(intent);
    }

    public void updateList(){

        mAdapter = new ConnectionListAdapter( conversations  );
        conversationList.setAdapter(mAdapter);
    }

}
