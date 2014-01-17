package com.sugarcrm.sugar;

import java.util.ArrayList;
import java.util.List;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.candybean.examples.sugar.SugarTest;
import com.sugarcrm.sugar.modules.*;
import com.sugarcrm.sugar.views.Alerts;
import com.sugarcrm.sugar.views.CreateAccountView;
import com.sugarcrm.sugar.views.CreateCallView;
import com.sugarcrm.sugar.views.CreateContactView;
import com.sugarcrm.sugar.views.CreateNoteView;
import com.sugarcrm.sugar.views.CreateOpportunityView;
import com.sugarcrm.sugar.views.CreateTaskView;
import com.sugarcrm.sugar.views.Dashboard;
import com.sugarcrm.sugar.views.Footer;
import com.sugarcrm.sugar.views.LoginScreen;
import com.sugarcrm.sugar.views.Navbar;
import com.sugarcrm.sugar.views.NewUserWizard;

/** 
 * AppModel is a model of the application under test.  It contains tasks and
 * objects representing different screens/views in the application.  Those
 * objects, in turn, contain objects representing controls on each page.
 * @author David Safar <dsafar@sugarcrm.com>
 * @author Mazen Louis <mlouis@sugarcrm.com>
 */
public class AppModel {
	protected static AppModel app;

	public Navbar navbar;
	public Footer footer;
	public LoginScreen loginScreen;
	public Dashboard dashboard;
	public NewUserWizard newUserWizard;
	public Alerts alerts;

	public List<Module> moduleRegistry = new ArrayList<Module>();

	// Standard RecordModules
	public AccountsModule accounts;
	public BugsModule bugs;
	public ContactsModule contacts;
	public ForecastsModule forecasts;
	public OpportunitiesModule opportunities;
	public RevLineItemsModule revLineItems;
	public LeadsModule leads;
	public TargetsModule targets;
	public TargetListsModule targetlists;
	public CasesModule cases;
	public TasksModule tasks;
	public NotesModule notes;

	// BWC RecordModules
	public CallsModule calls;
    public CampaignsModule campaigns;
    public DocumentsModule documents;
	public QuotesModule quotes;
    public MeetingsModule meetings;
	public UsersModule users;
	
	// Non-standard modules
	public AdminModule admin;
	public CreateAccountView accountCreateView;
	public CreateOpportunityView opportunityCreateView;
	public CreateContactView contactCreateView;
	public CreateCallView callCreateView;
	public CreateNoteView noteCreateView;
	public CreateTaskView taskCreateView;
	
	/*
	public NotesModule notes;
	public CampaignsModule campaigns;
	public ContractsModule contracts;
	public CurrencyModule currency;
	public EmailsModule emails;
	public EmployeesModule employee;
	public KBModule knowledgeBase;
	public ProjectsModule projects;
	public ReportsModule reports;
	public RolesModule roles;
	public StudioModule studio;
	public WorkFlowModule workflow;
	*/
	public static AppModel getInstance() throws Exception {
		if (app == null) app = new AppModel();
		return app;
	}

	private AppModel() throws Exception {
	
	}

