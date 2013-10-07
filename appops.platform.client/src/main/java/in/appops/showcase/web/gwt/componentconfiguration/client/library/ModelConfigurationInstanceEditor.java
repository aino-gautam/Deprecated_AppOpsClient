package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.ConfigInstanceEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigInstanceEventHandler;
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
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ModelConfigurationInstanceEditor extends Composite implements FieldEventHandler, ConfigInstanceEventHandler {

	private Entity modelConfigInstance;
	private HorizontalPanel basePanel;
 
	private VerticalPanel queryParamFlex;
	private VerticalPanel opParamFlex;
	
	public static final String QUERYMODE = "queryMode";
	public static final String OPERATIONMODE = "operationMode";
	
	private final String MODELLBLCSS = "modelLblCss";
	
	private DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	
	/*******************  Fields ID *****************************/
	private final String QUERYNAMEFLD = "queryNameFieldId";
	private final String OPERATIONNAMEFLD = "opNameFldId";
	
	private final String QUERYNAMEPROP = "queryname";
	private final String OPERATIONNAMEPROP = "operationname";
	private final String QUERYPARAMPROP = "queryParam";
	private final String OPERATIONNPARAMPROP = "operationParam";
	
	private TextField qryNamevalFld;
	private TextField opNamevalFld;
	private VerticalPanel queryDetailHolder;
	private VerticalPanel operationDetailHolder;

	private Entity queryNameConfigTypeEnt;
	private Entity opNameConfigTypeEnt;
	
	private Entity queryNameConfigInsEntity;
	private Entity operationNameConfigInsEntity;
	
	private Entity queryParamConfigInstanceEnt;
	private Entity opParamConfigInstanceEnt;

	private Entity queryParamConfigTypeEnt;
	private Entity opParamConfigTypeEnt;

	private Entity pageEntity;
	private ArrayList<SnippetPropValueEditor> snippetPropValueEditors;
	
	private HandlerRegistration fieldEventHandler = null;
	private HandlerRegistration configEventHandler = null;
	private HashMap<String, Object> childConfigInstMap;
	
	public ModelConfigurationInstanceEditor(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new HorizontalPanel();
			initWidget(basePanel);
			opParamFlex = new VerticalPanel();
			queryParamFlex = new VerticalPanel();
			snippetPropValueEditors = new ArrayList<SnippetPropValueEditor>();
			
			if(fieldEventHandler == null)
				fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
			if(configEventHandler == null)
				configEventHandler = AppUtils.EVENT_BUS.addHandler(ConfigInstanceEvent.TYPE, this);
 		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deregisterHandler(){
		fieldEventHandler.removeHandler();
		configEventHandler.removeHandler();
	}
	
	public void createUi() {
		try{
			basePanel.setStylePrimaryName("fullWidth");
			opParamFlex.setStylePrimaryName("fullWidth");

			VerticalPanel queryDetailHolder = getQryEditorUi();
			queryDetailHolder.addStyleName("fullWidth");
			
			VerticalPanel operationDetailHolder = getOpEditorUi();
			operationDetailHolder.addStyleName("fullWidth");
			
			basePanel.add(operationDetailHolder);
			basePanel.add(queryDetailHolder);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private VerticalPanel getOpEditorUi() {
		operationDetailHolder = new VerticalPanel();
		try{
			HorizontalPanel opNameHolder = new HorizontalPanel();
			operationDetailHolder.setStylePrimaryName("opDetailHolderCss");

			LabelField qryNameLblFld = new LabelField();
			qryNameLblFld.setConfiguration(getQryNameLblFldConf(false));
			qryNameLblFld.configure();
			qryNameLblFld.create();
			
			opNamevalFld = new TextField();
			opNamevalFld.setConfiguration(getQryNameValFldConf(false));
			opNamevalFld.configure();
			opNamevalFld.create();
			
			opNameHolder.add(qryNameLblFld);
			opNameHolder.add(opNamevalFld);
			
			operationDetailHolder.add(opNameHolder);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return operationDetailHolder;
	}

	private VerticalPanel getQryEditorUi() {
		queryDetailHolder = new VerticalPanel();
		try{
			queryDetailHolder.setStylePrimaryName("queryDetailHolderCss");
			
			HorizontalPanel qryNameHolder = new HorizontalPanel();
			
			LabelField qryNameLblFld = new LabelField();
			qryNameLblFld.setConfiguration(getQryNameLblFldConf(true));
			qryNameLblFld.configure();
			qryNameLblFld.create();
			
			qryNamevalFld = new TextField();
			qryNamevalFld.setConfiguration(getQryNameValFldConf(true));
			qryNamevalFld.configure();
			qryNamevalFld.create();
			
			qryNameHolder.add(qryNameLblFld);
			qryNameHolder.add(qryNamevalFld);
			
			queryDetailHolder.add(qryNameHolder);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return queryDetailHolder;
	}

	private Configuration getQryParamLblFldConf(boolean isQueryParamCall) {
		Configuration configuration = null;
		try{
			configuration = new Configuration();
			if(isQueryParamCall)
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Query param : ");
			else
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Operation param : ");
		
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, MODELLBLCSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getQryNameValFldConf(boolean isQueryCall) {
		Configuration configuration = null;
		try{
			configuration = new Configuration();
			
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			
			if(isQueryCall){
				configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Query name");
				configuration.setPropertyByName(TextFieldConstant.BF_ID, QUERYNAMEFLD);
				configuration.setPropertyByName(TextFieldConstant.BF_BINDPROP, QUERYNAMEPROP);
			}else{
				configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Operation name");
				configuration.setPropertyByName(TextFieldConstant.BF_ID, OPERATIONNAMEFLD);
				configuration.setPropertyByName(TextFieldConstant.BF_BINDPROP, OPERATIONNAMEPROP);
			}
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, MODELLBLCSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getQryNameLblFldConf(boolean isQueryCall) {
		Configuration configuration = null;
		try{
			configuration = new Configuration();
			if(isQueryCall)
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Query name : ");
			else
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Operation name : ");
			
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, MODELLBLCSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	public Entity getModelConfigInstance() {
		return modelConfigInstance;
	}

	public void setModelConfigInstance(Entity modelConfigInstance) {
		this.modelConfigInstance = modelConfigInstance;
	}
	
	@SuppressWarnings("unchecked")
	public void fetchModel() {
		Entity configTypeEnt = (Entity) modelConfigInstance.getProperty("configtype");
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
					Entity modelConfigTypeEnt = result.getOperationResult();
					if(modelConfigTypeEnt != null)
						populateModel(modelConfigTypeEnt);
				}
			}

		});
	}


	@SuppressWarnings("unchecked")
	private void populateModel(Entity modelConfigTypeEnt) {
		ArrayList<Entity> childConfTypeList = (ArrayList<Entity>) modelConfigTypeEnt.getPropertyByName("configtypes");

		if(childConfTypeList!=null){
			for(Entity childConfType : childConfTypeList) {
				String keyName = childConfType.getPropertyByName("keyname");
				String keyValue = childConfType.getPropertyByName("keyvalue");

				if(keyName.equalsIgnoreCase("queryname")) {
					qryNamevalFld.setValue(keyValue);
					queryNameConfigTypeEnt = childConfType;
					Entity configInstEntity = getQryOpNameInstanceEntity(keyValue, true);
					saveQryOpNameInstance(configInstEntity, true);
				} else if(keyName.equalsIgnoreCase("operationname")) {
					opNamevalFld.setValue(keyValue);
					opNameConfigTypeEnt = childConfType;
					Entity configInstEntity = getQryOpNameInstanceEntity(keyValue, false);
					saveQryOpNameInstance(configInstEntity, false);
				}  else if(keyName.equalsIgnoreCase("queryparam")) {
					LabelField qryParamLblFld = new LabelField();
					qryParamLblFld.setConfiguration(getQryParamLblFldConf(true));
					qryParamLblFld.configure();
					qryParamLblFld.create();
					queryDetailHolder.add(qryParamLblFld);
					queryParamConfigTypeEnt = childConfType;
					createAndSaveConfigInstanceEntity(true);
				}  else if(keyName.equalsIgnoreCase("operationparam")) {
					LabelField qryParamLblFld = new LabelField();
					qryParamLblFld.setConfiguration(getQryParamLblFldConf(false));
					qryParamLblFld.configure();
					qryParamLblFld.create();
					operationDetailHolder.add(qryParamLblFld);
					opParamConfigTypeEnt = childConfType;
					createAndSaveConfigInstanceEntity(false);		
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			if(event.getEventSource() instanceof TextField){
				if(event.getEventType() == FieldEvent.TAB_KEY_PRESSED){
					if(((TextField)event.getEventSource()).equals(qryNamevalFld)){
						String paramValue = ((TextField)event.getEventSource()).getValue().toString();
						if(!paramValue.trim().equals("")){
							Entity configInstEntity = null;
							if(queryNameConfigInsEntity != null) {
								configInstEntity = queryNameConfigInsEntity;
								configInstEntity.setPropertyByName("instancevalue", paramValue);
							} else {
								configInstEntity = getQryOpNameInstanceEntity(paramValue, true);
							}
							saveQryOpNameInstance(configInstEntity, true);
						}
					}
					else if(((TextField)event.getEventSource()).equals(opNamevalFld)){
						String paramValue = ((TextField)event.getEventSource()).getValue().toString();
						if(!paramValue.trim().equals("")){
							Entity configInstEntity = null;
							if(operationNameConfigInsEntity != null) {
								configInstEntity = operationNameConfigInsEntity;
								configInstEntity.setPropertyByName("instancevalue", paramValue);
							} else {
								configInstEntity = getQryOpNameInstanceEntity(paramValue, false);
							}
							saveQryOpNameInstance(configInstEntity, false);
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Entity getQryOpNameInstanceEntity(String paramValue, boolean isQueryCall) {
		Entity configInstance = null;
		try{
			configInstance = new Entity();
			configInstance.setType(new MetaType("Configinstance"));

			
			if(isQueryCall){
				configInstance.setPropertyByName("instancename", QUERYNAMEPROP);
				configInstance.setPropertyByName("configkeyname", QUERYNAMEPROP);
				configInstance.setProperty("configtype", queryNameConfigTypeEnt);
			}
			else{
				configInstance.setPropertyByName("instancename", OPERATIONNAMEPROP);
				configInstance.setPropertyByName("configkeyname", OPERATIONNAMEPROP);
				configInstance.setProperty("configtype", opNameConfigTypeEnt);
			}
			configInstance.setPropertyByName("instancevalue", paramValue);
			configInstance.setProperty("configinstance", modelConfigInstance);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configInstance;
	}
	
	/**
	 * TODO  To be optimized
	 * @param context
	 * @return
	 */
	private EntityContext getEntityContextForModelPageAppService(EntityContext context) {
		if(context == null) {
			context = getEntityContext(context, modelConfigInstance);
			Entity parentEnt = (Entity)modelConfigInstance.getProperty("configinstance");
			EntityContext context1 = getEntityContext(context, parentEnt);
			Entity pageInstEnt = (Entity)parentEnt.getProperty("configinstance");
			EntityContext context2 = getEntityContext(context1, pageInstEnt);
			EntityContext context3 = getEntityContext(context2, pageEntity);
			EntityContext context4 = getEntityContext(context3, AppEnviornment.CURRENTAPP);
			EntityContext context5 = getEntityContext(context4, AppEnviornment.CURRENTSERVICE);
			return context;

		} else {
			EntityContext extracontext = getEntityContext(context, modelConfigInstance);
			Entity parentEnt = (Entity)modelConfigInstance.getProperty("configinstance");
			EntityContext context1 = getEntityContext(extracontext, parentEnt);
			Entity pageInstEnt = (Entity)parentEnt.getProperty("configinstance");
			EntityContext context2 = getEntityContext(context1, pageInstEnt);
			EntityContext context3 = getEntityContext(context2, pageEntity);
			EntityContext context4 = getEntityContext(context3, AppEnviornment.CURRENTAPP);
			EntityContext context5 = getEntityContext(context4, AppEnviornment.CURRENTSERVICE);
			return context;
	}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void saveQryOpNameInstance(Entity configInstEnt, final boolean isQueryCall) {
		try{
			Map parameterMap = new HashMap();
			parameterMap.put("confInstEnt", configInstEnt);
			
			if(configInstEnt.getPropertyByName("id") != null) {
				parameterMap.put("isUpdate", true);
			} else {
				parameterMap.put("isUpdate", false);
			}

			EntityContext context = getEntityContextForModelPageAppService(null);
			
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
						if(result.getOperationResult() !=null){
							if(isQueryCall){
								queryNameConfigInsEntity = result.getOperationResult();
//								if(queryNameConfigInsEntity != null) {
//									createAndSaveConfigInstanceEntity(true);
//								}
							}
							else{
								operationNameConfigInsEntity = result.getOperationResult();
//								if(operationNameConfigInsEntity!=null) {
//									createAndSaveConfigInstanceEntity(false);
//								}
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
	
	
	@SuppressWarnings("unchecked")
	private void createAndSaveConfigInstanceEntity(final boolean isQueryCall) {
		Entity configInstance = null;
		try{
			
			configInstance = new Entity();
			configInstance.setType(new MetaType("Configinstance"));

			if(isQueryCall){
				configInstance.setPropertyByName("instancename", QUERYPARAMPROP);
				configInstance.setPropertyByName("configkeyname", QUERYPARAMPROP);
				configInstance.setProperty("configtype", queryParamConfigTypeEnt);
			}
			else{
				configInstance.setPropertyByName("instancename", OPERATIONNPARAMPROP);
				configInstance.setPropertyByName("configkeyname", OPERATIONNPARAMPROP);
				configInstance.setProperty("configtype", opParamConfigTypeEnt);
			}
			configInstance.setProperty("configinstance", modelConfigInstance);
			
			Map parameterMap = new HashMap();
			parameterMap.put("confInstEnt", configInstance);
			parameterMap.put("isUpdate", false);
			
			
			EntityContext context = getEntityContextForModelPageAppService(null);
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
						if(result.getOperationResult()!=null){
							if(isQueryCall){
								queryParamConfigInstanceEnt = result.getOperationResult();
								fetchQueryOpParams(isQueryCall);
							}
							else{
								opParamConfigInstanceEnt = result.getOperationResult();
								fetchQueryOpParams(isQueryCall);
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

	@SuppressWarnings("unchecked")
	protected void fetchQueryOpParams(final boolean isQueryCall) {
		Entity configTypeEnt = null;
		if(isQueryCall) {
			configTypeEnt = (Entity) queryParamConfigInstanceEnt.getProperty("configtype");
		} else {
			configTypeEnt = (Entity) opParamConfigInstanceEnt.getProperty("configtype");
		}
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
					Entity paramConfigTypeEnt = result.getOperationResult();
					if(paramConfigTypeEnt != null) {
						populateParams(paramConfigTypeEnt, isQueryCall);
					}
				}
			}

		});		
	}

	protected void populateParams(Entity configTypeEnt, boolean isQueryCall) {
		ArrayList<Entity> childConfTypeList = (ArrayList<Entity>) configTypeEnt.getPropertyByName("configtypes");
		
		if(isQueryCall) {
			queryDetailHolder.add(queryParamFlex);
		} else {
			operationDetailHolder.add(opParamFlex);
		}
		if(childConfTypeList != null && !childConfTypeList.isEmpty()) {
			for(Entity childConfType : childConfTypeList) {
				if(isQueryCall) {
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
					snippetPropValueEditors.add(snipPropValEditor);
					snipPropValEditor.setDeletable(false);
					snipPropValEditor.setInstanceMode(true);
					snipPropValEditor.setConfigTypeListboxVisible(true);
					snipPropValEditor.setParentConfInstanceEntity(queryParamConfigInstanceEnt);
					snipPropValEditor.setConfTypeParamValEnt(childConfType);
					snipPropValEditor.createUi();
					snipPropValEditor.populate();
					queryParamFlex.add(snipPropValEditor);
					Entity paramConfigInsEnt = snipPropValEditor.getConfInstanceParamValEnt();
					
					EntityContext context = getEntityContext(null, queryParamConfigInstanceEnt);
					context = getEntityContextForModelPageAppService(context);
					
					saveParamConfigIns(paramConfigInsEnt, snipPropValEditor, context);
				} else {
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
					snippetPropValueEditors.add(snipPropValEditor);
					snipPropValEditor.setDeletable(false);
					snipPropValEditor.setInstanceMode(true);
					snipPropValEditor.setConfigTypeListboxVisible(true);
					snipPropValEditor.setParentConfInstanceEntity(opParamConfigInstanceEnt);
					snipPropValEditor.setConfTypeParamValEnt(childConfType);
					snipPropValEditor.createUi();
					snipPropValEditor.populate();
					opParamFlex.add(snipPropValEditor);
					Entity paramConfigInsEnt = snipPropValEditor.getConfInstanceParamValEnt();
					
					EntityContext context = getEntityContext(null, opParamConfigInstanceEnt);
					context = getEntityContextForModelPageAppService(context);
					
					saveParamConfigIns(paramConfigInsEnt, snipPropValEditor, context);
				}
			}
		} else {
			if(isQueryCall) {
				SnippetPropValueEditor snipPropValEditor = getNewSnippetPropValueEditor(QUERYMODE, queryParamConfigInstanceEnt);
				snippetPropValueEditors.add(snipPropValEditor);
				queryParamFlex.add(snipPropValEditor);
			} else {
				SnippetPropValueEditor snipPropValEditor = getNewSnippetPropValueEditor(OPERATIONMODE, opParamConfigInstanceEnt);
				snippetPropValueEditors.add(snipPropValEditor);
				opParamFlex.add(snipPropValEditor);
			}
		}
	
	}

	@SuppressWarnings("unchecked")
	private void saveParamConfigIns(Entity configInstEnt, final SnippetPropValueEditor snippetPropValueEditor, EntityContext entityContext) {
		try{
			Map parameterMap = new HashMap();
			parameterMap.put("confInstEnt", configInstEnt);
			parameterMap.put("isUpdate", false);
			parameterMap.put("entityContext", entityContext);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationInstance", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {


				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						if(result.getOperationResult() !=null){
							Entity paramConfigInsEntity = result.getOperationResult();
							snippetPropValueEditor.setConfInstanceParamValEnt(paramConfigInsEntity);
							
							if(snippetPropValueEditor.getMode().equals(QUERYMODE)) {
								int index = queryParamFlex.getWidgetIndex(snippetPropValueEditor);
								if(index == (queryParamFlex.getWidgetCount() - 1)) {
									SnippetPropValueEditor snipPropValEditor = getNewSnippetPropValueEditor(QUERYMODE, queryParamConfigInstanceEnt);
									snippetPropValueEditors.add(snipPropValEditor);
									queryParamFlex.add(snipPropValEditor);
								}
							} else {
								int index = opParamFlex.getWidgetIndex(snippetPropValueEditor);
								if(index == (opParamFlex.getWidgetCount() - 1)) {
									SnippetPropValueEditor snipPropValEditor = getNewSnippetPropValueEditor(OPERATIONMODE, opParamConfigInstanceEnt);
									snippetPropValueEditors.add(snipPropValEditor);
									opParamFlex.add(snipPropValEditor);
								}
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
	
	private SnippetPropValueEditor getNewSnippetPropValueEditor(String mode, Entity paramParentConfigInstEnt) {
		SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(mode);
		snipPropValEditor.setDeletable(false);
		snipPropValEditor.setInstanceMode(true);
		snipPropValEditor.setConfigTypeListboxVisible(true);
		snipPropValEditor.setParentConfInstanceEntity(paramParentConfigInstEnt);
		snipPropValEditor.createUi();
		return snipPropValEditor;
	}

	@Override
	public void onConfigInstanceEvent(ConfigInstanceEvent event) {
		if(event.getEventType() == ConfigEvent.SAVEPROPVALUEADDWIDGET){
			SnippetPropValueEditor snipPropValEditorSelected = (SnippetPropValueEditor) event.getEventSource();
			if(	snippetPropValueEditors.contains(snipPropValEditorSelected)) {
				Entity paramConfigEnt = snipPropValEditorSelected.getConfInstanceParamValEnt();
				
				EntityContext context = getEntityContext(null, snipPropValEditorSelected.getParentConfInstanceEntity());
				context = getEntityContextForModelPageAppService(context);
				
				saveParamConfigIns(paramConfigEnt, snipPropValEditorSelected, context);
			}
		}			
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

	public void populateValues(EntityList list) {
		if(list != null && !list.isEmpty()) {
			Iterator<Entity> iterator = list.iterator();
			while(iterator.hasNext()) {
				Entity entity = iterator.next();
				String instancename = entity.getPropertyByName("instancename").toString();
				if(instancename.equals("operationname")) {
					operationNameConfigInsEntity = entity;
					String instancevalue = entity.getPropertyByName("instancevalue").toString();
					opNamevalFld.setValue(instancevalue);
				} else if(instancename.equals("queryname")) {
					queryNameConfigInsEntity = entity;
					String instancevalue = entity.getPropertyByName("instancevalue").toString();
					qryNamevalFld.setValue(instancevalue);
				} else if(instancename.equals("operationParam")) {
					EntityList operationParamChildList = (EntityList) childConfigInstMap.get(instancename);
					createListOfSnippetPropValueEditor(false, operationParamChildList);
				} else if(instancename.equals("queryParam")) {
					EntityList queryParamChildList = (EntityList) childConfigInstMap.get(instancename);
					createListOfSnippetPropValueEditor(true, queryParamChildList);
				}
			}
		}
	}

	public HashMap<String, Object> getChildConfigInstMap() {
		return childConfigInstMap;
	}

	public void setChildConfigInstMap(HashMap<String, Object> childConfigInstMap) {
		this.childConfigInstMap = childConfigInstMap;
	}
	
	public void createListOfSnippetPropValueEditor(boolean isQueryCall, EntityList paramChildList) {
		
		snippetPropValueEditors.clear();
		Iterator<Entity> iterator = paramChildList.iterator();
		while(iterator.hasNext()) {
			Entity configInstEntity = iterator.next();
			if(isQueryCall) {
				SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
				snippetPropValueEditors.add(snipPropValEditor);
				snipPropValEditor.setDeletable(false);
				snipPropValEditor.setInstanceMode(true);
				snipPropValEditor.setConfigTypeListboxVisible(true);
				snipPropValEditor.setParentConfInstanceEntity(queryParamConfigInstanceEnt);
				snipPropValEditor.setConfInstanceParamValEnt(configInstEntity);
				//snipPropValEditor.setConfTypeParamValEnt(childConfType);
				snipPropValEditor.createUi();
				snipPropValEditor.populate();
				queryParamFlex.add(snipPropValEditor);
			} else {
				SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
				snippetPropValueEditors.add(snipPropValEditor);
				snipPropValEditor.setDeletable(false);
				snipPropValEditor.setInstanceMode(true);
				snipPropValEditor.setConfigTypeListboxVisible(true);
				snipPropValEditor.setParentConfInstanceEntity(opParamConfigInstanceEnt);
				snipPropValEditor.setConfInstanceParamValEnt(configInstEntity);
				//snipPropValEditor.setConfTypeParamValEnt(childConfType);
				snipPropValEditor.createUi();
				snipPropValEditor.populate();
				opParamFlex.add(snipPropValEditor);
			}
		}
	}
}