package wip;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.views.View;
import com.sugarcrm.sugar.VoodooUtils;

/**
 * Document module object which contains tasks associated with the Documents module
 * like create/deleteAll
 * @author 
 *
 */
public class DocumentsModule {
	protected static AppModel Sugar;
	protected static DocumentsModule documents;
	protected static DataSource defaultData = new DataSource();
	protected View EditView, ListView;
	
	public static DocumentsModule getInstance() throws Exception {
		if (documents == null) documents = new DocumentsModule();
		return documents;
	}
}