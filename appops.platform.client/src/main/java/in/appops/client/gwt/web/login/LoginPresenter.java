package in.appops.client.gwt.web.login;

import in.appops.client.common.event.AppOpsEventBus;
import in.appops.client.gwt.web.login.ILoginView.ILoginPresenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = LoginView.class)
public class LoginPresenter extends BasePresenter<ILoginView, AppOpsEventBus> implements ILoginPresenter {

	@Override
	public void loginUser() {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void bind() {
		//view.setPresenter(this);
	}*/
}
