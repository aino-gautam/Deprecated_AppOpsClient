package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pallavi@ensarm.com
 *
 */
public class ConfigurationEditor extends Composite{
	
	private VerticalPanel basePanel;
	private static String BASEPANEL_CSS = "confEditorPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	
	public ConfigurationEditor() {
		basePanel = new VerticalPanel();
		initWidget(basePanel);
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
		
		ConfPropertyEditor confPropertyEditor = new ConfPropertyEditor(configType);
		confPropertyEditor.createUi();
		
		basePanel.add(headerLbl);
		basePanel.add(confPropertyEditor);
				
		basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_LEFT);
		
		ConfigurationListDisplayer configurationListDisplayer = new ConfigurationListDisplayer();
		//configurationListDisplayer.getPropertyConfigList(compDef);
				
		basePanel.add(configurationListDisplayer);
		basePanel.setCellHorizontalAlignment(configurationListDisplayer, HasHorizontalAlignment.ALIGN_CENTER);
		
	}
	
	private Configuration getHeaderLblConfig(Entity componentEntity) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Add configuration::"+ componentEntity.getPropertyByName("name").toString());
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	

}
