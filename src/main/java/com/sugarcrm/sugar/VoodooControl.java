package com.sugarcrm.sugar;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.candybean.automation.VInterface;
import com.sugarcrm.candybean.automation.control.VHook;
import com.sugarcrm.candybean.automation.control.VHook.Strategy;
import com.sugarcrm.candybean.automation.control.VControl;
import com.sugarcrm.candybean.automation.control.VSelect;

import static org.junit.Assert.*;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Exposes functionality for interacting directly with controls in SugarCRM. The
 * interface is intended to be as platform-agnostic as is practical (i.e. can
 * wrap web or mobile controls and exposes as consistent an interface to them as
 * possible).
 * 
 * @author David Safar <dsafar@sugarcrm.com>
 * 
 */
public class VoodooControl {
	Strategy strategy;
	protected static VInterface iface;
	VHook hook;
	String hookString = "";
	String strategyName = "";
	String tag = "";

	/**
	 * VoodooControl constructor
	 * 
	 * @param tagIn
	 *            HTML element tag name
	 * @param strategyNameIn
	 *            how to access this element ie... id, css, xpath
	 * @param hookStringIn
	 *            the value of the strategy
	 * @throws Exception
	 */
	public VoodooControl(String tagIn, String strategyNameIn, String hookStringIn) throws Exception {
		iface = VoodooUtils.iface;
		hookString = hookStringIn;
		strategyName = strategyNameIn;
		tag = tagIn;

		strategy = VHook.getStrategy(strategyNameIn.toUpperCase());
		hook = new VHook(strategy, hookStringIn);
	}

	/**
	 * Search for this element for up to 15s.
	 * 
	 * @return the element, if found.
	 * @throws Exception
	 *             if the element is not found
	 */
	public VControl waitForElement() throws Exception {
		return this.<VControl> waitForElement(VControl.class, 15000);
	}

	/**
	 * Search for this control for up to maxWait ms.<br />
	 * <br />
	 * 
	 * Note that the duration of a single iteration of the search is capped by
	 * the perf.implicit_wait time, and that if a search takes longer than the
	 * remaining timeout, the last search will be completed even if it runs over
	 * the timeout (which it usually will).
	 * 
	 * @return the element, if found.
	 * @throws Exception
	 */
	public VControl waitForElement(long maxWait) throws Exception {
		return this.<VControl> waitForElement(VControl.class, maxWait);
	}

	/**
	 * Search for this control for up to maxWait ms (with VSelect support). I
	 * suspect this is a terrible, terrible hack.
	 * 
	 * @param myClass
	 *            the class of the expected return type.
	 * @param maxWait
	 *            the maximum amount of time to search for the control, in ms.
	 * @return the element, if found.
	 * @throws Exception
	 */
	public <T extends VControl> T waitForElement(Class<?> myClass, long maxWait) throws Exception {
		long totalTime = 0;
		boolean found = false;
		VControl foundControl = null;
		long startTime = System.currentTimeMillis();
		long iterationStartTime, iterationDuration, findStartTime, findDuration;

		iterationStartTime = findStartTime = System.currentTimeMillis();

		while (totalTime < maxWait) {
			iterationStartTime = System.currentTimeMillis();
			try {

				findStartTime = System.currentTimeMillis();
				foundControl = iface.getControl(hook);

				// If we get this far, the control is found.
				findDuration = System.currentTimeMillis() - findStartTime;
				found = true;
				VoodooUtils.voodoo.log.info("Find took " + findDuration + "ms.");
				break;
			} catch (Exception e) {
				iterationDuration = System.currentTimeMillis() - iterationStartTime;
				totalTime = System.currentTimeMillis() - startTime;
				VoodooUtils.voodoo.log.info("Waited " + iterationDuration + "ms, " + totalTime + "ms elapsed so far.");
			}
		}

		if (totalTime > 0)
			VoodooUtils.voodoo.log.info("Waited " + totalTime + "ms for " + this + ".");

		if (found) {
			try {
				// If the element found is of a VSelect type, return it as a
				// VSelect.
				if (tag.equals("select")) {
					return (T) (iface.getSelect(hook));
				} else { // Otherwise, a VControl.
					return (T) foundControl;
				}
			} catch (Exception e) {
				// Don't fail if the element does not have a type.
				return null;
			}
		} else { // unfound element
			throw (new UnfoundElementException());
		}
	}

	/**
	 * Wait for this element to become visible for up to 15s.
	 * 
	 * @return the element, if found and visible.
	 * @throws Exception
	 *             if the element is not found or not visible
	 */
	public VControl waitForVisible() throws Exception {
		return this.<VControl> waitForVisible(VControl.class, 15000);
	}

