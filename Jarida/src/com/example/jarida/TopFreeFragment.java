/*package com.example.jarida;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import de.ronnyfriedland.shoppinglist.entity.Entry;

public  class TopFreeFragment extends SherlockFragment implements  OnRefreshListener {

		private ArrayList<String> topfreeurls = new ArrayList<String>();
		private ArrayList<Entry> magazinesListItems;
		
	  
	   
	   
		private PullToRefreshLayout mPullToRefreshLayout;
		private View topfreeview;
		private StaggeredGridView gridView;
		int margin;
		private Bundle b;
		private StaggeredAdapter topfreeviewadapter;
	
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {

	 topfreeurls.add("http://kikosoftwareltd.com/jaridaimages/samantha.jpg");
	 topfreeurls.add("http://kikosoftwareltd.com/jaridaimages/smartlife.jpg");
	 topfreeurls.add("http://kikosoftwareltd.com/jaridaimages/smartlife2.jpg");
	 topfreeurls.add("http://kikosoftwareltd.com/jaridaimages/smtfarmer.jpg");
	 topfreeurls.add("http://kikosoftwareltd.com/jaridaimages/standardcovenant.jpg");
	 topfreeurls.add("http://kikosoftwareltd.com/jaridaimages/star.jpg");
	 topfreeurls.add("http://kikosoftwareltd.com/jaridaimages/taifabensuda.gif");
	 
	 for (int i=0; i<topfreeurls.size(); i++){
    	 Entry newentry = new Entry();
         newentry.setId("5678");
         newentry.setName("The Drum");
         newentry.setIssue("8");
         newentry.setCategory("Magazine");
         newentry.setQuantity("1");
         newentry.setPrice("250");
         newentry.setDescription("African Woman Newspaper");    	                    	
         newentry.setimgUrl(topfreeurls.get(i));
         newentry.setAddedToCart(true);
         
         magazinesListItems.add(newentry);
     }
	 
	 
	 
	 
 	topfreeview = inflater.inflate(R.layout.layout_fragment, container, false);

     mPullToRefreshLayout = (PullToRefreshLayout) topfreeview.findViewById(R.id.ptr_layout);
     ActionBarPullToRefresh.from(getActivity())
             .allChildrenArePullable()
             .listener(this)
             .setup(mPullToRefreshLayout);

     StaggeredGridView gridView = (StaggeredGridView) topfreeview.findViewById(R.id.staggeredGridView1);
     int margin = getResources().getDimensionPixelSize(R.dimen.margin);

     Bundle b = getArguments();
     if (b != null) {
     	gridView.setItemMargin(margin); 
 		gridView.setPadding(margin, 0, margin, 0); 
 		topfreeviewadapter = new StaggeredAdapter(topfreeview.getContext(), margin, magazinesListItems);     		
 		gridView.setAdapter(topfreeviewadapter);
 		topfreeviewadapter.notifyDataSetChanged();
     }
     return topfreeview;
 }
 @Override
 public void onRefreshStarted(View view) {
 	new AsyncTask<Void, Void, Void>() {
         @Override
         protected Void doInBackground(Void... params) {
             try {
                 Thread.sleep(5000);
                 Bundle b = getArguments();
                 if (b != null) {
                 	gridView.setItemMargin(margin); 
             		gridView.setPadding(margin, 0, margin, 0); 
             		topfreeviewadapter = new StaggeredAdapter(topfreeview.getContext(), margin, topfreeurls);     		
             		gridView.setAdapter(topfreeviewadapter);
             		topfreeviewadapter.notifyDataSetChanged();
                 }
                 
                 
                 
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
}*/