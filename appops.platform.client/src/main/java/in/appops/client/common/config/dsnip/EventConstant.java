package in.appops.client.common.config.dsnip;

public interface EventConstant {
	String EVNT_TYPE = "type";
	String EVNT_TRANSTYPE = "transformType";

	int EVNT_TRANSWGT = 1; 
	int EVNT_EXECOP = 2;
	int EVNT_EXECQUERY = 3;
	
	int EVNT_SNIPPET = 1;
	int EVNT_COMPONENT = 2;
	
	String EVNT_TRANSINS = "transformInstance";
	String EVNT_TRANSTO = "transformTo";

	String EVNT_OPERATION = "operation";
	String EVNT_QUERY = "query";
	
	String EVNT_ENTITYTYPE = "entityType";
	String EVNT_AFTEREVENT = "afterEvent";

	String EVNT_NAME = "eventName";
	String EVNT_DATA = "eventData";
	String EVNT_PARAM = "eventParam";
	String EVNT_DATA_IDENTIFIER = "eventDataId";
}
