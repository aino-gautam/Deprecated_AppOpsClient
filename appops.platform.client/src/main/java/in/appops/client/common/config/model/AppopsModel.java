package in.appops.client.common.config.model;

import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;

public interface AppopsModel extends Configurable {
	
	void setOperationName(String operationName);
	
	void setQueryName(String queryName);

//	void setQueryParamList(ArrayList<Configuration> queryParamList);
	
	void configure();

}
