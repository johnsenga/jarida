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

public class NewspapersFragment extends SherlockFragment implements  OnRefreshListener {
	
	private ArrayList<String> newspapersurls = new ArrayList<String>();
	private ArrayList<Entry> magazinesListItems;
	   
	private PullToRefreshLayout mPullToRefreshLayout;
	private View newspapersview;
	private StaggeredGridView gridView;
	int margin;
	private Bundle b;
	private StaggeredAdapter newspapersadapter;
	
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 
	 newspapersurls.add("http://kikosoftwareltd.com/jaridaimages/dailynation2.JPG");
	 newspapersurls.add("http://kikosoftwareltd.com/jaridaimages/drumemmy.jpg");
	 newspapersurls.add("http://kikosoftwareltd.com/jaridaimages/drumoliech.jpg");
	 newspapersurls.add("http://kikosoftwareltd.com/jaridaimages/drumwahu.jpg");
	 newspapersurls.add("http://kikosoftwareltd.com/jaridaimages/exchange.jpg");
	 newspapersurls.add("http://kikosoftwareltd.com/jaridaimages/managecar.jpg");
	 
	 for (int i=0; i<newspapersurls.size(); i++){
    	 Entry newentry = new Entry();
         newentry.setId("5678");
         newentry.setName("The Drum");
         newentry.setIssue("8");
         newentry.setCategory("Magazine");
         newentry.setQuantity("1");
         newentry.setPrice("250");
         newentry.setDescription("African Woman Newspaper");    	                    	
         newentry.setimgUrl(newspapersurls.get(i));
         newentry.setAddedToCart(true);
         
         magazinesListItems.add(newentry);
     }
	 
 	newspapersview = inflater.inflate(R.layout.layout_fragment, container, false);

     mPullToRefreshLayout = (PullToRefreshLayout) newspapersview.findViewById(R.id.ptr_layout);
     ActionBarPullToRefresh.from(getActivity())
             .allChildrenArePullable()
             .listener(this)
             .setup(mPullToRefreshLayout);

     StaggeredGridView gridView = (StaggeredGridView) newspapersview.findViewById(R.id.staggeredGridView1);
     int margin = getResources().getDimensionPixelSize(R.dimen.margin);

     Bundle b = getArguments();
     if (b != null) {
     	gridView.setItemMargin(margin); 
 		gridView.setPadding(margin, 0, margin, 0);  
 		newspapersadapter = new StaggeredAdapter(newspapersview.getContext(), margin, magazinesListItems);     		
 		gridView.setAdapter(newspapersadapter);
 		newspapersadapter.notifyDataSetChanged();
     }
     return newspapersview;
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
             		newspapersadapter = new StaggeredAdapter(newspapersview.getContext(), margin, newspapersurls);     		
             		gridView.setAdapter(newspapersadapter);
             		newspapersadapter.notifyDataSetChanged();
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