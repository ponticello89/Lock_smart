package com.lockscreen.listener;

import com.lockscreen.LockScreenAppActivity;
import com.lockscreen.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class TouchChiaveListener extends LockScreenAppActivity implements OnTouchListener{

	Context context;
	
	public TouchChiaveListener(Context context){
		this.context= context; 
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		LockScreenAppActivity ll = (LockScreenAppActivity)context;
		
//		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		chiaveLayout = (LayoutParams) v.getLayoutParams();
		 					
		
		
		switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				Log.d("LockScreenAppActivity", "MyOnTouchListener Action Down");
											
//				vibrator.vibrate(40);
//				unloack();									
				
				
				
//				
//				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chiave);
//				bitmap = Bitmap.createScaledBitmap(	bitmap, 
//													(((LockScreenAppActivity)context).getWindowwidth()   / 24)  * 3, 
//													(windowheight  / 32)  * 2, 
//													false);				
//				getChiave().setImageBitmap(bitmap);
				ImageView a = getLucchetto();
				a.setVisibility(View.VISIBLE);
				ll.setLucchetto(a);
				circle.setVisibility(View.VISIBLE);
				lineDashed.setVisibility(View.VISIBLE);
				
				int[] hompos 	= new int[2];
				chiavePos 		= new int[2];
				
				lucchetto.getLocationOnScreen(hompos);
				lucchetto_x = hompos[0];
				lucchetto_y = hompos[1];
				
				break;
				
			case MotionEvent.ACTION_MOVE:
				Log.d("LockScreenAppActivity", "MyOnTouchListener Action Move");
				
				int x_cord = (int) event.getRawX();
				int y_cord = (int) event.getRawY();

//				if (x_cord > windowwidth - (windowwidth / 24)) {
//					x_cord = windowwidth - (windowwidth / 24) * 2;
//				}
//				if (y_cord > windowheight - (windowheight / 32)) {
//					y_cord = windowheight - (windowheight / 32) * 2;
//				}

				circleLayout.leftMargin = x_cord - (circle.getWidth() /2);
				circleLayout.topMargin  = y_cord - (circle.getHeight()/2);								
				circle.setLayoutParams(circleLayout);
				
//				pageLayout.leftMargin = x_cord;
//				pageLayout.topMargin = y_cord;
//				page.setLayoutParams(pageLayout);
//				page.requestLayout();
				
				chiaveLayout.leftMargin = x_cord - (chiave.getWidth() /2);
				chiaveLayout.topMargin  = y_cord - (chiave.getHeight()/2);

				chiave.getLocationOnScreen(chiavePos);
				v.setLayoutParams(chiaveLayout);

				if ((	(x_cord - lucchetto_x) <= (windowwidth / 24) * 5 && (lucchetto_x - x_cord) <= (windowwidth / 24) * 4) && 
						((lucchetto_y - y_cord) <= (windowheight / 32) * 5)) {
					
//					System.out.println("home overlapps");
//					System.out.println("homeee" + home_x + "  " + (int) event.getRawX() + "  " + x_cord + " " + droidpos[0]);	
//					System.out.println("homeee" + home_y + "  " + (int) event.getRawY() + "  " + y_cord + " " + droidpos[1]);

					v.setVisibility(View.GONE);
					lucchetto.setVisibility(View.GONE);
					
					

					// startActivity(new Intent(Intent.ACTION_VIEW,
					// Uri.parse("content://contacts/people/")));
					
					unloack();
					
				} else {
					
//					System.out.println("homeee" + home_x + "  " + (int) event.getRawX() + "  " + x_cord + " " + droidpos[0]);	
//					System.out.println("homeee" + home_y + "  " + (int) event.getRawY() + "  " + y_cord + " " + droidpos[1]);	
//					System.out.println("home notttt overlapps");
					
				}
				/*
				 * if(((x_cord-phone_x)>=128 && (x_cord-phone_x)<=171
				 * )&&((phone_y-y_cord)<=10)) {
				 * System.out.println("phone overlapps"); finish(); }
				 * else{
				 * System.out.println(phone_x+"  "+(int)event.getRawX
				 * ()+"  "+x_cord+" "+droidpos[0]);
				 * 
				 * System.out.println(phone_y+"  "+(int)event.getRawY()+"  "
				 * +y_cord+" "+droidpos[1]);
				 * 
				 * 
				 * System.out.println("phone not overlapps" +
				 * " overlapps"); }
				 */
				// v.invalidate();

				break;
				
			case MotionEvent.ACTION_UP:
				Log.d("LockScreenAppActivity", "MyOnTouchListener Action Up");

				int x_cord1 = (int) event.getRawX();
				int y_cord2 = (int) event.getRawY();

				if (	((x_cord1 - lucchetto_x) <= (windowwidth / 24) * 5 && (lucchetto_x - x_cord1) <= (windowwidth / 24) * 4)
						&& ((lucchetto_y - y_cord2) <= (windowheight / 32) * 5)) {
					


					// startActivity(new Intent(Intent.ACTION_VIEW,
					// Uri.parse("content://contacts/people/")));
					// finish();
					
				} else {

					lucchetto.setVisibility(View.INVISIBLE);						
					circle.setVisibility(View.INVISIBLE);
					lineDashed.setVisibility(View.INVISIBLE);
					
					chiave = (ImageView) findViewById(R.id.chiave);	
//					bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homeupdated);
//					bitmap = Bitmap.createScaledBitmap(	bitmap, 
//														(windowheight  / 100) * 10, 
//														(windowheight  / 100) * 10, 
//														false);							
//					chiave.setImageBitmap(bitmap);
					
					circleLayout.leftMargin = (windowwidth  / 100) * 2;
					circleLayout.topMargin  = (windowheight  / 100) * 42;				
					circle.setLayoutParams(circleLayout);
					
					chiaveLayout.leftMargin = (windowwidth / 24) * 2;
					chiaveLayout.topMargin  = (windowheight / 32) * 16;
					v.setLayoutParams(chiaveLayout);

				}

		}

		return true;
		
	}

		
	

}
