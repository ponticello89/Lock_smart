package com.lockscreen;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.lockscreen.task.CaricamentoTask;

public class LockScreenAppActivity extends Activity {

	/** Called when the activity is first created. */
	boolean inDragMode;
	int selectedImageViewX;
	int selectedImageViewY;
	int windowwidth;
	int windowheight;
	
	ImageView chiave, phone, lucchetto;
	ImageView lineDashed;
	ImageView circle;
	ImageView bgkUp, bgkDown;
	
	private TextView clock;
	private TextView second;
	
	ListView notificationList;
	
	// int phone_x,phone_y;
	int lucchetto_x, lucchetto_y;
	int[] chiavePos;
	
	boolean start 			= false;
	boolean lock 			= true;
	boolean initEseguito 	= false;

	private LayoutParams chiaveLayout, circleLayout, pageLayout;
	
	private RelativeLayout page;
	
	public void onCreate(Bundle savedInstanceState) {
		Log.e("LockScreenAppActivity", "Start OnCreate --->");
								
		super.onCreate(savedInstanceState);
				
		//Log memoria
//		new Utility().logHeap(this.getClass());
		
		windowwidth  = getWindowManager().getDefaultDisplay().getWidth();
		windowheight = getWindowManager().getDefaultDisplay().getHeight();
		
		/*
		 * SOUND DEFAULT LOCK SCREEN
		 */
		Settings.System.putInt(getContentResolver(), "lockscreen_sounds_enabled", 0);
		
		
		// Con questo setto che l'applicazione deve attivarsi:
		// -quando il telefono e bloccato
		// -e in modalita fullscreen
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(	WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
								WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
								WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		
		start = true;
		Thread t = new Thread(separateThread);		
        t.start();   
                
		/*
		 *  BOH GESTIONE VARIA
		 */	
		if(	getIntent() != null && 
			getIntent().hasExtra("kill") && 
			getIntent().getExtras().getInt("kill") == 1) {

			finish();
		}

		try {
			
			/*
			 *  BOH GESTIONE VARIA
			 */	
			StateListener phoneStateListener = new StateListener();
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
			
			/*
			 * LAYOUT
			 */
			setContentView(R.layout.main);
			
			
			Bitmap bitmap;
			
			page = (RelativeLayout) findViewById(R.id.page);
			
			ViewGroup.MarginLayoutParams marginLayoutParams;
							
			/*
			 * BACKGROUND
			 */
			Log.e("LockScreenAppActivity", "initUI Background --->");
			bgkUp = (ImageView) findViewById(R.id.bgkUp);
			marginLayoutParams = (ViewGroup.MarginLayoutParams) bgkUp.getLayoutParams();
			marginLayoutParams.setMargins(	0, 
	        								0, 
	        								0, 
	        								(windowheight  / 100) * 50);
			CaricamentoTask task = new CaricamentoTask(bgkUp, getResources(), CaricamentoTask.bkgUp);
		    task.execute();
		    
			bgkDown = (ImageView) findViewById(R.id.bgkDown);
			marginLayoutParams = (ViewGroup.MarginLayoutParams) bgkDown.getLayoutParams();
			marginLayoutParams.setMargins(	0, 
											(windowheight  / 100) * 50, 
	        								0, 
	        								0);
			task = new CaricamentoTask(bgkDown, getResources(), CaricamentoTask.bkgDown);
		    task.execute();
			
		    /*
			 * NOTIFICHE
			 */				
			notificationList 	= (ListView) findViewById(R.id.notificationList);		
			marginLayoutParams 	= (ViewGroup.MarginLayoutParams) notificationList.getLayoutParams();
			marginLayoutParams.setMargins(	20, 
	        								(windowheight  / 100) * 70, 
	        								20, 
	        								0);
			
			/*
			 * CLOCK
			 */
			Log.e("LockScreenAppActivity", "initUI Clock --->");
			clock  = (TextView) findViewById(R.id.clock);
			clock.setTextColor(Color.BLACK);
	    	clock.setTextSize(80);
	    	marginLayoutParams = (ViewGroup.MarginLayoutParams) clock.getLayoutParams();
			marginLayoutParams.setMargins(	20, 
	        								(windowheight  / 100) * 10, 
	        								20, 
	        								0);	
			
			second = (TextView) findViewById(R.id.second);
			second.setTextColor(Color.BLACK);					
			marginLayoutParams = (ViewGroup.MarginLayoutParams) second.getLayoutParams();
			marginLayoutParams.setMargins(	20, 
	        								(windowheight  / 100) * 10 + clock.getLineHeight(), 
	        								20, 
	        								0);
			
			/*
			 * Lucchetto
			 */
			Log.e("LockScreenAppActivity", "initUI Lucchetto --->");
			lucchetto = (ImageView) findViewById(R.id.lucchetto);
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homeupdated);
			bitmap = Bitmap.createScaledBitmap(	bitmap, 
												(windowheight  / 100) * 10, 
												(windowheight  / 100) * 10, 
												false);
			lucchetto.setImageBitmap(bitmap);						
			lucchetto.setVisibility(View.INVISIBLE);
									
			/*
			 * Circle Default
			 */
			Log.e("LockScreenAppActivity", "initUI Circle --->");
			circle = (ImageView) findViewById(R.id.circle);
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
			bitmap = Bitmap.createScaledBitmap(	bitmap, 
												(windowheight  / 100) * 16, 
												(windowheight  / 100) * 16, 
												false);					
			circle.setImageBitmap(bitmap);		
			circle.setVisibility(View.INVISIBLE);
			circleLayout = (LayoutParams) circle.getLayoutParams();
			circleLayout.leftMargin = (windowwidth  / 100) * 2;
			circleLayout.topMargin  = ((windowheight  / 100) * 50) - (bitmap.getHeight()/2);
			
			/*
			 * Chiave 
			 */
			Log.e("LockScreenAppActivity", "initUI chiave --->");		
			chiave = (ImageView) findViewById(R.id.chiave);	
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homeupdated);
			bitmap = Bitmap.createScaledBitmap(	bitmap, 
												(windowheight  / 100) * 10, 
												(windowheight  / 100) * 10, 
												false);
			chiave.setImageBitmap(bitmap);
			chiave.setOnTouchListener(myOnTouchListener);
			chiaveLayout = (LayoutParams) chiave.getLayoutParams();
			chiaveLayout.leftMargin = (windowwidth / 24) * 2;
			chiaveLayout.topMargin  = (windowheight / 32) * 16;
						
			lineDashed = (ImageView) findViewById(R.id.lineDashed);
			lineDashed.setVisibility(View.INVISIBLE);
			
			bitmap = null;
						
			
													
		} catch (Exception e) {}

