package com.example.guillemllados.uiiot;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.*;
import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

public class Gps extends Fragment implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private SupportMapFragment mSupportMapFragment;
    boolean locationStatus;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    GoogleMap mGoogleMap;
    Marker mCurrLocationMarker;
    LocationManager locationManager;
    double lat, lon;
    LatLng latLng;
    Location location;
    Button button;
    Marker markers;
    ArrayList<Marker> marks = new ArrayList<>();
    String idDevice;

    public Gps(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gps, container, false);
        InitializeLocationManager(savedInstanceState);
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    private void InitializeLocationManager(final Bundle savedInstanceState){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mSupportMapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
        mSupportMapFragment.getMapAsync(this);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        boolean gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); //es true si esta ences el gps! sino surt false
        boolean networkStatus = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gpsStatus && !networkStatus) {
            locationStatus = false;
            Log.d("TAG", "No pot obtindre l'ubicació");
        }

    }

    @Override //GoogleApiClient
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }
    @Override//GoogleApiClient
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

        List<String> providers = locationManager.getProviders(true);

        for (int i = 0; i < providers.size(); i++) {
            location = locationManager.getLastKnownLocation(providers.get(i));
            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();

                latLng =new LatLng(lat, lon);
                break;
            }
        }

        for(int i = 0; i<Principal.items.size(); i++){

            markers=  mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(Principal.items.get(Integer.toString(i)).getLatitude()),
                    Double.parseDouble(Principal.items.get(Integer.toString(i)).getLongitude()))).title(Principal.items.get(Integer.toString(i)).getNom()));
            marks.add(i,markers);
        }

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        iniButtons();

    }

    public void iniButtons(){
        button = (Button) getActivity().findViewById(R.id.buttonMaps);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(Principal.itemclicked.getLatitude())
                        , Double.parseDouble(Principal.itemclicked.getLongitude())), 15));
                String aux = Principal.itemclicked.getNom().substring(4);
                Marker m = marks.get(Integer.parseInt(aux));
                m.showInfoWindow();
            }
        });
    }

    @Override  //GoogleApiClient
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(getActivity(), "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override //GoogleApiClient
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override //GoogleApiClient
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }


    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}