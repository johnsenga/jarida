package com.example.jarida;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * 
 * This will not work so great since the heights of the imageViews 
 * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get 
 * the (intrinsic) image width and height and set the views height manually. I will
 * look into a fix once I find extra time.
 * 
 * @author Maurycy Wojtowicz
 *
 */
public class MainActivity extends Activity {

	private String urls[] = { 
			"http://kikosoftwareltd.com/jaridaimages/africanwoman.jpg",
			"http://kikosoftwareltd.com/jaridaimages/animal.jpg",
			"http://kikosoftwareltd.com/jaridaimages/bdaily1.JPG",
			"http://kikosoftwareltd.com/jaridaimages/bdaily2.jpg",
			"http://kikosoftwareltd.com/jaridaimages/bdnjiraini.jpg",
			"http://kikosoftwareltd.com/jaridaimages/countyweekly.png",
			"http://kikosoftwareltd.com/jaridaimages/dailynation1.jpg",
			"http://kikosoftwareltd.com/jaridaimages/dailynation2.JPG",
			"http://kikosoftwareltd.com/jaridaimages/drumemmy.jpg",
			"http://kikosoftwareltd.com/jaridaimages/drumoliech.jpg",
			"http://kikosoftwareltd.com/jaridaimages/drumwahu.jpg",
			"http://kikosoftwareltd.com/jaridaimages/exchange.jpg",
			"http://kikosoftwareltd.com/jaridaimages/managecar.jpg",
			"http://kikosoftwareltd.com/jaridaimages/manageissac.jpg",
			"http://kikosoftwareltd.com/jaridaimages/managemaggie.jpg",
			"http://kikosoftwareltd.com/jaridaimages/managemugenda.jpg",
			"http://kikosoftwareltd.com/jaridaimages/msafiri.jpg",
			"http://kikosoftwareltd.com/jaridaimages/msafiri79.jpg",
			"http://kikosoftwareltd.com/jaridaimages/msafirirudisha.png",
			"http://kikosoftwareltd.com/jaridaimages/nationobama.jpg",
			"http://kikosoftwareltd.com/jaridaimages/nrblaw.jpg",
			"http://kikosoftwareltd.com/jaridaimages/nrblaw2.jpg",
			"http://kikosoftwareltd.com/jaridaimages/parents.jpg",
			"http://kikosoftwareltd.com/jaridaimages/parentstoday.jpg",
			"http://kikosoftwareltd.com/jaridaimages/people.jpg",
			"http://kikosoftwareltd.com/jaridaimages/pregnant.jpg",
			"http://kikosoftwareltd.com/jaridaimages/pregnantoct.jpg",
			"http://kikosoftwareltd.com/jaridaimages/pulse.jpg",
			"http://kikosoftwareltd.com/jaridaimages/pulseeee.jpg",
			"http://kikosoftwareltd.com/jaridaimages/salon.jpg",
			"http://kikosoftwareltd.com/jaridaimages/salon3.jpg",
			"http://kikosoftwareltd.com/jaridaimages/samantha.jpg",
			"http://kikosoftwareltd.com/jaridaimages/smartlife.jpg",
			"http://kikosoftwareltd.com/jaridaimages/smartlife2.jpg",
			"http://kikosoftwareltd.com/jaridaimages/smtfarmer.jpg",
			"http://kikosoftwareltd.com/jaridaimages/standardcovenant.jpg",
			"http://kikosoftwareltd.com/jaridaimages/star.jpg",
			"http://kikosoftwareltd.com/jaridaimages/taifabensuda.gif",
			"http://kikosoftwareltd.com/jaridaimages/taifaleo.jpg",
			"http://kikosoftwareltd.com/jaridaimages/taifaleo2.jpg",
			"http://kikosoftwareltd.com/jaridaimages/thecitizen.jpg",
			"http://kikosoftwareltd.com/jaridaimages/thestarkenya.jpg",
			"http://kikosoftwareltd.com/jaridaimages/thestart.png",
			"http://kikosoftwareltd.com/jaridaimages/trueajuma.jpg",
			"http://kikosoftwareltd.com/jaridaimages/truehellen.jpg",
			"http://kikosoftwareltd.com/jaridaimages/truejullie.gif",
			"http://kikosoftwareltd.com/jaridaimages/truekobi.jpg",
			"http://kikosoftwareltd.com/jaridaimages/truelove.png",
			"http://kikosoftwareltd.com/jaridaimages/zuqka.jpg",
			"http://kikosoftwareltd.com/jaridaimages/zuqka2.png",
			"http://kikosoftwareltd.com/jaridaimages/zuqkainside.png"

	};
	/**
	 * This will not work so great since the heights of the imageViews 
	 * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get 
	 * the (intrinsic) image width and height and set the views height manually. I will
	 * look into a fix once I find extra time.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StaggeredGridView gridView = (StaggeredGridView) this.findViewById(R.id.staggeredGridView1);
		
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		
		gridView.setItemMargin(margin); // set the GridView margin
		
		gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 
		
		/*StaggeredAdapter adapter = new StaggeredAdapter(MainActivity.this, R.id.imageView1, urls);
		
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();*/
	}

	
}
