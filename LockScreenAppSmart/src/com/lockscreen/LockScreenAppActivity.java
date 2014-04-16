package com.lockscreen;

import java.util.Calendar;

import com.lockscreen.task.CaricamentoTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

public class LockScreenAppActivity extends Activity {

	/** Called when the activity is first created. */
	boolean inDragMode;
	int selectedImageViewX;
	int selectedImageViewY;
	int windowwidth;
	int windowheight;
	
	ImageView chiave, phone, lucchetto; 
	ImageView circle;
	private TextView clock;
	private TextView second;
	
	ListView notificationList;
	
	// int phone_x,phone_y;
	int lucchetto_x, lucchetto_y;
	int[] chiavePos;
	
	boolean start = false;
	boolean lock = false;

	private LayoutParams chiaveLayout, circleLayout, pageLayout;
	
	private RelativeLayout page;
	
	

//	@Override
//	public void onAttachedToWindow() {
//		System.out.println("onAttachedToWindow");
//		
//		// TODO Auto-generated method stub		
//		this.getWindow().setType(	WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG | 
//									WindowManager.LayoutParams.FLAG_FULLSCREEN |
//									WindowManager.LayoutParams.TYPE_KEYGUARD);
//		
////		System.out.println("onAttachedToWindow");
//			   		
//		super.onAttachedToWindow();
//	}

	
	
	
	
	
	
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
 
