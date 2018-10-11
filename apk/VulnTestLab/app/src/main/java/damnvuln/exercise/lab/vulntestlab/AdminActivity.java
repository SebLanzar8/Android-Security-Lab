package damnvuln.exercise.lab.vulntestlab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import damnvuln.exercise.lab.vulntestlab.adapter.ConnectionListAdapter;
import damnvuln.exercise.lab.vulntestlab.adapter.MessageListAdapter;
import damnvuln.exercise.lab.vulntestlab.adapter.UserListAdapter;
import damnvuln.exercise.lab.vulntestlab.generalPurpouse.User;

public class AdminActivity extends AppCompatActivity implements CallAPIComp {

    private RecyclerView userList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<JSONObject> user = new ArrayList<>();
    private JSONArray response;

    private EditText msg;
    private int id;

    @Override
    public void saveResponse(Object response){


        if(response instanceof JSONArray) {
            this.response = (JSONArray) response;

            try {

                user = new ArrayList<>();
                for (int i = 0; i < this.response.length(); i++) {

                    user.add(this.response.getJSONObject(i));
                }

                if (userList == null) initList();
                else updateList();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(response instanceof JSONObject){

            JSONObject mResponse = (JSONObject) response;
            try {

                CallAPI.launchRequest("getUser", this);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


    //    CallAPI.launchRequest("getUser", this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CallAPI.launchRequest("getUser", this);

    }

    private void initList(){

        userList = (RecyclerView)  findViewById(R.id.userRecycleList);
        userList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        userList.setLayoutManager(mLayoutManager);

        mAdapter = new UserListAdapter( user  );
        userList.setAdapter(mAdapter);
    }

    public void newUser(View v){
        final View viewId = v;

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        LayoutInflater inflater = AdminActivity.this.getLayoutInflater();
        final View v_iew=inflater.inflate(R.layout.modify_user_layout, null);
        builder.setView(v_iew)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String password = CallAPI.getMd5(((EditText) v_iew.findViewById(R.id.newpassword)).getText().toString());
                        String username =  ((EditText) v_iew.findViewById(R.id.newusername)).getText().toString();
                        String email = ((EditText) v_iew.findViewById(R.id.newemail)).getText().toString();

                        HashMap<String, String> user = new HashMap<>();
                        user.put("password", password.compareTo( "") == 0 ? null : password);
                        user.put("username",username.compareTo( "") == 0 ? null : username);
                        user.put("email",email.compareTo( "") == 0 ? null : email );


                        CallAPI.launchRequest("newUser",user,  AdminActivity.this);

                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.create();
        builder.show();
    }

    public void modifyUser(View v){

        final View viewId = v;

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        LayoutInflater inflater = AdminActivity.this.getLayoutInflater();
        final View v_iew=inflater.inflate(R.layout.modify_user_layout, null);
        builder.setView(v_iew)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String password = CallAPI.getMd5(((EditText) v_iew.findViewById(R.id.newpassword)).getText().toString());
                        String username =  ((EditText) v_iew.findViewById(R.id.newusername)).getText().toString();
                        String email = ((EditText) v_iew.findViewById(R.id.newemail)).getText().toString();

                        HashMap<String, String> user = new HashMap<>();
                        user.put("id", viewId.getTag().toString());
                        user.put("password", password.compareTo( "") == 0 ? null : password);
                        user.put("username",username.compareTo( "") == 0 ? null : username);
                        user.put("email",email.compareTo( "") == 0 ? null : email );

                        CallAPI.launchRequest("updateUser",user , AdminActivity.this);

                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.create();
        builder.show();
    }

    public void deleteUser(View v){
        HashMap<String, String> user = new HashMap<>();
        user.put("id", v.getTag().toString());
        CallAPI.launchRequest("delUser",user, this);
    }


    public void updateList(){

        mAdapter = new UserListAdapter( user  );
        userList.setAdapter(mAdapter);
    }


}
