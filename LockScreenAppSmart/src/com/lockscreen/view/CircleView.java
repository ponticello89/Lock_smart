//package com.lockscreen.view;
//
//import android.content.Context;
//import android.graphics.BitmapFactory;
//import android.graphics.BlurMaskFilter;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.Typeface;
//import android.util.AttributeSet;
//import android.view.View;
//
//
//public class CircleView extends View {
//	
//	public CircleView(Context context) {
//        super(context);        
//    }
//    
//    public CircleView(Context context, AttributeSet attrs) {
//        super(context, attrs);        
//    }
//    
//    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);        
//    }
//    
//    
//    protected void onDraw(Canvas canvas) {     
//        drawBattery(80.0f, canvas);        
//    }
//    
//    void drawBattery(float perc, Canvas canvas) {
//        path.reset();
//        int radius = (int)((float)unit * 0x3dcccccd);
//        strokewidth = (int)((float)unit * 0x3ca3d70a);
//        paint.setStrokeWidth((float)strokewidth);
//        paint.setStrokeCap(Paint.Cap.SQUARE);
//        blur.setStrokeWidth((float)strokewidth);
//        paint.setColor(-0x3d9a);
//        blur.setColor(-0x7500);
//        BlurMaskFilter mBlur = new BlurMaskFilter((float)strokewidth, -0x7500);
//        blur.setMaskFilter(mBlur);
//        battery.set((float)((canvas.getWidth() / 0x2) - radius), (float)((canvas.getHeight() / 0x2) - radius), (float)((canvas.getWidth() / 0x2) + radius), (float)((canvas.getHeight() / 0x2) + radius));
//        if(perc >= 99.0f) {
//            perc = 99.0f;
//            path.addCircle((float)(canvas.getWidth() / 0x2), (float)(canvas.getHeight() / 0x2), (float)radius, Path.Direction.CCW);
//        } else if(perc == 0x0) {
//            path.addCircle((float)(canvas.getWidth() / 0x2), (float)(canvas.getHeight() / 0x2), (float)radius, Path.Direction.CCW);
//            paint.setAlpha(0x40);
//            blur.setAlpha(0x40);
//        } else {
//            path.arcTo(battery, -90.0f, (-360.0f * (perc / 100.0f)));
//        }
//        canvas.drawPath(path, blur);
//        canvas.drawPath(path, paint);
//        Paint blurtext = new Paint();
//        blurtext.setTextScaleX(1.1f);
//        blurtext.setColor(-0x7500);
//        blurtext.setTextSize((float)tool.dpToPx(16.0f));
//        blurtext.setTextAlign(Paint.Align.CENTER);
//        BlurMaskFilter mBlur = new BlurMaskFilter((float)tool.dpToPx(2.0f), paint);
//        blurtext.setMaskFilter(mBlur);
//        blurtext.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "light.ttf"));
//        canvas.drawText("%", (float)(((canvas.getWidth() / 0x2) - radius) - tool.dpToPx(50.0f)), tool.dpToPx(50.0f), blurtext);
//        ((canvas.getHeight() / 0x2) + tool.dpToPx(5.0f)) = (float)((canvas.getHeight() / 0x2) + tool.dpToPx(5.0f));
//        Paint text = new Paint();
//        text.setTextScaleX(1.1f);
//        text.setColor(-0x3d9a);
//        text.setTextSize((float)tool.dpToPx(16.0f));
//        text.setTextAlign(Paint.Align.CENTER);
//        text.setDither(true);
//        text.setAntiAlias(true);
//        text.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "light.ttf"));
//        canvas.drawText("%", (float)(((canvas.getWidth() / 0x2) - radius) - tool.dpToPx(50.0f)), tool.dpToPx(50.0f), text);
//        ((canvas.getHeight() / 0x2) + tool.dpToPx(5.0f)) = (float)((canvas.getHeight() / 0x2) + tool.dpToPx(5.0f));
//        canvas.drawBitmap(bbattery, 0x0, localRectF1, (float)(((canvas.getWidth() / 0x2) - radius) - tool.dpToPx(82.0f)));
//        Paint localPaint2 = new Paint();
//    }
//}