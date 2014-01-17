package wip;

/**
 * Employee module object which contains tasks associated with the Employees module
 * like create/deleteAll
 * @author 
 *
 */
public class EmployeesModule {
	protected static EmployeesModule module;

	public static EmployeesModule getInstance() throws Exception {
		if (module == null) module = new EmployeesModule();
		return module;
	}
}