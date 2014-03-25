package com.example.jarida;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.jarida.loader.ImageLoader;
import com.example.jarida.views.ScaleImageView;

import de.ronnyfriedland.shoppinglist.entity.Entry;
import de.ronnyfriedland.shoppinglist.entity.Shoppinglist;
import de.ronnyfriedland.shoppinglist.entity.enums.ADDEDTOCART;

import android.view.View.OnClickListener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
/***
 * ADAPTER
 */

public class EtsyGridAdapter extends ArrayAdapter<Magazine> {

    private static final String TAG = "HomeGridAdapter";

    
    private ImageLoader mLoader;
	private ShoppingListAdapter shoppingAdapter;
	
	private int uuid;//"articleid";
	private int issue, quantity, price;
	private String name, category, description, imgUrl;
	private Entry entry;
	boolean addedToCart;
	private Entry newentry;
	private ViewHolder holder = null;
	
	private List<Magazine> magz = new ArrayList<Magazine>();

    private class ViewHolder {
    	ScaleImageView imageView;
		ImageView imgAddCart;
    }

    /*private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    
    */
    
    public EtsyGridAdapter(final Context context, final List<Magazine>  myMagazines) {
        
    	super(context, R.layout.row_staggered_demo);
    	mLoader = new ImageLoader(context);
		this.magz = myMagazines;
        /*mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.color.orange);
        mBackgroundColors.add(R.color.green);
        mBackgroundColors.add(R.color.blue);
        mBackgroundColors.add(R.color.Yellow);
        mBackgroundColors.add(R.color.grey);*/
    }
    
    
    public int getCount(){
		return this.magz.size();
	}
	
	public Magazine getItem(int index){
		return this.magz.get(index);
	}
    
    

    @Override 
    public View getView(final int position, final View convertView, final ViewGroup parent) {
		
    	uuid =magz.get(position).id;
		name = magz.get(position).name;
		issue  = magz.get(position).issue;
		category = magz.get(position).category;
		quantity = magz.get(position).quantity;
		price = magz.get(position).price;
		description = magz.get(position).description;
		imgUrl = magz.get(position).imgUrl;
		addedToCart = magz.get(position).addedToCart;

		newentry = new Entry(uuid);
		   newentry.setId(uuid);
           newentry.setName(name);
           newentry.setIssue(issue);
           newentry.setCategory(category);
           newentry.setQuantity(quantity);
           newentry.setPrice(price);
           newentry.setDescription(description);	                    	
           newentry.setimgUrl(imgUrl);
           newentry.setAddedToCart(ADDEDTOCART.YES);
		
        Log.v("Magazine Values", newentry.toString());
		
		View griditem = convertView;
		
		if (griditem == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			griditem = layoutInflator.inflate(R.layout.rowetsygrid,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) griditem .findViewById(R.id.magazineImg);
			griditem.setTag(holder);

			holder = (ViewHolder) griditem.getTag();
			holder.imageView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					
					Intent issuesIntent = new Intent (v.getContext(), IssuesActivity.class);
					issuesIntent.putExtra("Magazine", name);
					v.getContext().startActivity(issuesIntent);
				}
				
			});
			mLoader.DisplayImage(imgUrl, holder.imageView);
		
			Log.v("Website URL", imgUrl);
		
	}else{
		holder = (ViewHolder) griditem.getTag();
	}

        return griditem;
    }

    
   /* private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }*/
}