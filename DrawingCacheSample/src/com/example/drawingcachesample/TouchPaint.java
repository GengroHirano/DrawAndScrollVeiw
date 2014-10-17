package com.example.drawingcachesample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ClickableViewAccessibility") 
public class TouchPaint extends View {

	private boolean isEdit;
	private Paint mPaint;
	private Path mPath;

	public TouchPaint(Context context) {
		super(context);
		isEdit = false;

		mPath = new Path();

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(8);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
	}

	public void setEditMode(boolean mode){
		isEdit = mode;
	}

	public boolean isEditMode(){
		return isEdit;
	}


	Bitmap _bitmap;
	Canvas _canvas;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.v("onSizeChanged", ""+w +" : "+ h);
		if( _bitmap != null ){
			_bitmap.recycle();
			_bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
		}
		else{
			_bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
		}
		_canvas = new Canvas(_bitmap);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if( !isEdit ){
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.v("色", Integer.toHexString(mPaint.getColor()));
			mPath.reset();
			mPath.moveTo(event.getX(), event.getY());
			break;
		case MotionEvent.ACTION_MOVE:
			mPath.lineTo(event.getX(), event.getY());
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			mPath.lineTo(event.getX(), event.getY());
			_canvas.drawPath(mPath, mPaint);
			mPath.reset();
			invalidate();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(_bitmap, 0, 0, null);
		canvas.drawPath(mPath, mPaint);
	}

	/**
	 * ペンの色を変える
	 * @param color カラーコード
	 */
	public void changeColor(int color){
		mPaint.setColor(color);
	}
}
