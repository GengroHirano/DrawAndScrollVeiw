package com.example.drawingcachesample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class NextActivity extends Activity {

	public interface WindowFocusChange{
		public void windowFocusChange(boolean hasFocus);
	}
	
	private WindowFocusChange callback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
		callback = (MainFragment)getFragmentManager().findFragmentById(R.id.fragment);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		callback.windowFocusChange(hasFocus);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
