package in.appops.client.common.core;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;

public class AppOpsView extends Composite implements IAppOpsView{

	/*private AppopsPresenter<IAppOpsView, AppOpsEventBus> presenter;*/
	private AppopsPresenter presenter;
	
	@Override
	public void createView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBody(IsWidget body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenter(AppopsPresenter presenter) {
		this.presenter = presenter;
		
	}

	@Override
	public AppopsPresenter getPresenter() {
		return this.presenter;
	}
}
