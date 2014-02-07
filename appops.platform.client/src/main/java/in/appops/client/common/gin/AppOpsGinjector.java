package in.appops.client.common.gin;

import in.appops.client.common.config.dsnip.DynamicMVPFactory;
import in.appops.client.common.snippet.SnippetFactory;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(AppOpsModule.class)
public interface AppOpsGinjector extends Ginjector {
	public SnippetFactory getSnippetFactory();
	public DynamicMVPFactory getMVPFactory();
	public SimpleEventBus getLocalEventBus();
}
