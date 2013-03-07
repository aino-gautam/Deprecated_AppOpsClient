package in.appops.client.common.core;


import com.google.gwt.user.client.ui.IsWidget;

/*public interface IAppOpsView<I> extends IsWidget{

	public void createView();
	
	public void setBody(IsWidget body);
	
	public void setPresenter(AppopsPresenter<I, AppOpsEventBus> presenter) ;
	
	public AppopsPresenter<I, AppOpsEventBus> getPresenter();
	
	
	public interface IAppOpsPresenter {
		
	}
}*/

public interface IAppOpsView extends IsWidget{

	public void createView();
	
	public void setBody(IsWidget body);
	
	public void setPresenter(AppopsPresenter presenter) ;
	
	public AppopsPresenter getPresenter();
	
	
	public interface IAppOpsPresenter {
		
	}
}
