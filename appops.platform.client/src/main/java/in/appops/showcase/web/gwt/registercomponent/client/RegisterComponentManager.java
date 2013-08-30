/**
 * 
 */
package in.appops.showcase.web.gwt.registercomponent.client;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahes@ensarm.com
 *
 */
public class RegisterComponentManager extends Composite{

	private final String SELECTSCHEMALBLCSS = "registerCompHeaderLbl";
	private VerticalPanel basePanel;
	private RegisterComponentForm regCompForm;
	private RegisterComponentLister regCompList;
	private ConfigurationEditor configEditor;
	
	public RegisterComponentManager(){
		initialize();
	}

	public void createUi() {
		try{
			basePanel.setWidth("100%");
			
			LabelField headerLbl = new LabelField();
			Configuration headerLblConfig = getHeaderLblConfig();
		
			headerLbl.setConfiguration(headerLblConfig);
			headerLbl.configure();
			headerLbl.create();
			
			HorizontalPanel formListHolder = new HorizontalPanel();
			formListHolder.setWidth("100%");
			
			formListHolder.add(regCompForm);
			formListHolder.add(regCompList);
			
			formListHolder.setCellHorizontalAlignment(regCompForm, HorizontalPanel.ALIGN_LEFT);
			formListHolder.setCellHorizontalAlignment(regCompForm, HorizontalPanel.ALIGN_LEFT);

			configEditor.createEditor();
			regCompForm.createUi();
		//	regCompList.createUi();
			
			basePanel.add(headerLbl);
			basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_CENTER);

			basePanel.add(formListHolder);
			basePanel.add(configEditor);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}

	private Configuration getHeaderLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Select schema: ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, SELECTSCHEMALBLCSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	private void initialize() {
		try{
			basePanel = new VerticalPanel();
			regCompForm = new RegisterComponentForm();
			regCompList = new RegisterComponentLister();
			configEditor = new ConfigurationEditor();
			initWidget(basePanel);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}
}
