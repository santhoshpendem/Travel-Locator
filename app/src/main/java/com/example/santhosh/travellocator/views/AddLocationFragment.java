package com.example.santhosh.travellocator.views;

import com.example.santhosh.travellocator.R;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * Created by Santhosh Pendem on 1/25/2018.
 */

public class AddLocationFragment extends DialogFragment implements View.OnClickListener{
	
	private EditText address;
	private ImageView cancel;
	private Dialog dialog ;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return super.onCreateDialog(savedInstanceState);
	}
	
	/**
	 * This method is used to inflate the view with layout.
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.add_location,container,true);
	}
	
	/**
	 * This method Creates the view and initializes all the widgets. Here we are also setting the address to the edittext
	 * @param view
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		address = view.findViewById(R.id.address);
		EditText description = view.findViewById(R.id.description);
		Button saveButton = view.findViewById(R.id.saveLocation);
		cancel = view.findViewById(R.id.cancel);
		cancel.setOnClickListener(v -> dialog.dismiss());
		
		String exactAddress = getArguments().getString("exactAddress");
		address.setText(exactAddress.substring(0,exactAddress.trim().length()-1));
	}
	
	/**
	 * In this method we are trying to make the Dialog fragment to occupy the whole screen.
	 */
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
