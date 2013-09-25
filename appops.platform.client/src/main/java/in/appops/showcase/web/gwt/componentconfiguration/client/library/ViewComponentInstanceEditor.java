package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.client.EntityContextGenerator;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com	
 * Class for creating configurations for components like ListComponent etc.
 */
public class ViewComponentInstanceEditor extends Composite implements FieldEventHandler{

	private VerticalPanel baseVp;
	
	private Entity viewConfigInstnceEntity;
	private Entity snippetInstanceConfigInstanceEntity;
	private Entity snippetTypeConfigInstEntity;
	
	private Entity parentCompInstanceEnt;
	
	private Entity snippetTypeConfigTypeEnt;
	private Entity snippetInstnceConfigTypeEnt;
	private Entity pageEntity;

	private ListBoxField transformToListbox;
	private TextField transformInstanceTextField;
	private ButtonField configureButton;
	
	private String TRANSFORM_INSTANCE_LABEL_CSS = "transformInstanceLabel";
	private String TRANSFORM_INSTANCE_PANEL_CSS = "transformInstancePanel";
	private String TRANSFORM_TO_LABEL_CSS = "transformToLabel";
	private String TRANSFORM_TO_PANEL_CSS = "transformToPanel";
	private String CONFIGURE_BUTTON_CSS = "configureButton";

	private String DEFTEXTLISTBOX = "--Select transform to--";
	
	private DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private HandlerRegistration fieldEventHandler = null;
	
	
	public ViewComponentInstanceEditor(){
		initialize();
	}

