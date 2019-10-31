package com.example.pavi.patientapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class information extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String ID;
    String Full_Name;
    String Name;
    String Surname;
    String Age;
    String Gender;
    String Blood;
    String NIC;
    String Address;
    String City;
    String Email;
    String Phone;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
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

        String json_url = "http://192.168.43.55/hospital/getPatientInformation.php?id="+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, json_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Name = jsonObject.getString("name");
                    Surname = jsonObject.getString("surname");
                    Full_Name = jsonObject.getString("full_name");
                    Age = jsonObject.getString("age");
                    Gender = jsonObject.getString("gender");
                    Blood = jsonObject.getString("blood");
                    NIC = jsonObject.getString("NIC");
                    Address = jsonObject.getString("address");
                    City = jsonObject.getString("city");
                    Email = jsonObject.getString("email");
                    Phone = jsonObject.getString("phone");
                    Username = jsonObject.getString("username");

                    TextView txtName = (TextView)findViewById(R.id.txtName);
                    TextView txtSurname = (TextView)findViewById(R.id.txtSurname);
                    TextView txtFullName = (TextView)findViewById(R.id.txtFullName);
                    TextView txtAge = (TextView)findViewById(R.id.txtAge);
                    TextView txtGender = (TextView)findViewById(R.id.txtGender);
                    TextView txtBlood = (TextView)findViewById(R.id.txtBlood);
                    TextView txtNIC = (TextView)findViewById(R.id.txtNIC);
                    TextView txtAddress = (TextView)findViewById(R.id.txtAddress);
                    TextView txtCity = (TextView)findViewById(R.id.txtCity);
                    TextView txtEmail = (TextView)findViewById(R.id.txtEmail);
                    TextView txtPhone = (TextView)findViewById(R.id.txtPhone);
                    TextView txtUsername = (TextView)findViewById(R.id.txtUsername);

                    txtName.setText(Name);
                    txtSurname.setText(Surname);
                    txtFullName.setText(Full_Name);
                    txtAge.setText(Age);
                    txtGender.setText(Gender);
                    txtBlood.setText(Blood);
                    txtNIC.setText(NIC);
                    txtAddress.setText(Address);
                    txtCity.setText(City);
                    txtEmail.setText(Email);
                    txtPhone.setText(Phone);
                    txtUsername.setText(Username);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(information.this,"Error",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(information.this).addToRequestque(stringRequest);

            View headerView = navigationView.getHeaderView(0);

            TextView navUsername = (TextView) headerView.findViewById(R.id.txtFullName);
            navUsername.setText(Full_Name);
    }

    public void onMakeUpdate (View view){
        Intent intent = new Intent(information.this,InformationUpdate.class);
        Bundle bundle= new Bundle();
        bundle.putString("ID",ID);
        bundle.putString("Full_Name",Full_Name);
        bundle.putString("Age",Age);
        bundle.putString("NIC",NIC);
        bundle.putString("Address",Address);
        bundle.putString("City",City);
        bundle.putString("Email",Email);
        bundle.putString("Phone",Phone);
        intent.putExtras(bundle);
        startActivity(intent);
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
        getMenuInflater().inflate(R.menu.information, menu);
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
            Intent intent = new Intent(information.this,MakeAppointment.class);
            Bundle bundle= new Bundle();
            bundle.putString("ID",ID);
            bundle.putString("Full_Name",Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_specification){
            Intent intent = new Intent(information.this,ViewSpecification.class);
            Bundle bundle= new Bundle();
            bundle.putString("ID",ID);
            bundle.putString("Full_Name",Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_information) {
            Intent intent = new Intent(information.this, information.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", ID);
            bundle.putString("Full_Name", Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_home) {
            Intent intent = new Intent(information.this, Patient.class);
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
