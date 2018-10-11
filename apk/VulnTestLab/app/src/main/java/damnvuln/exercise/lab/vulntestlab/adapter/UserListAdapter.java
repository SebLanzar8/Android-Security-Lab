package damnvuln.exercise.lab.vulntestlab.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import damnvuln.exercise.lab.vulntestlab.CallAPIComp;
import damnvuln.exercise.lab.vulntestlab.R;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> implements CallAPIComp {

    private ArrayList<JSONObject> dataSet;
    private JSONArray response;

    @Override
    public void saveResponse(Object response){
        this.response = (JSONArray) response;
        dataSet = new ArrayList<>();
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (TableLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.textView.setText("Username: " + dataSet.get(position).get("username").toString());
            holder.idButton.setTag(dataSet.get(position).get("id").toString());
            holder.cancButton.setTag(dataSet.get(position).get("id").toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public Button idButton;
        public Button cancButton;
        public MyViewHolder(View v){
            super(v);
            textView = (TextView) v.findViewById(R.id.username);
            idButton = (Button) v.findViewById(R.id.modifyUser);
            cancButton = (Button) v.findViewById(R.id.delUser);
        }
    }

    public UserListAdapter(ArrayList<JSONObject> dataSet){
        this.dataSet = dataSet;
    }




}
