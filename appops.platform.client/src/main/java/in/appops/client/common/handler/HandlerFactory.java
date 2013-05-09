package in.appops.client.common.handler;

public interface HandlerFactory {
	ResponseActionHandler getActionHandlerByName(String actionName);
}
