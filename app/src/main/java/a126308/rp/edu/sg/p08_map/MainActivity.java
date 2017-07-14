package a126308.rp.edu.sg.p08_map;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Spinner spnLocation;

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnLocation = (Spinner)findViewById(R.id.spinnerLocation);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map1);


        final LatLng poi_NorthPoint = new LatLng(1.4296, 103.8361);
        final LatLng poi_Downtown = new LatLng(1.3764, 103.9549);
        final LatLng poi_AMK = new LatLng(1.3694, 103.8488);

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);


                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                }

                final Marker np = map.addMarker(new
                        MarkerOptions()
                        .position(poi_NorthPoint)
                        .title("North - HQ")
                        .snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm\n" +
                                "Tel:65433456\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                final Marker dt = map.addMarker(new
                        MarkerOptions()
                        .position(poi_Downtown)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm\n" +
                                "Tel:66776677\"\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                final Marker amk = map.addMarker(new
                        MarkerOptions()
                        .position(poi_AMK)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                                "Operating hours: 11am-8pm\n" +
                                "Tel:67788652\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        if (marker.getTitle().equalsIgnoreCase("north - hq")){
                            Toast.makeText(MainActivity.this, np.getTitle(), Toast.LENGTH_SHORT).show();
                        } else if (marker.getTitle().equalsIgnoreCase("east")) {
                            Toast.makeText(MainActivity.this, dt.getTitle(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, amk.getTitle(), Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
            }
        });

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spnLocation.getSelectedItemPosition() == 0) {


                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_NorthPoint,
                            15));

                } else if (spnLocation.getSelectedItemPosition() == 1){



                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Downtown,
                            15));
                } else {

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_AMK,
                            15));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}