package com.lockscreen.task;

import com.lockscreen.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
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
        
    	ImageView page = (ImageView)x;
		
		//*********************************//
		//**Experiment*********************//
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inSampleSize = 3;				
		Bitmap bitmapOrg = BitmapFactory.decodeResource(res, R.drawable.background, options);		
		
//		page.setBackgroundDrawable(new BitmapDrawable(Bitmap.createBitmap(BitmapFactory.decodeResource(res, R.drawable.background, options), 0, 0, 300, 300)));
		
//		bitmapOrg = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth()/2, bitmapOrg.getHeight()/2);
		
		// make a Drawable from Bitmap to allow to set the BitMap 
		// to the ImageView, ImageButton or what ever
//		Drawable bmd = new BitmapDrawable(bitmapOrg);		
//		bitmapOrg = null;		
//		page.setBackgroundDrawable(bmd);
		
//		ImageView imageView = new ImageView(this);
//		// set the Drawable on the ImageView
//		page.setImageBitmap(bitmapOrg);
		
		
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

/*
 * class LoadImage extends AsyncTask<Object, Void, Bitmap>{

        private ImageView imv;
        private String path;

        public LoadImage(ImageView imv) {
             this.imv = imv;
             this.path = imv.getTag().toString();
        }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bitmap = null;
        File file = new File( 
                Environment.getExternalStorageDirectory().getAbsolutePath() + path);

        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (!imv.getTag().toString().equals(path)) {
               
               return;
        }

        if(result != null && imv != null){
            imv.setVisibility(View.VISIBLE);
            imv.setImageBitmap(result);
        }else{
            imv.setVisibility(View.GONE);
        }
    }

}
 */