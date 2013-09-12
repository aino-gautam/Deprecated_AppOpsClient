/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pallavi@ensarm.com
 *
 */
public class ComponentManager extends Composite {
	
	private VerticalPanel basePanel;
	private ComponentRegistrationForm compRegForm;
	private ComponentListDisplayer compListDisplayer;
	private Logger logger = Logger.getLogger("ComponentManager");

	/**CSS styles **/
	private final String BASEPANEL_CSS = "componentManager";
	
	public ComponentManager(){
		
	}

	public void createUi() {
		try{
			basePanel = new VerticalPanel();
			basePanel.setStylePrimaryName(BASEPANEL_CSS);
			
			compRegForm = new ComponentRegistrationForm();
			compRegForm.createUi();
			
			compListDisplayer = new ComponentListDisplayer();
			compListDisplayer.createUi();
			
			basePanel.add(compRegForm);
			basePanel.add(compListDisplayer);
			
			initWidget(basePanel);
							
		}
		catch (Exception e) {	
			logger.log(Level.SEVERE, "ComponentManager :: createUi :: Exception", e);
		}
	}

}
