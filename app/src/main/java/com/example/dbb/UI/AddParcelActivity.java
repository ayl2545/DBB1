package com.example.dbb.UI;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.app.AlertDialog;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.dbb.Entities.Parcel;
import com.example.dbb.Entities.StatusEnum;
import com.example.dbb.Entities.TypeEnum;
import com.example.dbb.Entities.WeightEnum;
import com.example.dbb.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class AddParcelActivity extends AppCompatActivity {

    DatabaseReference firebaseDatabase=FirebaseDatabase.getInstance().getReference();
    // DatabaseReference parcels=firebaseDatabase.getReference("parcels");
    Parcel parcel;
    Spinner typeP;
    Switch fragileP;
    Spinner weightP;
    TextView location;
    EditText recipient_name;
    EditText recipient_address;
    Button delivery_date_btn;
    TextView delivery_date_txt;
    Button received_date_btn;
    TextView received_date_txt;
    EditText phone_number;
    EditText email;
    Spinner status;
    EditText delivery_name;
    Button save_btn;
    DatePickerDialog datePickerDialog1;
    DatePickerDialog datePickerDialog2;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
    LocationManager locationManager;
    FusedLocationProviderClient client;
    final int LOCATION_PERMISSION_REQUEST=1;
    Geocoder geocoder;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parcel = new Parcel();
        onCreateTypeP();
        onCreateFragileP();
        onCreateWeightP();
        onCreateLocation();
        onCreateRecipientName();
        onCreateRecipientAddress();
        onCreateDeliveryDate();
        onCreateReceivedDate();
        onCreatePhoneNumber();
        onCreateEmail();
        onCreateStatus();
        onCreateDeliveryName();
        onCreateSaveBtn();
    }

    void onCreateTypeP() {
        typeP = findViewById(R.id.type);
        ArrayAdapter<String> myAdepter1 = new ArrayAdapter<String>(AddParcelActivity.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.packTypes)) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        myAdepter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeP.setAdapter(myAdepter1);
    }

    void onCreateFragileP(){
        fragileP = findViewById(R.id.switch_fragile);
    }

    void onCreateWeightP(){
        weightP = findViewById(R.id.weight);
        ArrayAdapter<String> myAdepter2 = new ArrayAdapter<String>(AddParcelActivity.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.packWeight)) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        myAdepter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightP.setAdapter(myAdepter2);
    }

    void onCreateLocation(){
        if (Build.VERSION.SDK_INT>=23){
            int hasLocationPermission=checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasLocationPermission!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST);
            }
            else {
                startLocation();
            }
        }
    }

    void onCreateRecipientName(){
        recipient_name = findViewById(R.id.recipientN);
    }

    void onCreateRecipientAddress(){
        recipient_address = findViewById(R.id.address);
    }

    void onCreateDeliveryDate() {
        delivery_date_btn = findViewById(R.id.delivery_date_btn);
        delivery_date_txt = findViewById(R.id.delivery_date_txt);
        delivery_date_txt.setText(day_of_month + " / " + (month + 1) + " / " + year);
        delivery_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog1 = new DatePickerDialog(AddParcelActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        delivery_date_txt.setText(dayOfMonth + " / " + (month + 1) + " / " + year);

                    }
                }, year, month, day_of_month);
                datePickerDialog1.show();
            }
        });
    }
    void onCreateReceivedDate(){
        received_date_btn = findViewById(R.id.received_date_btn);
        received_date_txt = findViewById(R.id.received_date_txt);
        received_date_txt.setText(day_of_month+" / "+(month+1)+" / "+year);
        received_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog2 = new DatePickerDialog(AddParcelActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        received_date_txt.setText(dayOfMonth + " / " + (month+1) + " / " + year);
                    }
                }, year, month, day_of_month);
                datePickerDialog2.show();
            }
        });
    }

    void onCreatePhoneNumber() {
        phone_number = findViewById(R.id.phone);
    }

    void onCreateEmail(){
        email = findViewById(R.id.email);
    }

    void onCreateStatus(){
        status = findViewById(R.id.status);
        ArrayAdapter<String> myAdepter3 = new ArrayAdapter<String>(AddParcelActivity.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.status)) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        myAdepter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(myAdepter3);
    }

    void onCreateDeliveryName(){
        delivery_name = findViewById(R.id.name_delivery);
    }

    void onCreateSaveBtn(){
        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setParcel();
                String key=firebaseDatabase.push().getKey();
                parcel.setKey(key);
                firebaseDatabase.child("parcels").child(key).setValue(parcel);

            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==LOCATION_PERMISSION_REQUEST) {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddParcelActivity.this);
                builder.setTitle("Attention").setMessage("The application must have location permission in order for it to work!")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:"+getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).setCancelable(false).show();
            }
            else {
                startLocation();
            }
        }
        else {
            startLocation();
        }
    }

    public void setParcel() {
        //parcel.setTypeEnum(TypeEnum.values()[(typeP.getSelectedItemPosition() )-1]);
        //parcel.setFragile(fragileP.isChecked());
        //parcel.setWeightEnum(WeightEnum.values()[weightP.getSelectedItemPosition() - 1]);
        //parcel.setLocation(new Location(String.valueOf(location)));
        //parcel.setRecipientN(recipient_name.getText().toString());
        //parcel.setAddress(recipient_address.getText().toString());
        //parcel.setDateS(datePickerDialog1);
        //parcel.setDateR(datePickerDialog2);
        //parcel.setPhoneNumber(phone_number.getText().toString());
        //parcel.setEmail(email.getText().toString());
        //parcel.setStatusEnum(StatusEnum.values()[status.getSelectedItemPosition() - 1]);
        //parcel.setDeliveryN(delivery_name.getText().toString());
    }

    private void startLocation(){
        location = findViewById(R.id.location);
        geocoder=new Geocoder(this);
        startLocation1();
    }

    void startLocation1(){
        client= LocationServices.getFusedLocationProviderClient(this);
        LocationCallback callback=new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                final Location lastLocation=locationResult.getLastLocation();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                final List<Address> addresses;
                                try {
                                    addresses = geocoder.getFromLocation(lastLocation.getLatitude(),lastLocation.getLongitude(),1);
                                    location.setText(addresses.get(0).getCountryName()+" , "+addresses.get(0).getFeatureName()+" ' "+addresses.get(0).getThoroughfare()+" "+addresses.get(0).getSubThoroughfare());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                };

            }
        };

        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        client.requestLocationUpdates(locationRequest,callback,null);
    }

}

