package com.example.jarida;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.devspark.robototextview.widget.RobotoTextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class SliderArrayAdapter extends ArrayAdapter<String>  implements MenuItems{

    private Context context;
   
    
    private List<String> menus;

    public SliderArrayAdapter(Context context, int viewResourceId) {
        super(context, viewResourceId);
        this.context = context;
        menus = MenuItemsUtils.getList();
        
    }
 
    public int getCount() {
        return this.menus.size();
    }
 
    public String getItem(int index) {
        return this.menus.get(index);
    }
 
    private class ViewHolder {
    	 ImageView menuIcon;
    	 RobotoTextView menuLabel;
    	  
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
    	View row = convertView;
        if (row == null) {
        	row = LayoutInflater.from(getContext()).inflate(
					R.layout.menulistview_item, null);
            holder = new ViewHolder();
            holder.menuLabel = (RobotoTextView) row.findViewById(R.id.textViewIcon);
            holder.menuIcon = (ImageView) row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }else{
        	holder = (ViewHolder) row.getTag();
        }
        String  menuItem = menus.get(position);

        holder.menuLabel.setText(menuItem);
        if (menuItem == SHOPPING_CART){
        	holder.menuIcon.setImageResource(R.drawable.cartpurple);
        }else if  (menuItem == FAVORITES){
            holder.menuIcon.setImageResource(R.drawable.favicons);
        }else if  (menuItem == SUBSCRIPTIONS){
            holder.menuIcon.setImageResource(R.drawable.subscriptionimg);
        } else if  (menuItem == FEEDBACK){
            holder.menuIcon.setImageResource(R.drawable.fdback);
        } else if  (menuItem == SETTINGS){
            holder.menuIcon.setImageResource(R.drawable.settings);
        }

        return row;
    }
}


