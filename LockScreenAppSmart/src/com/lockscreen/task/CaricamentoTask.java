package com.lockscreen.task;

import com.lockscreen.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;

public class CaricamentoTask extends AsyncTask<String, Void, String> {

	View x;
	Resources res;
	
	public CaricamentoTask(Resources res, View imageView) {
		x = imageView;
		this.res = res;		
    }
	
	
    @Override
    protected String doInBackground(String... params) {
        
    	RelativeLayout page = (RelativeLayout)x;
		
		//*********************************//
		//**Experiment*********************//
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inSampleSize = 3;				
		Bitmap bitmapOrg = BitmapFactory.decodeResource(res, R.drawable.background, options);		
		
		page.setBackgroundDrawable(new BitmapDrawable(Bitmap.createBitmap(BitmapFactory.decodeResource(res, R.drawable.background, options), 0, 0, 300, 300)));
		
//		bitmapOrg = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth()/2, bitmapOrg.getHeight()/2);
		
		// make a Drawable from Bitmap to allow to set the BitMap 
		// to the ImageView, ImageButton or what ever
//		Drawable bmd = new BitmapDrawable(bitmapOrg);		
//		bitmapOrg = null;		
//		page.setBackgroundDrawable(bmd);
		
//		ImageView imageView = new ImageView(this);
//		// set the Drawable on the ImageView
//		imageView.setImageDrawable(bmd);
		
		
		//*********************************//
    	
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {}

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}