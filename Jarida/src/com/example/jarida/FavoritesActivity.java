package com.example.jarida;


import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.robototextview.widget.RobotoTextView;
import com.google.analytics.tracking.android.EasyTracker;
import com.slidinglayer.SlidingLayer;



public class FavoritesActivity extends SherlockFragmentActivity implements MenuItems{
	
	private SlidingLayer mSlidingLayer;
    private ListView swipeList;

    private String mStickContainerToRightLeftOrMiddle;
    private boolean mShowShadow;
    private boolean mShowOffset;
    
	private UserFunctions userfunc;
	private ApplicationContextProvider ctxProvider;
	private static Context ctx;
	private boolean loggedin;
	
    private ArrayList<String> sidemenuitems; 
    private static String EXTRA_TITLE = "extra_title";

    private FragmentTabPager mFragmentTabPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);
        

        getPrefs();
        bindViews();
        initState();
        
        sidemenuitems = MenuItemsUtils.getList();
	    ctx = ctxProvider.getContext(); 
	    userfunc = new UserFunctions();
	   
	    loggedin = false;
        
	    ViewPager vp = (ViewPager) findViewById(R.id.ptr_viewpager);
        mFragmentTabPager = new FragmentTabPager(this, vp);
        ActionBar ab = getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        /*Typeface tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/everytime.ttf");
        view.setTypeface(tf);
        view.setTextSize(15);*/
        
        
        Bundle b = new Bundle();
        b.putString(EXTRA_TITLE, "All Categories");
        mFragmentTabPager.addTab(ab.newTab().setText("All Categories"), AllCategoriesFragment.class, b);
/*
        b = new Bundle();
        b.putString(EXTRA_TITLE, "Newspapers");
        mFragmentTabPager.addTab(ab.newTab().setText("Newspapers"), NewspapersFragment.class, b);

        b = new Bundle();
        b.putString(EXTRA_TITLE, "Magazines");
        mFragmentTabPager.addTab(ab.newTab().setText("Magazines"), MagazinesFragment.class, b);
        
        b = new Bundle();
        b.putString(EXTRA_TITLE, "Top Paid");
        mFragmentTabPager.addTab(ab.newTab().setText("Top Paid"), TopPaidFragment.class, b);

        b = new Bundle();
        b.putString(EXTRA_TITLE, "Top Free");
        mFragmentTabPager.addTab(ab.newTab().setText("Top Free"), TopFreeFragment.class, b);
        
        b = new Bundle();
        b.putString(EXTRA_TITLE, "Trending");
        mFragmentTabPager.addTab(ab.newTab().setText("Trending"), TrendingFragment.class, b);*/
  
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.actionbarmenu, menu);
        
        MenuItem loginMenu = menu.findItem(R.id.action_first);

        
        if(loggedin ==true){
    		loginMenu.setTitle("Erick");
    	}else{
    		loginMenu.setTitle("Login");
    	}
        
        return super.onCreateOptionsMenu(menu);
    }
  
    @Override 
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_first:
            	if (loggedin == true) {
    				startActivity(new Intent(getApplicationContext(),
    						ProfileActivity.class));
    			} else {
    				startActivity(new Intent(getApplicationContext(),
    						LoginActivity.class));
    			}
				return true;
            case R.id.action_second:
            	
            	if (!mSlidingLayer.isOpened()) {
                    mSlidingLayer.openLayer(true);
                }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
@Override
    public void onStart() {
      super.onStart();
       // The rest of your onStart() code.
      EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
      super.onStop();
      // The rest of your onStop() code.
      EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }

    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:
            if (mSlidingLayer.isOpened()) {
                mSlidingLayer.closeLayer(true);
                return true;
            }

        default:
            return super.onKeyDown(keyCode, event);
        }
    }
    
    /**
     * View binding
     */
    private void bindViews() {
    	mSlidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer1);
        swipeList = (ListView)findViewById(R.id.listViewmenu);
        swipeList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String selection = sidemenuitems.get(position);

				if(selection == SHOPPING_CART){
					
					startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
					
				}else if (selection == FAVORITES ){
					
					startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
					
				}else if (selection == SUBSCRIPTIONS){
					
					startActivity(new Intent(getApplicationContext(), SubscriptionsActivity.class));
					
				}else if(selection == FEEDBACK){
					
					startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
					
				}else if(selection == SETTINGS){
					
					startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
					
				}
				
			}
        	
        });

        SliderArrayAdapter adapter = new SliderArrayAdapter(getApplicationContext(), R.layout.menulistview_item); 
        swipeList.setAdapter(adapter);
   
    }

    /**
     * Get current value for preferences
     */
    private void getPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mStickContainerToRightLeftOrMiddle = prefs.getString("layer_location", "left");
        mShowShadow = prefs.getBoolean("layer_has_shadow", true);
        mShowOffset = prefs.getBoolean("layer_has_offset", false);
    }

    /**
     * Initializes the origin state of the layer
     */
    private void initState() {
    	mStickContainerToRightLeftOrMiddle.equals("left");
        LayoutParams rlp = (LayoutParams) mSlidingLayer.getLayoutParams();
        int textResource;
        Drawable d;

        if (mStickContainerToRightLeftOrMiddle.equals("left")) {
            textResource = R.string.swipe_left_label;
            rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            textResource = R.string.swipe_label;
            d = getResources().getDrawable(R.drawable.container_rocket);

            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
        }

        mSlidingLayer.setLayoutParams(rlp);
        if (mShowShadow) {
            mSlidingLayer.setShadowWidthRes(R.dimen.shadow_width);
            mSlidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        } else {
            mSlidingLayer.setShadowWidth(2000);
            mSlidingLayer.setShadowDrawable(null);
        }
        if(mShowOffset) {
            mSlidingLayer.setOffsetWidth(getResources().getDimensionPixelOffset(R.dimen.offset_width));
        } else {
            mSlidingLayer.setOffsetWidth(0);
        }
    }
    
    private class ViewHolder {
   	 ImageView menuIcon;
   	 RobotoTextView menuLabel;
  }
    

}

