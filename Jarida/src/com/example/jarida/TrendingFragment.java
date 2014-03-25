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

public class TrendingFragment extends SherlockFragment implements  OnRefreshListener {
	private ArrayList<String> trendingurls = new ArrayList<String>();
	private ArrayList<Entry> magazinesListItems;
	
	private PullToRefreshLayout mPullToRefreshLayout;
	private View trendingview;
	private StaggeredGridView gridView;
	int margin;
	private Bundle b;
	private StaggeredAdapter trendingadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
    	
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/taifaleo.jpg");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/taifaleo2.jpg");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/thecitizen.jpg");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/thestarkenya.jpg");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/thestart.png");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/trueajuma.jpg");	 
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/truehellen.jpg");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/truejullie.gif"); 
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/truekobi.jpg");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/truelove.png");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/zuqka.jpg");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/zuqka2.png");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/zuqkainside.png");
    	 trendingurls.add("http://kikosoftwareltd.com/jaridaimages/dailynation1.jpg");
    	 
    	 for (int i=0; i<trendingurls.size(); i++){
        	 Entry newentry = new Entry();
             newentry.setId("5678");
             newentry.setName("The Drum");
             newentry.setIssue("8");
             newentry.setCategory("Magazine");
             newentry.setQuantity("1");
             newentry.setPrice("250");
             newentry.setDescription("African Woman Newspaper");    	                    	
             newentry.setimgUrl(trendingurls.get(i));
             newentry.setAddedToCart(true);
             
             magazinesListItems.add(newentry);
         }
    	 

        trendingview = inflater.inflate(R.layout.layout_fragment, container, false);
        mPullToRefreshLayout = (PullToRefreshLayout) trendingview.findViewById(R.id.ptr_layout);
        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefreshLayout);

        gridView = (StaggeredGridView) trendingview.findViewById(R.id.staggeredGridView1);
        margin = getResources().getDimensionPixelSize(R.dimen.margin);
   
        b = getArguments();
        if (b != null) {
        	gridView.setItemMargin(margin); 
    		gridView.setPadding(margin, 0, margin, 0); 
    		trendingadapter = new StaggeredAdapter(trendingview.getContext(), margin, magazinesListItems);     		
    		gridView.setAdapter(trendingadapter);
    		trendingadapter.notifyDataSetChanged();
        }
        
        return trendingview;
    }
    @Override
    public void onRefreshStarted(View view) {
    	new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(5000);
                    
                    b = getArguments();

                    if (b != null) {
                    	gridView.setItemMargin(margin); 
                		gridView.setPadding(margin, 0, margin, 0); 
                		trendingadapter = new StaggeredAdapter(trendingview.getContext(), margin, trendingurls);     		
                		gridView.setAdapter(trendingadapter);
                		trendingadapter.notifyDataSetChanged();
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