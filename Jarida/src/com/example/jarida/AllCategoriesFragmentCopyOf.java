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

public class AllCategoriesFragmentCopyOf extends SherlockFragment implements  OnRefreshListener {
	   
	   private ArrayList<String> allcategoriesurls = new ArrayList<String>();
	   private Integer [] allcatids;
    	private List<Magazine> myMagazines = new ArrayList<Magazine>();
		private PullToRefreshLayout mPullToRefreshLayout;
		private View allcategoriesview;
		private StaggeredGridView gridView;
		int margin;
		private Bundle b;
		private StaggeredAdapter allcategoriesadapter;
		
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
    	 allcatids = new Integer [] {23,24,25,26,27,28,29,30,31,32};
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/parents.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/parentstoday.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/salon.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/salon3.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/samantha.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/smtfarmer.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/africanwoman.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/animal.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/pregnant.jpg");
    	 allcategoriesurls.add("http://kikosoftwareltd.com/jaridaimages/pregnantoct.jpg");
    	 
     	allcategoriesview = inflater.inflate(R.layout.layout_fragment, container, false);

     	mPullToRefreshLayout = (PullToRefreshLayout) allcategoriesview.findViewById(R.id.ptr_layout);
         ActionBarPullToRefresh.from(getActivity())
                 .allChildrenArePullable()
                 .listener(this)
                 .setup(mPullToRefreshLayout);

         gridView = (StaggeredGridView) allcategoriesview.findViewById(R.id.staggeredGridView1);
         int margin = getResources().getDimensionPixelSize(R.dimen.margin);

         for (int i=0; i<allcategoriesurls.size(); i++){
        	 int magId  = allcatids[i];
             String Name = "The Drum";
             int Issue = 8;
             String Category = "Magazine";
             int Quantity  = 1;
             int Price = 250;
             String Description = "African Woman Newspaper";    	                    	
             String imgUrl =allcategoriesurls.get(i);
             boolean addedToCart = true;

             Magazine mag = new Magazine(magId, Name, Issue, Category, Quantity,  Price, 
            		 Description,imgUrl, addedToCart);
             myMagazines.add(mag);
         }
         

         Bundle b = getArguments();
         if (b != null) {
         	gridView.setItemMargin(margin); 
     		gridView.setPadding(margin, 0, margin, 0); 
     		allcategoriesadapter = new StaggeredAdapter(allcategoriesview.getContext(), margin, myMagazines);     		
     		gridView.setAdapter(allcategoriesadapter);
     		allcategoriesadapter.notifyDataSetChanged();
         }
         return allcategoriesview;
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
