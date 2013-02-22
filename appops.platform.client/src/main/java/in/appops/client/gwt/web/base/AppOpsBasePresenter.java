package in.appops.client.gwt.web.base;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;

import in.appops.client.common.core.AppopsPresenter;
import in.appops.client.common.event.AppOpsEventBus;
import in.appops.client.gwt.web.base.IAppOpsBaseView.IAppOpsBasePresenter;

@Presenter(view = AppOpsBaseview.class)
public class AppOpsBasePresenter extends AppopsPresenter<AppOpsBaseview, AppOpsEventBus> implements IAppOpsBasePresenter{

	@Override
	public void setView(AppOpsBaseview view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AppOpsBaseview getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EventBus getEventBus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventBus getTokenGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}
	
}