	private void initialize() {
		try {
			baseVp = new VerticalPanel();
			if(fieldEventHandler ==null)
				fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
			initWidget(baseVp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deregisterHandler(){
		fieldEventHandler.removeHandler();
	}
	
	public void createUi(){
		try{
			baseVp.clear();
			fetchViewConfigType();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void populateViewConfigType(Entity viewConfigTypeEnt) {
		try{
			ArrayList<Entity> childConfTypeList = (ArrayList<Entity>) viewConfigTypeEnt.getPropertyByName("configtypes");

			for(Entity childConfType : childConfTypeList) {
				String keyName = childConfType.getPropertyByName("keyname");
				String keyValue = childConfType.getPropertyByName("keyvalue");

				if(keyName.equalsIgnoreCase("snippetType")) {
					snippetTypeConfigTypeEnt = childConfType;
					HorizontalPanel instnceTypeSelectorPanel = createtransformToPanel();
					baseVp.add(instnceTypeSelectorPanel);
				} else if(keyName.equalsIgnoreCase("snippetInstance")) {
					snippetInstnceConfigTypeEnt = childConfType;
					HorizontalPanel instanceListPanel = createtransformInstancePanel(keyValue);
					baseVp.add(instanceListPanel);
				}
			}
			HorizontalPanel configureBtnPanel = createButtonPanel();
			baseVp.add(configureBtnPanel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fetchViewConfigType() {
		Entity configTypeEnt = (Entity) viewConfigInstnceEntity.getProperty("configtype");
		Key confTypeKey = (Key)configTypeEnt.getPropertyByName("id");
		Long confTypeId = (Long) confTypeKey.getKeyValue();
		
		Map parameterMap = new HashMap();
		parameterMap.put("configurationId", confTypeId);

		StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.getConfigurationType", parameterMap);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<Entity> result) {
				if(result != null) {
					Entity viewConfigTypeEnt = result.getOperationResult();
					if(viewConfigTypeEnt!=null)
						populateViewConfigType(viewConfigTypeEnt);
				}
			}

		});
	}
	
	public HorizontalPanel createButtonPanel() {
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		configureButton = new ButtonField();
		configureButton.setConfiguration(getConfigureButtonConfiguration(true));
		configureButton.configure();
		configureButton.create();
		buttonPanel.add(configureButton);
		
		buttonPanel.setWidth("100%");
		buttonPanel.setCellHorizontalAlignment(configureButton, HasHorizontalAlignment.ALIGN_RIGHT);
		return buttonPanel;
	}
	
	private Configuration getConfigureButtonConfiguration(boolean isEnabled) {
		try {
			Configuration configuration = new Configuration();
			try {
				configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Configure transform widget");
				configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,CONFIGURE_BUTTON_CSS);
				
				if(isEnabled)
					configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
				else
					configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			if(event.getEventType() == FieldEvent.CLICKED){
				if(event.getEventSource() instanceof ButtonField){
					if(event.getEventSource().equals(configureButton)) {
						String snippetInstanceName = transformInstanceTextField.getValue().toString().trim();
						if(!snippetInstanceName.equals("") && !snippetInstanceName.equals("Instance name")){
							String value = (String) transformToListbox.getValue();

							if(!value.equals(DEFTEXTLISTBOX)){
								if(snippetTypeConfigInstEntity == null){
									snippetTypeConfigInstEntity = new Entity();
									snippetTypeConfigInstEntity.setType(new MetaType("Configinstance"));
								}

								snippetTypeConfigInstEntity.setPropertyByName("instancename", "snippetType");
								snippetTypeConfigInstEntity.setPropertyByName("configkeyname", "snippetType");
								snippetTypeConfigInstEntity.setProperty("configinstance", viewConfigInstnceEntity);
								snippetTypeConfigInstEntity.setProperty("configtype", snippetTypeConfigTypeEnt);
								snippetTypeConfigInstEntity.setPropertyByName("instancevalue", value);
								
								saveSnippetTypeInstEnt(snippetTypeConfigInstEntity);
							}
							else
								Window.alert("Please select snippet type");
						}
						else
							Window.alert("Please enter instance name");
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveSnippetTypeInstEnt(Entity configinstanceEntity) {

		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Map parameterMap = new HashMap();
			parameterMap.put("confInstEnt", configinstanceEntity);
			parameterMap.put("isUpdate", false);
			
			EntityContext context  = getEntityContextForViewPageAppService(null);
			parameterMap.put("entityContext", context);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationInstance", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						Entity ent = result.getOperationResult();
						if(ent != null) {
							snippetTypeConfigInstEntity = ent;
							createSnippetInstConInstEntity();
						}
					}
				}});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void createSnippetInstConInstEntity() {
		try {
			String value = transformInstanceTextField.getValue().toString().trim();
			if(!value.equals("")){
				if(snippetInstanceConfigInstanceEntity == null){
					snippetInstanceConfigInstanceEntity = new Entity();
					snippetInstanceConfigInstanceEntity.setType(new MetaType("Configinstance"));
				}
			
				snippetInstanceConfigInstanceEntity.setPropertyByName("instancename", "snippetInstance");
				snippetInstanceConfigInstanceEntity.setPropertyByName("configkeyname", "snippetInstance");
				snippetInstanceConfigInstanceEntity.setProperty("configinstance", viewConfigInstnceEntity);
				snippetInstanceConfigInstanceEntity.setProperty("configtype", snippetInstnceConfigTypeEnt);
				snippetInstanceConfigInstanceEntity.setPropertyByName("instancevalue", value);
				
				DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
				DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
				
				Map parameterMap = new HashMap();
				parameterMap.put("confInstEnt", snippetInstanceConfigInstanceEntity);
				parameterMap.put("isUpdate", false);
				
				EntityContext context  = getEntityContextForViewPageAppService(null);
				parameterMap.put("entityContext", context);
				
				StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationInstance", parameterMap);
				dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Result<Entity> result) {
						if(result!=null){
							Entity ent = result.getOperationResult();
							if(ent != null) {
								snippetInstanceConfigInstanceEntity = ent;
								saveTransformWidgetInstance();
							}
						}
					}
				});
			}
			else
				Window.alert("Please enter instance name");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveTransformWidgetInstance() {
		try{
			Entity compInstEntity = new Entity();
			compInstEntity.setType(new MetaType("Componentinstance"));
			compInstEntity.setPropertyByName("instancename", transformInstanceTextField.getFieldValue());
			compInstEntity.setProperty("componentdefinition", transformToListbox.getAssociatedEntity(transformToListbox.getValue().toString()));
			compInstEntity.setProperty("componentinstance", parentCompInstanceEnt);

			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Map parameterMap = new HashMap();
			parameterMap.put("componentInstEnt", compInstEntity);
			parameterMap.put("isUpdate", false);
			
			Entity parentComponentEnt = (Entity)viewConfigInstnceEntity.getProperty("configinstance");
			parameterMap.put("entityContext", getEntityContextForPgInstPageAppService(null, parentComponentEnt));

			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveComponentInstance", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Object>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<HashMap<String, Object>> result) {
					if(result!=null){
						HashMap<String, Object> resMap = result.getOperationResult();
						
						if(resMap != null && !resMap.isEmpty()) {
							Entity configInst = (Entity) resMap.get("configInstance");
							Entity viewInstanceEnt = null;
							Entity modelInstanceEnt = null;
							
							ArrayList<Entity> childList = (ArrayList<Entity>) resMap.get("childConfigInstanceList");
							Iterator<Entity> iterator = childList.iterator();
							while(iterator.hasNext()) {
								Entity entity = iterator.next();
								String name = entity.getPropertyByName("instancename").toString();
								if(name.equals("view")) {
									viewInstanceEnt = entity;
								} else {
									modelInstanceEnt = entity;
								}
							}
							
							if(true){
								configureButton.setConfiguration(getConfigureButtonConfiguration(false));
								configureButton.configure();
								configureButton.create();
								
								ConfigurationInstanceMVPEditor instanceMVPEditor = new ConfigurationInstanceMVPEditor();
								instanceMVPEditor.setConfigInstEnt(configInst);
								instanceMVPEditor.setViewInstanceEnt(viewInstanceEnt);
								instanceMVPEditor.setModelInstanceEnt(modelInstanceEnt);
								instanceMVPEditor.setPageEntity(pageEntity);
								instanceMVPEditor.createUi();
								baseVp.add(instanceMVPEditor);
							}
						}
					}
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Configuration getTransformToLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Snippet type :");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, TRANSFORM_TO_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private HorizontalPanel createtransformToPanel() {
		HorizontalPanel transformToPanel = new HorizontalPanel();
		
		LabelField transformToLabel = new LabelField();
		transformToLabel.setConfiguration(getTransformToLabelConfiguration());
		transformToLabel.configure();
		transformToLabel.create();
		transformToPanel.add(transformToLabel);
		
		transformToListbox = new ListBoxField();
		byte isMvp = 0;
		HashMap map = new HashMap();
		map.put("isMvp", isMvp);
		
		transformToListbox.setConfiguration(getTransformToListBoxConfiguration(map));
		transformToListbox.configure();
		transformToListbox.create();
		transformToPanel.add(transformToListbox);
		
		transformToPanel.setStylePrimaryName(TRANSFORM_TO_PANEL_CSS);
		transformToPanel.setCellWidth(transformToLabel, "172px");
		transformToPanel.setCellVerticalAlignment(transformToLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return transformToPanel;
	}
	
	private Configuration getTransformToListBoxConfiguration(HashMap<String, Object> paramMap) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL, DEFTEXTLISTBOX);
			
			if(paramMap != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getEntityList");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getComponentDefinationForIsMvp");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION,paramMap);
			} else {
				ArrayList<String> items = new ArrayList<String>();
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			}
			
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private HorizontalPanel createtransformInstancePanel(String keyValue) {
		HorizontalPanel transformInstancePanel = new HorizontalPanel();
		
		LabelField transformInstanceLabel = new LabelField();
		transformInstanceLabel.setConfiguration(getTransformInstanceLabelConfiguration());
		transformInstanceLabel.configure();
		transformInstanceLabel.create();
		transformInstancePanel.add(transformInstanceLabel);
		
		transformInstanceTextField = new TextField();
		transformInstanceTextField.setConfiguration(getTextFieldConfiguration());
		transformInstanceTextField.configure();
		transformInstanceTextField.create();
		transformInstanceTextField.setValue(keyValue);
		transformInstancePanel.add(transformInstanceTextField);
		
		transformInstancePanel.setStylePrimaryName(TRANSFORM_INSTANCE_PANEL_CSS);
		transformInstancePanel.setCellWidth(transformInstanceLabel, "172px");
		transformInstancePanel.setCellVerticalAlignment(transformInstanceLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		return transformInstancePanel;
	}
	
	private Configuration getTextFieldConfiguration() {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, 1);
			configuration.setPropertyByName(TextFieldConstant.BF_READONLY, false);
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
			configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 100);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, true);
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Configuration getTransformInstanceLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Instcnce name :");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, TRANSFORM_INSTANCE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return the viewConfigInstnceEntity
	 */
	public Entity getViewConfigInstnceEntity() {
		return viewConfigInstnceEntity;
	}

	/**
	 * @param viewConfigInstnceEntity the viewConfigInstnceEntity to set
	 */
	public void setViewConfigInstnceEntity(Entity viewConfigInstnceEntity) {
		this.viewConfigInstnceEntity = viewConfigInstnceEntity;
	}

	/**
	 * @return the parentCompInstanceEnt
	 */
	public Entity getParentCompInstanceEnt() {
		return parentCompInstanceEnt;
	}

	/**
	 * @param parentCompInstanceEnt the parentCompInstanceEnt to set
	 */
	public void setParentCompInstanceEnt(Entity parentCompInstanceEnt) {
		this.parentCompInstanceEnt = parentCompInstanceEnt;
	}
	

	private EntityContext getEntityContextForViewPageAppService(EntityContext context) {
		context = getEntityContext(context, viewConfigInstnceEntity);
		Entity parentComponentEnt = (Entity)viewConfigInstnceEntity.getProperty("configinstance");
		EntityContext context1 =  getEntityContext(context, parentComponentEnt);
		EntityContext contextPgInsPageAppSer =  getEntityContextForPgInstPageAppService(context1, parentComponentEnt);
		return context;
	}

	/**
	 * Returns entity context for page conf inst | Page | App | Service
	 * @param context
	 * @return
	 */
	private EntityContext getEntityContextForPgInstPageAppService(EntityContext context, Entity parentComponentEnt) {
		Entity pageInstEnt = (Entity)parentComponentEnt.getProperty("configinstance");
		EntityContext context1 = getEntityContext(context, pageInstEnt);
		EntityContext context2 = getEntityContext(context1, pageEntity);
		EntityContext context3 = getEntityContext(context2, AppEnviornment.CURRENTAPP);
		EntityContext context4 = getEntityContext(context3, AppEnviornment.CURRENTSERVICE);
		
		if(context == null) {
			return context1;
		}
		return context;
	}
	
	private EntityContext getEntityContext(EntityContext context, Entity entity) {
		Key<Long> key = entity.getPropertyByName("id");
		Long id = key.getKeyValue();

		if(context == null) {
			context  = EntityContextGenerator.defineContext(null, id);
		} else {
			context = context.defineContext(id);;
		}
		return context;
	}

	public Entity getPageEntity() {
		return pageEntity;
	}

	public void setPageEntity(Entity pageEntity) {
		this.pageEntity = pageEntity;
	}
}
