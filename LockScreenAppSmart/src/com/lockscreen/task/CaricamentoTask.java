package com.lockscreen.task;

import com.lockscreen.R;

import android.R.color;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CaricamentoTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView imv;
    private String path;
    private Resources res;

    public CaricamentoTask(ImageView imv, Resources res) {
    	Log.e("CaricamentoTask", "Start CaricamentoTask --->");
    	
    	this.imv = imv;
//    	this.imv.setBackgroundColor(Color.BLACK);
    	
    	this.res = res;
    	
    	Log.e("CaricamentoTask", "Finish CaricamentoTask ---|");
    }
     
    @Override    
	protected Bitmap doInBackground(String... arg0) {
	
    	Log.e("CaricamentoTask", "Start DoInBackground --->");
    	
    	Bitmap bitmap = null;
       	 	
    	BitmapFactory.Options optionBitMap = new BitmapFactory.Options();
    	optionBitMap.inSampleSize = 2;    
    	bitmap = BitmapFactory.decodeResource(res, R.drawable.background, optionBitMap);	    	
    	bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth()/2, bitmap.getHeight()/2);
    	
    	Log.e("CaricamentoTask", "Finish DoInBackground ---|");
        return bitmap;        
    }
    
    protected void onPostExecute(Bitmap bitMap) {
    	Log.e("CaricamentoTask", "Start OnPostExecute --->");
    	
    	BitmapDrawable bitMapDraweble = new BitmapDrawable(bitMap);
    	
    	imv.setBackgroundDrawable(bitMapDraweble);
    	
    	Log.e("CaricamentoTask", "Finish OnPostExecute ---|");
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}

	
}







//View x;
//Resources res;
//
//public CaricamentoTask(Resources res, View imageView) {
//	x = imageView;
//	this.res = res;		
//}
//
//
//@Override
//protected String doInBackground(String... params) {
//    
//	ImageView page = (ImageView)x;
//	
//	//*********************************//
//	//**Experiment*********************//
//	BitmapFactory.Options options=new BitmapFactory.Options();
//	options.inSampleSize = 3;				
//	Bitmap bitmapOrg = BitmapFactory.decodeResource(res, R.drawable.background, options);		
//	
////	page.setBackgroundDrawable(new BitmapDrawable(Bitmap.createBitmap(BitmapFactory.decodeResource(res, R.drawable.background, options), 0, 0, 300, 300)));
//	
////	bitmapOrg = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth()/2, bitmapOrg.getHeight()/2);
//	
//	// make a Drawable from Bitmap to allow to set the BitMap 
//	// to the ImageView, ImageButton or what ever
////	Drawable bmd = new BitmapDrawable(bitmapOrg);		
////	bitmapOrg = null;		
////	page.setBackgroundDrawable(bmd);
//	
////	ImageView imageView = new ImageView(this);
////	// set the Drawable on the ImageView
////	page.setImageBitmap(bitmapOrg);
//	
//	
//	//*********************************//
//	
//    return "Executed";
//}