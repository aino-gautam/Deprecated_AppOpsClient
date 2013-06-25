package in.appops.showcase.web.gwt.wizardform.client;

import in.appops.client.common.fields.Field;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.client.touch.Screen;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class ContactDetailsScreen extends Composite implements Screen {
	private FlexTable flex;
	private ArrayList<Field> fieldMap;
	private Entity entity;
	private Configuration configuration;
	
	public ContactDetailsScreen(){
		flex = new FlexTable();
		fieldMap = new ArrayList<Field>();
		initWidget(flex);
	}
	
	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createScreen() {
		TextField tbAddress1 = new TextField();
		tbAddress1.setConfiguration(getTextFieldConfiguration(3, false, TextFieldConstant.TFTTYPE_TXTAREA, null, null, null));
		tbAddress1.setFieldValue("Address");
		
		TextField tbPhoneNo = new TextField();
		tbPhoneNo.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, null, null, null));
		tbPhoneNo.setFieldValue("Last Name");
		
		tbAddress1.create();
		tbPhoneNo.create();
		

		//fieldMap.add(tbAddress1);
		//fieldMap.add(tbPhoneNo);
		
		flex.setWidget(0, 0, tbAddress1);
		flex.setWidget(0, 2, tbPhoneNo);
		
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
	 * @return
	 */
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		//configuration.setPropertyByName(TextFieldConstant.TF_DEBUGID, debugId);
		return configuration;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}
}
