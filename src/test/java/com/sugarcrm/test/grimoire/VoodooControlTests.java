package com.sugarcrm.test.grimoire;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;

import static org.junit.Assert.*;

public class VoodooControlTests extends SugarTest {

	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void exists() throws Exception {
		VoodooUtils.voodoo.log.info("Running exists...");

		sugar.accounts.navToListView();

		// Positive test cases (should pass).
		sugar.accounts.listView.getControl("createButton").assertExists(true);
		sugar.accounts.createDrawer.getEditField("billingAddressStreet").assertExists(false);

		// Negative test cases (should fail, so we call a method that verifies
		// that they do).
		expectFailure(sugar.accounts.listView.getControl("createButton"), false);
		expectFailure(sugar.accounts.createDrawer.getEditField("billingAddressStreet"), true);
	}

	public void expectFailure(VoodooControl control, boolean shouldExist) {
		boolean assertFailed = false;

		try {
			control.assertExists(shouldExist);
		} catch (AssertionError e) {
			assertFailed = true;
		}
		if (assertFailed == false) {
			String problem;
			if (shouldExist == true)
				problem = " existed when it did not.";
			else
				problem = " did not exist when it did.";

			fail("VoodooControl reported element with " + control.getStrategyName() + " = " + control.getHookString() + problem);
		}
	}

	@Test
	public void asserts() throws Exception {
		VoodooUtils.voodoo.log.info("Running asserts...");
		AccountRecord myAccount;
		myAccount = (AccountRecord) sugar.accounts.api.create();
		sugar.accounts.navToListView();
		// Test assertElementContains()
		new VoodooControl("div", "css", "div[data-voodoo-name='recordlist']").assertElementContains("SugarCRM Inc.", true);

		// Test assertEquals()
		new VoodooControl("a", "css", ".fld_name.list a").assertEquals(myAccount.getRecordIdentifier(), true);

		// Test assertAttribute()
		new VoodooControl("span", "css", ".fld_name.list").assertAttribute("class", "fld_name list");

		// Test assertContains
		new VoodooControl("a", "css", ".fld_name.list a").assertContains(myAccount.getRecordIdentifier(), true);
	}

	@Test
	public void checkboxes() throws Exception {
		VoodooUtils.voodoo.log.info("Running checkboxes...");
		VoodooUtils.voodoo.log.info("Checking in a BWC module..........");
		
		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame"); // focus on iframe document to get access to elements in backwards compatible mode
		VoodooUtils.pause(1000);
		sugar.admin.adminTools.getControl("passwordManagement").click();
		
		// Confirm default state of checkbox
		assertTrue("This checkbox should be checked!", (true == Boolean.parseBoolean(sugar.admin.passwordManagement.getControl("passwordSettingOneUpper").getAttribute("checked"))));
		
		// The following checkbox by default is checked. We will use .set("true") to confirm that the set method doesn't
		// click regardless of desired state.
		sugar.admin.passwordManagement.getControl("passwordSettingOneUpper").set("true");
		assertTrue("This checkbox should be checked!", (true == Boolean.parseBoolean(sugar.admin.passwordManagement.getControl("passwordSettingOneUpper").getAttribute("checked"))));
		
		// The same checkbox above should still be checked. We will use .set("false") to confirm that the set method keeps the
		// checkbox in the desired state.
		sugar.admin.passwordManagement.getControl("passwordSettingOneUpper").set("false");
		assertTrue("This checkbox should NOT be checked!", (false == Boolean.parseBoolean(sugar.admin.passwordManagement.getControl("passwordSettingOneUpper").getAttribute("checked"))));
		
		// Confirm default state of checkbox
		assertTrue("This checkbox should NOT be checked!", (false == Boolean.parseBoolean(sugar.admin.passwordManagement.getControl("SystemGeneratedPasswordCheckbox").getAttribute("checked"))));
		
		// The following checkbox by default is NOT checked. We will use .set("false") to confirm that the set method doesn't
		// click regardless of desired state.
		sugar.admin.passwordManagement.getControl("SystemGeneratedPasswordCheckbox").set("false");
		assertTrue("This checkbox should be checked!", (false == Boolean.parseBoolean(sugar.admin.passwordManagement.getControl("SystemGeneratedPasswordCheckbox").getAttribute("checked"))));

		// The following checkbox should still NOT be checked. We will use .set("true") to confirm that the set method doesn't
		// click regardless of desired state.
		sugar.admin.passwordManagement.getControl("SystemGeneratedPasswordCheckbox").set("true");
		assertTrue("This checkbox should be checked!", (true == Boolean.parseBoolean(sugar.admin.passwordManagement.getControl("SystemGeneratedPasswordCheckbox").getAttribute("checked"))));
		
		VoodooUtils.focusDefault();
		
		
		// Sidecar enabled tests
		VoodooUtils.voodoo.log.info("Checking checkboxes in a sidecar module.......");
		
		sugar.contacts.navToListView();
		sugar.contacts.listView.create();
		if(sugar.contacts.createDrawer.getControl("showMore").queryVisible())
			sugar.contacts.createDrawer.showMore();
		
		// Confirm default state of checkbox
		assertTrue("This checkbox should not be checked!", (false == Boolean.parseBoolean(new VoodooControl("input","css",".fld_do_not_call.edit input").getAttribute("checked"))));
		
		// The following checkbox by default is not checked. We use .set("false") to confirm that the set method doesn't
		// click regardless of desired state.
		new VoodooControl("input", "css", ".fld_do_not_call.edit input").set("false");
		assertTrue("This checkbox should not be checked!", (false == Boolean.parseBoolean(new VoodooControl("input","css",".fld_do_not_call.edit input").getAttribute("checked"))));
		
		// The above checkbox should still NOT be checked. We use .set("true") to confirm that the set method doesn't
		// click regardless of desired state.
		new VoodooControl("input", "css", ".fld_do_not_call.edit input").set("true");
		assertTrue("This checkbox should be checked!", (true == Boolean.parseBoolean(new VoodooControl("input","css",".fld_do_not_call.edit input").getAttribute("checked"))));
		
		// Confirm default state of checkbox
		assertTrue("This checkbox should be checked!", (true == Boolean.parseBoolean(new VoodooControl("input", "css", ".fld_copy.edit input").getAttribute("checked"))));
		
		// The following checkbox by default is checked. We use .set("true") to confirm that the set method doesn't
		// click regardless of desired state.
		new VoodooControl("input", "css", ".fld_copy.edit input").set("true");
		assertTrue("This checkbox should be checked!", (true == Boolean.parseBoolean(new VoodooControl("input", "css", ".fld_copy.edit input").getAttribute("checked"))));
	
		// The above checkbox should still be checked. We use .set("false") to confirm that the set method doesn't
		// click regardless of desired state.
		new VoodooControl("input", "css", ".fld_copy.edit input").set("false");
		assertTrue("This checkbox should NOT be checked!", (false == Boolean.parseBoolean(new VoodooControl("input", "css", ".fld_copy.edit input").getAttribute("checked"))));
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}