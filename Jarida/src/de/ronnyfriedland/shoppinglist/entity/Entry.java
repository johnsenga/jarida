package de.ronnyfriedland.shoppinglist.entity;

import java.util.UUID;
import de.ronnyfriedland.shoppinglist.entity.enums.ADDEDTOCART;

/**
 * Entity to store entries of a list.
 * 
 * @author Ronny Friedland
 */
public class Entry extends AbstractEntity {


    public static final String TABLE = "Entry";
    public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
    public static final String COL_ISSUE = "issue";
    public static final String COL_CATEGORY = "category";
    public static final String COL_QUANTITY = "quantity";
	public static final String COL_PRICE = "price";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMGUrl ="imgurl";
    public static final String COL_ADDEDTOCART = "addedtocart";
	public static final String COL_LIST = "list";
    
	public int id;
	public String name;
	public int issue;
	public String category;
	public int quantity;
	public int price;
	public String description;
	public String imgUrl;
	public ADDEDTOCART aDDEDTOCART;
	public Shoppinglist list;

    /**
     * Creates a new {@link Entry}.
     */
    /*public Entry() {
    	//this(id);
    	this(getId());
    	int id = this.id;
        this(id);
    }

    *//**
     * Creates a new {@link Entry}.
     * 
     * @param uuid
     *            the initial {@link #uuid}
     */
    public Entry(final int uuid) {
        super(uuid);
        this.aDDEDTOCART = ADDEDTOCART.NO;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setimgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public ADDEDTOCART getAddedToCart() {
        return aDDEDTOCART;
    }

    public void setAddedToCart(ADDEDTOCART aDDEDTOCART) {
        this.aDDEDTOCART = aDDEDTOCART;
    }

    public void setAddedToCart(String status) {
        this.aDDEDTOCART = ADDEDTOCART.valueOf(status);
    }

    public Shoppinglist getList() {
        return list;
    }

    public void setList(Shoppinglist list) {
        this.list = list;
    }


    @Override
    public String toString() {
        StringBuilder sbuild = new StringBuilder();

        sbuild.append(getName());
        sbuild.append(getIssue());
        sbuild.append(getCategory());
        sbuild.append(getQuantity());
        sbuild.append(getPrice());
        sbuild.append(getDescription());
        sbuild.append(getImgUrl());
        return sbuild.toString();
    }
}
