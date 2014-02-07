package in.appops.client.common.gin;

import in.appops.client.common.config.dsnip.DynamicMVPFactory;
import in.appops.client.common.config.dsnip.DynamicMVPFactoryImpl;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.client.common.snippet.SnippetFactoryImpl;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;

public class AppOpsModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SnippetFactory.class).to(SnippetFactoryImpl.class);
		bind(DynamicMVPFactory.class).to(DynamicMVPFactoryImpl.class);
		bind(SimpleEventBus.class);
	}

}
