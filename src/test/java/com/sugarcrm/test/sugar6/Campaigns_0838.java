package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

public class Campaigns_0838 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
			/*
			// To be created with default data.
			CampaignRecord myCampaign = (CampaignRecord)sugar.campaigns.create();  

			// Verify the campaign using the GUI
			myCampaign.verify();
			 */
	}

	public void cleanup() throws Exception {
		//sugar.campaigns.api.deleteAll();
		sugar.logout();
	}
}
