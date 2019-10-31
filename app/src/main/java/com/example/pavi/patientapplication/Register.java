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

public class Register extends AppCompatActivity {
    EditText Name,Surname,Fullname,Email,UserName,Password,ConfPassword;
    String name,surname,fullname,email,username,password,confpassword;
    String ID;
    String code;
    AlertDialog.Builder builder;
    String register_url="http://192.168.43.55/hospital/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Email=(EditText)findViewById(R.id.etEmail);
        Name=(EditText)findViewById(R.id.etName);
        Surname=(EditText)findViewById(R.id.etSurname);
        Fullname=(EditText)findViewById(R.id.etFullName);
        Password=(EditText)findViewById(R.id.etPassword);
        ConfPassword=(EditText)findViewById(R.id.etConfPassword);
        UserName=(EditText)findViewById(R.id.etUserName);
        builder=new AlertDialog.Builder(Register.this);
    }

    public void onReg(View view){
        name=Name.getText().toString();
        surname=Surname.getText().toString();
        fullname=Fullname.getText().toString();
        email=Email.getText().toString();
        username=UserName.getText().toString();
        password=Password.getText().toString();
        confpassword=ConfPassword.getText().toString();

        if(name.equals("")||email.equals("")||username.equals("")||password.equals("")||confpassword.equals("")){
            builder.setTitle("Something went wrong...");
            builder.setMessage("Please fill all the fields...");
            displayAlert("input_error");
        }else if(!(password.equals(confpassword))){
            builder.setTitle("Something went wrong...");
            builder.setMessage("Your passwords are not matching...");
            displayAlert("input_error");
        }else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        code=jsonObject.getString("code");
                        String message=jsonObject.getString("message");
                        builder.setTitle("Server Response...");
                        builder.setMessage(message);

                        String json_url = "http://192.168.43.55/hospital/afterRegister.php?username="+username;
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, json_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code1=jsonObject.getString("code");
                                    if(code1.equals("login_failed")){
                                    }else{
                                        ID = jsonObject.getString("id");
                                        displayAlert(code);
                                    }
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Register.this,"Error",Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }
                        });
                        MySingleton.getInstance(Register.this).addToRequestque(stringRequest);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Register.this,"Error",Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params=new HashMap<String, String>();
                    params.put("name",name);
                    params.put("surname",surname);
                    params.put("full_name",fullname);
                    params.put("email",email);
                    params.put("username",username);
                    params.put("password",password);

                    return params;
                }
            };
            MySingleton.getInstance(Register.this).addToRequestque(stringRequest);
        }

    }

    public void displayAlert(final String code){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("input_error")){
                    Password.setText("");
                    ConfPassword.setText("");
                }else if(code.equals("reg_success")){
                    Intent il= new Intent(Register.this,Patient.class);
                    Bundle bundle = new Bundle();
                    //bundle.putString("Username",username);
                    bundle.putString("Full_Name",fullname);
                    bundle.putString("ID",ID);
                    il.putExtras(bundle);
                    startActivity(il);
                }else if(code.equals(("reg_failed"))){
                    Name.setText("");
                    Email.setText("");
                    UserName.setText("");
                    Password.setText("");
                    ConfPassword.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
