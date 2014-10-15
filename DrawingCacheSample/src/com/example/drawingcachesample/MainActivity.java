package com.example.drawingcachesample;

import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ui.custom_scrollview.LockingScrollView;

/**
 * DrawingCaheSampleと銘打っときながら
 * DrawingCaheを全く使っていないサンプル
 * @author ootaakihiro
 */
@SuppressLint("ClickableViewAccessibility") public class MainActivity extends Activity {

	private ViewGroup relative;
	private TouchPaint editScreen;
	private LockingScrollView mScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		relative = (RelativeLayout)findViewById(R.id.relative);
	}

	@Override
	protected void onResume() {
		Log.v("layout_size", ""+relative.getWidth() + " : " + relative.getHeight());
		super.onResume();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Log.v("layout_size", ""+relative.getWidth() + " : " + relative.getHeight());
		editScreen = new TouchPaint(this);
		relative.addView(editScreen, relative.getWidth(), relative.getHeight());
		mScrollView = (LockingScrollView)findViewById(R.id.scrollView);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_regist:
			registScreen();
			break;
		case R.id.action_edit:
			if(editScreen.isEditMode()){
				editScreen.setEditMode(false);
				mScrollView.setScrollingEnabled(true);
				Toast.makeText(this, "EditMode_OFF", Toast.LENGTH_SHORT).show();
			}
			else{
				editScreen.setEditMode(true);
				mScrollView.setScrollingEnabled(false);
				Toast.makeText(this, "EditMode_ON", Toast.LENGTH_SHORT).show();
			}
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void registScreen(){
		String filepath = Environment.getExternalStorageDirectory() + "/"
				+ "sample" + "/" + System.currentTimeMillis() + ".jpg";

		File file = new File(filepath);
		file.getParentFile().mkdir();

		//ViewGroupのサイズで作成したBitMapをCanvasの書き込み先にしてView自体を描画する
		Bitmap relativeBmp = Bitmap.createBitmap(relative.getWidth(), 
				relative.getHeight(), 
				Config.ARGB_8888);
		Canvas canvas = new Canvas(relativeBmp);
		relative.draw(canvas);
		Bitmap save_bmp = relativeBmp;
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			save_bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			Toast.makeText(this, "保存されました。", Toast.LENGTH_SHORT).show();
		} 
		catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "エラー", Toast.LENGTH_SHORT).show();
		}
	}

}
