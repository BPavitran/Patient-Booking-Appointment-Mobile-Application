package com.example.pavi.patientapplication;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.YearMonth;
import org.threeten.bp.temporal.TemporalAdjusters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MakeAppointment extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String ID;
    String Full_Name;
    Spinner Specialization;
    Spinner Doctor;
    Spinner Day;
    Spinner Date;
    Button Submit;

    AlertDialog.Builder builder;
    String submit_url="http://192.168.43.55/hospital/submit.php";

    TextView TextView;
    TextView TextView2;
    TextView TextView3;
    TextView TextView4;
    TextView TextView5;

    TextView txtTime;

    String [] SpecializationArray;
    String [] DoctorArray;
    String [] DayArray;
    String [] TimeArray;

    ArrayList<LocalDate> dates;

    ArrayList<String> SpecializationDifferent;
    ArrayList<String> DoctorDifferent;
    ArrayList<String> DayDifferent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        builder=new AlertDialog.Builder(MakeAppointment.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        ID=bundle.getString("ID");

        View headerView = navigationView.getHeaderView(0);

        Full_Name=bundle.getString("Full_Name");
        TextView navUsername = (TextView) headerView.findViewById(R.id.txtFullName);
        navUsername.setText(Full_Name);

        Specialization = (Spinner)findViewById(R.id.spSpecialization);
        Doctor = (Spinner)findViewById(R.id.spDoctor);
        Day = (Spinner)findViewById(R.id.spDay);
        Date = (Spinner)findViewById(R.id.spDate);

        TextView = (TextView)findViewById(R.id.textView1);
        TextView2 = (TextView)findViewById(R.id.textView2);
        TextView3 = (TextView)findViewById(R.id.textView3);
        TextView4 = (TextView)findViewById(R.id.textView4);
        TextView5 = (TextView)findViewById(R.id.textView5);

        txtTime = (TextView)findViewById(R.id.txtTime);

        Submit = (Button)findViewById(R.id.btnSubmit);

        Doctor.setVisibility(View.GONE);
        Day.setVisibility(View.GONE);
        Date.setVisibility(View.GONE);

        TextView2.setVisibility(View.GONE);
        TextView3.setVisibility(View.GONE);
        TextView4.setVisibility(View.GONE);
        TextView5.setVisibility(View.GONE);

        txtTime.setVisibility(View.GONE);

        Submit.setVisibility(View.GONE);

        String json_url="http://192.168.43.55/hospital/makeAppointment.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, (String)null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count=0;

                SpecializationArray = new String[response.length()];
                DoctorArray = new String[response.length()];
                DayArray = new String[response.length()];
                TimeArray = new String[response.length()];

                if(response.length()!=0) {
                    try {
                        while (count < response.length()) {
                            JSONObject jsonObject = response.getJSONObject(count);

                            SpecializationArray[count] = jsonObject.getString("Specialization");
                            DoctorArray[count] = jsonObject.getString("Doctor");
                            DayArray[count] = jsonObject.getString("Day");
                            TimeArray[count] = jsonObject.getString("Time");

                            count++;
                        }

                        SpecializationDifferent = new ArrayList<>();
                        SpecializationDifferent.add("Select");

                        for(int i=0; i<SpecializationArray.length; i++){
                            if(!SpecializationDifferent.contains(SpecializationArray[i])){
                                SpecializationDifferent.add(SpecializationArray[i]);
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MakeAppointment.this,
                                android.R.layout.simple_spinner_item, SpecializationDifferent);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Specialization.setAdapter(adapter);

                        Specialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Doctor.setVisibility(View.GONE);
                                Day.setVisibility(View.GONE);
                                Date.setVisibility(View.GONE);

                                TextView2.setVisibility(View.GONE);
                                TextView3.setVisibility(View.GONE);
                                TextView4.setVisibility(View.GONE);
                                TextView5.setVisibility(View.GONE);

                                Submit.setVisibility(View.GONE);

                                txtTime.setVisibility(View.GONE);

                                String text = Specialization.getSelectedItem().toString();
                                if(!text.equals("Select")) {
                                    String SpecializationName = Specialization.getSelectedItem().toString();

                                    DoctorDifferent = new ArrayList<>();
                                    DoctorDifferent.add("Select");

                                    for(int i=0; i<DoctorArray.length; i++){
                                        if(!DoctorDifferent.contains(DoctorArray[i])){
                                            if(SpecializationArray[i].equals(SpecializationName)){
                                                DoctorDifferent.add(DoctorArray[i]);
                                            }
                                        }
                                    }

                                    Doctor.setVisibility(View.VISIBLE);

                                    TextView2.setVisibility(View.VISIBLE);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MakeAppointment.this,
                                            android.R.layout.simple_spinner_item, DoctorDifferent);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Doctor.setAdapter(adapter);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        Doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Day.setVisibility(View.GONE);
                                Date.setVisibility(View.GONE);

                                TextView3.setVisibility(View.GONE);
                                TextView4.setVisibility(View.GONE);
                                TextView5.setVisibility(View.GONE);

                                Submit.setVisibility(View.GONE);

                                txtTime.setVisibility(View.GONE);

                                String text2 = Doctor.getSelectedItem().toString();
                                if(!text2.equals("Select")){
                                    String DoctorName = Doctor.getSelectedItem().toString();

                                    DayDifferent = new ArrayList<>();
                                    DayDifferent.add("Select");

                                    for(int i=0; i<DayArray.length; i++){
                                        if(!DayDifferent.contains(DayArray[i])){
                                            if(DoctorArray[i].equals(DoctorName)){
                                                DayDifferent.add(DayArray[i]);
                                            }
                                        }
                                    }

                                    Day.setVisibility(View.VISIBLE);

                                    TextView3.setVisibility(View.VISIBLE);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MakeAppointment.this,
                                            android.R.layout.simple_spinner_item, DayDifferent);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Day.setAdapter(adapter);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        Day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                Date.setVisibility(View.GONE);

                                TextView4.setVisibility(View.GONE);
                                TextView5.setVisibility(View.GONE);

                                Submit.setVisibility(View.GONE);

                                txtTime.setVisibility(View.GONE);

                                String text3 = Day.getSelectedItem().toString();
                                if(!text3.equals("Select")){
                                    String DoctorName = Doctor.getSelectedItem().toString();
                                    String DayName = Day.getSelectedItem().toString();

                                    for(int i=0; i<TimeArray.length; i++){
                                        if(DoctorArray[i].equals(DoctorName)){
                                            if(DayArray[i].equals(DayName)){
                                                txtTime.setText(TimeArray[i]);
                                            }
                                        }
                                    }

                                    Calendar c = Calendar.getInstance();
                                    int year = c.get(Calendar.YEAR);
                                    int month = c.get(Calendar.MONTH);
                                    int week = c.get(Calendar.WEEK_OF_MONTH);

                                    String[] monthName = {"January", "February",
                                            "March", "April", "May", "June", "July",
                                            "August", "September", "October", "November",
                                            "December"};

                                    String name_of_month = monthName[month];

                                    Set<DayOfWeek> daysOfWeek = EnumSet.noneOf ( DayOfWeek.class );
                                    daysOfWeek.add ( DayOfWeek.valueOf(DayName.toUpperCase()) );

                                    YearMonth ym = YearMonth.of ( year , Month.valueOf(name_of_month.toUpperCase()) );  // Or ( 2017 , 1 ) for January.

                                    LocalDate firstOfMonth = ym.atDay ( 1 );

                                    for ( DayOfWeek dayOfWeek : daysOfWeek ) {
                                        dates = new ArrayList<> ( 5 );
                                        LocalDate ld = firstOfMonth.with ( TemporalAdjusters.dayOfWeekInMonth ( week , dayOfWeek ) );
                                        do {
                                            dates.add(ld);
                                            // Set up next loop.
                                            ld = ld.plusWeeks ( 1 );
                                        } while ( YearMonth.from ( ld ).equals ( ym ) );  // While in the targeted month.
                                    }

                                    if(dates.size()<5){
                                        String name_of_next_month = monthName[month+1];

                                        Set<DayOfWeek> daysOfWeek2 = EnumSet.noneOf ( DayOfWeek.class );
                                        daysOfWeek2.add ( DayOfWeek.valueOf(DayName.toUpperCase()) );

                                        YearMonth ym2 = YearMonth.of ( year , Month.valueOf(name_of_next_month.toUpperCase()) );  // Or ( 2017 , 1 ) for January.

                                        LocalDate firstOfMonth2 = ym2.atDay ( 1 );

                                        for ( DayOfWeek dayOfWeek : daysOfWeek ) {
                                            LocalDate ld2 = firstOfMonth2.with ( TemporalAdjusters.dayOfWeekInMonth ( week , dayOfWeek ) );
                                            do {
                                                dates.add ( ld2 );
                                                if(dates.size()==5){
                                                    break;
                                                }
                                                // Set up next loop.
                                                ld2 = ld2.plusWeeks ( 1 );
                                            } while ( YearMonth.from ( ld2).equals ( ym2 ) );  // While in the targeted month.
                                        }
                                    }

                                    Date.setVisibility(View.VISIBLE);

                                    TextView4.setVisibility(View.VISIBLE);

                                    ArrayAdapter<LocalDate> adapter = new ArrayAdapter<LocalDate>(MakeAppointment.this,
                                            android.R.layout.simple_spinner_item, dates);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Date.setAdapter(adapter);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        Date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                TextView5.setVisibility(View.VISIBLE);

                                txtTime.setVisibility(View.VISIBLE);

                                Submit.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MakeAppointment.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MakeAppointment.this,"Error...",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(MakeAppointment.this).addToRequestque(jsonArrayRequest);
    }

    public void onSubmit(View view){

        final String doctor = Doctor.getSelectedItem().toString();
        final String date = Date.getSelectedItem().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, submit_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String message=jsonObject.getString("message");
                        builder.setTitle("Server Response...");
                        builder.setMessage(message);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MakeAppointment.this,"Error",Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params=new HashMap<String, String>();
                    params.put("patient_id",ID);
                    params.put("doctor_name",doctor);
                    params.put("date",date);

                    return params;
                }
            };
            MySingleton.getInstance(MakeAppointment.this).addToRequestque(stringRequest);
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
        getMenuInflater().inflate(R.menu.make_appointment, menu);
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
            Intent intent = new Intent(MakeAppointment.this,MakeAppointment.class);
            Bundle bundle= new Bundle();
            bundle.putString("ID",ID);
            bundle.putString("Full_Name",Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_specification){
            Intent intent = new Intent(MakeAppointment.this,ViewSpecification.class);
            Bundle bundle= new Bundle();
            bundle.putString("ID",ID);
            bundle.putString("Full_Name",Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_information) {
            Intent intent = new Intent(MakeAppointment.this, information.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", ID);
            bundle.putString("Full_Name", Full_Name);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.nav_home) {
            Intent intent = new Intent(MakeAppointment.this, Patient.class);
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
