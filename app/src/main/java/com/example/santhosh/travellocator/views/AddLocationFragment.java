package com.example.santhosh.travellocator.views;

import com.example.santhosh.travellocator.R;
import com.example.santhosh.travellocator.utils.ExactAddress;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Santhosh Pendem on 1/25/2018.
 */

public class AddLocationFragment extends DialogFragment implements View.OnClickListener{
	
	private EditText address,description;
	private Button saveButton;
	private ImageView cancel;
	private Dialog dialog ;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return super.onCreateDialog(savedInstanceState);
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_location,container,true);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		address = view.findViewById(R.id.address);
		description = view.findViewById(R.id.description);
		saveButton = view.findViewById(R.id.saveLocation);
		cancel = view.findViewById(R.id.cancel);
		cancel.setOnClickListener(v -> dialog.dismiss());
		
		String exactAddress = getArguments().getString("exactAddress");
		deDup(exactAddress);
		address.setText(exactAddress);
	}
	
	public String deDup(String s) {
		return Arrays.stream(s.split(",")).distinct().collect(Collectors.joining(",\n"));
	}
	
	@Override
	public void onStart() {
		super.onStart();
		dialog = getDialog();
		if (dialog != null) {
			int width = ViewGroup.LayoutParams.MATCH_PARENT;
			int height = ViewGroup.LayoutParams.MATCH_PARENT;
			dialog.getWindow().setLayout(width, height);
		}
	}
	
	@Override
	public void onClick(View v) {
	
		
	}
}
