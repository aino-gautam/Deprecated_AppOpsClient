package in.appops.client.common.gin;

import in.appops.client.common.config.dsnip.DynamicMvpFactory;
import in.appops.client.common.config.dsnip.DynamicMvpFactoryImpl;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.client.common.snippet.SnippetFactoryImpl;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;

public class AppOpsModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SnippetFactory.class).to(SnippetFactoryImpl.class);
		bind(DynamicMvpFactory.class).to(DynamicMvpFactoryImpl.class);
		bind(SimpleEventBus.class);
	}

}
