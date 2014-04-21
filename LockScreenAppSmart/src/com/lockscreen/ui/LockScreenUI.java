package com.lockscreen.ui;

import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class LockScreenUI {

	/*
	 * View
	 */
	private ImageView chiave;
	private ImageView phone;
	private ImageView lucchetto;
	private ImageView lineDashed;
	private ImageView circle;
	private ImageView bgkUp;
	private ImageView bgkDown;
	
	private TextView clock;
	private TextView second;
	
	private ListView notificationList;
	
	private LayoutParams chiaveLayout;
	private LayoutParams circleLayout;
	private LayoutParams pageLayout;
	
	private RelativeLayout page;
	
	private Animation animBgkUp;
	private Animation animBgkDown;	
	
	/*
	 * Logici
	 */
	private int windowwidth;
	private int windowheight;
		
	private int lucchetto_x;
	private int lucchetto_y;
	private int[] chiavePos;
	
	private boolean start 			= false;
	private boolean lock 			= true;
	private boolean initEseguito 	= false;
	
	
	
	
	
	
	
	
	
	public ImageView getChiave() {
		return chiave;
	}
	public void setChiave(ImageView chiave) {
		this.chiave = chiave;
	}
	public ImageView getPhone() {
		return phone;
	}
	public void setPhone(ImageView phone) {
		this.phone = phone;
	}
	public ImageView getLucchetto() {
		return lucchetto;
	}
	public void setLucchetto(ImageView lucchetto) {
		this.lucchetto = lucchetto;
	}
	public ImageView getLineDashed() {
		return lineDashed;
	}
	public void setLineDashed(ImageView lineDashed) {
		this.lineDashed = lineDashed;
	}
	public ImageView getCircle() {
		return circle;
	}
	public void setCircle(ImageView circle) {
		this.circle = circle;
	}
	public ImageView getBgkUp() {
		return bgkUp;
	}
	public void setBgkUp(ImageView bgkUp) {
		this.bgkUp = bgkUp;
	}
	public ImageView getBgkDown() {
		return bgkDown;
	}
	public void setBgkDown(ImageView bgkDown) {
		this.bgkDown = bgkDown;
	}
	public TextView getClock() {
		return clock;
	}
	public void setClock(TextView clock) {
		this.clock = clock;
	}
	public TextView getSecond() {
		return second;
	}
	public void setSecond(TextView second) {
		this.second = second;
	}
	public ListView getNotificationList() {
		return notificationList;
	}
	public void setNotificationList(ListView notificationList) {
		this.notificationList = notificationList;
	}
	public LayoutParams getChiaveLayout() {
		return chiaveLayout;
	}
	public void setChiaveLayout(LayoutParams chiaveLayout) {
		this.chiaveLayout = chiaveLayout;
	}
	public LayoutParams getCircleLayout() {
		return circleLayout;
	}
	public void setCircleLayout(LayoutParams circleLayout) {
		this.circleLayout = circleLayout;
	}
	public LayoutParams getPageLayout() {
		return pageLayout;
	}
	public void setPageLayout(LayoutParams pageLayout) {
		this.pageLayout = pageLayout;
	}
	public RelativeLayout getPage() {
		return page;
	}
	public void setPage(RelativeLayout page) {
		this.page = page;
	}
	public Animation getAnimBgkUp() {
		return animBgkUp;
	}
	public void setAnimBgkUp(Animation animBgkUp) {
		this.animBgkUp = animBgkUp;
	}
	public Animation getAnimBgkDown() {
		return animBgkDown;
	}
	public void setAnimBgkDown(Animation animBgkDown) {
		this.animBgkDown = animBgkDown;
	}
	public int getWindowwidth() {
		return windowwidth;
	}
	public void setWindowwidth(int windowwidth) {
		this.windowwidth = windowwidth;
	}
	public int getWindowheight() {
		return windowheight;
	}
	public void setWindowheight(int windowheight) {
		this.windowheight = windowheight;
	}
	public int getLucchetto_x() {
		return lucchetto_x;
	}
	public void setLucchetto_x(int lucchetto_x) {
		this.lucchetto_x = lucchetto_x;
	}
	public int getLucchetto_y() {
		return lucchetto_y;
	}
	public void setLucchetto_y(int lucchetto_y) {
		this.lucchetto_y = lucchetto_y;
	}
	public int[] getChiavePos() {
		return chiavePos;
	}
	public void setChiavePos(int[] chiavePos) {
		this.chiavePos = chiavePos;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public boolean isLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	public boolean isInitEseguito() {
		return initEseguito;
	}
	public void setInitEseguito(boolean initEseguito) {
		this.initEseguito = initEseguito;
	}
	
}
