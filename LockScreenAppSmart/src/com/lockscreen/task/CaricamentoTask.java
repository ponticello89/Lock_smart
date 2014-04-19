package com.lockscreen.task;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.lockscreen.R;

public class CaricamentoTask extends AsyncTask<String, Void, Bitmap> {

	private String cosaCaricare;
	
	public static String bkgUp 		= "backGroundUp";
	public static String bkgDown 	= "backGroundDown";
	

	private final WeakReference<ImageView> imageViewReference;
    private Resources res;
    
    public CaricamentoTask(ImageView imv, Resources res, String cosaCaricare) {
    	Log.e("CaricamentoTask", "Start CaricamentoTask --->");
    	
    	this.res 			= res;
    	this.cosaCaricare 	= cosaCaricare;
    	imageViewReference 	= new WeakReference<ImageView>(imv);
    	
    	Log.e("CaricamentoTask", "Finish CaricamentoTask ---|");
    }
     
    @Override    
	protected Bitmap doInBackground(String... arg0) {	
    	Log.e("CaricamentoTask", "Start DoInBackground --->");
    	
    	Bitmap bitmap = null;
       	 	
    	BitmapFactory.Options optionBitMap = new BitmapFactory.Options();
    	optionBitMap.inSampleSize = 3;    
    	
    	if(cosaCaricare.equals(bkgUp)){
    		bitmap = BitmapFactory.decodeResource(res, R.drawable.background, optionBitMap);	    	
    		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()/2);
    		
    	}else if(cosaCaricare.equals(bkgDown)){
    		bitmap = BitmapFactory.decodeResource(res, R.drawable.background, optionBitMap);	    	
    		bitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2, bitmap.getWidth(), bitmap.getHeight()/2);
    	}
    	
    	Log.e("CaricamentoTask", "Finish DoInBackground ---|");
        return bitmap;        
    }
    
    protected void onPostExecute(Bitmap bitMap) {
    	Log.e("CaricamentoTask", "Start OnPostExecute --->");
    	
    	if(imageViewReference != null && bitMap != null){
	    
    		BitmapDrawable bitMapDraweble = new BitmapDrawable(bitMap);
    		
	    	try{
	    		if(bitMap.isRecycled()){
	    			Log.e("CaricamentoTask", "Reciclata");
	    			bitMap.recycle();
	    		}
	    		bitMap = null; 
	    	}catch(Exception e){}
    	    	
    		final ImageView imageView = imageViewReference.get();
    		if(imageView != null){
    			Log.e("CaricamentoTask", "Inserita");
    			imageView.setBackgroundDrawable(bitMapDraweble);
    		}
    		
    		bitMapDraweble = null;
    	}
    	    
    	Log.e("CaricamentoTask", "Finish OnPostExecute ---|");
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
	
}