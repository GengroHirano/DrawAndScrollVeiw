package com.example.drawingcachesample;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ui.custom_scrollview.LockingScrollView;

public class MainFragment extends Fragment implements NextActivity.WindowFocusChange{

	private ViewGroup mRelative;
	private TouchPaint mEditScreen;
	private LockingScrollView mScrollView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View mView = inflater.inflate(R.layout.fragment_main, container);
		mRelative = (RelativeLayout)mView.findViewById(R.id.relativeF);
		mScrollView = (LockingScrollView)mView.findViewById(R.id.scrollViewF);
		return mView;
	}
	
	@Override
	public void windowFocusChange(boolean hasFocus) {
		mEditScreen = new TouchPaint(getActivity().getApplicationContext());
		if (mEditScreen == null) {
			Log.v("msg", "editscreen is null");
		}
		mRelative.addView(mEditScreen, mRelative.getWidth(), mRelative.getHeight());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_regist:
			registScreen();
			break;
		case R.id.action_edit:
			if(mEditScreen.isEditMode()){
				mEditScreen.setEditMode(false);
				mScrollView.setScrollingEnabled(true);
				Toast.makeText(getActivity(), "EditMode_OFF", Toast.LENGTH_SHORT).show();
			}
			else{
				mEditScreen.setEditMode(true);
				mScrollView.setScrollingEnabled(false);
				Toast.makeText(getActivity(), "EditMode_ON", Toast.LENGTH_SHORT).show();
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
		Bitmap relativeBmp = Bitmap.createBitmap(mRelative.getWidth(), 
				mRelative.getHeight(), 
				Config.ARGB_8888);
		Canvas canvas = new Canvas(relativeBmp);
		mRelative.draw(canvas);
		Bitmap save_bmp = relativeBmp;
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			save_bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			Toast.makeText(getActivity(), "保存されました。", Toast.LENGTH_SHORT).show();
		} 
		catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "エラー", Toast.LENGTH_SHORT).show();
		}
	}

}
