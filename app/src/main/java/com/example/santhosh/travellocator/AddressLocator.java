package com.example.santhosh.ubercaranimation;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Santhosh Pendem on 1/24/2018.
 */

public class AddressLocator {
	
	Geocoder geocoder;
	List<Address> addresses;
	
	public String getExactAddress(Context context,LatLng latLng) throws IOException {
		geocoder = new Geocoder(context, Locale.getDefault());
		addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
		
		String street = addresses.get(0).getAddressLine(0);
		String city = addresses.get(0).getLocality();
		String state = addresses.get(0).getAdminArea();
		String country = addresses.get(0).getCountryName();
		String postalCode = addresses.get(0).getPostalCode();

		StringBuilder address = new StringBuilder();
		return address.append(street + ",\n").append(city + ",\n").append(state + ",\n").append(country + ",\n").append(postalCode + ",\n").toString();
		
	}
}