	public void init() throws Exception {
		// App-wide views
		navbar = Navbar.getInstance();
		footer = Footer.getInstance();
		alerts = Alerts.getInstance();

		// Standard modules.
		accounts = (AccountsModule)registerModule(AccountsModule.getInstance());
		bugs = (BugsModule)registerModule(BugsModule.getInstance());
		contacts = (ContactsModule)registerModule(ContactsModule.getInstance());
		forecasts = (ForecastsModule)registerModule(ForecastsModule.getInstance());
		opportunities = (OpportunitiesModule)registerModule(OpportunitiesModule.getInstance());
		revLineItems = (RevLineItemsModule)registerModule(RevLineItemsModule.getInstance());
		leads = (LeadsModule)registerModule(LeadsModule.getInstance());
		targets = (TargetsModule)registerModule(TargetsModule.getInstance());
		targetlists = (TargetListsModule)registerModule(TargetListsModule.getInstance());
		cases = (CasesModule)registerModule(CasesModule.getInstance());
		tasks = (TasksModule)registerModule(TasksModule.getInstance());
		notes = (NotesModule)registerModule(NotesModule.getInstance());

		// BWC modules.
		users = (UsersModule)registerModule(UsersModule.getInstance());
		campaigns = (CampaignsModule)registerModule(CampaignsModule.getInstance());
		documents = (DocumentsModule)registerModule(DocumentsModule.getInstance());
		quotes = (QuotesModule)registerModule(QuotesModule.getInstance());
		meetings = (MeetingsModule)registerModule(MeetingsModule.getInstance());
		calls = (CallsModule)registerModule(CallsModule.getInstance());
		
		// Non-standard modules
		admin = (AdminModule)registerModule(AdminModule.getInstance());

		// Not implemented.
		/*
		contracts = ContractsModule.getInstance();
		currency = CurrencyModule.getInstance();
		emails = EmailsModule.getInstance();
		employee = EmployeesModule.getInstance();
		knowledgeBase = KBModule.getInstance();
		projects = ProjectsModule.getInstance();
		reports = ReportsModule.getInstance();
		roles = RolesModule.getInstance();
		studio = StudioModule.getInstance();			
		workflow = WorkFlowModule.getInstance();
		*/
		
		// Run initialization code that depends on other modules already being
		// constructed.
		for(Module module : moduleRegistry) {
			module.init();
		}

		loginScreen = LoginScreen.getInstance();
		newUserWizard = NewUserWizard.getInstance();
		dashboard = Dashboard.getInstance();
		accountCreateView = CreateAccountView.getInstance();
		callCreateView = CreateCallView.getInstance();
		opportunityCreateView = CreateOpportunityView.getInstance();
		contactCreateView = CreateContactView.getInstance();
		noteCreateView = CreateNoteView.getInstance();
		taskCreateView = CreateTaskView.getInstance();
	}
	
	public Module registerModule(Module toRegister) throws Exception {
		moduleRegistry.add(toRegister);
		navbar.addMenu(toRegister, toRegister.getMenu());
		return toRegister;
	}

	/**
	 * Log into SugarCRM as the admin user.
	 * TODO: Adapt this to log in as any user.
	 * @throws Exception
	 */
	public void login() throws Exception {
		loginScreen.login();
	}
	
	/**
	 * Log into SugarCRM as another user.
	 * 
	 * @param userData FieldSet of data to be used in login.
	 * @throws Exception
	 */
	public void login(FieldSet userData) throws Exception {
		loginScreen.login(userData);
	}
		
	/**
	 * Log into SugarCRM as Administrator.
	 * 
	 * @throws Exception
	 */
	public void login_admin() throws Exception {
		loginScreen.loginActions(0);
	}
	
	/**
	 * Log into SugarCRM as Biz Administrator (level0 user0).
	 * 
	 * @throws Exception
	 */
	public void login_bizAdmin() throws Exception {
		loginScreen.loginActions(1);
	}
	
	/**
	 * Log into SugarCRM as regular user (level10 user10).
	 * 
	 * @throws Exception
	 */
	public void login_regularUser() throws Exception {
		loginScreen.loginActions(2);
	}
	
	/**
	 * Log into SugarCRM as IGFRep user (level10 user17).
	 * 
	 * @throws Exception
	 */
	public void login_IGFRep() throws Exception {
		loginScreen.loginActions(3);
	}
	
	/**
	 * Log into SugarCRM.
	 * 
	 * @param userName data to be used in login.
	 * @param password data to be used in login.
	 * @throws Exception
	 */
	public void login(String userName, String password) throws Exception {
		loginScreen.login(userName, password);
	}
	
	/**
	 * Log out of SugarCRM.
	 * @throws Exception
	 */
	public void logout() throws Exception {
		navbar.selectUserAction("logout");
	}
}
