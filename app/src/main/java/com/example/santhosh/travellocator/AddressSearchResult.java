package com.example.santhosh.travellocator;

import android.location.Address;

/**
 * Created by Santhosh Pendem on 1/30/2018.
 */

public class AddressSearchResult {
	
	private Address address;
	
	public AddressSearchResult(Address address) {
		this.address = address;
	}
	
	public String getAddress() {
		StringBuilder display_address = new StringBuilder();
		display_address.append(address.getAddressLine(0)).append("\n");
		
		for (int i = 1; i < address.getMaxAddressLineIndex(); i++) {
			display_address.append(address.getAddressLine(i)).append(", ");
		}
		display_address = new StringBuilder(display_address.substring(0, display_address.length() - 2));
		return display_address.toString();
	}
	
	public String toString() {
		StringBuilder display_address = new StringBuilder();
		if (address.getFeatureName() != null) {
			display_address.append(address).append(", ");
		}
		for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
			display_address.append(address.getAddressLine(i));
		}
		return display_address.toString();
	}
}
