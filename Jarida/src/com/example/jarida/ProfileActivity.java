package com.example.jarida;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.slidinglayer.SlidingLayer;


public class ProfileActivity extends SherlockFragmentActivity {
	
	private SlidingLayer mSlidingLayer;
    private ListView swipeList;

    private String mStickContainerToRightLeftOrMiddle;
    private boolean mShowShadow;
    private boolean mShowOffset;
	
    private static final String[] sidemenuitems = { "MyShelf", "Favorites", "Subscriptions","Feedback", "Settings", "Help" };

	private static String urls[] = { 
			"http://kikosoftwareltd.com/jaridaimages/africanwoman.jpg",
			"http://kikosoftwareltd.com/jaridaimages/animal.jpg",
			"http://kikosoftwareltd.com/jaridaimages/bdaily1.JPG",
			"http://kikosoftwareltd.com/jaridaimages/zuqkainside.png"

	};

    private static String EXTRA_TITLE = "extra_title";

    private FragmentTabPager mFragmentTabPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myshelf);
       
        getPrefs();
        bindViews();
        initState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.actionbarmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
  
    @Override 
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_first:
                //Toast.makeText(this, "First Action Item", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(HomeActivity.this, PayPalActivity.class));
                //startActivity(new Intent(HomeActivity.this, ChoosePDFActivity.class));
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
				String selection = sidemenuitems[position];

				/*if(selection == "MyShelf"){
					
					startActivity(new Intent(getApplicationContext(), MyShelfActivity.class));
					
				}else if (selection == "Favorites" ){
					
					startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
					
				}else if (selection == "Subscriptions" ){
					
					startActivity(new Intent(getApplicationContext(), SubscriptionsActivity.class));
					
				}else if(selection == "Feedback" ){
					
					startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
					
				}else if(selection == "Settings" ){
					
					startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
					
				}else if(selection == "Help" ){
					
					startActivity(new Intent(getApplicationContext(), HelpActivity.class));
					
				}*/
				
			}
        	
        });
        swipeList.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView == null) {
					convertView = getLayoutInflater().inflate(R.layout.listitem_menu, null);
				}
				((TextView) convertView).setText(getItem(position));
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return sidemenuitems[position].hashCode();
			}

			@Override
			public String getItem(int position) {
				return sidemenuitems[position];
			}

			@Override
			public int getCount() {
				return sidemenuitems.length;
			}
		});
   
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
        // Sticks container to right or left
        LayoutParams rlp = (LayoutParams) mSlidingLayer.getLayoutParams();
        int textResource;
        Drawable d;

        if (mStickContainerToRightLeftOrMiddle.equals("right")) {
            textResource = R.string.swipe_right_label;
            d = getResources().getDrawable(R.drawable.container_rocket_right);

            rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else if (mStickContainerToRightLeftOrMiddle.equals("left")) {
            textResource = R.string.swipe_left_label;
            //d = getResources().getDrawable(R.drawable.container_rocket_left);

            rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (mStickContainerToRightLeftOrMiddle.equals("top")) {
            textResource = R.string.swipe_up_label;
            d = getResources().getDrawable(R.drawable.container_rocket);

            mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_TOP);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            rlp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            rlp.height = getResources().getDimensionPixelSize(R.dimen.layer_width);
        } else if (mStickContainerToRightLeftOrMiddle.equals("bottom")) {
            textResource = R.string.swipe_down_label;
            d = getResources().getDrawable(R.drawable.container_rocket);

            mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            rlp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            rlp.height = getResources().getDimensionPixelSize(R.dimen.layer_width);
        } else {
            textResource = R.string.swipe_label;
            d = getResources().getDrawable(R.drawable.container_rocket);

            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
        }

        //d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //swipeText.setCompoundDrawables(null, d, null, null);
        //swipeText.setText(getResources().getString(textResource));
        mSlidingLayer.setLayoutParams(rlp);

        // Sets the shadow of the container
        if (mShowShadow) {
            mSlidingLayer.setShadowWidthRes(R.dimen.shadow_width);
            mSlidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        } else {
            mSlidingLayer.setShadowWidth(20);
            mSlidingLayer.setShadowDrawable(null);
        }
        if(mShowOffset) {
            mSlidingLayer.setOffsetWidth(getResources().getDimensionPixelOffset(R.dimen.offset_width));
        } else {
            mSlidingLayer.setOffsetWidth(0);
        }
        
       
    }
    

   public static class SampleFragment extends SherlockFragment implements  OnRefreshListener {

        private PullToRefreshLayout mPullToRefreshLayout;
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.layout_fragment, container, false);

            // Now give the find the PullToRefreshLayout and set it up
            mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
            ActionBarPullToRefresh.from(getActivity())
                    .allChildrenArePullable()
                    .listener(this)
                    .setup(mPullToRefreshLayout);

            // Set title in Fragment for display purposes.
            StaggeredGridView gridView = (StaggeredGridView) view.findViewById(R.id.staggeredGridView1);
            int margin = getResources().getDimensionPixelSize(R.dimen.margin);
            
            Bundle b = getArguments();
            if (b != null) {
            	
            	gridView.setItemMargin(margin); // set the GridView margin
        		
        		gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 
        		
        		//StaggeredAdapter adapter = new StaggeredAdapter(getApplicationContext(), R.id.imageView1, urls);
        		/*StaggeredAdapter adapter = new StaggeredAdapter(view.getContext(), margin, urls);
        		
        		gridView.setAdapter(adapter);
        		adapter.notifyDataSetChanged();*/
            }

            return view;
        }

        
        @Override
        public void onRefreshStarted(View view) {
            //Simulate Refresh with 4 seconds sleep

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);

                    // Notify PullToRefreshLayout that the refresh has finished
                    mPullToRefreshLayout.setRefreshComplete();
                }
            }.execute();
        }
    }
}

