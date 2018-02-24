package com.example.santhosh.travellocator.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.santhosh.travellocator.AddressAutoCompleteAdapter;
import com.example.santhosh.travellocator.AddressLocator;
import com.example.santhosh.travellocator.AddressSearchResult;
import com.example.santhosh.travellocator.DelayAutoCompleteAddress;
import com.example.santhosh.travellocator.R;
import com.example.santhosh.travellocator.utils.Permissions;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, View.OnClickListener {
	
	
	private GoogleMap mMap;
	private boolean hasGrantedPermissions = true;
	private LocationManager mLocationManager;
	
	
	public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
	public static final int LOCATION_UPDATE_MIN_TIME = 5000;
	
	private Integer THRESHOLD = 2;
	//private DelayAutoCompleteAddress geo_autocomplete;
	//private ImageView geo_autocomplete_clear;
	
	Bundle bundle = new Bundle();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		
		/*geo_autocomplete_clear = (ImageView) findViewById(R.id.geo_autocomplete_clear);
		geo_autocomplete = (DelayAutoCompleteAddress) findViewById(R.id.geo_autocomplete);
		geo_autocomplete.setThreshold(THRESHOLD);
		geo_autocomplete.setAdapter(new AddressAutoCompleteAdapter(this));
		
		geo_autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AddressSearchResult result = (AddressSearchResult) parent.getItemAtPosition(position);
				geo_autocomplete.setText(result.getAddress());
			}
		});
		geo_autocomplete.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()>0){
					geo_autocomplete_clear.setVisibility(View.VISIBLE);
				}else{
					geo_autocomplete_clear.setVisibility(View.GONE);
				}
			}
		});
		
		geo_autocomplete_clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				geo_autocomplete.setText("");
			}
		});*/
		
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mapFragment.getMapAsync(this);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
				.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && hasGrantedPermissions) {
			Permissions.requestPermissions(MapsActivity.this);
			return;
		}
		mMap.setMyLocationEnabled(true);
		getCurrentLocation();
		mMap.setOnMapLongClickListener(this);
		
	}
	
	@SuppressLint("MissingPermission")
	private void getCurrentLocation() {
		boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		Location location = null;
		if (isGPSEnabled) {
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
			location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			addMarker(location);
		}
	}
	
	/**
	 * This initialization allows us to detect the change in the location
	 */
	private android.location.LocationListener mLocationListener = new android.location.LocationListener() {
		@Override
		public void onLocationChanged(android.location.Location location) {
			if (location != null) {
				addMarker(location);
			}
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		
		}
		
		@Override
		public void onProviderEnabled(String provider) {
		
		}
		
		@Override
		public void onProviderDisabled(String provider) {
		
		}
	};
	
	/**
	 * THis method is used to add the marker on the map and animate the screen when we launch the app(like the zoom in effect)
	 * @param location
	 */
	@SuppressLint("MissingPermission")
	private void addMarker(Location location) {
		if (mMap != null) {
			mMap.clear();
			location = mLocationManager.getLastKnownLocation(mLocationManager.getProviders(true).get(0));
			double latitude = 0, longitude = 0;
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}
			LatLng latLng = new LatLng(latitude, longitude);
			mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
			mMap.setOnMarkerClickListener(marker -> {
				onMarkerClickListener();
				return false;
			});
		}
	}
	
	/**
	 * THis method listens for the clicks on the markers. Here we are opening the dialog fragment.
	 */
	private void onMarkerClickListener() {
		AddLocationFragment addLocationFragment = new AddLocationFragment();
		addLocationFragment.setArguments(bundle);
		addLocationFragment.show(getFragmentManager(), "Location");
	}
	
	@Override
	public void onMapClick(LatLng latLng) {
	
	}
	
	@Override
	public void onMapLongClick(LatLng latLng) {
		AddressLocator addressLocator = new AddressLocator();
		try {
			bundle.putString("exactAddress", addressLocator.getExactAddress(this, latLng));
			mMap.addMarker(new MarkerOptions().position(latLng).title(addressLocator.getExactAddress(this, latLng).replace(",", ",\n")).icon
					(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		if (requestCode == Permissions.REQUEST_PERMISSIONS) {
			for (int grantResult : grantResults) {
				if (grantResult != PackageManager.PERMISSION_GRANTED) {
					hasGrantedPermissions = false;
					break;
				}
			}
		} else {
			finish();
		}
	}
	
	@SuppressLint("MissingPermission")
	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
			case R.id.search:
				EditText address_search = findViewById(R.id.address_search);
				String addressEntered = address_search.getText().toString().trim();
				List<Address> addresses = null;
				if (addressEntered != null || addressEntered.equals("")) {
					Geocoder geocoder = new Geocoder(this);
					try {
						addresses = geocoder.getFromLocationName(addressEntered, 1);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					Address address = addresses.get(0);
					LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
					mMap.addMarker(new MarkerOptions().position(latLng));
					mMap.setMyLocationEnabled(true);
					mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
				}
				break;
			case R.id.fab:
				if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
					mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				} else {
					mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				}
		}
		
	}
}
