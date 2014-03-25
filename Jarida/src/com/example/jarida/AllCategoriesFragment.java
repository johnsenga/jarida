package com.example.jarida;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.ronnyfriedland.shoppinglist.entity.Entry;

public class AllCategoriesFragment extends SherlockFragment implements  AbsListView.OnScrollListener, AbsListView.OnItemClickListener {
	   
	
	private static final String TAG = "AllCategoriesFragment";
    public static final String SAVED_DATA_KEY = "SAVED_DATA";

    private EtsyStaggeredGridView mGridView;
    private boolean mHasRequestedMore;
    
    private EtsyGridAdapter mAdapter;

    private ArrayList<String> mData;

    private View allcategoriesview;
    
    private Map<String,String> allcategoriesurls;
	   private Integer [] allcatids;
	   public String [] allmagazinesnames;
    	private List<Magazine> myMagazines;
		private PullToRefreshLayout mPullToRefreshLayout;
		private StaggeredGridView gridView;
		int margin;
		private Bundle b;
		private StaggeredAdapter allcategoriesadapter;
		
		
		public static final String PARENTS = "parents";
		public static final String SALON = "salon";
		public static final String TRUELOVE = "truelove";
		public static final String MANAGEMENT = "management";
		public static final String DRUM = "drum";
		public static final String SMARTFARMER = "smartfarmer";
		public static final String MSAFIRI = "msafiri";
		public static final String AFRICANWOMAN = "africanwoman";
		public static final String ANIMAL = "animal";
		public static final String NAIROBILAW = "nairobilaw";
	
	 @SuppressLint("NewApi")	
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
		 

    	 allcatids = new Integer [] {23,24,25,26,27,28,29,30,31,32};
    	 allmagazinesnames = new String [] {PARENTS,SALON,TRUELOVE,MANAGEMENT,DRUM,SMARTFARMER,MSAFIRI,AFRICANWOMAN,ANIMAL,NAIROBILAW};
    	 myMagazines = new ArrayList<Magazine>();
    	 allcategoriesurls = new HashMap<String, String>();

    	 allcategoriesurls.put(PARENTS,"http://kikosoftwareltd.com/jaridaimages/parentstoday.jpg");
    	 allcategoriesurls.put(SALON,"http://kikosoftwareltd.com/jaridaimages/salon3.jpg");
    	 allcategoriesurls.put(TRUELOVE,"http://kikosoftwareltd.com/jaridaimages/truejullie.gif"); 
    	 allcategoriesurls.put(MANAGEMENT, "http://kikosoftwareltd.com/jaridaimages/managemugenda.jpg");
    	 allcategoriesurls.put(DRUM, "http://kikosoftwareltd.com/jaridaimages/drumoliech.jpg");
    	 allcategoriesurls.put(SMARTFARMER, "http://kikosoftwareltd.com/jaridaimages/smtfarmer.jpg");
    	 allcategoriesurls.put(MSAFIRI, "http://kikosoftwareltd.com/jaridaimages/msafirirudisha.png");
    	 allcategoriesurls.put(AFRICANWOMAN, "http://kikosoftwareltd.com/jaridaimages/africanwoman.jpg");
    	 allcategoriesurls.put(ANIMAL,  "http://kikosoftwareltd.com/jaridaimages/animal.jpg");
    	 allcategoriesurls.put(NAIROBILAW, "http://kikosoftwareltd.com/jaridaimages/nrblaw.jpg");

    	 
    	 
     	allcategoriesview = inflater.inflate(R.layout.homelayout_fragment, container, false);
     	
     	mGridView = (EtsyStaggeredGridView) allcategoriesview.findViewById(R.id.grid_view);
     	
        for (int i=0; i<allcategoriesurls.size(); i++){

        	int magId  = allcatids[i];
        	String magName = allmagazinesnames[i];
        	int Issue = 8;
        	String Category = "Magazine";
	        int Quantity  = 1;
	        int Price = 250;
	        String Description = "African Woman Newspaper";    	                    	
	        String imgUrl =allcategoriesurls.get(magName);
	        boolean addedToCart = true;

	        Magazine mag = new Magazine(magId, magName, Issue, Category, Quantity,  Price, 
       		Description,imgUrl, addedToCart);
	        myMagazines.add(mag);
        }

       
        
	    Bundle b = getArguments();
	    if (b != null) {
	    	mAdapter = new EtsyGridAdapter(allcategoriesview.getContext(), myMagazines);
	    	mGridView.setAdapter(mAdapter);			 
	    }
        
        // do we have saved data?
        /*if (savedInstanceState != null) {
            mData = savedInstanceState.getStringArrayList(SAVED_DATA_KEY);
        }

        if (mData == null) {
            mData = EtsySampleData.generateSampleData();
        }

        for (String data : mData) {
            mAdapter.add(data);
        }*/
        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(this);
        mGridView.setOnItemClickListener(this);


         return allcategoriesview;
     }
  

     //////////////////////////////////////////////////////////////////////
     
     @Override
	public void onSaveInstanceState(final Bundle outState) {
         super.onSaveInstanceState(outState);
         outState.putStringArrayList(SAVED_DATA_KEY, mData);
     }

     @Override
     public void onScrollStateChanged(final AbsListView view, final int scrollState) {
         Log.d(TAG, "onScrollStateChanged:" + scrollState);
     }

     @Override
     public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
         Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                             " visibleItemCount:" + visibleItemCount +
                             " totalItemCount:" + totalItemCount);
         // our handling
         if (!mHasRequestedMore) {
             int lastInScreen = firstVisibleItem + visibleItemCount;
             if (lastInScreen >= totalItemCount) {
                 Log.d(TAG, "onScroll lastInScreen - so load more");
                 mHasRequestedMore = true;
                 //onLoadMoreItems();
             }
         }
     }

    /* private void onLoadMoreItems() {
         final ArrayList<String> sampleData = EtsySampleData.generateSampleData();
         for (String data : sampleData) {
             mAdapter.add(data);
         }
         // stash all the data in our backing store
         mData.addAll(sampleData);
         // notify the adapter that we can update now
         mAdapter.notifyDataSetChanged();
         mHasRequestedMore = false;
     }*/

     @Override
     public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
         Toast.makeText(view.getContext(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
     }

 }

