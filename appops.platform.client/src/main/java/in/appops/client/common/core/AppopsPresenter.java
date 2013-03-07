package in.appops.client.common.core;

import in.appops.client.common.core.IAppOpsView.IAppOpsPresenter;
import in.appops.client.common.event.AppOpsEventBus;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/*@Presenter(view = AppOpsView.class)
public class AppopsPresenter<I, E extends AppOpsEventBus> extends BasePresenter<I, E> implements IAppOpsPresenter{

}
*/

@Presenter(view = AppOpsView.class)
public class AppopsPresenter extends BasePresenter<IAppOpsView, AppOpsEventBus> implements IAppOpsPresenter{

}
