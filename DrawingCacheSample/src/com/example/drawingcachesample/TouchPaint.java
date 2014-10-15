package com.example.drawingcachesample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ClickableViewAccessibility") public class TouchPaint extends View {

	private boolean isEdit;

	private float oldX = 0f, oldY = 0f;
	private Paint paint;

	public TouchPaint(Context context) {
		super(context);
		isEdit = false;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.LTGRAY);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(8);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}

	public void setEditMode(boolean mode){
		isEdit = mode;
	}

	public boolean isEditMode(){
		return isEdit;
	}


	Bitmap _bitmap;
	Canvas _canvas;

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.v("size", ""+w +" : "+ h);
		_bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		_canvas = new Canvas(_bitmap);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.v("Touch", ""+isEdit);
		if( !isEdit ){
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldX = event.getX();
			oldY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			_canvas.drawLine(oldX, oldY, event.getX(), event.getY(), paint);
			oldX = event.getX();
			oldY = event.getY();
			invalidate();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(_bitmap, 0, 0, null);
	}
}
