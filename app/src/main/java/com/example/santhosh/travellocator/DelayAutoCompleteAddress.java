package com.example.santhosh.travellocator;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Santhosh Pendem on 1/30/2018.
 */

public class DelayAutoCompleteAddress extends android.support.v7.widget.AppCompatAutoCompleteTextView {
	
	private static final int MESSAGE_TEXT_CHANGED =100;
	private static final int DEFAULT_AUTO_COMPLETE_DELAY = 750;
	
	private int autoCompleteDelay = DEFAULT_AUTO_COMPLETE_DELAY;
	private ProgressBar loadingIndicator;
	
	private final Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			DelayAutoCompleteAddress.super.performFiltering((CharSequence) msg.obj, msg.arg1);
		}
	};
	
	public DelayAutoCompleteAddress(Context context, AttributeSet attributeSet){
		super(context,attributeSet);
	}
	
	public void setLoadingIndicator(ProgressBar progressBar){
		loadingIndicator = progressBar;
	}
	
	public void setAutoCompleteDelay(int autoCompleteDelay){
		autoCompleteDelay = autoCompleteDelay;
	}
	
	@Override
	protected void performFiltering(CharSequence text, int keyCode){
		if(loadingIndicator != null){
			loadingIndicator.setVisibility(View.VISIBLE);
		}
		handler.removeMessages(MESSAGE_TEXT_CHANGED);
		handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_TEXT_CHANGED, text), autoCompleteDelay);
	}
	
	@Override
	public void onFilterComplete(int count){
		if(loadingIndicator != null){
			loadingIndicator.setVisibility(View.GONE);
		}
		super.onFilterComplete(count);
	}
}
