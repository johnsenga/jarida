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

public class TopPaidFragment extends SherlockFragment implements  OnRefreshListener {
	   private ArrayList<String> toppaidurls = new ArrayList<String>();
	   private ArrayList<Entry> magazinesListItems;
	  
	   
	   	private PullToRefreshLayout mPullToRefreshLayout;
		private View toppaidview;
		private StaggeredGridView gridView;
		int margin;
		private Bundle b;
		private StaggeredAdapter toppaidadapter;
	
	
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 
	 toppaidurls.add("http://kikosoftwareltd.com/jaridaimages/parents.jpg");
	 toppaidurls.add("http://kikosoftwareltd.com/jaridaimages/parentstoday.jpg");
	 toppaidurls.add("http://kikosoftwareltd.com/jaridaimages/pregnant.jpg");
	 toppaidurls.add("http://kikosoftwareltd.com/jaridaimages/pregnantoct.jpg");
	 toppaidurls.add("http://kikosoftwareltd.com/jaridaimages/pulse.jpg");
	 toppaidurls.add("http://kikosoftwareltd.com/jaridaimages/pulseeee.jpg");
	 toppaidurls.add("http://kikosoftwareltd.com/jaridaimages/salon.jpg");
	 toppaidurls.add("http://kikosoftwareltd.com/jaridaimages/salon3.jpg");

	 
	 for (int i=0; i<toppaidurls.size(); i++){
    	 Entry newentry = new Entry();
         newentry.setId("5678");
         newentry.setName("The Drum");
         newentry.setIssue("8");
         newentry.setCategory("Magazine");
         newentry.setQuantity("1");
         newentry.setPrice("250");
         newentry.setDescription("African Woman Newspaper");    	                    	
         newentry.setimgUrl(toppaidurls.get(i));
         newentry.setAddedToCart(true);
         
         magazinesListItems.add(newentry);
     }
	 
	 
 	toppaidview = inflater.inflate(R.layout.layout_fragment, container, false);

     mPullToRefreshLayout = (PullToRefreshLayout) toppaidview.findViewById(R.id.ptr_layout);
     ActionBarPullToRefresh.from(getActivity())
             .allChildrenArePullable()
             .listener(this)
             .setup(mPullToRefreshLayout);

     gridView = (StaggeredGridView) toppaidview.findViewById(R.id.staggeredGridView1);
     int margin = getResources().getDimensionPixelSize(R.dimen.margin);

     Bundle b = getArguments();
     if (b != null) {
     	gridView.setItemMargin(margin); 
 		gridView.setPadding(margin, 0, margin, 0); 
 		toppaidadapter = new StaggeredAdapter(toppaidview.getContext(), margin, magazinesListItems);     		
 		gridView.setAdapter(toppaidadapter);
 		toppaidadapter.notifyDataSetChanged();
     }
     return toppaidview;
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
             		toppaidadapter = new StaggeredAdapter(toppaidview.getContext(), margin, toppaidurls);     		
             		gridView.setAdapter(toppaidadapter);
             		toppaidadapter.notifyDataSetChanged();
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
}
*/