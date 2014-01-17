package wip;

/**
 * Contract module object which contains tasks associated with the Contracts module
 * like create/deleteAll
 * @author 
 *
 */
public class ContractsModule {
	protected static ContractsModule module;

	public static ContractsModule getInstance() throws Exception {
		if (module == null) module = new ContractsModule();
		return module;
	}
}