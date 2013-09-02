/**
 * 
 */
package in.appops.showcase.web.gwt.registercomponent.client;

import java.util.HashMap;
import java.util.Map;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahes@ensarm.com
 *
 */
public class RegisterComponentManager extends Composite implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private RegisterComponentForm regCompForm;
	private RegisterComponentLister regCompList;
	private ConfigurationEditor configEditor;
	
	private final String FORMLIST_HOLDER_CSS = "formListHolder";
	private final String BASEPANEL_CSS = "componentManager";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	private final String LIBPANEL_CSS = "libraryPanel";
	
	private final String LIBRARYBOX_ID = "libraryBoxFieldId";
	
	public RegisterComponentManager(){
		initialize();
	}

	public void createUi() {
		try{
			basePanel.setStylePrimaryName(BASEPANEL_CSS);
			
			HorizontalPanel formListHolder = new HorizontalPanel();
			regCompForm.createUi();
			
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
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,LIBRARYBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			//configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"Select library");
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
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			initWidget(basePanel);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	private void intializeFormListHolder(Entity libEntity){
		try {
			regCompList.createUi(libEntity);
			regCompForm.setLibraryEntity(libEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		
		int eventType = event.getEventType();
		Object eventSource = event.getEventSource();
		
		try {
			if(eventType == FieldEvent.VALUECHANGED){
				if(eventSource instanceof ListBoxField){
					
					ListBoxField listBoxField = (ListBoxField) eventSource;
					if(listBoxField.getBaseFieldId().equalsIgnoreCase(LIBRARYBOX_ID)){
						SelectedItem selectedItem = (SelectedItem) event.getEventData();
						Entity libEntity = selectedItem.getAssociatedEntity();
						if(libEntity!=null){
							intializeFormListHolder(libEntity);
						}
					}
				}
			}else if(eventType == FieldEvent.CLICKED){
				if(eventSource instanceof ComponentPanel){
					Entity componentEtity = (Entity) event.getEventData();
						if(componentEtity!=null){
							populateComponentConfiguration(componentEtity);
						}
					
				}
			}else if(eventType == FieldEvent.ADDCOMPONENT){
				Entity componentEtity = (Entity) event.getEventData();
				if(componentEtity!=null){
					regCompList.addComponent(componentEtity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void populateComponentConfiguration(final Entity componentDefEntity) {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Query queryObj = new Query();
			queryObj.setQueryName("getConfigurationdefOfComponent");
			
			HashMap<String, Object> queryParam = new HashMap<String, Object>();
			Long libId = ((Key<Long>)componentDefEntity.getPropertyByName("id")).getKeyValue();
			queryParam.put("confdefId", libId);
			queryObj.setQueryParameterMap(queryParam);
						
			Map parameterMap = new HashMap();
			parameterMap.put("query", queryObj);
			
			StandardAction action = new StandardAction(EntityList.class, "appdefinition.AppDefinitionService.getComponentDefinitions", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						EntityList confDeflist   = result.getOperationResult();
						if(!confDeflist.isEmpty()){
							configEditor.createEditor(componentDefEntity, confDeflist);
						}
					}
				}
			});
		} catch (Exception e) {
		}
	}
}
