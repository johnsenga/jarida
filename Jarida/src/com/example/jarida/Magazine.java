package com.example.jarida;

public class Magazine {
	public int id = 0; 
	public String name = null; 
	public int issue = 0; 
	public String category = null; 
	public int quantity = 0;  
	public int price = 0; 
	public String description = null; 
	public String imgUrl  = null;
	public boolean addedToCart = false; 

     public Magazine(){
            
     }

     public Magazine(int id, String name, int issue, String category, int quantity,  int price, 
	 String description,String imgUrl, boolean addedToCart){
		 this.id = id;
    	 this.name = name;
		 this.issue = issue;
		 this.category = category;
		 this.quantity = quantity;
		 this.price = price;
		 this.description = description;
		 this.imgUrl = imgUrl;
		 this.addedToCart = addedToCart;
     }

     public boolean addedToCart(){
    	 return addedToCart;
     }
     public void setAddedToCart(boolean addedToCart){
    	 this.addedToCart = addedToCart;
     }
     
     @Override
     public String toString(){
             return this.name;
     }
}
