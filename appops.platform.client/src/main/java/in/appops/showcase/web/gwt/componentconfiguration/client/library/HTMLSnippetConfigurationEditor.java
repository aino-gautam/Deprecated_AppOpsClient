package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class will let one add / edit htmlsnippet properties.
 * The html snippet is a MVP, hence it has 3 top level configuration viz. model, view, presenter.
 * Each of this is represented by one tab, where specific configurations are added, edited and displayed.
 * @author nitish@ensarm.com
 */
public class HTMLSnippetConfigurationEditor {
	private TabPanel configuratorBasePanel;
	private VerticalPanel configuratorEditorPanel;
	private Entity modelConfigurationType;
	private Entity viewConfigurationType;
	private Entity presenterConfigurationType;
	
	private final String MODEL = "model";
	private final String VIEW = "view";
	private final String PRESENTER = "presenter";
	
	public void initialize() {
		configuratorBasePanel = new TabPanel();
		configuratorEditorPanel = new VerticalPanel();
	}
	
	public void createUi() {
		// TODO Create instances of Property Editor for model, view and presenter
		
		// Create each editor and add one in each tab.
		configuratorBasePanel.add(getModelEditor(), MODEL);
		configuratorBasePanel.add(null, VIEW);
		configuratorBasePanel.add(null, PRESENTER);
	}
	
	private Widget getModelEditor() {
		VerticalPanel modelEditorPanel = new VerticalPanel();
		
		TextField valueField = new TextField();
		valueField.setConfiguration(getStringValueFieldConf());
		valueField.configure();
		valueField.create();
		
		LabelField nameField = new LabelField();
		nameField.setConfiguration(getLabelFieldConf(""));
		nameField.configure();
		nameField.create();

		return modelEditorPanel;
	}
	
	private Widget getViewEditor() {
		VerticalPanel viewEditorPanel = new VerticalPanel();
		return viewEditorPanel;
	}
	
	private Widget getPresenterEditor() {
		VerticalPanel presenterEditorPanel = new VerticalPanel();
		return presenterEditorPanel;
	}

	public void setModelConfigurationType(Entity modelConfigurationType) {
		this.modelConfigurationType = modelConfigurationType;
	}

	public void setViewConfigurationType(Entity viewConfigurationType) {
		this.viewConfigurationType = viewConfigurationType;
	}
	
	private Configuration getStringValueFieldConf() {
		final String STRINGVAL_FIELD_ID = "stringValFieldId";
		Configuration configuration = null;	

		try {
			configuration = new Configuration();

			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Add string value");
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, STRINGVAL_FIELD_ID);
			
		} catch (Exception e) {

		}
		return configuration;
	}

	private Configuration getLabelFieldConf(String displayText) {
		final String COMPLISTLBL_CSS = "compRegisterLabelCss";

		Configuration configuration = null;	
		try{
			configuration = new Configuration();

			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, COMPLISTLBL_CSS);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	

}
