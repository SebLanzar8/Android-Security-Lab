package damnvuln.exercise.lab.vulntestlab;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CallAPIComp{

    private JSONObject response;


    //admin menu
    private CheckBox adminCheck;
    private EditText adminPassword;
    private Button adminButton;
    private TextView adminView;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        MainActivity.id = id;
    }

    private static int id;


    @Override
    public void saveResponse(Object response) {
        this.response = (JSONObject) response;
        try {

            //if response come from login request

            if (this.response.getString("token") != null)
                if (this.response.getString("token").compareTo("LoginFailed") !=  0) {
                    setId(Integer.valueOf(this.response.getString("id")));
                    CallAPI.setSessionToken(this.response.getString("token"));
                    switchToMenu();
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Utente o password errati!!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adminCheck = (CheckBox) findViewById(R.id.adminCheck);
        adminPassword = (EditText) findViewById(R.id.adminPass);
        adminButton = (Button) findViewById(R.id.adminGo);
        adminView = (TextView) findViewById(R.id.adminView);

        adminCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    adminView.setVisibility(View.VISIBLE);
                    adminButton.setVisibility(View.VISIBLE);
                    adminPassword.setVisibility(View.VISIBLE);
                }else{

                    adminButton.setVisibility(View.INVISIBLE);
                    adminPassword.setVisibility(View.INVISIBLE);
                    adminView.setVisibility(View.INVISIBLE);
                }

            }
        });

        CallAPI.initCallApi(getCacheDir(), this);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }


    public void sendLoginRequest(View view){

        EditText passwordEdit = (EditText) findViewById(R.id.newpassword);
        String password = passwordEdit.getText().toString();

        password = CallAPI.getMd5(password);

        EditText usernameEdit = (EditText) findViewById(R.id.username);
        String username = usernameEdit.getText().toString();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", username);
        parameters.put("password", password);

        CallAPI.launchRequest("login", parameters , this);

    }

    public void switchToMenu(){
        Intent intent = new Intent(this, ConversationActivity.class);
        startActivity(intent);
    }

    public void goAdminMenu(View v){
        String password  = adminPassword.getText().toString();

        try{

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            password = sb.toString();

        }catch(Exception e){
            e.printStackTrace();
        }


        //Se hai trovato questo md5 NON È UNA PASSWORD non provarla ad usare mai come password admin //maiiiiiiiiiiiii
        if(password.compareTo("00ad24a52f45a5ec45f092592de41007") == 0){
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        }

    }

    public void setIp(View v){
        CallAPI.setIpServer(((TextView) findViewById(R.id.ipServer)).getText().toString());
    }


}

