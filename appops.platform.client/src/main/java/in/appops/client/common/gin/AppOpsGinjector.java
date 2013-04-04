package in.appops.client.common.gin;

import in.appops.client.common.snippet.SnippetFactory;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Singleton;

@GinModules(AppOpsModule.class)
@Singleton
public interface AppOpsGinjector extends Ginjector {
	public SnippetFactory getSnippetFactory();

}
