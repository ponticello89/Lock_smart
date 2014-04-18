package com.lockscreen;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationList extends ListView{
	
	public static final int ACTION_INCREASE = 1;
	public static final int ACTION_RESET = 0;
	public static final int ACTION_SET_TO = 2;
	
	ArrayList<Notification> list = new ArrayList();
  
	BroadcastReceiver notifyreceiver = new BroadcastReceiver(){
	  
		public void onReceive(Context paramContext, Intent paramIntent){
    	
			int action = paramIntent.getIntExtra("action", -1);
			int count = paramIntent.getIntExtra("count", -1);
			String sender = paramIntent.getStringExtra("sender");
			Parcelable localParcelable = paramIntent.getParcelableExtra("pendingIntent");
			CharSequence localCharSequence = paramIntent.getCharSequenceExtra("message");

			Log.e("NotificationList", "Sender: " + sender);
			Log.e("NotificationList", "count: "  + count);
			Log.e("NotificationList", "action: " + action);
			Log.e("NotificationList", "localParcelable: "   + localParcelable);
			Log.e("NotificationList", "localParcelable: "   + localParcelable.toString());
			Log.e("NotificationList", "localCharSequence: " + localCharSequence);
      
			NotificationList.this.add(new NotificationList.Notification(sender, count, localCharSequence, localParcelable));
			
		}
	};
	
	public NotificationList(Context paramContext){
		
		super(paramContext);
		init();
	}

	public NotificationList(Context paramContext, AttributeSet paramAttributeSet) {
		
		super(paramContext, paramAttributeSet);
		init();
	}

	public NotificationList(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		
		super(paramContext, paramAttributeSet, paramInt);
		init();
	}

	public void add(Notification paramNotification) {
			
		for (Notification notification : this.list) {
			
			if(notification.getSender().equals(paramNotification.getSender())){
				this.list.remove(notification);
			}
			
		}	
		
		this.list.add(paramNotification);
	}
	

//
//  public String getLabel(String paramString)
//  {
//    PackageManager localPackageManager = getContext().getPackageManager();
//    try
//    {
//      ApplicationInfo localApplicationInfo2 = localPackageManager.getApplicationInfo(paramString, 0);
//      ApplicationInfo localApplicationInfo1 = localApplicationInfo2;
//      if (localApplicationInfo1 != null)
//      {
//    	  CharSequence localObject = localPackageManager.getApplicationLabel(localApplicationInfo1);
//        return (String)localObject;
//      }
//    }
//    catch (Exception localException)
//    {
//      while (true)
//      {
//        ApplicationInfo localApplicationInfo1 = null;        
//        Object localObject = "(unknown)";
//        continue;
//      }
//    }
//    return "";
//  }


	public void init(){
		  
		setDividerHeight(0);
		setAdapter(new NotificationAdapter());
		IntentFilter localIntentFilter = new IntentFilter("ginlemon.smartlauncher.notification");
		getContext().registerReceiver(this.notifyreceiver, localIntentFilter);
      
		Log.e("NotificationList", "init()");
		
		
//		NotificationList.this.add(new NotificationList.Notification("com.android.contacts", 2, null, null));
//		NotificationList.this.add(new NotificationList.Notification("com.mysms.android.sms", 2, null, null));		
		
//		((NotificationList.NotificationAdapter)NotificationList.this.getAdapter()).notifyDataSetChanged();
//		((NotificationList.NotificationAdapter)NotificationList.this.getAdapter()).notifyDataSetInvalidated();
		
//      setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
//      {
//        public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
//        {
//          try
//          {
//            PendingIntent localPendingIntent = (PendingIntent)((NotificationList.Notification)NotificationList.this.list.get(paramInt)).p;
//            Intent localIntent = new Intent();
//            localPendingIntent.send(NotificationList.this.getContext(), 0, localIntent);
//            ((LockScreen)NotificationList.this.getContext()).close();
//            NotificationList.this.list.remove(paramInt);
//            ((NotificationList.NotificationAdapter)NotificationList.this.getAdapter()).notifyDataSetChanged();
//            ((NotificationList.NotificationAdapter)NotificationList.this.getAdapter()).notifyDataSetInvalidated();
//            NotificationList.this.invalidateViews();
//            return true;
//          }
//          catch (Exception localException)
//          {
//            while (true)
//              System.out.println("Sending contentIntent failed: ");
//          }
//        }
//      });     
	}

	protected void onDetachedFromWindow(){
		
		super.onDetachedFromWindow();
		getContext().unregisterReceiver(this.notifyreceiver);
		this.notifyreceiver.clearAbortBroadcast();		
	}

//  public boolean onTouchEvent(MotionEvent paramMotionEvent)
//  {
//    boolean bool = super.onTouchEvent(paramMotionEvent);
//    paramMotionEvent.getAction();
//    return bool;
//  }

//  public void remove(String paramString)
//  {
//    for (int i = 0; ; i++)
//    {
//      if (i >= this.list.size())
//        return;
//      if (!((Notification)this.list.get(i)).getSender().equals(paramString))
//        continue;
//      this.list.remove(i);
//    }
//  }

  	class Notification {
  		
	    static final int TYPE_CALL = 0;
	    static final int TYPE_MAIL = 2;
	    static final int TYPE_OTHER = 9;
	    static final int TYPE_SMS = 1;
	    
	    int count;
	    CharSequence message;
	    CharSequence omessage;
	    Parcelable p;
	    String sender;

	    
	    	    
	    public Notification(String sender, int count, CharSequence localCharSequence, Parcelable localParcelable)	{
	    			      
		      this.sender = sender;
		      this.omessage = localCharSequence;
		      this.count = count;
		      
		      init();
	    }

//	    public int getIconDrawable() {
//	    	
//	    	switch (getType()) {
//	    	
//		      default:
//		      case 0:
//		      case 1:
//		      case 2:
//	    	}
//	    	
//	    	try {
//	    		
//		        Log.e("TAG", "Searching for not_" + this.sender.replace(".", "_"));
//		        
//		        int j = NotificationList.this.getResources().getIdentifier("not_" + this.sender.replace(".", "_"), "drawable", NotificationList.this.getContext().getPackageName());
//		        i = j;
//		        
//		        if (i > 0){
//		        	while (true){
//		        				        				        		
//		        		i = R.drawable.not_call;
//		        		continue;
//		            	i = R.drawable.not_message;
//		            	continue;
//		            	i = R.drawable.not_mail;
//		            	
//		            	return i;
//		        	}
//		        }
//	    	}catch (Exception localException){
//	    		
//	    		while (true){
//	    			int i = R.drawable.not_other;
//	    		}
//	    		
//	    	}
//	    }

	    public CharSequence getMessage(){
	    	
	    	if (this.message != null && !"".equals(this.message)){
	    		return message;
	    	}else{
	    		return "";
	    	}
	    	
	    }

	    public String getSender() {
	    	
	    	return this.sender;
	    }

	    public void init() {
	    	
	    	int i = 1;
	    	
	    	if (this.sender == null){
	    		this.sender = "";
	    	}
	    	
	    	String str = String.valueOf(this.count);	    	
	    	if (this.count < i){
	    		str = "";
	    	}
	    	
	    	if ((this.sender.equals("com.android.phone")) || (this.sender.equals("com.android.contacts"))) {
	    		this.message = (str + " missed call(s)");
//	    		return Notification.TYPE_CALL;
	    	}
      	
	    	if ((this.sender.equals("com.android.mms")) || (this.sender.equals("com.mysms.android.sms")) || (this.sender.equals("jp.naver.line.android"))){	    			
	    		this.message = (str + " unread message(s)");
//	    		return Notification.TYPE_SMS;	    			
	    	}
	    	
	    	if ((this.sender.equals("com.google.android.gm")) || (this.sender.equals("com.android.email"))) {	    		
	    		this.message = (str + " unread mail(s)");
//	    		return Notification.TYPE_MAIL;	    				    		
	    	}
	    	
    		if ((this.omessage == null) || (this.omessage.equals(""))){
//    			this.message = (NotificationList.this.getLabel(this.sender) + ": " + str + " new notification(s)");
//    			return Notification.TYPE_OTHER;
    		}
    		
//    		return Notification.TYPE_OTHER;
        	    
	    }

//	    public void increase() {
//	    	this.count = (1 + this.count);
//	    	getType();
//	    	Log.e("TAG", this.sender + " " + this.count + " " + this.message);
//	    }
	    
  	}

  	class NotificationAdapter extends BaseAdapter{
	  
  		LayoutInflater inflater = (LayoutInflater)NotificationList.this.getContext().getSystemService("layout_inflater");

  		NotificationAdapter(){}

  		public int getCount(){
  			
  			if (NotificationList.this.list.size() > 0){
  				NotificationList.this.setVisibility(0);
  				
  			}else{	
  				NotificationList.this.setVisibility(4);
  				  			
  			}
  			  			
  			return NotificationList.this.list.size();
  		}

  		public Object getItem(int paramInt){
  			
  			return null;
  		}

  		public long getItemId(int paramInt) {
  			
  			return 0L;
  		}

  		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
  			
  			View localView;
  			
  			if(paramView == null){
  			
  				Log.e("NotificationList", ((NotificationList.Notification)NotificationList.this.list.get(paramInt)).getMessage().toString());
  				
  				localView = this.inflater.inflate(R.layout.notification, null);
  				
//  		      ((ImageView)localView.findViewById(R.id.not_icon)).setImageResource(((NotificationList.Notification)NotificationList.this.list.get(paramInt)).getIconDrawable());
  	  			((TextView)localView.findViewById(R.id.not_message)).setText(((NotificationList.Notification)NotificationList.this.list.get(paramInt)).getMessage());
  				
  			}else{
  				localView = (View) paramView;
  			}
  			
  			return localView;
  		}

  	}
}