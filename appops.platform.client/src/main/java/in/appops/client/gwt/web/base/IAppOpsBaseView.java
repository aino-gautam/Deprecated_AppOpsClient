package in.appops.client.gwt.web.base;

import com.google.gwt.user.client.ui.IsWidget;

/*public interface IAppOpsBaseView extends IAppOpsView{
	
	public interface IAppOpsBasePresenter extends IAppOpsPresenter{
		
	}

}
*/

public interface IAppOpsBaseView extends IsWidget{
	
	public interface IAppOpsBasePresenter {
		
	}

	void setPresenter(AppOpsBasePresenter presenter);

	AppOpsBasePresenter getPresenter();

	void createView();

	void setBody(IsWidget body);

}