	/**
	 * Wait for this element to become visible for up to the specified number of
	 * ms.
	 * 
	 * @param maxWait
	 *            the number of ms to wait
	 * @return the element, if found and visible.
	 * @throws Exception
	 *             if the element is not found or not visible
	 */
	public VControl waitForVisible(long maxWait) throws Exception {
		return this.<VControl> waitForVisible(VControl.class, maxWait);
	}

	/**
	 * Wait for this element to become visible for up to the specified number of
	 * ms.
	 * 
	 * @param myClass
	 *            the class of the expected return type.
	 * @param maxWait
	 *            the number of ms to wait
	 * @return the element, if found and visible.
	 * @throws Exception
	 *             if the element is not found or not visible
	 */
	public <T extends VControl> T waitForVisible(Class<?> myClass, long maxWait) throws Exception {
		long startTime = System.currentTimeMillis(), currentTime = 0;
		VControl toReturn = waitForElement(maxWait);
		TimeoutException t = null;

		while (currentTime < maxWait && !queryVisible()) {
			currentTime = System.currentTimeMillis() - startTime;
			try {
				toReturn.pause.untilVisible((int) maxWait / 1000);
			} catch (TimeoutException e) {
				// store the timeout exception so we can keep looking and
				// throw it if the element is still invisible when we're done.
				t = e;
			}
		}
		if (!(queryVisible())) {
			VoodooUtils.voodoo.log.severe(this + " not visible in " + (System.currentTimeMillis() - startTime) + "ms.");
			if (t != null) {
				throw t;
			}
			throw new Exception("Something strange happened.  " + this + " never became visible, but no timeout occurred.");
		} else {
			VoodooUtils.voodoo.log.info(this + " became visible in " + (System.currentTimeMillis() - startTime) + "ms.");
			return (T) toReturn;
		}
	}

	/**
	 * Query whether this element is presently visible.
	 * 
	 * @return boolean true if the element is visible, false otherwise.
	 * @throws Exception
	 */
	public boolean queryVisible() throws Exception {
		try {
			return waitForElement().isDisplayed();
		} catch (StaleElementReferenceException e) {
			return false;
		} catch (UnfoundElementException e) {
			return false;
		}
	}

	/**
	 * 
	 * @return the hook string for this control to be interpreted according to
	 *         the strategy name.
	 */
	public String getHookString() {
		return hookString;
	}

	/**
	 * 
	 * @return the strategy name that corresponds to the hook string.
	 */
	public String getStrategyName() {
		return strategyName;
	}

	/**
	 * 
	 * @return the name of the tag representing this control in HTML.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Assert that this element exists on the current page.
	 * 
	 * @param shouldExist
	 *            - set to true if you expect the element to exist, false if it
	 *            should be absent.
	 * @return boolean true if the element exists; else boolean false.
	 */
	public void assertExists(boolean shouldExist) {
		VoodooUtils.voodoo.log.info("Asserting " + ((shouldExist == false) ? "non-" : "") + "existence of " + this);

		boolean match = (shouldExist == queryExists());

		if (shouldExist)
			assertTrue(this + " does not exist when it should.", match);
		else
			assertTrue(this + " exists when it should not.", match);
	}

	/**
	 * Query whether this element exists on the current page.
	 * 
	 * @return boolean true if the element exists; else boolean false.
	 */
	public boolean queryExists() {
		VoodooUtils.voodoo.log.info("Querying existence of " + this);

		try {
			return (waitForElement() != null);
		} catch (Exception e) { // Don't fail if the element isn't found...
			return false;
		}
	}

	/**
	 * Set the text of an input, the state of a checkbox, or value of a select element
	 * @param toSet text/state/value to be set
	 * @throws Exception 
	 */
	public void set(String toSet) throws Exception {
		// Check to see if its a checkbox/radio or select element or another
		// input type
		VoodooUtils.voodoo.log.info("Setting " + this + " to " + toSet);
		// If its a checkbox or radio button
		// TODO: Figure out how to get this to work with radio buttons
		if (tag.equals("select")) {
			this.<VSelect>waitForVisible(VSelect.class, 15000).select(toSet);
		} else if(tag.equals("input") &&
				waitForVisible() != null &&
				waitForVisible().getAttribute("type").equals("checkbox")) {
			boolean desiredState = Boolean.parseBoolean(toSet);

			Boolean checked = Boolean.parseBoolean(getAttribute("checked"));
//			VoodooUtils.voodoo.log.info("The 'checked' attribute = " + checked + ". This checkbox is " + ((checked == false) ? 
//					"not checked" : "checked")); // Keeping this Debug line for future use.
			if (checked != desiredState) {
				waitForVisible().click();
			} else {
				VoodooUtils.voodoo.log.info("Checkbox state for " + this + "is already " + ((checked == false) ? 
						"unchecked" : "checked") + ", no action needed");
			}
		} else { // All other types
			waitForVisible().sendString(toSet);
		}
	}

