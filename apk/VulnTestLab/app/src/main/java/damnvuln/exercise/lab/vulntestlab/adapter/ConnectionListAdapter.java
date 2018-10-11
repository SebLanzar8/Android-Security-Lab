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
import damnvuln.exercise.lab.vulntestlab.MainActivity;
import damnvuln.exercise.lab.vulntestlab.R;

public class ConnectionListAdapter extends RecyclerView.Adapter<ConnectionListAdapter.MyViewHolder> implements CallAPIComp {

    private ArrayList<JSONObject> dataSet;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (TableLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversationa_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            JSONObject tmp = dataSet.get(position);
            String info1 = "Conversazione con: ";
            String info2 = ""+MainActivity.getId() == tmp.get("idSender").toString() ? tmp.get("nameSender").toString(): tmp.get("nameReceiver").toString();
            holder.textView.setText(info1 + info2);
            holder.textView.setTag(dataSet.get(position).get("id").toString());
            holder.idButton.setTag(dataSet.get(position).get("id").toString());
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

        public MyViewHolder(View v){
            super(v);
            textView = (TextView) v.findViewById(R.id.conversationDesc);
            idButton = (Button) v.findViewById(R.id.goButton);
        }
    }

    public ConnectionListAdapter(ArrayList<JSONObject> dataSet){
        this.dataSet = dataSet;
    }
}
