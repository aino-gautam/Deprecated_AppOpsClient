package in.appops.client.common.event;

import in.appops.client.common.gin.AppOpsModule;
import in.appops.client.gwt.web.base.AppOpsBasePresenter;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBusWithLookup;

@Events(startPresenter = AppOpsBasePresenter.class, historyOnStart = true, ginModules = { AppOpsModule.class })
public interface AppOpsEventBus extends EventBusWithLookup{
	
	@Start
	@Event(handlers = { AppOpsBasePresenter.class })
	void start();

	@InitHistory
	@Event(handlers = AppOpsBasePresenter.class)
	void init();

}
