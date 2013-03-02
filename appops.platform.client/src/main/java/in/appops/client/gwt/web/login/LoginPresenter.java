package in.appops.client.gwt.web.login;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;

import in.appops.client.common.core.AppopsPresenter;
import in.appops.client.common.event.AppOpsEventBus;
import in.appops.client.gwt.web.login.ILoginView.ILoginPresenter;

@Presenter(view = LoginView.class, multiple = true)
public class LoginPresenter extends AppopsPresenter<LoginView, AppOpsEventBus> implements ILoginPresenter{

	@Override
	public void loginUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bind() {
		view.setPresenter(this);
	}
	
	@Override
	public void setView(LoginView view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LoginView getView() {
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
	
}
