/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	public void deregisterHandler(){
		try {
			if(compRegForm !=null)
				compRegForm.deregisterHandler();
			if(compListDisplayer !=null)
				compListDisplayer.deregisterHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
