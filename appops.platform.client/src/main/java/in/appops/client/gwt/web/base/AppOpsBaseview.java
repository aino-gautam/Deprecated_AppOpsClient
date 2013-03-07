package in.appops.client.gwt.web.base;

import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.gwt.web.login.LoginView;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/*public class AppOpsBaseview extends AppOpsView implements IAppOpsBaseView{*/
public class AppOpsBaseview extends Composite implements IAppOpsBaseView{

	private AppOpsGinjector ginjector;
	private AbsolutePanel basePanel;
	private LoginView loginView;
	private Widget body;
	private AppOpsBasePresenter basePresenter;

	
	@Inject
	public AppOpsBaseview(AppOpsGinjector ginjector, LoginView loginView){
		this.ginjector = ginjector;
		basePanel = new AbsolutePanel();
		this.loginView = loginView;
		initWidget(basePanel);
	}
	
	public void createView(){
		basePanel.clear();
		loginView.createView();
		basePanel.add(loginView);
	}
	
	public void setBody(IsWidget body) {
		this.body = (Widget)body;
		basePanel.clear();
		basePanel.add(this.body);
	}
	
	@Override
	public void setPresenter(AppOpsBasePresenter presenter) {
		this.basePresenter = (AppOpsBasePresenter) presenter;
	}

	@Override
	public AppOpsBasePresenter getPresenter() {
		return this.basePresenter;
	}

	public AppOpsGinjector getGinjector() {
		return ginjector;
	}

	public void setGinjector(AppOpsGinjector ginjector) {
		this.ginjector = ginjector;
	}
}
