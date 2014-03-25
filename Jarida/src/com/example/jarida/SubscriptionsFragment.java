package com.example.jarida;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import de.ronnyfriedland.shoppinglist.entity.Entry;

public class SubscriptionsFragment extends SherlockFragment implements  OnRefreshListener {
	   
	   private ArrayList<String> subscriptionsurl = new ArrayList<String>();
	   private Integer [] subscriptionsids;
	   
    	private List<Magazine> myMagazines = new ArrayList<Magazine>();
		private PullToRefreshLayout mPullToRefreshLayout;
		private View subscriptionsview;
		private StaggeredGridView gridView;
		int margin;
		private Bundle b;
		
		private SubscriptionsStaggeredAdapter subscriptionsadapter;
		
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
    	 
    	 subscriptionsids = new Integer [] {23,24,25,26,27,28,29,30,31,32};
    	 subscriptionsurl.add("http://kikosoftwareltd.com/jaridaimages/manageissac.jpg");
    	 subscriptionsurl.add("http://kikosoftwareltd.com/jaridaimages/managemugenda.jpg");
    	 subscriptionsurl.add("http://kikosoftwareltd.com/jaridaimages/msafiri.jpg");
    	 subscriptionsurl.add("http://kikosoftwareltd.com/jaridaimages/msafiri79.jpg");
    	 subscriptionsurl.add("http://kikosoftwareltd.com/jaridaimages/msafirirudisha.png");
    	 subscriptionsurl.add("http://kikosoftwareltd.com/jaridaimages/nrblaw.jpg");
    	 subscriptionsurl.add("http://kikosoftwareltd.com/jaridaimages/nrblaw2.jpg");
    	 subscriptionsurl.add("http://kikosoftwareltd.com/jaridaimages/parents.jpg");
    	 
     	subscriptionsview = inflater.inflate(R.layout.layout_fragment, container, false);

     	mPullToRefreshLayout = (PullToRefreshLayout) subscriptionsview.findViewById(R.id.ptr_layout);
         ActionBarPullToRefresh.from(getActivity())
                 .allChildrenArePullable()
                 .listener(this)
                 .setup(mPullToRefreshLayout);

         gridView = (StaggeredGridView) subscriptionsview.findViewById(R.id.staggeredGridView1);
         int margin = getResources().getDimensionPixelSize(R.dimen.margin);
    
         for (int i=0; i<subscriptionsurl.size(); i++){
        	 int magId  = subscriptionsids[i];
             String Name = "The Drum";
             int Issue = 8;
             String Category = "Magazine";
             int Quantity  = 1;
             int Price = 250;
             String Description = "African Woman Newspaper";    	                    	
             String imgUrl =subscriptionsurl.get(i);
             boolean addedToCart = true;

             Magazine mag = new Magazine(magId, Name, Issue,
    					Category, Quantity, Price, Description,
    					imgUrl, addedToCart);
             myMagazines.add(mag);
         }
         

         Bundle b = getArguments();
         if (b != null) {
         	gridView.setItemMargin(margin); 
     		gridView.setPadding(margin, 0, margin, 0); 
     		subscriptionsadapter = new SubscriptionsStaggeredAdapter(subscriptionsview.getContext(), margin, myMagazines);     		
     		gridView.setAdapter(subscriptionsadapter);
     		subscriptionsadapter.notifyDataSetChanged();
         }
         return subscriptionsview;
     }
     @Override
     public void onRefreshStarted(View view) {
     	new AsyncTask<Void, Void, Void>() {
             @Override
             protected Void doInBackground(Void... params) {
                 try {
                     Thread.sleep(5000);
                     Bundle b = getArguments();
                    /* if (b != null) {
                     	gridView.setItemMargin(margin); 
                 		gridView.setPadding(margin, 0, margin, 0); 
                 		allcategoriesadapter = new StaggeredAdapter(allcategoriesview.getContext(), margin, allcategoriesurls);     		
                 		gridView.setAdapter(allcategoriesadapter);
                 		allcategoriesadapter.notifyDataSetChanged();
                     }*/
                     
                     
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 return null;
             }

             @Override
             protected void onPostExecute(Void result) {
                 super.onPostExecute(result);
                 mPullToRefreshLayout.setRefreshComplete();
             }
         }.execute();
     }
 }
