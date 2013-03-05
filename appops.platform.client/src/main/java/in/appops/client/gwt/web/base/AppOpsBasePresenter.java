package in.appops.client.gwt.web.base;

import in.appops.client.common.event.AppOpsEventBus;
import in.appops.client.gwt.web.base.IAppOpsBaseView.IAppOpsBasePresenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = AppOpsBaseview.class)
/*public class AppOpsBasePresenter extends AppopsPresenter<IAppOpsBaseView, AppOpsEventBus> implements IAppOpsBasePresenter{*/
public class AppOpsBasePresenter extends BasePresenter<IAppOpsBaseView, AppOpsEventBus> implements IAppOpsBasePresenter{

	public void onStart(){
		view.setPresenter(this);
		view.createView();
	}
	
	public void onInit() {
		
	}

	/*@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}*/
	
	public void onSetBody( IsWidget body ) {
		view.setBody( body );
	}
	
}
