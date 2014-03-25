package com.example.jarida;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jarida.loader.ImageLoader;
import com.example.jarida.views.ScaleImageView;

import de.ronnyfriedland.shoppinglist.entity.Entry;
import de.ronnyfriedland.shoppinglist.entity.Shoppinglist;
import de.ronnyfriedland.shoppinglist.entity.enums.ADDEDTOCART;


public class SubscriptionsStaggeredAdapter extends ArrayAdapter<Magazine> {

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
	

	public SubscriptionsStaggeredAdapter(Context context, int textViewResourceId,
			List<Magazine>  myMagazines) {
		super(context, textViewResourceId, myMagazines);
		mLoader = new ImageLoader(context);
		this.magz = myMagazines;
	}
	
	
	private class ViewHolder {
		ScaleImageView imageView;
		ImageView imgAddCart;		
	}

	public int getCount(){
		return this.magz.size();
	}
	
	public Magazine getItem(int index){
		return this.magz.get(index);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
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
		
           
           
		
		
		View griditem = convertView;
		
		if (griditem == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			griditem = layoutInflator.inflate(R.layout.row_staggered_subsc,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) griditem .findViewById(R.id.imageView1);
			griditem.setTag(holder);

			holder = (ViewHolder) griditem.getTag();
			holder.imageView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					v.getContext().startActivity( new Intent( v.getContext(), NewsPaperViewActivity.class ) );
				}
				
			});
		
			mLoader.DisplayImage(imgUrl, holder.imageView);
		
			Log.v("Website URL", imgUrl);
		
	}else{
		holder = (ViewHolder) griditem.getTag();
	}
	
		
		
		
		
		return griditem;
	}	
	public void changeImages(String color){
		
		
		
		
		
		
	}
	
	
	private class MyNewTask extends AsyncTask <String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			
			String preresult = null;
			 Shoppinglist list = DataSource.getInstance(getContext()).getList();
             
             if (addedToCart){
            	 preresult ="yellow";
             	DataSource.getInstance(getContext()).deleteEntry(newentry);
             	
 				/*Toast.makeText(getContext(), "Removed from Cart", Toast.LENGTH_LONG)
					.show();*/
 				
 				
             }else{
             	
            	 preresult = "white";
             	
                 DataSource.getInstance(getContext()).createEntry(newentry);
 				
                /* Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_LONG)
						.show();*/
                 
             } 
		
			return preresult;
		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			
			if (result.equals("yellow")){
				holder.imgAddCart.setImageDrawable(getContext().getResources()
		                .getDrawable(R.drawable.addtocartyellow));
		    	holder.imgAddCart.setVisibility(View.VISIBLE);

			}else{
				holder.imgAddCart.setImageDrawable(getContext().getResources()
	                    .getDrawable(R.drawable.addtocartwhite));
	        	holder.imgAddCart.setVisibility(View.VISIBLE);
			}
			
		}
		
	}
	
	

}

