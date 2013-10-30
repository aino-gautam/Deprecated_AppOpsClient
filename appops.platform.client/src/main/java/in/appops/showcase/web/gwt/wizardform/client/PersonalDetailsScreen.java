package in.appops.showcase.web.gwt.wizardform.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.fields.Field;
import in.appops.client.touch.Screen;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

public class PersonalDetailsScreen extends Composite implements Screen{

	private VerticalPanel vp;
	private ArrayList<Field> fieldMap;
	private Entity entity;
	private Configuration configuration;
	private HashMap<String, Field> nameVsFieldHashMap;
	private TextField tbFName ;
	private TextField tbLName;
	
	public PersonalDetailsScreen(){
		vp = new VerticalPanel();
		//fieldMap = new ArrayList<Field>();
		nameVsFieldHashMap = new HashMap<String, Field>();
		initWidget(vp);
	}
	
	@Override
	public Widget asWidget() {
		return this.getParent();
	}

	@Override
	public void createScreen() {
		tbFName = new TextField();
		tbFName.setFieldValue("First Name");
		tbFName.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, null, null, null,"First Name"));
		
		tbLName = new TextField();
		tbLName.setFieldValue("Last Name");
		tbLName.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, null, null, null,"Last Name"));
		
		tbFName.create();
		tbLName.create();
		
		
		FlexTable flex = new FlexTable();
		flex.setWidget(0, 0, tbFName);
		flex.setWidget(2, 0, tbLName);
		
		vp.add(flex);
		//nameVsFieldHashMap.put(tbFName.getConfiguration().getPropertyByName(TextFieldConstant.PROPERTY_BY_FIELD_NAME).toString(), tbFName);
		//nameVsFieldHashMap.put(tbLName.getConfiguration().getPropertyByName(TextFieldConstant.PROPERTY_BY_FIELD_NAME).toString(), tbLName);
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public Entity populateEntity() {
		if(entity== null)
			entity = new Entity();
/*		Property<Serializable> prop = new Property<Serializable>();
		prop.setName(tbFName.getConfiguration().getPropertyByName(TextFieldConstant.PROPERTY_BY_FIELD_NAME).toString());
		//prop.setValue(tbFName.getValue());
		entity.setProperty(tbFName.getConfiguration().getPropertyByName(TextFieldConstant.PROPERTY_BY_FIELD_NAME).toString(), prop);
		
		Property<Serializable> prop1 = new Property<Serializable>();
		prop1.setName(tbLName.getConfiguration().getPropertyByName(TextFieldConstant.PROPERTY_BY_FIELD_NAME).toString());
		//prop1.setValue(tbLName.getValue());
		entity.setProperty(tbLName.getConfiguration().getPropertyByName(TextFieldConstant.PROPERTY_BY_FIELD_NAME).toString(), prop1);
*/		
		return entity;
	}
	

	/**
	 * creates the configuration object for a {@link}TextField
	 * @param visibleLines int - the number of visibles lines ( 1 if textbox / passwordtextbox. For textarea > 1)
	 * @param readOnly boolean - true / false
	 * @param textFieldType - type of the textField ( textbox / passwordtextbox / textarea )
	 * @param primaryCss - String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss - (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId - (optional) String the debug id for the {@link}TextField
	 * @param propertyByName 
	 * @return
	 */
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId, String propertyByName){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		//configuration.setPropertyByName(TextFieldConstant.TF_DEBUGID, debugId);
		configuration.setPropertyByName(TextFieldConstant.PROPERTY_BY_FIELD_NAME, propertyByName);
		return configuration;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}
}
