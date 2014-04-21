package com.lockscreen.listener;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.lockscreen.LockScreenAppActivity;

public class AnimationEndListener implements AnimationListener{

	List<Animation> animActive = new ArrayList<Animation>();
	Context context;	
	
	public AnimationEndListener(Context context){
		this.context = context;		
	}
	
	@Override
	public void onAnimationEnd(Animation animation) {
		animActive.remove(animation);
		
		if(animActive.isEmpty()){		
			Log.e("AnimationEndListener", "Finish");
			((LockScreenAppActivity)context).finish();
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {}

	@Override
	public void onAnimationStart(Animation animation) {
		animActive.add(animation);
	}
}
