package com.surat.surate_app.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

//https://stackoverflow.com/questions/28042140/creating-a-drawable-zoomable-image-view-in-android
public class DrawableImageView extends ImageView implements View.OnTouchListener
{
    // These matrices will be used to move and zoom image
    Matrix mMatrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    static final int DRAW = 3;

    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    float lastScale = 0f;
    String savedItemClicked;
    int count;
    //====================================================
    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;

    Canvas canvas;
    Paint paint;
    Matrix matrix;
    boolean setDraw,setDrag, statusDraw;

    public void setDragImage(boolean status)
    {
        setDrag = status;
    }

    public void setDrawImage(boolean status)
    {
        setDraw = status;
    }

    public DrawableImageView(Context context)
    {
        super(context);
        setOnTouchListener(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(null);
        paint.setAlpha(0xff);
    }

    public DrawableImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOnTouchListener(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(null);
        paint.setAlpha(0xff);
    }

    public DrawableImageView(Context context, AttributeSet attrs,
                             int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(null);
        paint.setAlpha(0xff);
    }

    public void sizePaint(int size)
    {
        paint.setStrokeWidth(size);
    }

    public void setColorPaint(int warna)
    {
        paint.setXfermode(null);
        paint.setColor(warna);
    }

    public void setNewImage(Bitmap alteredBitmap, Bitmap bmp)
    {
        canvas = new Canvas(alteredBitmap );

        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
//        matrix = new Matrix();
        canvas.drawBitmap(bmp, mMatrix, paint);

        setImageBitmap(alteredBitmap);
    }

    public void setClearImage(int width,int height)
    {

        Bitmap mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap );
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
//        matrix = new Matrix();
        canvas.drawBitmap(mBitmap, mMatrix, paint);

        setImageBitmap(mBitmap);
    }

    public void startEraser(int size)
    {
        paint.setAlpha(0);
        paint.setStrokeWidth(size);
        paint.setColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.STROKE);
        paint.setMaskFilter(null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAntiAlias(true);
    }


    public void eraser(boolean sts)
    {
        statusDraw = sts;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();
        DrawableImageView view = (DrawableImageView) v;
        dumpEvent(event);
        count = 0;

        switch (action & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                if(setDraw)
                {
                    downx = getPointerCoords(event)[0];//event.getX();
                    downy = getPointerCoords(event)[1];//event.getY();
                    count++;
                    mode = DRAW;
                    setDrag = false;
                }
                else if(setDrag)
                {
                    savedMatrix.set(mMatrix);
                    start.set(event.getX(), event.getY());
                    Log.d(TAG, "mode=DRAG");
                    mode = DRAG;
                    setDraw = false;
                }
                else
                {
                    savedMatrix.set(mMatrix);
                    start.set(event.getX(), event.getY());
                    Log.d(TAG, "mode=DRAG");
                    mode = DRAG;
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
//                Toast.makeText(getContext(),String.valueOf(oldDist),Toast.LENGTH_LONG).show();
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist >= 10f) {
                    savedMatrix.set(mMatrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    // ...
                    mMatrix.set(savedMatrix);
                    mMatrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 10f) {
                        mMatrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        mMatrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                else if(mode == DRAW)
                {

                    upx = getPointerCoords(event)[0];//event.getX();
                    upy = getPointerCoords(event)[1];//event.getY();
                    canvas.drawLine(downx, downy, upx, upy, paint);
                    invalidate();
                    downx = upx;
                    downy = upy;
                }
                break;
            case MotionEvent.ACTION_UP:
//                upx = getPointerCoords(event)[0];//event.getX();
//                upy = getPointerCoords(event)[1];//event.getY();
//                canvas.drawLine(downx, downy, upx, upy, paint);
                mode = NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        view.setImageMatrix(mMatrix);
        return true;
    }

    final float[] getPointerCoords(MotionEvent e)
    {
        final int index = e.getActionIndex();
        final float[] coords = new float[] { e.getX(index), e.getY(index) };
        Matrix matrix = new Matrix();
        getImageMatrix().invert(matrix);
        matrix.postTranslate(getScrollX(), getScrollY());
        matrix.mapPoints(coords);
        return coords;
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        Log.d(TAG, sb.toString());
    }

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

}