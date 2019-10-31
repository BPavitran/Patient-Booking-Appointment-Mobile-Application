package com.example.pavi.patientapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
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

public class InformationUpdate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String ID;
    String Full_Name;
    String Age;
    String Address;
    String City;
    String Email;
    String Phone;

    AlertDialog.Builder builder;
    String json_url;
    EditText txtAge,txtAddress,txtCity,txtEmail,txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        ID=bundle.getString("ID");
        Full_Name=bundle.getString("Full_Name");
        Age=bundle.getString("Age");
        Address=bundle.getString("Address");
        City=bundle.getString("City");
        Email=bundle.getString("Email");
        Phone=bundle.getString("Phone");
        builder=new AlertDialog.Builder(InformationUpdate.this);

        json_url="http://192.168.43.55/hospital/updatePatientInformation.php";

        View headerView = navigationView.getHeaderView(0);

        TextView navUsername = (TextView) headerView.findViewById(R.id.txtFullName);
        navUsername.setText(Full_Name);

        txtAge = (EditText)findViewById(R.id.txtAge);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtCity = (EditText)findViewById(R.id.txtCity);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPhone = (EditText)findViewById(R.id.txtPhone);

        txtAge.setText(Age);
        txtAddress.setText(Address);
        txtCity.setText(City);
        txtEmail.setText(Email);
        txtPhone.setText(Phone);
    }

    public void onUpdate (View view){
        Age=txtAge.getText().toString();
        Address=txtAddress.getText().toString();
        City=txtCity.getText().toString();
        Email=txtEmail.getText().toString();
        Phone=txtPhone.getText().toString();

        if(Age.equals("")||Address.equals("")||City.equals("")||Email.equals("")||Phone.equals("")){
            builder.setTitle("Something went wrong...");
            builder.setMessage("Please fill all the fields...");
            displayAlert("input_error");
        }else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, json_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code=jsonObject.getString("code");
                        String message=jsonObject.getString("message");
                        builder.setTitle("Server Response...");
                        builder.setMessage(message);
                        displayAlert(code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(InformationUpdate.this,"Error",Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params=new HashMap<String, String>();
                    params.put("id",ID);
                    params.put("age",Age);
                    params.put("address",Address);
                    params.put("city",City);
                    params.put("email",Email);
                    params.put("phone",Phone);

                    return params;
                }
            };
            MySingleton.getInstance(InformationUpdate.this).addToRequestque(stringRequest);
        }
    }

    public void displayAlert(final String code){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("input_error")){
                    txtAge.setText(Age);
                    txtAddress.setText(Address);
                    txtCity.setText(City);
                    txtEmail.setText(Email);
                    txtPhone.setText(Phone);
                }else if(code.equals("update_success")){
                    Intent intent = new Intent(InformationUpdate.this,information.class);
                    Bundle bundle= new Bundle();
                    bundle.putString("ID",ID);
                    bundle.putString("Full_Name",Full_Name);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.information_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_appointment){
            Intent intent = new Intent(InformationUpdate.this,MakeAppointment.class);
            Bundle bundle= new Bundle();
            bundle.putString("ID",ID);
            bundle.putString("Full_Name",Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_specification){
            Intent intent = new Intent(InformationUpdate.this,ViewSpecification.class);
            Bundle bundle= new Bundle();
            bundle.putString("ID",ID);
            bundle.putString("Full_Name",Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_information) {
            Intent intent = new Intent(InformationUpdate.this, information.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", ID);
            bundle.putString("Full_Name", Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_home) {
            Intent intent = new Intent(InformationUpdate.this, Patient.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", ID);
            bundle.putInt("Num", 1);
            bundle.putString("Full_Name", Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