	/**
	 * Click on the control.
	 * 
	 * @throws Exception
	 */
	public void click() throws Exception {
		VoodooUtils.voodoo.log.info("Clicking " + this + '.');

		Exception toThrow = null;
		long startTime = System.currentTimeMillis(), currentTime = 0;

		while (currentTime < 15000) {
			currentTime = System.currentTimeMillis() - startTime;
			try {
				waitForVisible();
				// Note that we do not chain off of waitForVisible(); to avoid
				// stale element exceptions.
				iface.getControl(hook).click();
				return;
			} catch (StaleElementReferenceException e) {
				VoodooUtils.voodoo.log.warning("Exception caught: StaleElementReferenceException.");
				toThrow = e;
			} catch (TimeoutException e) {
				VoodooUtils.voodoo.log.warning("Exception caught: TimeoutException.");
				toThrow = e;
			} catch (Exception e) {
				if (e.getMessage() != null && e.getMessage().contains("Element is not clickable at point")
						&& e.getMessage().contains("Other element would receive the click:")) {
					VoodooUtils.voodoo.log.warning("Exception caught: overlapping element.");
					toThrow = new OverlappingElementException(e);
				} else {
					throw e;
				}
			}
			long iterationTime = System.currentTimeMillis() - currentTime;
			if (iterationTime < 333) {
				VoodooUtils.pause(333 - iterationTime);
			}
		}

		// If we got here without returning or throwing an exception, the
		// overlap did not clear.
		throw toThrow;
	}

	/**
	 * Hover over the control.
	 * 
	 * @throws Exception
	 */
	public void hover() throws Exception {
		VoodooUtils.voodoo.log.info("Hovering over " + this);
		waitForVisible().hover();
	}

	/**
	 * Double click the control.
	 * 
	 * @throws Exception
	 */
	public void doubleClick() throws Exception {
		VoodooUtils.voodoo.log.info("Double-clicking " + this);

		waitForVisible().doubleClick();
	}

	/**
	 * Right click the control.
	 * 
	 * @throws Exception
	 */
	public void rightClick() throws Exception {
		VoodooUtils.voodoo.log.info("Right-clicking " + this);

		waitForVisible().rightClick();
	}

	/**
	 * Verify that an attribute of the control contains an expected value. TODO:
	 * If we need an equals match, split this method in two or add an argument
	 * to control contains vs. equals.
	 * 
	 * @param attribute
	 *            the name of the attribute to assert against
	 * @param value
	 *            the value of that attribute which you wish to assert
	 * @throws Exception
	 */
	public void assertAttribute(String attribute, String value) throws Exception {
		VoodooUtils.voodoo.log.info("Verifying " + this + " has attribute " + attribute
				+ "=" + value);
		// Normally we would waitForVisible(), but we may be asserting that an
		// element is not visible.
		String foundAttribute = waitForElement().getAttribute(attribute);
		assertTrue("Expected: '" + value + "' Found: '" + foundAttribute + "'", foundAttribute.contains(value));
	}

	/**
	 * Verify that an attribute of the control contains or does not contain an
	 * expected value. TODO: If we need an equals match, split this method in
	 * two or add an argument to control contains vs. equals.
	 * 
	 * @param attribute
	 *            the name of the attribute to assert against
	 * @param value
	 *            the value of that attribute which you wish to assert
	 * @param expected
	 *            true to assert that the attribute SHOULD contain that value,
	 *            false to assert that it should NOT.
	 * @throws Exception
	 */
	public void assertAttribute(String attribute, String value, boolean expected) throws Exception {
		String foundAttribute = waitForElement().getAttribute(attribute);
		if (expected) {
			VoodooUtils.voodoo.log.info("Verifying " + this + " has attribute " + attribute
					+ "=" + value);
			assertTrue("Expected: '" + value + "' Found: '" + foundAttribute + "'", foundAttribute.contains(value));
		} else {
			VoodooUtils.voodoo.log.info("Verifying " + this + " does not have attribute "
					+ attribute + "=" + value);
			assertFalse("Not Expected: '" + value + "' Found: '" + foundAttribute + "'", foundAttribute.contains(value));
		}
	}

