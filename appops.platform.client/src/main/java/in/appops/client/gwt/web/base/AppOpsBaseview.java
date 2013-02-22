package in.appops.client.gwt.web.base;

import in.appops.client.common.core.AppOpsView;
import in.appops.client.common.core.AppopsPresenter;
import in.appops.client.common.gin.AppOpsGinjector;

import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;

public class AppOpsBaseview extends Composite implements AppOpsView, IAppOpsBaseView{

	private AppOpsGinjector ginjector;
	
	@Inject
	public AppOpsBaseview(AppOpsGinjector ginjector){
		this.ginjector = ginjector;
		
	}
	
	public void createView(){
		
	}
	
	@Override
	public void setPresenter(AppopsPresenter presenter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AppopsPresenter getPresenter() {
		// TODO Auto-generated method stub
		return null;
	}

	public AppOpsGinjector getGinjector() {
		return ginjector;
	}

	public void setGinjector(AppOpsGinjector ginjector) {
		this.ginjector = ginjector;
	}

}
