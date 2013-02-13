package in.appops.showcase.web.gwt.hellopojo.server.featurebundle.feature;

import in.appops.platform.core.feature.BaseFeature;
import in.appops.platform.core.operation.Result;
import in.appops.platform.server.core.IsOperationHook;


@IsOperationHook(isAsync = true)
public class HelloHook extends BaseFeature {

	@Override
	public Result execute() {
		System.out.println("Executed " +this.getClass().getSimpleName());
		//System.out.println("Second post / async / hook executed.. :) ");
		return null;
	}

}
