/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.util.HashMap;
import java.util.Map;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.configuration.constant.ConfigTypeConstant;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com	
 *
 */
public class ModelConfigurationEditor extends Composite implements ConfigEventHandler, FieldEventHandler{

	private Entity modelConfigType;
	private Entity queryParamConfigEnt;
	private Entity opParamConfigEnt;
	private Entity queryNameConfigEntity;
	private Entity operationNameConfigEntity;
	
	private HorizontalPanel basePanel;
 
	private VerticalPanel queryParamFlex;
	private VerticalPanel opParamFlex;
	
	public static final String QUERYMODE = "queryMode";
	public static final String OPERATIONMODE = "operationMode";
	
//	private int opRow,queryRow ;
	
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
	
	
	public ModelConfigurationEditor(){
		initialize();
	}

	private void initialize() {
		try{
			//TODO: creating dummy model contype entity
/*			modelConfigType = new Entity();
			modelConfigType.setType(new MetaType("Configtype"));
			Key<Long> key = new Key<Long>(245L);
			modelConfigType.setPropertyByName("id", key);*/
			
			basePanel = new HorizontalPanel();
			initWidget(basePanel);
			opParamFlex = new VerticalPanel();
			queryParamFlex = new VerticalPanel();
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createUi(){
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

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try{
			if(event.getEventType() == ConfigEvent.SAVEPROPVALUEADDWIDGET){
				SnippetPropValueEditor snipPropValEditorSelected = (SnippetPropValueEditor) event.getEventSource();
				if(snipPropValEditorSelected.getSnipPropValEditorId().equals(OPERATIONMODE)){
					saveParamValue(snipPropValEditorSelected);
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
					snipPropValEditor.setSnipPropValEditorId(OPERATIONMODE);
					snipPropValEditor.setParentConfTypeEntity(opParamConfigEnt);
					snipPropValEditor.getParamNameField().setFocus();
					opParamFlex.add(snipPropValEditor);
				/*	opParamFlex.setWidget(opRow, 0, snipPropValEditor);
					opRow++;*/
				}
				else{
					saveParamValue(snipPropValEditorSelected);
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
					snipPropValEditor.setSnipPropValEditorId(QUERYMODE);
					snipPropValEditor.setParentConfTypeEntity(queryParamConfigEnt);
					snipPropValEditor.getParamNameField().setFocus();
					queryParamFlex.add(snipPropValEditor);

					/*queryParamFlex.setWidget(queryRow, 0, snipPropValEditor);
					queryRow++;*/
				}
			}
			else if(event.getEventType() == ConfigEvent.REMOVEPARAMPROPERTYVALUE){
				SnippetPropValueEditor snipPropValEditorSelected = (SnippetPropValueEditor) event.getEventSource();
				Entity entInContext = snipPropValEditorSelected.getConfigTypeEntity();
				if(entInContext.getPropertyByName("id")!=null)
					deleteParamValueEntity(entInContext);
				
				
				snipPropValEditorSelected.removeFromParent();

				//TODO  need to test following approach
				if(snipPropValEditorSelected.getSnipPropValEditorId().equals(OPERATIONMODE)){
					
					if(opParamFlex.getWidgetCount() == 0){
						SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
						snipPropValEditor.setParentConfTypeEntity(opParamConfigEnt);
						opParamFlex.add(snipPropValEditor);
					}
				}
				else if(snipPropValEditorSelected.getSnipPropValEditorId().equals(QUERYMODE)){
					if(queryParamFlex.getWidgetCount()  == 0){
						SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
						snipPropValEditor.setParentConfTypeEntity(queryParamConfigEnt);
						queryParamFlex.add(snipPropValEditor);
					}
				}
				
				
			//	int rowToRemove = snipPropValEditorSelected.getValuePanelRow();

			/*	if(snipPropValEditorSelected.getSnipPropValEditorId().equals(OPERATIONMODE)){
					
					opParamFlex.removeCell(rowToRemove,0);
				}
				else{
					queryParamFlex.removeCell(rowToRemove,0);
				}*/
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void deleteParamValueEntity(Entity configTypeEntity) {
		try{
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeEnt", configTypeEntity);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.deleteConfigurationType", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						if(result.getOperationResult()!=null){
							Window.alert("Entity deleted");
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
	private void saveParamValue(final SnippetPropValueEditor snipPropValEditorSelected) {
		try{
			Entity configTypeEntity = snipPropValEditorSelected.getConfTypeParamValEnt();
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeEnt", configTypeEntity);
			if(configTypeEntity.getPropertyByName("id")!=null)
				parameterMap.put("update", true);
			else
				parameterMap.put("update", false);
			parameterMap.put("context", null);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationType", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						if(result.getOperationResult() != null){
							Entity savedEnt = result.getOperationResult();
							snipPropValEditorSelected.setConfTypeParamValEnt(savedEnt);
							/*int rowValue = snipPropValEditorSelected.getValuePanelRow();
							if(snipPropValEditorSelected.getSnipPropValEditorId().equals(QUERYMODE)){
								queryParamFlex.setWidget(rowValue, 0, snipPropValEditorSelected);
							}
							else if(snipPropValEditorSelected.getSnipPropValEditorId().equals(OPERATIONMODE)){
								opParamFlex.setWidget(rowValue, 0, snipPropValEditorSelected);
							}*/
						}
					}
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the modelConfigType
	 */
	public Entity getModelConfigType() {
		return modelConfigType;
	}

	/**
	 * @param modelConfigType the modelConfigType to set
	 */
	public void setModelConfigType(Entity modelConfigType) {
		this.modelConfigType = modelConfigType;
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
							Entity configEntity = getQryOpNameEntity(paramValue, true);
							saveQryOpName(configEntity, true);
						}
					}
					else if(((TextField)event.getEventSource()).equals(opNamevalFld)){
						String paramValue = ((TextField)event.getEventSource()).getValue().toString();
						if(!paramValue.trim().equals("")){
							Entity configEntity = getQryOpNameEntity(paramValue, false);
							saveQryOpName(configEntity, false);
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	private void saveQryOpName(Entity configTypeEnt, final boolean isQueryCall) {
		try{
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeEnt", configTypeEnt);
			parameterMap.put("update", false);
			parameterMap.put("context", new EntityContext());
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationType", parameterMap);
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
								queryNameConfigEntity = result.getOperationResult();
								if(queryNameConfigEntity!=null){
									createAndSaveConfigEntity(true);
								}
							}
							else{
								operationNameConfigEntity = result.getOperationResult();
								if(operationNameConfigEntity!=null){
									createAndSaveConfigEntity(false);
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

	@SuppressWarnings("unchecked")
	private void createAndSaveConfigEntity(final boolean isQueryCall) {
		try{
			Entity paramValueEntity = new Entity();
			paramValueEntity.setType(new MetaType("Configtype"));
			
			paramValueEntity.setPropertyByName(ConfigTypeConstant.EMSTYPEID,160);
			paramValueEntity.setPropertyByName(ConfigTypeConstant.ISDEFAULT,false);
			
			if(isQueryCall){
				paramValueEntity.setPropertyByName(ConfigTypeConstant.KEYNAME,QUERYPARAMPROP);
			}
			else{
				paramValueEntity.setPropertyByName(ConfigTypeConstant.KEYNAME,OPERATIONNPARAMPROP);
			}
			
			paramValueEntity.setProperty("configtype", modelConfigType);

			paramValueEntity.setPropertyByName(ConfigTypeConstant.SERVICEID, 10);
			
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeEnt", paramValueEntity);
			parameterMap.put("update", false);
			parameterMap.put("context", null);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationType", parameterMap);
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
								queryParamConfigEnt = result.getOperationResult();
								
								LabelField qryParamLblFld = new LabelField();
								qryParamLblFld.setConfiguration(getQryParamLblFldConf(true));
								qryParamLblFld.configure();
								qryParamLblFld.create();
								
								queryDetailHolder.add(qryParamLblFld);
								queryDetailHolder.add(queryParamFlex);
								
								SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
								snipPropValEditor.setParentConfTypeEntity(queryParamConfigEnt);
								queryParamFlex.add( snipPropValEditor);
							}
							else{
								opParamConfigEnt = result.getOperationResult();
								
								LabelField opParamLblFld = new LabelField();
								opParamLblFld.setConfiguration(getQryParamLblFldConf(false));
								opParamLblFld.configure();
								opParamLblFld.create();
								
								operationDetailHolder.add(opParamLblFld);
								operationDetailHolder.add(opParamFlex);
								
								SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
								snipPropValEditor.setParentConfTypeEntity(opParamConfigEnt);
								opParamFlex.add( snipPropValEditor);
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
	
	private Entity getQryOpNameEntity(String paramValue, boolean isQueryCall) {
		Entity configTypeEnt = null;
		try{
			configTypeEnt = new Entity();
			configTypeEnt.setType(new MetaType("Configtype"));
			
			configTypeEnt.setPropertyByName(ConfigTypeConstant.EMSTYPEID,3);
			configTypeEnt.setPropertyByName(ConfigTypeConstant.ISDEFAULT,true);
			
			if(isQueryCall){
				configTypeEnt.setPropertyByName(ConfigTypeConstant.KEYNAME,QUERYNAMEPROP);
			}
			else{
				configTypeEnt.setPropertyByName(ConfigTypeConstant.KEYNAME,OPERATIONNAMEPROP);
			}
			
			configTypeEnt.setPropertyByName(ConfigTypeConstant.KEYVALUE, paramValue);
			configTypeEnt.setProperty("configtype", modelConfigType);

			configTypeEnt.setPropertyByName(ConfigTypeConstant.SERVICEID, 10);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configTypeEnt;
	}
}
