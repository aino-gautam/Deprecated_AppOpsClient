package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.fields.CheckboxField;
import in.appops.client.common.fields.CheckboxGroupField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LinkField;
import in.appops.client.common.fields.StateField;
import in.appops.client.common.fields.TextField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;

public class FieldsShowCase implements EntryPoint {

	private FlexTable flex = new FlexTable();
	
	@Override
	public void onModuleLoad() {
		LabelField labelFieldTB = new LabelField();
		labelFieldTB.setFieldValue("Text Box");
		labelFieldTB.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
		
		TextField textFieldTB = new TextField();
		textFieldTB.setFieldValue("");
		textFieldTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "appops-TextField", null, null));
		
		LabelField labelFieldPTB = new LabelField();
		labelFieldPTB.setFieldValue("Password Textbox");
		labelFieldPTB.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
		
		TextField textFieldPTB = new TextField();
		textFieldPTB.setFieldValue("Password");
		textFieldPTB.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_PASSWORDTEXTBOX, "appops-TextField", null, null));
		
		LabelField labelFieldTA = new LabelField();
		labelFieldTA.setFieldValue("Text Area");
		labelFieldTA.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
		
		TextField textFieldTA = new TextField();
		textFieldTA.setFieldValue("");
		textFieldTA.setConfiguration(getTextFieldConfiguration(10, false, TextField.TEXTFIELDTYPE_TEXTAREA, "appops-TextField", null, null));
		
		LinkField hyperlink = new LinkField();
		hyperlink.setFieldValue("Hyperlink");
		hyperlink.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_HYPERLINK, "appops-LinkField", null, null));
		
		LinkField anchor = new LinkField();
		anchor.setFieldValue("Anchor");
		anchor.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_HYPERLINK, "appops-LinkField", null, null));
		
		StateField stateField = new StateField();
		//stateField.setFieldValue("Suggestion");
		stateField.setConfiguration(getStateFieldConfiguration(StateField.STATEFIELDMODE_SUGGESTIVE, "getAllSpaceTypes", "spacemanagement.SpaceManagementService.getEntityList"));
		
		CheckboxField checkboxfield = new CheckboxField();
		Configuration config = getCheckboxFieldConfiguration("Allow permissions");
		checkboxfield.setFieldValue("true");
		checkboxfield.setConfiguration(config);
		
		CheckboxGroupField checkboxGroupField = new CheckboxGroupField();
		Configuration configuration = getCheckboxGroupFieldConfiguration(CheckboxGroupField.CHECKBOX_MULTISELECT,CheckboxGroupField.CHECKBOX_VERTICALBASEPANEL);
		checkboxGroupField.setConfiguration(configuration);
		
		try {
			labelFieldTB.createField();
			textFieldTB.createField();
			
			labelFieldPTB.createField();
			textFieldPTB.createField();
			
			labelFieldTA.createField();
			textFieldTA.createField();
			
			hyperlink.createField();
			anchor.createField();
			
			stateField.createField();
			
			checkboxfield.createField();
			
			checkboxGroupField.createField();
			
			
		} catch (AppOpsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		checkboxGroupField.addCheckItem("Red");
		checkboxGroupField.addCheckItem("Green");
		checkboxGroupField.addCheckItem("Blue");
		
		flex.setWidget(0, 0, labelFieldTB);
		flex.setWidget(0, 1, textFieldTB);
		
		flex.setWidget(1, 0, labelFieldPTB);
		flex.setWidget(1, 1, textFieldPTB);
		
		flex.setWidget(2, 0, labelFieldTA);
		flex.setWidget(2, 1, textFieldTA);
		
		flex.setWidget(3, 0, hyperlink);
		flex.setWidget(3, 1, anchor);
		
		flex.setWidget(4, 0, checkboxGroupField);
		
		flex.setWidget(5, 0, checkboxfield);
		
		flex.setWidget(6, 0, stateField);
		
		RootPanel.get().add(flex);
		
	}
	
	/**
	 * creates the configuration object for a {@link}LabelField
	 * @param allowWordWrap boolean true / false
	 * @param primaryCss String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId (optional) String the debug id for the {@link}LabelField
	 * @return
	 */
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	/**
	 * creates the configuration object for a {@link}TextField
	 * @param visibleLines int - the number of visibles lines ( 1 if textbox / passwordtextbox. For textarea > 1)
	 * @param readOnly boolean - true / false
	 * @param textFieldType - type of the textField ( textbox / passwordtextbox / textarea )
	 * @param primaryCss - String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss - (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId - (optional) String the debug id for the {@link}TextField
	 * @return
	 */
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextField.TEXTFIELD_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextField.TEXTFIELD_READONLY, readOnly);
		configuration.setPropertyByName(TextField.TEXTFIELD_TYPE, textFieldType);
		configuration.setPropertyByName(TextField.TEXTFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	private Configuration getLinkFieldConfiguration(String linkFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkField.LINKFIELD_TYPE, linkFieldType);
		configuration.setPropertyByName(LinkField.LINKFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	private Configuration getStateFieldConfiguration(String stateFieldType, String qname, String operationName) {

		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, stateFieldType);
		configuration.setPropertyByName(StateField.STATEFIELD_QUERY, qname);
		configuration.setPropertyByName(StateField.STATEFIELD_OPERATION, operationName);
		return configuration;
	}
	
	public Configuration getCheckboxFieldConfiguration(String text) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckboxField.CHECKBOXFIELD_DISPLAYTEXT, text);
		return configuration;
	}
	
	private Configuration getCheckboxGroupFieldConfiguration(String selectMode, String basePanel) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckboxGroupField.CHECKBOX_SELECT_MODE, selectMode);
		configuration.setPropertyByName(CheckboxGroupField.CHECKBOX_BASEPANEL, basePanel);
		return configuration;
	}
}
