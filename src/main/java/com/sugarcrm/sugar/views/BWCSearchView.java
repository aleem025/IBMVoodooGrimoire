package com.sugarcrm.sugar.views;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.RecordsModule;

public class BWCSearchView extends View{
	
	public final VoodooControl BASIC_SEARCH_LINK;	
	public final VoodooControl BASIC_SEARCH_NAME_CONTACT;
	public final VoodooControl BASIC_SEARCH_NAME;
	public final VoodooControl BASIC_CURRENT_USER_CHBOX;
	public final VoodooControl BASIC_FAVORITES_CHBOX;
	public final VoodooControl BASIC_SEARCH_BUTTON;
	public final VoodooControl BASIC_CLEAR_BUTTON;
	
	public final VoodooControl ADVANCED_SEARCH_LINK;
	public final VoodooControl ADVANCED_CLEAR_BUTTON;
	public final VoodooControl ADVANCED_SEARCH_BUTTON;
	public final VoodooControl ADVANCED_CURRENT_USER_CHBOX;
	public final VoodooControl ADVANCED_FAVORITES_CHBOX;
	
	public final VoodooControl POPUP_SEARCH_NAME;
	public final VoodooControl POPUP_SEARCH_NAME_ALT;
	
	public BWCSearchView() throws Exception {
		super();
		
		BASIC_SEARCH_LINK = new VoodooControl("a", "id", "basic_search_link");		
		BASIC_SEARCH_NAME_CONTACT = new VoodooControl("input", "id", "search_name_basic");
		BASIC_SEARCH_NAME = new VoodooControl("input", "id", "name_basic");
		BASIC_CURRENT_USER_CHBOX = new VoodooControl("input", "id", "current_user_only_basic");
		BASIC_FAVORITES_CHBOX = new VoodooControl("input", "id", "favorites_only_basic");
		BASIC_SEARCH_BUTTON = new VoodooControl("input", "id", "search_form_submit");
		BASIC_CLEAR_BUTTON = new VoodooControl("input", "id", "search_form_clear");
		
		ADVANCED_SEARCH_LINK = new VoodooControl("a", "id", "advanced_search_link");
		ADVANCED_CLEAR_BUTTON = new VoodooControl("input", "xpath", "//div[@id='Contactsadvanced_searchSearchForm']//input[@id='search_form_clear']");
		ADVANCED_SEARCH_BUTTON = new VoodooControl("input", "xpath", "//div[@id='Contactsadvanced_searchSearchForm']//input[@id='search_form_submit']");
		ADVANCED_CURRENT_USER_CHBOX = new VoodooControl("input", "id", "current_user_only_advanced");
		ADVANCED_FAVORITES_CHBOX = new VoodooControl("input", "id", "favorites_only_advanced");		
		
		POPUP_SEARCH_NAME = new VoodooControl("input", "id", "search_name_advanced");
		POPUP_SEARCH_NAME_ALT = new VoodooControl("input", "id", "name_advanced");
	}

	/**
	 * Search the record by Name on ListView
	 * 
	 * @param name
	 * @return true - the record was found
	 *         false - the record was not found
	 */
	public boolean searchByName(String name) throws Exception{
		if (this.BASIC_SEARCH_LINK.queryVisible()) 
			this.BASIC_SEARCH_LINK.click();
	    this.BASIC_CLEAR_BUTTON.click();
	    if (this.BASIC_SEARCH_NAME.queryVisible())  
	    	this.BASIC_SEARCH_NAME.set(name);
	    else 
	    	this.BASIC_SEARCH_NAME_CONTACT.set(name);
	    this.BASIC_CURRENT_USER_CHBOX.set("true");
	    this.BASIC_FAVORITES_CHBOX.set("false");
	    this.BASIC_SEARCH_BUTTON.click();   
	    return new VoodooControl("a", "xpath", "//a[contains(text(), '" + name + "')]").queryVisible();
	}	
	
	/**
	 * Search by any search field on Basic Search
	 * ListView
	 * 
	 * @param control - the control of searching field 
	 * @param searched - the search value
	 * @param verifyName - the name of Record, expected should be found.  
	 * 
	 * @return true - the record was found
	 *         false - the record was not found
	 */
	public boolean basicSearch(VoodooControl control, String searched, String verifyName) throws Exception{		
		if (this.BASIC_SEARCH_LINK.queryVisible())
			this.BASIC_SEARCH_LINK.click();
		this.BASIC_CLEAR_BUTTON.click();
		this.BASIC_CURRENT_USER_CHBOX.set("true");
		this.BASIC_FAVORITES_CHBOX.set("false");
		control.set(searched);		
		this.BASIC_SEARCH_BUTTON.click();
		return new VoodooControl("a", "xpath", "//a[contains(text(), '" + verifyName + "')]").queryVisible();
	}
	
	/**
	 * Search by any search field on Advanced Search
	 * ListView
	 * 
	 * @param control - the control of searching field 
	 * @param searched - the search value
	 * @param verifyName - the name of Record, expected should be found.  
	 * 
	 * @return true - the record was found
	 *         false - the record was not found
	 */
	public boolean advancedSearch(VoodooControl control, String searched, String verifyName) throws Exception{		
		if (this.ADVANCED_SEARCH_LINK.queryVisible())
			this.ADVANCED_SEARCH_LINK.click();
		VoodooUtils.pause(5000);
		this.ADVANCED_CLEAR_BUTTON.click();
		this.ADVANCED_CURRENT_USER_CHBOX.set("true");
		this.ADVANCED_FAVORITES_CHBOX.set("false");
		control.set(searched);		
		this.ADVANCED_SEARCH_BUTTON.click();
		return new VoodooControl("a", "xpath", "//a[contains(text(), '" + verifyName + "')]").queryVisible();
	}
	
	/**
	 * Select the record by Name on Search pop-ups
	 * 
	 * @param name
	 */
	public void popupSelectRecord(String name) throws Exception{		
		VoodooUtils.focusWindow("SalesConnect");
		this.BASIC_CLEAR_BUTTON.click();
	    if (this.POPUP_SEARCH_NAME.queryVisible())  
	    	this.POPUP_SEARCH_NAME.set(name);
	    else 
	    	this.POPUP_SEARCH_NAME_ALT.set(name);	   
	    this.BASIC_SEARCH_BUTTON.click();	    
	    VoodooUtils.pause(5000);
	    new VoodooControl("a", "xpath", "//a[contains(text(), '" + name + "')]").click();
	    VoodooUtils.switchBackToWindow();	  
	    VoodooUtils.switchToBWCFrame();
	}
	
}
