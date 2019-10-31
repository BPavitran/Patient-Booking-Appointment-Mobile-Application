package com.example.pavi.patientapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText UserName,Password;
    String username,password;
    AlertDialog.Builder builder;
    String login_url="http://192.168.43.55/hospital/patient_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserName=(EditText)findViewById(R.id.etUserName);
        Password=(EditText)findViewById(R.id.etPassword);
        builder=new AlertDialog.Builder(MainActivity.this);
    }

    public void onLogin(View view){
        username=UserName.getText().toString();
        password=Password.getText().toString();

        if(username.equals("")||password.equals("")){
            builder.setTitle("Something went wrong");
            displayAlert("Enter a valid username and password...");
        }else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code=jsonObject.getString("code");
                        if(code.equals("login_failed")){
                            builder.setTitle("Login Error...");
                            displayAlert(jsonObject.getString("message"));
                        }else{
                                Intent intent = new Intent(MainActivity.this,Patient.class);
                            Bundle bundle= new Bundle();
                            bundle.putString("Username",jsonObject.getString("username"));
                            bundle.putString("ID",jsonObject.getString("id"));
                            bundle.putString("Full_Name",jsonObject.getString("full_name"));
                            bundle.putInt("Num",1);
                            intent.putExtras(bundle);
                                startActivity(intent);
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params=new HashMap<String, String>();
                    params.put("username",username);
                    params.put("password",password);

                    return params;
                }
            };
            MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);

        }
    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserName.setText("");
                Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void openReg(View view){
        startActivity(new Intent(this,Register.class));
    }
}
