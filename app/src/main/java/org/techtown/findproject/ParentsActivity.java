package org.techtown.findproject;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;


public class ParentsActivity extends AppCompatActivity {
    SupportMapFragment mapFragment;
    GoogleMap map;
    private String TAG = "abc";
    MarkerOptions myLocationMarker;

    NavigationView navigationView;
    private String dbName, dbPhoneNumber, dbBirthDay, dbAddress, dbEmail;
    private boolean flag = true;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);


        //-------------------------------------------------------------------------------
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("??????").addChildEventListener(new ChildEventListener() {  // message??? child??? ???????????? ???????????????.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TestData chatData = dataSnapshot.getValue(TestData.class);  // chatData??? ????????????
                Log.d(TAG, "??????=" + chatData.getLatitude());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // ----------------------------------------------------------------------
        databaseReference.child("??????").addChildEventListener(new ChildEventListener() {  // message??? child??? ???????????? ???????????????.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TestData chatData = dataSnapshot.getValue(TestData.class);  // chatData??? ????????????
                Log.d(TAG, "??????=" + chatData.getLongitude());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // ----------------------------------------------------------------------
        final DrawerLayout drawerLayout = findViewById(R.id.LinearLayout);
        findViewById(R.id.ParentsImageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
// ----------------------------------------------------------------------
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Log.d("Map", "?????? ?????????.");

                map = googleMap;
            }
        });
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ----------------------------------------?????? ??????
        Button callbutton = findViewById(R.id.callbutton);
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:010-9328-9578"));

                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // --------------------------------------?????????????????? ??????
        Button requestbutton = findViewById(R.id.requestbutton);
        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLocationService();
            }
        });
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        //DocumentReference docRef = db.collection("users").document(user.getUid()).collection("test");
//        CollectionReference collectionReference = db.collection("users").document(user.getUid()).collection("test");
//
//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot coll : task.getResult()) {
//                        coll.getId();
//                    }
//                }
//            }
//        });

    }

    // ----------------------------------------------------------------------
    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        long minTime = 10000;
        float minDistance = 0;

        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onProviderEnabled(@NonNull String provider) {

                        }

                        @Override
                        public void onProviderDisabled(@NonNull String provider) {

                        }
                    }
            );

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------------------
    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        try {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

            showMyLocationMarker(location);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------------------
    private void showMyLocationMarker(Location location) {
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myLocationMarker.title(" ??? ??????\n");
            myLocationMarker.snippet(" GSP??? ????????? ??????");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);
        } else {
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (map != null) {
            try {
                map.setMyLocationEnabled(false);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (map != null) {
            try {
                map.setMyLocationEnabled(true);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
//    }
//
//    @Override
//    public void onDenied(int i, String[] permissions) {
//        Toast.makeText(this, "permissions denied : " + permissions.length, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onGranted(int i, String[] permissions) {
//        Toast.makeText(this, "permissions denied : " + permissions.length, Toast.LENGTH_SHORT).show();
//    }

}


