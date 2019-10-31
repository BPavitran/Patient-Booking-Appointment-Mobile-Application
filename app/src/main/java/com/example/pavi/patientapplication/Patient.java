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

public class Patient extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView welcome;
    String ID;
    String Full_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
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
        welcome=(TextView)findViewById(R.id.txtWelcome);
        welcome.setText("Hello "+Full_Name);

        View headerView = navigationView.getHeaderView(0);

        TextView navUsername = (TextView) headerView.findViewById(R.id.txtFullName);
        navUsername.setText(Full_Name);
    }

    public void onViewSpecification (View view){
        Intent intent = new Intent(Patient.this,ViewSpecification.class);
        Bundle bundle= new Bundle();
        bundle.putString("ID",ID);
        bundle.putString("Full_Name",Full_Name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onMakeAppointment (View view){
        Intent intent = new Intent(Patient.this,MakeAppointment.class);
        Bundle bundle= new Bundle();
        bundle.putString("ID",ID);
        bundle.putString("Full_Name",Full_Name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onViewInformation (View view){
        Intent intent = new Intent(Patient.this,information.class);
        Bundle bundle= new Bundle();
        bundle.putString("ID",ID);
        bundle.putString("Full_Name",Full_Name);
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
        getMenuInflater().inflate(R.menu.patient, menu);
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
            Intent intent = new Intent(Patient.this,MakeAppointment.class);
            Bundle bundle= new Bundle();
            bundle.putString("ID",ID);
            bundle.putString("Full_Name",Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_specification){
            Intent intent = new Intent(Patient.this,ViewSpecification.class);
            Bundle bundle= new Bundle();
            bundle.putString("ID",ID);
            bundle.putString("Full_Name",Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_information) {
            Intent intent = new Intent(Patient.this, information.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", ID);
            bundle.putString("Full_Name", Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_home) {
            Intent intent = new Intent(Patient.this, Patient.class);
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
