package com.example.santhosh.travellocator.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.santhosh.travellocator.AddressLocator;
import com.example.santhosh.travellocator.R;
import com.example.santhosh.travellocator.utils.ExactAddress;
import com.example.santhosh.travellocator.utils.Permissions;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.tmobile.tmoid.sdk.Agent;
import com.tmobile.tmoid.sdk.AgentService;
import com.tmobile.tmoid.sdk.PushType;
import com.tmobile.tmoid.sdk.impl.util.Prefs;
import com.tmobile.tmoid.sdk.AccessToken;
import com.tmobile.tmoid.sdk.AsyncCall;
import com.tmobile.tmoid.sdk.impl.configuration.ConfiguratorActivity;
import com.tmobile.tmoid.sdk.impl.util.Prefs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
	
	
	private GoogleMap mMap;
	private boolean hasGrantedPermissions = true;
	private LocationManager mLocationManager;
	
	public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
	public static final int LOCATION_UPDATE_MIN_TIME = 5000;
	
	Bundle bundle = new Bundle();
	
	/*//TODO Remove this later
	private AgentService agentService;
	private Agent agent;
	private AccessToken accessToken;
	
	private String myClientId = "";
	private String myTransId = LaunchUUID.getHexUUID();
	public static final String EMPTY_STRING = "";
	private static final int CONFIG_ACT = 1000;
	*/
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		/*Prefs prefs = new Prefs(this);
		myClientId = prefs.fetch("client.id", "TMOAppNative");
		myTransId = prefs.fetch("transaction.id", UUID.randomUUID().toString());
		agentService = AgentService.getInstance(this,myClientId, myTransId, PushType.PushNone);
		initialize();*/
		
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
	
	
	private void addMarker(Location location) {
		if (mMap != null) {
			mMap.clear();
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
			mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker marker) {
					onMarkerClickListener();
					return false;
				}
			});
		}
	}
	
	private void onMarkerClickListener(){
	AddLocationFragment addLocationFragment = new AddLocationFragment();
	bundle
	addLocationFragment.show(getFragmentManager(),"Location");
	}
	
	@Override
	public void onMapClick(LatLng latLng) {
	
	}
	
	@Override
	public void onMapLongClick(LatLng latLng) {
		ExactAddress exactAddress = new ExactAddress();
		try {
			mMap.addMarker(new MarkerOptions().position(latLng).title(exactAddress.getExactAddress(this, latLng)).icon(BitmapDescriptorFactory.defaultMarker
					(BitmapDescriptorFactory.HUE_GREEN)));
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
	
	/*protected void initialize() {
		startActivityForResult(new Intent(this, ConfiguratorActivity.class), CONFIG_ACT);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1000) {
			agentService = AgentService.getInstance(this, myClientId, myTransId, PushType.PushNone);
			agentService.connectAgent(agent -> {
				MapsActivity.this.agent = agent;
				useSDK();
				
			}, ex -> {
				Log.e("MapsActivity", "", ex);
			});
			
		}
		
	}
	
	private void useSDK() {
		String userId = "";
		Map<String, String> oauthParameters = new HashMap<>();
		oauthParameters.put("scope", "TMO_ID_profile associated_lines openid extended_lines");
		oauthParameters.put("access_type", "online");
		oauthParameters.put("approval_prompt", "auto");
		oauthParameters.put("response_selection", "id_token.basic");
		agent.setBioEnabled(false, null);
		
		AsyncCall call = agent.requestAccessToken(MapsActivity.this, userId, oauthParameters, token -> {
			MapsActivity.this.accessToken = token;
			System.out.println("AccessToken " + accessToken.toJsonString());
		}, ex -> {
			Log.e("Playground", "Got error:" + ex.toMsg());
		}, json -> {
			Log.d("Playground", json);
		});
		
	}
	
	public static final class LaunchUUID {
		private static AtomicReference<String> hexValue = new AtomicReference<>(EMPTY_STRING);
		
		public static String getHexUUID() {
			return hexValue.get();
		}
	}
	*/
}
