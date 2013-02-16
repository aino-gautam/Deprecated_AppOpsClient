package in.appops.showcase.web.gwt.hellopojo.server.featurebundle.feature;

import in.appops.platform.core.feature.BaseFeature;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.AppOpsException;

public class HelloPostSyncFeature extends BaseFeature {
	
	@Override
	public Result execute() throws AppOpsException {
		System.out.println("Executed " +this.getClass().getSimpleName());
		//System.out.println("First post / sync / hook executed.. :) ");
		return null;
	}
	
}
