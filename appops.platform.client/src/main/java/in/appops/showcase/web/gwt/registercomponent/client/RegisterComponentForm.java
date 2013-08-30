/**
 * 
 */
package in.appops.showcase.web.gwt.registercomponent.client;


import java.util.HashMap;
import java.util.Map;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class RegisterComponentForm extends Composite implements FieldEventHandler{

	private final String PREVIEW_BTN_PCLS = "saveCompBtnCss";
	private VerticalPanel basePanel;
	private final String ENTERSCHEMANAMETBCSS = "registerCompHeaderLbl";
	private TextField nameTf ;
	private TextField descTf ;
	private TextField typeTf ;
	private ButtonField saveBtnFld;
	
	public RegisterComponentForm(){
		initialize();
	}

	public void createUi() {
		try{
			basePanel.setWidth("100%");
			
			FlexTable containerTable = new FlexTable();
			containerTable.setWidth("100%");
			containerTable.setBorderWidth(1);
			
			LabelField fieldNameLbl = new LabelField();
			Configuration fldNameConfig = getNameLblConfig();
		
			fieldNameLbl.setConfiguration(fldNameConfig);
			fieldNameLbl.configure();
			fieldNameLbl.create();
			
			LabelField descLbl = new LabelField();
			Configuration frndNameConfig = getDescNameLblConfig();
		
			descLbl.setConfiguration(frndNameConfig);
			descLbl.configure();
			descLbl.create();
			
			LabelField typeLbl = new LabelField();
			Configuration typeConfig = getTypeLblConfig();
		
			typeLbl.setConfiguration(typeConfig);
			typeLbl.configure();
			typeLbl.create();
			
			nameTf = new TextField();
			Configuration nameTfConfig = getNameTfConfig();
			nameTf.setConfiguration(nameTfConfig);
			nameTf.configure();
			nameTf.create();
			
			descTf = new TextField();
			Configuration descTfConfig = getDescTfConfig();
			descTf.setConfiguration(descTfConfig);
			descTf.configure();
			descTf.create();
			
			typeTf = new TextField();
			Configuration typeTfConfig = getTypeTfConfig();
			typeTf.setConfiguration(typeTfConfig);
			typeTf.configure();
			typeTf.create();
			
			saveBtnFld = new ButtonField();
			Configuration savebTnConfig = getSaveBtnConfig();
			saveBtnFld.setConfiguration(savebTnConfig);
			saveBtnFld.configure();
			saveBtnFld.create();
			
			containerTable.setWidget(0, 0, fieldNameLbl);
			containerTable.setWidget(0, 1, nameTf);

			containerTable.setWidget(1, 0, descLbl);
			containerTable.setWidget(1, 1, descTf);

			containerTable.setWidget(2, 0, typeLbl);
			containerTable.setWidget(2, 1, typeTf);
			
			containerTable.setWidget(3, 1, saveBtnFld);
			
			basePanel.add(containerTable);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}

	private Configuration getSaveBtnConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Save component");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,PREVIEW_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getNameTfConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, ENTERSCHEMANAMETBCSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(ButtonFieldConstant.BF_BINDPROP, "name");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getDescTfConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, ENTERSCHEMANAMETBCSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(ButtonFieldConstant.BF_BINDPROP, "description");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	private Configuration getTypeTfConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, ENTERSCHEMANAMETBCSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(ButtonFieldConstant.BF_BINDPROP, "type");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	private Configuration getNameLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Component name: ");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getDescNameLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Description: ");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	private Configuration getTypeLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Type: ");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	private void initialize() {
		try{
			basePanel = new VerticalPanel();
			initWidget(basePanel);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			if (event.getEventType() == FieldEvent.CLICKED) {
				if (event.getSource() instanceof ButtonField){
					saveComponent();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void saveComponent() {
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Entity entity = getPopulatedEntity();
						
			Map parameterMap = new HashMap();
			parameterMap.put("entity", entity);
			
			StandardAction action = new StandardAction(EntityList.class, "", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						Entity savedEntity   = result.getOperationResult();
						if(savedEntity!=null){
							Window.alert("Component Saved...");
						}
					}
				}
			});
		} catch (Exception e) {
		}

	}

	private Entity getPopulatedEntity() {
		Entity entity = null;
		try{
			entity = new Entity();
			entity.setType(new MetaType("Componentdefinition"));

			entity.setPropertyByName(nameTf.getBindProperty(), nameTf.getValue().toString());
			entity.setPropertyByName(typeTf.getBindProperty(), typeTf.getValue().toString());
			entity.setPropertyByName(descTf.getBindProperty(), descTf.getValue().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
}