	/**
	 * Return the requested attribute of the element or null if the attribute
	 * does not exist.
	 * 
	 * Note that this method does not wait for visibility, so it may be invoked
	 * on invisible elements. The underlying code (CandyBean, Selenium) may not
	 * agree, however.
	 * 
	 * @param attribute
	 *            the name of the attribute to return
	 * @return the value of the specified attribute or null
	 * @throws Exception
	 */
	public String getAttribute(String attribute) throws Exception {
		VoodooUtils.voodoo.log.info("Querying attribute " + attribute + " of " + this);
		try {
			return waitForElement().getAttribute(attribute);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Return the control's text.
	 * 
	 * @return the text of this element
	 * @throws Exception
	 */
	public String getText() throws Exception {
		if (tag.equals("input")) {
			return waitForVisible().getAttribute("value");
		} else {
			// Pass the getText() request to VControl
			return StringEscapeUtils.unescapeHtml(waitForVisible().getText());
		}
	}

	// TODO: Core team to implement triggerEvent(String event) for a future
	// sprint.
	// e.g. onclick, onmouseover, onmouseout, onfocus, onblur, etc...
	public void triggerEvent(String event) {
		VoodooUtils.voodoo.log.info("(UNIMPLEMENTED) Triggering event " + event + " on " + this);
	}

	// TODO: Consult VControl definition and flesh this out.
	// TODO: Consult Voodoo1's Events.xml file and flesh this out.

	// Not sure what this is or why we'd want it or if it should use
	// waitForVisible()
	public void scroll() throws Exception {
		waitForElement().scroll();
	}

	/**
	 * Drag one element to another
	 * 
	 * @param dropHere
	 *            location to drop element
	 * @throws Exception
	 */
	public void dragNDrop(VoodooControl dropHere) throws Exception {
		waitForVisible().dragNDrop(dropHere.waitForVisible());
	}

	/**
	 * Query whether string appears within this control.
	 * 
	 * @param string
	 *            the string to search for
	 * @param caseSensitive
	 *            boolean true for case-sensitive search, boolean false for
	 *            case-insensitive
	 * @return boolean true if string is found, false otherwise.
	 * @throws Exception
	 */
	public boolean queryContains(String string, boolean caseSensitive) throws Exception {
		return waitForVisible().contains(string, caseSensitive);
	}

	/**
	 * Assert the string does or does not appear within this control.
	 * 
	 * @param string
	 *            the string to search for
	 * @param shouldContain
	 *            boolean true for a positive assert, boolean false for a
	 *            negative "assertnot" assert.
	 * @throws Exception
	 */
	public void assertContains(String string, boolean shouldContain) throws Exception {
		VoodooUtils.voodoo.log.info("Asserting " + ((shouldContain == false) ? "non-" : "") + "existence of text '" + string
				+ "' within " + this);

		// Normally we'd do a waitForVisible(), but getText() already does that.
		boolean match = (shouldContain == getText().contains(string));

		if (shouldContain)
			assertTrue(this + " does not contain string '" + string	+ "' when it should.", match);
		else
			assertTrue(this + " contains string '" + string + "' when it should not.", 	match);
	}

	/**
	 * Assert the string does or does not equal the value of this control.
	 * 
	 * @param string
	 *            the string to search for
	 * @param shouldEqual
	 *            boolean true for a positive assert, boolean false for a
	 *            negative "assertnot" assert.
	 * @throws Exception
	 */
	public void assertEquals(String string, boolean shouldEqual) throws Exception {
		VoodooUtils.voodoo.log.info("Asserting " + this + "' equals the string '"
				+ string + "'");
		// Omitted waitForElement() and replaced with getText() because
		// getText() performs a waitForElement()
		boolean match = (shouldEqual == getText().equals(string));

		if (shouldEqual)
			assertTrue(this + " does not equal '" + string + "' when it should.",
					match);
		else
			assertTrue(this + " equals '" + string + "' when it should not.", match);
	}

	/**
	 * Assert that this element does or does not contain the string. Searches
	 * all elements contained from this element.
	 * 
	 * @param string
	 *            the string to search for
	 * @param shouldContain
	 *            boolean true for a positive assert, boolean false for a
	 *            negative "assertnot" assert.
	 * @throws Exception
	 */
	public void assertElementContains(String string, boolean shouldContain) throws Exception {
		VoodooUtils.voodoo.log.info("Asserting " + this + "' contains the string '"	+ string + "'");

		boolean match = (shouldContain == waitForElement().contains(string, true));

		if (shouldContain)
			assertTrue(this + " does not contain the string '" + string + "' when it should.", match);
		else
			assertTrue(this + " contains the string '" + string + "' when it should not.", match);
	}

	/**
	 * Assert that this element is or is not visible.
	 * 
	 * @param shouldBeVisible
	 *            true if the element should be visible, false if it should not.
	 * @throws Exception
	 */
	public void assertVisible(boolean shouldBeVisible) throws Exception {
		VoodooUtils.voodoo.log.info("Asserting " + ((shouldBeVisible == false) ? "in" : "") + "visibility of " + this);

		boolean match = (shouldBeVisible == queryVisible());

		if (shouldBeVisible) {
			assertTrue(this + " is not visible when it should be.", match);
		} else {
			assertTrue(this + " is visible when it should not be.", match);
		}
	}
	
	public String toString() {
		return "<" + tag + " " + strategyName + '=' + "\"" + hookString + "\"" + " />";
	}
}