    public Runnable separateThread = new Runnable() {
    	
		@Override
	    public void run() {

			while(start) {
	    		
	    		System.out.println(start);
	    		
	    		updateUI();
	    		
	    		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
	    	}
			
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
	

	public void onCreate(Bundle savedInstanceState) {
		System.out.println("onCreate");
		
		lock = true;
		
		super.onCreate(savedInstanceState);
		
		Utility utility = new Utility();
		
		windowwidth  = getWindowManager().getDefaultDisplay().getWidth();
		windowheight = getWindowManager().getDefaultDisplay().getHeight();
		
		/*
		 * SOUND DEFAULT LOCK SCREEN
		 */
		Settings.System.putInt(getContentResolver(), "lockscreen_sounds_enabled", 0);
		
		
		// Con questo setto che l'applicazione deve attivarsi:
		// -quando il telefono e bloccato
		// -e in modalita fullscreenù
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(	WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
								WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
								WindowManager.LayoutParams.FLAG_FULLSCREEN);

//		lockScreenReeiver rec = new lockScreenReeiver();
		
		/*
		 * LAYOUT
		 */
		setContentView(R.layout.main);
		
		page = (RelativeLayout) findViewById(R.id.page);
				
		CaricamentoTask task = new CaricamentoTask(getResources(), page);
	    task.execute();
//		
//		//*********************************//
//		//**Experiment*********************//
//		BitmapFactory.Options options=new BitmapFactory.Options();
//		options.inSampleSize = 3;				
//		Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.background, options);		
//		
////		page.setBackgroundDrawable(new BitmapDrawable(Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background, options), 0, 0, 300, 300)));
//		
////		bitmapOrg = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth()/2, bitmapOrg.getHeight()/2);
//		
//		// make a Drawable from Bitmap to allow to set the BitMap 
//		// to the ImageView, ImageButton or what ever
////		Drawable bmd = new BitmapDrawable(bitmapOrg);		
////		bitmapOrg = null;		
////		page.setBackgroundDrawable(bmd);
//		
////		ImageView imageView = new ImageView(this);
////		// set the Drawable on the ImageView
////		imageView.setImageDrawable(bmd);
//		
//		
//		//*********************************//
				
		notificationList = (ListView) findViewById(R.id.notificationList);		
		ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) notificationList.getLayoutParams();
		marginLayoutParams.setMargins(	20, 
        								(windowheight  / 100) * 70, 
        								20, 
        								0);
		
		/*
		 * CLOCK
		 */
		clock  = (TextView) findViewById(R.id.clock);
		clock.setTextColor(Color.BLACK);
    	clock.setTextSize(80);
    	
		second = (TextView) findViewById(R.id.second);
		second.setTextColor(Color.BLACK);
		
		start = true;
		Thread t = new Thread(separateThread);		
        t.start();   
        
		marginLayoutParams = (ViewGroup.MarginLayoutParams) clock.getLayoutParams();
		marginLayoutParams.setMargins(	20, 
        								(windowheight  / 100) * 10, 
        								20, 
        								0);
		
		Log.e("LockScreenAppActivity", "...."+clock.getLineHeight()+"");
		
		marginLayoutParams = (ViewGroup.MarginLayoutParams) second.getLayoutParams();
		marginLayoutParams.setMargins(	20, 
        								(windowheight  / 100) * 10 + clock.getLineHeight(), 
        								20, 
        								0);
		
		     
                
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
			 * phone =(ImageView)findViewById(R.id.phone); MarginLayoutParams
			 * marginParams = new MarginLayoutParams(phone.getLayoutParams());
			 * marginParams.setMargins(0,windowheight/32,windowwidth/24,0);
			 * LinearLayout.LayoutParams layoutParams1 = new
			 * LinearLayout.LayoutParams(marginParams);
			 * phone.setLayoutParams(layoutParams1);
			 */
					
//			LinearLayout timelinear = (LinearLayout) findViewById(R.id.homelinearlayout);
			
			
			/*
			 * Home
			 */
			lucchetto = (ImageView) findViewById(R.id.lucchetto);
			Bitmap homeBMap = BitmapFactory.decodeResource(getResources(), R.drawable.homeupdated);
			homeBMap = Bitmap.createScaledBitmap(	homeBMap, 
													(windowheight  / 100) * 10, 
													(windowheight  / 100) * 10, 
													false);
			lucchetto.setImageBitmap(homeBMap);			
									
			/*
			 * Circle Default
			 */
			circle = (ImageView) findViewById(R.id.circle);
			Bitmap circleBMap = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
			circleBMap = Bitmap.createScaledBitmap(	circleBMap, 
													(windowheight  / 100) * 16, 
													(windowheight  / 100) * 16, 
													false);					
			circle.setImageBitmap(circleBMap);						
			circleLayout = (LayoutParams) circle.getLayoutParams();
			circleLayout.leftMargin = (windowwidth  / 100) * 2;
			circleLayout.topMargin  = (windowheight  / 100) * 42;
			
//			pageLayout = (LayoutParams) page.getLayoutParams();
			
			/*
			 * Chiave 
			 */
			chiave = (ImageView) findViewById(R.id.chiave);
			Bitmap droidBMap = BitmapFactory.decodeResource(getResources(), R.drawable.chiave);
			droidBMap = Bitmap.createScaledBitmap(	droidBMap, 
													(windowwidth   / 24)  * 3, 
													(windowheight  / 32)  * 2, 
													false);
			chiave.setImageBitmap(droidBMap);						
//			chiave.setBackgroundColor(Color.GREEN);
			
			chiaveLayout = (LayoutParams) chiave.getLayoutParams();
			chiaveLayout.leftMargin = (windowwidth / 24) * 2;
			chiaveLayout.topMargin  = (windowheight / 32) * 16;
			
			chiave.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {

					Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					chiaveLayout = (LayoutParams) v.getLayoutParams();
					 					
					switch (event.getAction()) {

						case MotionEvent.ACTION_DOWN:
														
							vibrator.vibrate(40);
														
							int[] hompos = new int[2];
							// int[] phonepos=new int[2];
							chiavePos     = new int[2];
							// phone.getLocationOnScreen(phonepos);
							lucchetto.getLocationOnScreen(hompos);
							lucchetto_x = hompos[0];
							lucchetto_y = hompos[1];
							// phone_x=phonepos[0];
							// phone_y=phonepos[1];
							
							break;
							
						case MotionEvent.ACTION_MOVE:
							
							int x_cord = (int) event.getRawX();
							int y_cord = (int) event.getRawY();
	
//							if (x_cord > windowwidth - (windowwidth / 24)) {
//								x_cord = windowwidth - (windowwidth / 24) * 2;
//							}
//							if (y_cord > windowheight - (windowheight / 32)) {
//								y_cord = windowheight - (windowheight / 32) * 2;
//							}
	
							circleLayout.leftMargin = x_cord - (circle.getWidth() /2);
							circleLayout.topMargin  = y_cord - (circle.getHeight()/2);								
							circle.setLayoutParams(circleLayout);
							
//							pageLayout.leftMargin = x_cord;
//							pageLayout.topMargin = y_cord;
//							page.setLayoutParams(pageLayout);
//							page.requestLayout();
							
							chiaveLayout.leftMargin = x_cord - (chiave.getWidth() /2);
							chiaveLayout.topMargin  = y_cord - (chiave.getHeight()/2);
	
							chiave.getLocationOnScreen(chiavePos);
							v.setLayoutParams(chiaveLayout);
	
							if ((	(x_cord - lucchetto_x) <= (windowwidth / 24) * 5 && (lucchetto_x - x_cord) <= (windowwidth / 24) * 4) && 
									((lucchetto_y - y_cord) <= (windowheight / 32) * 5)) {
								
//								System.out.println("home overlapps");
//								System.out.println("homeee" + home_x + "  " + (int) event.getRawX() + "  " + x_cord + " " + droidpos[0]);	
//								System.out.println("homeee" + home_y + "  " + (int) event.getRawY() + "  " + y_cord + " " + droidpos[1]);
	
								
								
								v.setVisibility(View.GONE);
								lucchetto.setVisibility(View.GONE);
	
								// startActivity(new Intent(Intent.ACTION_VIEW,
								// Uri.parse("content://contacts/people/")));
								
								unloack();
								
							} else {
								
//								System.out.println("homeee" + home_x + "  " + (int) event.getRawX() + "  " + x_cord + " " + droidpos[0]);	
//								System.out.println("homeee" + home_y + "  " + (int) event.getRawY() + "  " + y_cord + " " + droidpos[1]);	
//								System.out.println("home notttt overlapps");
								
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
	
							int x_cord1 = (int) event.getRawX();
							int y_cord2 = (int) event.getRawY();
	
							if (((x_cord1 - lucchetto_x) <= (windowwidth / 24) * 5 && (lucchetto_x - x_cord1) <= (windowwidth / 24) * 4)
									&& ((lucchetto_y - y_cord2) <= (windowheight / 32) * 5)) {
								
//								System.out.println("home overlapps");
//								System.out.println("homeee" + home_x + "  "
//										+ (int) event.getRawX() + "  " + x_cord1
//										+ " " + droidpos[0]);
//	
//								System.out.println("homeee" + home_y + "  "
//										+ (int) event.getRawY() + "  " + y_cord2
//										+ " " + droidpos[1]);
	
								// startActivity(new Intent(Intent.ACTION_VIEW,
								// Uri.parse("content://contacts/people/")));
								// finish();
							} else {

								circleLayout.leftMargin = (windowwidth  / 100) * 2;
								circleLayout.topMargin  = (windowheight  / 100) * 42;				
								circle.setLayoutParams(circleLayout);
								
								chiaveLayout.leftMargin = (windowwidth / 24) * 2;
								chiaveLayout.topMargin  = (windowheight / 32) * 16;
																
//								layout_alignParentLeft="true"
//							    layout_centerVertical="true"
								v.setLayoutParams(chiaveLayout);
	
							}

					}

					return true;
					
				}
				
			});

			/*
			 * Button close =(Button)findViewById(R.id.lockk);
			 * close.setOnClickListener(new View.OnClickListener() {
			 * 
			 * public void onClick(View v) { //k1.reenableKeyguard();
			 * //startActivity(new Intent(Intent.ACTION_VIEW,
			 * Uri.parse("content://contacts/people/")));
			 * 
			 * 
			 * finish(); } });
			 */

			// PowerManager pm =
			// (PowerManager)getSystemService(Context.POWER_SERVICE);

			// PowerManager.WakeLock w1
			// =pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.FULL_WAKE_LOCK,"MyApp");
			// w1.acquire();
			// w1.release();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

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

		// When the user pushes down on an ImageView
		/*
		 * if ( event.getAction() == MotionEvent.ACTION_DOWN ) { inDragMode =
		 * true; //Set a variable so we know we started draggin the imageView
		 * //Set the selected ImageView X and Y exact position
		 * selectedImageViewX =
		 * Math.abs((int)event.getRawX()-((ImageView)view).getLeft());
		 * selectedImageViewY =
		 * Math.abs((int)event.getRawY()-((ImageView)view).getTop()); //Bring
		 * the imageView in front ((ImageView)view).bringToFront(); }
		 * 
		 * //When the user let's the ImageView go (raises finger) if (
		 * event.getAction() == MotionEvent.ACTION_UP ) { inDragMode = false;
		 * //Reset the variable which let's us know we're not in drag mode
		 * anymore }
		 * 
		 * //When the user keeps his finger on te screen and drags it (slides
		 * it) if ( event.getAction() == MotionEvent.ACTION_MOVE ) { //If we've
		 * started draggin the imageView if ( inDragMode ) { //Get the imageView
		 * object // ImageView slide = (ImageView)findViewById(R.id.slide);
		 * //Get a parameters object (THIS EXAMPLE IS FOR A RELATIVE LAYOUT)
		 * RelativeLayout.LayoutParams params =
		 * (RelativeLayout.LayoutParams)view.getLayoutParams(); //Change the
		 * position of the imageview accordingly
		 * params.setMargins((int)event.getRawX()-selectedImageViewX,
		 * (int)event.getRawY()-selectedImageViewY, 0, 0); //Set the new params
		 * view.setLayoutParams(params);
		 * 
		 * //If we hit a limit with our imageView position
		 * /*if((int)event.getRawX()) { //Open another activity Intent it = new
		 * Intent(Slide.this,NextActivity.class); startActivity(it); } } }
		 */

	}

	@Override
	public void onBackPressed() {
		System.out.println("onBackPressed");		
		return;
	}

	// only used in lockdown mode
	@Override
	protected void onPause() {
		super.onPause();
		start = false;		
	}

	@Override
	protected void onResume() {
		super.onResume();

		start = true;		
		Thread t = new Thread(separateThread);		
        t.start();   		
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
		
//		k1.reenableKeyguard();
		
		super.onDestroy();
	}

}