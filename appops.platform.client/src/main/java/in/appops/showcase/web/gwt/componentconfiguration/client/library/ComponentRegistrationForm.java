/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;


import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ComponentRegistrationForm extends Composite implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private TextField nameTf ;
	private TextField descTf ;
	private TextField typeTf ;
	private ButtonField saveComponentBtnFld;
	private Entity libraryEntity;
	private Logger logger = Logger.getLogger("ComponentRegistrationForm");
		
	/** CSS styles **/
	private final String FORMLBL_CSS = "compRegisterLabelCss";
	private final String SAVECOMP_BTN_PCLS = "saveCompBtnCss";
	private final String COMPHEADER_LBL_CSS = "registerCompHeaderLbl";
	private final String COMPFORM_PANEL_CSS = "componentFormPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	
	/** Field ID **/
	private static String SAVECOMPONENT_BTN_ID = "saveCompBtnId";
	
	public ComponentRegistrationForm(){
		initialize();
	}

	public void createUi() {
		try{
					
			FlexTable containerTable = new FlexTable();
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
			
			saveComponentBtnFld = new ButtonField();
			Configuration savebTnConfig = getSaveBtnConfig();
			saveComponentBtnFld.setConfiguration(savebTnConfig);
			saveComponentBtnFld.configure();
			saveComponentBtnFld.create();
			
			containerTable.setWidget(0, 0, fieldNameLbl);
			containerTable.setWidget(0, 1, nameTf);

			containerTable.setWidget(1, 0, descLbl);
			containerTable.setWidget(1, 1, descTf);

			containerTable.setWidget(2, 0, typeLbl);
			containerTable.setWidget(2, 1, typeTf);
			
			containerTable.setWidget(3, 1, saveComponentBtnFld);
			
			LabelField headerLbl = new LabelField();
			Configuration headerLblConfig = getHeaderLblConfig();
		
			headerLbl.setConfiguration(headerLblConfig);
			headerLbl.configure();
			headerLbl.create();
			
			basePanel.add(headerLbl);
			basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_CENTER);
			
			basePanel.add(containerTable);
			basePanel.addStyleName(COMPFORM_PANEL_CSS);
		}
		catch (Exception e) {	
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: createUi :: Exception", e);
		}
	}
	
	private Configuration getHeaderLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Component Form ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getHeaderLblConfig :: Exception", e);
		}
		return configuration;
	}

	private Configuration getSaveBtnConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Save component");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVECOMP_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVECOMPONENT_BTN_ID);

		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getSaveBtnConfig :: Exception", e);
		}
		return configuration;
	}

	private Configuration getNameTfConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, COMPHEADER_LBL_CSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(ButtonFieldConstant.BF_BINDPROP, "name");

		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getNameTfConfig :: Exception", e);
		}
		return configuration;
	}

	private Configuration getDescTfConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, COMPHEADER_LBL_CSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(ButtonFieldConstant.BF_BINDPROP, "description");
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getDescTfConfig :: Exception", e);
		}
		return configuration;
	}
	
	private Configuration getTypeTfConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, COMPHEADER_LBL_CSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(ButtonFieldConstant.BF_BINDPROP, "type");
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getTypeTfConfig :: Exception", e);
		}
		return configuration;
	}
	
	private Configuration getNameLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Component name: ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, FORMLBL_CSS);
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getNameLblConfig :: Exception", e);
		}
		return configuration;
	}

	private Configuration getDescNameLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Description: ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, FORMLBL_CSS);
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getDescNameLblConfig :: Exception", e);
		}
		return configuration;
	}
	
	private Configuration getTypeLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Type: ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, FORMLBL_CSS);
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getTypeLblConfig :: Exception", e);
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
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: initialize :: Exception", e);
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			if (event.getEventType() == FieldEvent.CLICKED) {
				if (event.getEventSource() instanceof ButtonField){
					ButtonField saveCompBtnField = (ButtonField) eventSource;
					if (saveCompBtnField.getBaseFieldId().equals(SAVECOMPONENT_BTN_ID)) {
						if(libraryEntity!=null){
							saveComponent();
						}else{
							Window.alert("Please select a linbrary");
						}
					}
				}
			}else if(eventType == FieldEvent.VALUECHANGED){
				if(eventSource instanceof ListBoxField){
					ListBoxField listBoxField = (ListBoxField) eventSource;
					if(listBoxField.getBaseFieldId().equalsIgnoreCase(LibraryComponentManager.LIBRARYLISTBOX_ID)){
						SelectedItem selectedItem = (SelectedItem) event.getEventData();
						Entity libEntity = selectedItem.getAssociatedEntity();
						libraryEntity = libEntity;
					}
				}
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: onFieldEvent :: Exception", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void saveComponent() {
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Entity entity = getPopulatedEntity();
			Map parameterMap = new HashMap();
			parameterMap.put("componentEnt", entity);
			parameterMap.put("update", false);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveComponentDefinition", parameterMap);
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
							ConfigEvent configEvent = new ConfigEvent(ConfigEvent.ADDCOMPONENTTOLIST, savedEntity,this);
							AppUtils.EVENT_BUS.fireEvent(configEvent);
						}
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: saveComponent :: Exception", e);
		}

	}

	private Entity getPopulatedEntity() {
		
		try{
			Entity compLibEntity = new Entity();
			compLibEntity.setType(new MetaType("Componentlibrary"));
			
			Entity compEntity = new Entity();
			compEntity.setType(new MetaType("Componentdefinition"));

			compEntity.setPropertyByName(nameTf.getBindProperty(), nameTf.getValue().toString());
			compEntity.setPropertyByName(typeTf.getBindProperty(), typeTf.getValue().toString());
			compEntity.setPropertyByName(descTf.getBindProperty(), descTf.getValue().toString());
			
			compLibEntity.setPropertyByName("library", getLibraryEntity());
			compLibEntity.setPropertyByName("componentdefinition", compEntity);
			return compLibEntity;
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getPopulatedEntity :: Exception", e);
		}
		return null;
		
	}

	public Entity getLibraryEntity() {
		return libraryEntity;
	}

	public void setLibraryEntity(Entity libraryEntity) {
		this.libraryEntity = libraryEntity;
	}

}