		Log.e("LockScreenAppActivity", "Finish OnCreate ---|");
	}
	
	public Runnable separateThread = new Runnable() {
    	
		@Override
	    public void run() {
									
			while(start) {	    		
	    		updateUI();
	    		
	    		//1 Frame x 1 secondo
	    		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}	    		
	    	}
			
			//Resetto l'orario
			Message msg = new Message();	        
			handler.sendMessage(msg);
	    } 	 	    		
    };
	
    private void updateUI() {
	    
    	Utility utility = new Utility();
    	Calendar c = Calendar.getInstance();    	
    	Message msg = new Message();
        Bundle bundle = new Bundle();
                       			                       
        bundle.putString("ora",  	utility.dxFiller(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), 	"0", 2));
        bundle.putString("minuti", 	utility.dxFiller(String.valueOf(c.get(Calendar.MINUTE)), 		"0", 2));
        bundle.putString("secondi", utility.dxFiller(String.valueOf(c.get(Calendar.SECOND)), 		"0", 2));
        
        msg.setData(bundle);
        handler.sendMessage(msg);                         
    }
    
	private Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	        Bundle b = msg.getData();
	        
	        String ora 		= b.getString("ora");
	        String minuti 	= b.getString("minuti");
	        String secondi 	= b.getString("secondi");
	        
	        if(ora != null && minuti != null){
	        		        	
	        	SpannableString clockSS = new SpannableString(ora + ":" + minuti);
	        	clockSS.setSpan(new RelativeSizeSpan(1f), 0, 5, 0);
	        	clockSS.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, 0);
	        		        	  	        	
	        	Typeface font = Typeface.createFromAsset(getAssets(), "neue.ttf"); 	        	   
	        	
	        	clock.setText(clockSS);	        	
	        	clock.setTypeface(font);
	        	
	        	Log.e("LockScreenAppActivity", clock.getLineHeight()+"");
	        	
	        	SpannableString secondSS = new SpannableString(secondi);
	        	second.setText(secondSS);	        	
	        	second.setTypeface(font);
	        	
	        }else{	        	
	        	clock.setText("");
	        	second.setText("");
	        }
	    }
    };

    
    View.OnTouchListener myOnTouchListener = new View.OnTouchListener() {
		
    	@Override
		public boolean onTouch(View v, MotionEvent event) {

			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			chiaveLayout = (LayoutParams) v.getLayoutParams();
			 					
			switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					Log.d("LockScreenAppActivity", "MyOnTouchListener Action Down");
												
					vibrator.vibrate(40);
												
					chiave = (ImageView) findViewById(R.id.chiave);
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chiave);
					bitmap = Bitmap.createScaledBitmap(	bitmap, 
														(windowwidth   / 24)  * 3, 
														(windowheight  / 32)  * 2, 
														false);				
					chiave.setImageBitmap(bitmap);
					
					lucchetto.setVisibility(View.VISIBLE);
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

//					if (x_cord > windowwidth - (windowwidth / 24)) {
//						x_cord = windowwidth - (windowwidth / 24) * 2;
//					}
//					if (y_cord > windowheight - (windowheight / 32)) {
//						y_cord = windowheight - (windowheight / 32) * 2;
//					}

					circleLayout.leftMargin = x_cord - (circle.getWidth() /2);
					circleLayout.topMargin  = y_cord - (circle.getHeight()/2);								
					circle.setLayoutParams(circleLayout);
					
//					pageLayout.leftMargin = x_cord;
//					pageLayout.topMargin = y_cord;
//					page.setLayoutParams(pageLayout);
//					page.requestLayout();
					
					chiaveLayout.leftMargin = x_cord - (chiave.getWidth() /2);
					chiaveLayout.topMargin  = y_cord - (chiave.getHeight()/2);

					chiave.getLocationOnScreen(chiavePos);
					v.setLayoutParams(chiaveLayout);

					if ((	(x_cord - lucchetto_x) <= (windowwidth / 24) * 5 && (lucchetto_x - x_cord) <= (windowwidth / 24) * 4) && 
							((lucchetto_y - y_cord) <= (windowheight / 32) * 5)) {
						
//						System.out.println("home overlapps");
//						System.out.println("homeee" + home_x + "  " + (int) event.getRawX() + "  " + x_cord + " " + droidpos[0]);	
//						System.out.println("homeee" + home_y + "  " + (int) event.getRawY() + "  " + y_cord + " " + droidpos[1]);

						
						
						v.setVisibility(View.GONE);
						lucchetto.setVisibility(View.GONE);

						// startActivity(new Intent(Intent.ACTION_VIEW,
						// Uri.parse("content://contacts/people/")));
						
						unloack();
						
					} else {
						
//						System.out.println("homeee" + home_x + "  " + (int) event.getRawX() + "  " + x_cord + " " + droidpos[0]);	
//						System.out.println("homeee" + home_y + "  " + (int) event.getRawY() + "  " + y_cord + " " + droidpos[1]);	
//						System.out.println("home notttt overlapps");
						
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
						bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homeupdated);
						bitmap = Bitmap.createScaledBitmap(	bitmap, 
															(windowheight  / 100) * 10, 
															(windowheight  / 100) * 10, 
															false);							
						chiave.setImageBitmap(bitmap);
						
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
		
    };
      
	class StateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:
					break;
	
				case TelephonyManager.CALL_STATE_OFFHOOK:
					System.out.println("call Activity off hook");
					finish();
	
					break;
	
				case TelephonyManager.CALL_STATE_IDLE:
					break;
			}
		}
	};

	public void onSlideTouch(View view, MotionEvent event) {
		
		System.out.println("onSlideTouch");

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			break;

		case MotionEvent.ACTION_MOVE:
			int x_cord = (int) event.getRawX();
			int y_cord = (int) event.getRawY();

			if (x_cord > windowwidth) {
				x_cord = windowwidth;
			}
			if (y_cord > windowheight) {
				y_cord = windowheight;
			}

			chiaveLayout.leftMargin = x_cord - 25;
			chiaveLayout.topMargin = y_cord - 75;

			view.setLayoutParams(chiaveLayout);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onBackPressed() {		
		Log.d("LockScreenAppActivity", "OnBackPressed");
//		finish();
		return;
	}

	// only used in lockdown mode
	@Override
	protected void onPause() {
		Log.d("LockScreenAppActivity", "OnPause");
		super.onPause();
		start = false;		
	}

	@Override
	protected void onResume() {
		super.onResume();

		new Utility().logHeap(this.getClass());
		
		if(!start){
			start = true;		
			Thread t = new Thread(separateThread);		
	        t.start();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
//	public void onWindowFocusChanged(boolean hasFocus) {				
////        if((!exit) && (!hasFocus)) {
////            Log.e("TAG", "Broadacst ginlemon.smartlauncher.lock");
////            sendBroadcast(new Intent("ginlemon.smartlauncher.lock"));
////        }
////        startService(service)
////        super.onWindowFocusChanged(hasFocus);
//		return;
//    }
	
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		
//		System.out.println("onKeyDown");
//
//		if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) 	|| 
//			(keyCode == KeyEvent.KEYCODE_POWER) 		|| 
//			(keyCode == KeyEvent.KEYCODE_VOLUME_UP) 	|| 
//			(keyCode == KeyEvent.KEYCODE_CAMERA)) {
//			// this is where I can do my stuff
//			return true; // because I handled the event
//		}
//		if ((keyCode == KeyEvent.KEYCODE_HOME)) {
//
//			return true;
//		}
//
//		return false;
		
		return true;

	}

	public boolean dispatchKeyEvent(KeyEvent event) {

		System.out.println("dispatchKeyEvent");
		
		if(	event.getKeyCode() == KeyEvent.KEYCODE_POWER 		|| 
			event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN 	|| 
			event.getKeyCode() == KeyEvent.KEYCODE_POWER) {

			// Intent i = new Intent(this, NewActivity.class);
			// startActivity(i);
			return false;
		}
		if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {
			
			return false;
		}
		
		finish();
		return false;

	}
	
	public void unloack(){
		
		if(lock){
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(40);
			
			page.setMinimumHeight(100);
		}
		
		lock = false;
		finish();	 
	}

	public void onDestroy() {
		System.out.println("onDestroy");		
		start = false;
		
		System.exit(0);
		
//		finish();		
//		android.os.Process.killProcess(android.os.Process.myPid());
		
//		k1.reenableKeyguard();
		
		super.onDestroy();
	}

}