/**
 * 
 */
package in.appops.showcase.web.gwt.registercomponent.client;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;

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
	private ConfigurationEditor configEditor;
	
	private final String FORMLIST_HOLDER_CSS = "formListHolder";
	private final String BASEPANEL_CSS = "componentManager";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	private final String LIBPANEL_CSS = "libraryPanel";
	
	public RegisterComponentManager(){
		initialize();
	}

	public void createUi() {
		try{
			basePanel.setStylePrimaryName(BASEPANEL_CSS);
			
			HorizontalPanel formListHolder = new HorizontalPanel();
			regCompForm.createUi();
			regCompList.createUi();
			
			formListHolder.add(regCompForm);
			formListHolder.add(regCompList);
			
			formListHolder.setCellWidth(regCompForm, "55%");
			formListHolder.setCellWidth(regCompList, "40%");
			
			formListHolder.setCellHorizontalAlignment(regCompForm, HorizontalPanel.ALIGN_CENTER);
			//formListHolder.setCellHorizontalAlignment(regCompList, HorizontalPanel.ALIGN_LEFT);
			
			HorizontalPanel libraryPanel = new HorizontalPanel();
			LabelField libHeaderLbl = new LabelField();
			Configuration headerLblConfig = getHeaderLblConfig();
		
			libHeaderLbl.setConfiguration(headerLblConfig);
			libHeaderLbl.configure();
			libHeaderLbl.create();
			
			ListBoxField libraryBox = new ListBoxField();
			libraryBox.setConfiguration(getLibraryListBoxConfiguration());
			libraryBox.configure();
			libraryBox.create();
			
			libraryPanel.add(libHeaderLbl);
			libraryPanel.add(libraryBox);
			
			basePanel.add(libraryPanel);
			basePanel.add(formListHolder);
			formListHolder.setStylePrimaryName(FORMLIST_HOLDER_CSS);
			libraryPanel.setStylePrimaryName(LIBPANEL_CSS);
			
			configEditor.createEditor();
			basePanel.add(configEditor);
			
			basePanel.setCellHorizontalAlignment(configEditor, HorizontalPanel.ALIGN_CENTER);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	private Configuration getLibraryListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			
			/*configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"spacemanagement.SpaceManagementService.getEntityList");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllSpaces");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERY_MAXRESULT,10);*/
			
			ArrayList<String> items = new ArrayList<String>();
			items.add("library1");
			items.add("library2");
			items.add("library3");
			
			
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"library1");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			
		} catch (Exception e) {
			
		}
		
		return configuration;
	}

	private Configuration getHeaderLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Select Library: ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
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
