package com.example.santhosh.ubercaranimation.utils;

import com.example.santhosh.ubercaranimation.MapsActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Santhosh Pendem on 1/24/2018.
 */

public class Permissions {
	
	public static final String PERMISSIONS_REQUIRED[] = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION};
	public static final int REQUEST_PERMISSIONS = 100;
	
	
	public static void requestPermissions(Context context) {
		boolean showRationale;
		for (String permission : PERMISSIONS_REQUIRED) {
			showRationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
			if (!showRationale) {
				break;
			}
		}
		ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
	}
	
}
