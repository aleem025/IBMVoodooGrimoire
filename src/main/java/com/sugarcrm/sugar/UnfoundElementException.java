package com.sugarcrm.sugar;

/**
 * Indicates that the specified element was not found on the current page.
 * @author David Safar <dsafar@sugarcrm.com>
 *
 */
public class UnfoundElementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnfoundElementException() {
        super();
	}
	 public UnfoundElementException(String message) {
	        super(message);
	 }
}
