package wip;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.views.View;
import com.sugarcrm.sugar.VoodooUtils;

/**
 * Currency module object which contains tasks associated with the Currency module
 * like create/deleteAll
 * @author 
 *
 */
public class CurrencyModule {
	protected static AppModel Sugar;
	protected static CurrencyModule currencies;
	protected static DataSource defaultData = new DataSource();
	protected View EditView, ListView;
	
	public static CurrencyModule getInstance() throws Exception {
		if (currencies == null) currencies = new CurrencyModule();
		return currencies;
	}
}