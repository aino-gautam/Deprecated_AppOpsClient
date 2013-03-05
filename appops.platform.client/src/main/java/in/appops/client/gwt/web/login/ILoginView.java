package in.appops.client.gwt.web.login;


import com.google.gwt.user.client.ui.IsWidget;

public interface ILoginView extends IsWidget {

	public interface ILoginPresenter {
		void loginUser();
	}

	void setPresenter(LoginPresenter presenter);

	LoginPresenter getPresenter();
}
