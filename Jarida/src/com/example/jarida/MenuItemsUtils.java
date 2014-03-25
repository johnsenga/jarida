package com.example.jarida;

import java.util.ArrayList;

public class MenuItemsUtils implements MenuItems {
	
	public static ArrayList<String> getList(){
		
		ArrayList<String> menuList = new ArrayList<String>();
		
		menuList.add(SHOPPING_CART);
		menuList.add(FAVORITES);
		menuList.add(SUBSCRIPTIONS);
		menuList.add(FEEDBACK);
		menuList.add(SETTINGS);

		return menuList;
	}
	

}
