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


public class StaggeredAdapter extends ArrayAdapter<Magazine> {

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
	

	public StaggeredAdapter(Context context, int textViewResourceId,
			List<Magazine>  myMagazines) {
		super(context, textViewResourceId, myMagazines);
		mLoader = new ImageLoader(context);
		this.magz = myMagazines;
	}
	
	
	private static class ViewHolder {
		ScaleImageView imageView;
		public ImageView imgAddCart;		
	}

	public int getCount(){
		return this.magz.size();
	}
	
	public Magazine getItem(int index){
		return this.magz.get(index);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		
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
		
		//if (griditem == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			griditem = layoutInflator.inflate(R.layout.row_staggered_demo,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) griditem .findViewById(R.id.imageView1);
			holder.imgAddCart = (ImageView) griditem.findViewById(R.id.imgAddCart);
			holder.imgAddCart.setTag(position);
			griditem.setTag(holder);

			holder = (ViewHolder) griditem.getTag();

			holder.imageView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					v.getContext().startActivity( new Intent( v.getContext(), NewspaperDetailsActivity.class ) );
				}
				
			});
		
			holder.imgAddCart.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					
					if (newentry.getAddedToCart().equals(ADDEDTOCART.YES)){
						
						newentry.setAddedToCart(ADDEDTOCART.NO);
						
						holder.imgAddCart.setImageResource(R.drawable.addtocartwhite);
					
						AsyncTask <String, String, String> createTask = new CreateEntryTask();
						createTask.execute();
					
						Toast.makeText(v.getContext(), "Removed from cart "+ position, Toast.LENGTH_SHORT).show();

					}else{
						
						newentry.setAddedToCart(ADDEDTOCART.YES);
						holder.imgAddCart.setImageResource(R.drawable.addtocartyellow);
						holder.imgAddCart.invalidate();//post invalidate is for non-ui threads
						
						AsyncTask <String, String, String> deleteTask = new DeleteEntryTask();
						deleteTask.execute();
						Toast.makeText(v.getContext(), "Added to cart "+ position, Toast.LENGTH_SHORT).show();
						
					}
		

				}
			});
			mLoader.DisplayImage(imgUrl, holder.imageView);
		
			Log.v("Website URL", imgUrl);
		
	/*}else{
		holder = (ViewHolder) griditem.getTag();
	}*/
	
		
		
		
		
		return griditem;
	}	
	public void changeImages(String color){
		
		
		
		
		
		
	}
	
	
	private class CreateEntryTask extends AsyncTask <String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			
			 String preresult = null;
			 Shoppinglist list = DataSource.getInstance(getContext()).getList();
			 DataSource.getInstance(getContext()).createEntry(newentry);
			 preresult ="white";
			return preresult;
		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			
			
						
		}
		
	}
	
	
	private class DeleteEntryTask extends AsyncTask <String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			
			 String preresult = "white";
			 Shoppinglist list = DataSource.getInstance(getContext()).getList();
			 DataSource.getInstance(getContext()).deleteEntry(newentry);
			return preresult;
		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
						
		}
		
	}
	
	
	

}

