package com.example.santhosh.travellocator.utils;

import com.example.santhosh.travellocator.AddressLocator;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;

import java.io.IOException;

/**
 * Created by Santhosh Pendem on 1/26/2018.
 */

public class ExactAddress {
	
	
	public String getExactAddress(Context context, LatLng latLng) throws IOException {
		AddressLocator addressLocator = new AddressLocator();
		return addressLocator.getExactAddress(context, latLng);
	}
}
