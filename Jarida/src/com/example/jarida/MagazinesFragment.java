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

public class MagazinesFragment extends SherlockFragment implements  OnRefreshListener {
	   
   private ArrayList<String> magazinesurls = new ArrayList<String>();
   private ArrayList<Entry> magazinesListItems;
	      
	
	
	 
	   
	   private PullToRefreshLayout mPullToRefreshLayout;
		private View magazineview;
		private StaggeredGridView gridView;
		int margin;
		private Bundle b;
		private StaggeredAdapter magazineadapter;
		
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {

	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/manageissac.jpg");
	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/managemugenda.jpg");
	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/msafiri.jpg");
	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/msafiri79.jpg");
	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/msafirirudisha.png");
	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/nationobama.jpg");
	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/nrblaw.jpg");
	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/nrblaw2.jpg");
	 magazinesurls.add("http://kikosoftwareltd.com/jaridaimages/parents.jpg");
	 
	 
	  for (int i=0; i<magazinesurls.size(); i++){
     	 Entry newentry = new Entry();
          newentry.setId("5678678");
          newentry.setName("The Drum");
          newentry.setIssue("8");
          newentry.setCategory("Magazine");
          newentry.setQuantity("1");
          newentry.setPrice("250");
          newentry.setDescription("African Woman Newspaper");    	                    	
          newentry.setimgUrl(magazinesurls.get(i));
          newentry.setAddedToCart(true);
          
          magazinesListItems.add(newentry);
      }
	 
     magazineview = inflater.inflate(R.layout.layout_fragment, container, false);

     mPullToRefreshLayout = (PullToRefreshLayout) magazineview.findViewById(R.id.ptr_layout);
     ActionBarPullToRefresh.from(getActivity())
             .allChildrenArePullable()
             .listener(this)
             .setup(mPullToRefreshLayout);

     StaggeredGridView gridView = (StaggeredGridView) magazineview.findViewById(R.id.staggeredGridView1);
     int margin = getResources().getDimensionPixelSize(R.dimen.margin);

     Bundle b = getArguments();
     if (b != null) {
     	gridView.setItemMargin(margin); 
 		gridView.setPadding(margin, 0, margin, 0); 
 		magazineadapter = new StaggeredAdapter(magazineview.getContext(), margin, magazinesListItems);     		
 		gridView.setAdapter(magazineadapter);
 		magazineadapter.notifyDataSetChanged();
     }
     return magazineview;
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
             		magazineadapter = new StaggeredAdapter(magazineview.getContext(), margin, magazinesurls);     		
             		gridView.setAdapter(magazineadapter);
             		magazineadapter.notifyDataSetChanged();
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