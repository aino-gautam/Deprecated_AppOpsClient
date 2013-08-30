/**
 * 
 */
package in.appops.showcase.web.gwt.registercomponent.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahes@ensarm.com
 *
 */
public class RegisterComponentManager extends Composite{

	private VerticalPanel basePanel;
	private RegisterComponentForm regCompForm;
	private RegisterComponentLister regCompList;
	
	
	public RegisterComponentManager(){
		initialize();
	}

	public void createUi() {
		try{
			basePanel.setWidth("100%");
			
			HorizontalPanel formListHolder = new HorizontalPanel();
			formListHolder.setWidth("100%");
			
			formListHolder.add(regCompForm);
			formListHolder.add(regCompList);
			
			basePanel.add(formListHolder);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}

	private void initialize() {
		try{
			basePanel = new VerticalPanel();
			regCompForm = new RegisterComponentForm();
			regCompList = new RegisterComponentLister();
			initWidget(basePanel);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}
}
