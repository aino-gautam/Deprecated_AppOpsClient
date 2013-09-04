/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;


import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.config.field.StateField;
import in.appops.client.common.config.field.StateField.StateFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ComponentRegistrationForm extends Composite implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private Entity libraryEntity;
	private StateField componentNameField;
	private ListBoxField componentTypeField;
	
	private Logger logger = Logger.getLogger("ComponentRegistrationForm");
		
	/** CSS styles **/
	private final String SAVECOMP_BTN_PCLS = "saveCompBtnCss";
	private final String COMPFORM_PANEL_CSS = "componentFormPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	
	/** Field ID **/
	private static String SAVECOMPONENT_BTN_ID = "saveCompBtnId";
	public final String COMPONENTTYPELISTBOX_ID = "componenttTypeListBoxId";
	
	public ComponentRegistrationForm(){
		initialize();
	}

	public void createUi() {
		try{
					
			VerticalPanel containerTable = new VerticalPanel();
			
			componentNameField = new StateField();
			Configuration stateFieldConfig = getComponentSuggestionFieldConf();
			componentNameField.setConfiguration(stateFieldConfig);
			componentNameField.configure();
			componentNameField.create();	
			
			componentTypeField = new ListBoxField();
			componentTypeField.setConfiguration(getTypeBoxConfig());
			componentTypeField.configure();
			componentTypeField.create();
					
			ButtonField saveComponentBtnFld = new ButtonField();
			Configuration savebTnConfig = getSaveBtnConfig();
			saveComponentBtnFld.setConfiguration(savebTnConfig);
			saveComponentBtnFld.configure();
			saveComponentBtnFld.create();
			
			containerTable.add(componentNameField);
			containerTable.add(componentTypeField);
			containerTable.add(saveComponentBtnFld);
						
			LabelField headerLbl = new LabelField();
			Configuration headerLblConfig = getHeaderLblConfig();
		
			headerLbl.setConfiguration(headerLblConfig);
			headerLbl.configure();
			headerLbl.create();
			
			basePanel.add(headerLbl);
			basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_CENTER);
			basePanel.add(containerTable);
			containerTable.addStyleName(COMPFORM_PANEL_CSS);
			
		}
		catch (Exception e) {	
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: createUi :: Exception", e);
		}
	}
	
	private Configuration getTypeBoxConfig() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"---Select the type of component---");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,getDummyTypeList());
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,COMPONENTTYPELISTBOX_ID);
			
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	private EntityList getDummyTypeList(){
		Entity labelField = new Entity();
		Key<Long> key1 = new Key<Long>(2L);
		labelField.setPropertyByName("id",key1);
		labelField.setPropertyByName("name","LabelField");
		labelField.setPropertyByName("htmldescription","   Display text");
		
		Entity dateLabelField = new Entity();
		Key<Long> key2 = new Key<Long>(2L);
		dateLabelField.setPropertyByName("id",key2);
		dateLabelField.setPropertyByName("name","DateLabelField");
		dateLabelField.setPropertyByName("htmldescription","   Display date in different formats");
		
		EntityList list = new EntityList();
		list.add(labelField);
		list.add(dateLabelField);
		return list;
	}
	
	private Configuration getComponentSuggestionFieldConf() {

		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(StateFieldConstant.IS_STATIC_BOX,false);
			configuration.setPropertyByName(StateFieldConstant.STFD_OPRTION,"appdefinition.AppDefinitionService.getComponentDefinitions");
			configuration.setPropertyByName(StateFieldConstant.STFD_QUERYNAME,"getComponentDefOfLibrary");
			configuration.setPropertyByName(StateFieldConstant.STFD_ENTPROP,"name");
			configuration.setPropertyByName(StateFieldConstant.STFD_QUERY_MAXRESULT,10);
			configuration.setPropertyByName(StateFieldConstant.IS_AUTOSUGGESTION,false);
			configuration.setPropertyByName(StateFieldConstant.BF_SUGGESTION_POS,StateFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(StateFieldConstant.BF_SUGGESTION_TEXT,"Component Name");
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			Long libId = ((Key<Long>)libraryEntity.getPropertyByName("id")).getKeyValue();
			paramMap.put("libraryId", libId);
			configuration.setPropertyByName(StateFieldConstant.STFD_QUERY_RESTRICTION,paramMap);
		} catch (Exception e) {
		}
		
		return configuration;
	}
	
	private Configuration getHeaderLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Register a component");
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
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Register");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVECOMP_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVECOMPONENT_BTN_ID);

		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: getSaveBtnConfig :: Exception", e);
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
							Window.alert("Please select a library");
						}
					}
				}
			}else if(eventType == FieldEvent.VALUECHANGED){
				if(eventSource instanceof ListBoxField){
					ListBoxField listBoxField = (ListBoxField) eventSource;
					SelectedItem selectedItem = (SelectedItem) event.getEventData();
					if(listBoxField.getBaseFieldId().equalsIgnoreCase(LibraryComponentManager.LIBRARYLISTBOX_ID)){
						Entity libEntity = selectedItem.getAssociatedEntity();
						libraryEntity = libEntity;
					}else if(listBoxField.getBaseFieldId().equalsIgnoreCase(COMPONENTTYPELISTBOX_ID)){
						Entity typeEntity = selectedItem.getAssociatedEntity();
						
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
			
			Entity componentDefEnt = getPopulatedEntity();
			Map parameterMap = new HashMap();
			parameterMap.put("componentDefinition", componentDefEnt);
			parameterMap.put("library", libraryEntity);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveComponentDefinition", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Entity>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<HashMap<String, Entity>> result) {
					if(result!=null){
						HashMap<String, Entity> list = result.getOperationResult();
						Entity compEntity   = list.get("component");
						if(compEntity!=null){
							Window.alert("Component Saved...");
							ConfigEvent configEvent = new ConfigEvent(ConfigEvent.ADDCOMPONENTTOLIST, compEntity,this);
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
			Entity compEntity = new Entity();
			compEntity.setType(new MetaType("Componentdefinition"));

			compEntity.setPropertyByName("name", componentNameField.getValue().toString());
			
			Entity typeEnt =  componentTypeField.getAssociatedEntity(componentTypeField.getValue().toString());
			Long typeId = ((Key<Long>)typeEnt.getPropertyByName("id")).getKeyValue();
			compEntity.setPropertyByName("typeId",typeId);
			
			return compEntity;
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
