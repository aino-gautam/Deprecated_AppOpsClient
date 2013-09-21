package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.util.ArrayList;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author pallavi@ensarm.com
 *
 */
public class ConfigurationEditor extends Composite{
	
	private VerticalPanel basePanel;
	private ConfPropertyEditor confPropertyEditor;
	private ConfigurationListDisplayer configurationListDisplayer;
	
	
	private static String BASEPANEL_CSS = "confEditorPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	
	public ConfigurationEditor(Widget parentContainer) {
		
		basePanel = new VerticalPanel();
		confPropertyEditor = new ConfPropertyEditor(parentContainer);
		configurationListDisplayer = new ConfigurationListDisplayer();
		initWidget(basePanel);
	}
	
	public void deregisterHandler() {
		try {
			if(confPropertyEditor !=null)
				confPropertyEditor.deregisterHandler();
			if(configurationListDisplayer !=null)
				configurationListDisplayer.deregisterHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method is called when you have the entire configType entity with its children configtype properties 
	 * @param configTypeEntity
	 */
	public void createUi(Entity configTypeEntity, ArrayList<Entity> configList){
		if(basePanel!=null)
			basePanel.clear();
		
		basePanel.setStylePrimaryName(BASEPANEL_CSS);
		
		LabelField headerLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig(null);
	
		headerLbl.setConfiguration(headerLblConfig);
		headerLbl.configure();
		headerLbl.create();
		
		confPropertyEditor.setParentConfTypeEnt(configTypeEntity);
		confPropertyEditor.createUi();
		
		configurationListDisplayer.initialize();
		configurationListDisplayer.createUi(configList);
		
		basePanel.add(headerLbl);
		basePanel.add(confPropertyEditor);
		basePanel.add(configurationListDisplayer);
		
		basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_LEFT);
		basePanel.setCellHorizontalAlignment(configurationListDisplayer, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	public void createUi(Entity compDef, Entity configType){
		
		if(basePanel!=null)
			basePanel.clear();
		
		basePanel.setStylePrimaryName(BASEPANEL_CSS);
		
		LabelField headerLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig(compDef);
	
		headerLbl.setConfiguration(headerLblConfig);
		headerLbl.configure();
		headerLbl.create();
		
		confPropertyEditor.setParentConfTypeEnt(configType);
		confPropertyEditor.createUi();
		
		
		configurationListDisplayer.initialize();
		configurationListDisplayer.getConfigTypeList(configType);
		
		basePanel.add(headerLbl);
		basePanel.add(confPropertyEditor);
		basePanel.add(configurationListDisplayer);
		
		basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_LEFT);
		basePanel.setCellHorizontalAlignment(configurationListDisplayer, HasHorizontalAlignment.ALIGN_CENTER);
		
	}
	
	private Configuration getHeaderLblConfig(Entity componentEntity) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			if(componentEntity != null)
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Add configuration::"+ componentEntity.getPropertyByName("name").toString());
			else
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Add configuration:");
			
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}


}