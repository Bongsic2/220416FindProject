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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;


public class ParentsActivity extends AppCompatActivity {
    SupportMapFragment mapFragment;
    GoogleMap map;

    MarkerOptions myLocationMarker;

    NavigationView navigationView;

    private String dbName, dbPhoneNumber, dbBirthDay, dbAddress, dbEmail;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);

        final DrawerLayout drawerLayout = findViewById(R.id.LinearLayout);

        findViewById(R.id.ParentsImageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Log.d("Map", "지도 준비됨.");

                map = googleMap;
            }
        });
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 전화 코드
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
        // 위치불러오는 코드
        Button requestbutton = findViewById(R.id.requestbutton);
        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLocationService();
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            flag = false;
                            // 개인정보가 있다면 정보 출력
                            Map<String, Object> hm = document.getData();
                            dbName = hm.get("name").toString();
                            dbPhoneNumber = hm.get("phoneNumber").toString();
                            dbBirthDay = hm.get("birthDay").toString();
                            dbAddress = hm.get("address").toString();

                            if (hm.get("email") != null) {
                                dbEmail = hm.get("email").toString();
                            }

                        }
                    }
                }
            }
        });
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.child1 :
                        Toast.makeText(getApplicationContext(),"asdf",Toast.LENGTH_SHORT).show();
                    case R.id.child2 :
                        Toast.makeText(getApplicationContext(),"asdf",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }


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
//            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (lastLocation != null) {
//                showCurrentLocation(lastLocation);
//            }
//            manager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER,
//                    minTime,
//                    minDistance,
//                    new LocationListener() {
//                        @Override
//                        public void onLocationChanged(@NonNull Location location) {
//                            showCurrentLocation(location);
//
//                            addPictures(location);
//                        }
//
//                        @Override
//                        public void onProviderDisabled(@NonNull String provider) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(@NonNull String provider) {
//
//                        }
//                    });

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        try {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

            showMyLocationMarker(location);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMyLocationMarker(Location location) {
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myLocationMarker.title(" 집 위치\n");
            myLocationMarker.snippet(" GSP로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);
        } else {
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

//    private void addPictures(Location location) {
//        int pictureResId = R.drawable.friend03;
//        String msg = "김성수\n" + "010-8671-7273";
//
//        if (child1 == null) {
//            child1 = new MarkerOptions();
//            child1.position(new LatLng(location.getLatitude() + 3000, location.getLongitude() + 3000));
//            child1.title(" 아이1\n");
//            child1.snippet(msg);
//            child1.icon(BitmapDescriptorFactory.fromResource(pictureResId));
//            //child1.icon(BitmapDescriptorFactory.fromResource(R.drawable.friend03));
//            map.addMarker(child1);
//        } else {
//            child1.position(new LatLng(location.getLatitude() + 3000, location.getLongitude() + 3000));
//        }
//        pictureResId = R.drawable.friend04;
//        msg = "김상수\n" + "010-7286-8885";
//
//        if (child2 == null) {
//            child2 = new MarkerOptions();
//            child2.position(new LatLng(location.getLatitude() + 2000, location.getLongitude() - 1000));
//            child2.title(" 아이2\n");
//            child2.snippet(msg);
//            child2.icon(BitmapDescriptorFactory.fromResource(pictureResId));
//            //child2.icon(BitmapDescriptorFactory.fromResource(R.drawable.friend04));
//            map.addMarker(child2);
//        } else {
//            child2.position(new LatLng(location.getLatitude() + 2000, location.getLongitude() - 1000));
//        }
//    }

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


