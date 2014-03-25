package com.example.jarida;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.jarida.loader.ImageLoader;
import com.example.jarida.views.ScaleImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.ronnyfriedland.shoppinglist.entity.Entry;
import de.ronnyfriedland.shoppinglist.entity.enums.ADDEDTOCART;

public class ShoppingListAdapter<T extends Entry> extends ArrayAdapter<T> {

	private ImageLoader mLoader;
	private ViewHolder holder = null;
    private List<T> entries = new ArrayList<T>();
    
    String imgUrl = null;
    String description = null;
    
    public ShoppingListAdapter(Context context, int resource, Collection<T> mEntries) {
        super(context, resource);
        mLoader = new ImageLoader(context);      
        addAll(mEntries);
    }

    private class ViewHolder {
		ScaleImageView imageView;
		TextView description;
		
	}
    
    public ShoppingListAdapter(Context context, int resource) {
        super(context, resource);
        mLoader = new ImageLoader(context);      
    }


    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public T getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View row  = convertView;  	
        imgUrl = entries.get(position).imgUrl;
        description = entries.get(position).description;
    	if (row == null){
    		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    		row = layoutInflater.inflate(R.layout.row_shoppinglist_item, null);
    			
    		holder = new ViewHolder();
    		holder.imageView = (ScaleImageView) row.findViewById(R.id.imgMagazine);
    		holder.description = (TextView) row.findViewById (R.id.textViewDescription);
    		
    		holder.description.setText(description);
    		mLoader.DisplayImage(imgUrl, holder.imageView);
    	}else{
    		holder  = (ViewHolder) row.getTag();
    	}
        return row;
    }


    @Override
    public void addAll(Collection<? extends T> collection) {
        entries.addAll(collection);
    }

    @Override
    public void add(T entry) {
        entries.add(entry);
    }

    @Override
    public void remove(T entry) {
        entries.remove(entry);
    };


    @Override
    public void clear() {
        entries.clear();
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        /*Collections.sort(entries, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                  return lhs.getDescription().compareTo(rhs.getDescription());
            };
        });*/
    }
}
