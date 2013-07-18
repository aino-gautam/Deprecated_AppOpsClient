package in.appops.client.common.gin;

import in.appops.client.common.config.dsnip.ComponentFactory;
import in.appops.client.common.config.dsnip.ComponentFactoryImpl;
import in.appops.client.common.config.dsnip.SnippetGenerator;
import in.appops.client.common.config.dsnip.SnippetGeneratorImpl;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.client.common.snippet.SnippetFactoryImpl;

import com.google.gwt.inject.client.AbstractGinModule;

public class AppOpsModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SnippetFactory.class).to(SnippetFactoryImpl.class);
		bind(ComponentFactory.class).to(ComponentFactoryImpl.class);
		bind(SnippetGenerator.class).to(SnippetGeneratorImpl.class);
	}

}
