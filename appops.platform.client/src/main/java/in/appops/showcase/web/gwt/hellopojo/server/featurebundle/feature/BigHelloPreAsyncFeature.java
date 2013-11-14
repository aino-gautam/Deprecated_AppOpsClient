package in.appops.showcase.web.gwt.hellopojo.server.featurebundle.feature;

import com.google.inject.Injector;

import in.appops.platform.core.feature.BaseFeature;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.AppOpsException;

public class BigHelloPreAsyncFeature extends BaseFeature {
	
	public BigHelloPreAsyncFeature(Injector injector) {
		super(injector);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Result execute() throws AppOpsException {
		System.out.println("Executed " +this.getClass().getSimpleName());
		//System.out.println("First BIG pre / sync / hook executed.. :) ");
		return null;
	}
	
}
