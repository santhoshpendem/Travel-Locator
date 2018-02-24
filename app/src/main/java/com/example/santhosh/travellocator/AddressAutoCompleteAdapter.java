package com.example.santhosh.travellocator;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santhosh Pendem on 1/30/2018.
 */

public class AddressAutoCompleteAdapter extends BaseAdapter implements Filterable {
	
	private static final int MAX_RESULTS = 6;
	private List autoListAddress = new ArrayList();
	private Context context;
	
	public AddressAutoCompleteAdapter(Context context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return autoListAddress.size();
	}
	
	
	
	@Override
	public Object getItem(int position) {
		return position;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.address_search_result, parent,false);
		}
		((TextView) convertView.findViewById(R.id.address_search_result_text)).setText(getItem(position).toString());
		//((TextView) convertView.findViewById(R.id.geo_search_result_text)).setText(getItem(position).getAddress());
		return convertView;
	}
	
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults addressResults = new FilterResults();
				if(constraint != null){
					List locations = findLocations(context, constraint.toString());
					
					addressResults.values = locations;
					addressResults.count = locations.size();
				}
				return addressResults;
			}
			
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				if(results != null && results.count != 0){
					autoListAddress = (List) results.values;
					notifyDataSetChanged();
				}else{
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}
	
	private List<AddressSearchResult> findLocations(Context context, String query_text){
			List<AddressSearchResult> geo_search_results = new ArrayList<AddressSearchResult>();
		
		Geocoder geocoder = new Geocoder(context, context.getResources().getConfiguration().locale);
		List<Address> addresses = null;
		try{
			addresses  = geocoder.getFromLocationName(query_text, 15);
			for(int i=0; i<addresses.size();i++){
				Address address = addresses.get(i);
				if(address.getMaxAddressLineIndex() != -1){
					geo_search_results.add(new AddressSearchResult(address));
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return geo_search_results;
	}
}
