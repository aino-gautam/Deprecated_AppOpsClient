/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.configuration.constant.ConfigTypeConstant;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
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
//	private LabelField qryParamLblFld;
//	private LabelField opParamLblFld;
	
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
	
	private HandlerRegistration fieldEventHandler = null;
	
	
	public ModelConfigurationEditor(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new HorizontalPanel();
		//	qryParamLblFld = new LabelField();
		//	opParamLblFld = new LabelField();
			
			initWidget(basePanel);
			opParamFlex = new VerticalPanel();
			queryParamFlex = new VerticalPanel();
			
			//TODO : need to analyse and decide
		//	AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
			
			if(fieldEventHandler ==null)
				fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deregisterHandler(){
		fieldEventHandler.removeHandler();
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
				if(snipPropValEditorSelected.getMode().equals(OPERATIONMODE)){
					saveParamValue(snipPropValEditorSelected);
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
					snipPropValEditor.setDeletable(true);
					snipPropValEditor.setMode(OPERATIONMODE);
					snipPropValEditor.setParentConfTypeEntity(opParamConfigEnt);
					snipPropValEditor.getParamNameField().setFocus();
					opParamFlex.add(snipPropValEditor);
				}
				else{
					saveParamValue(snipPropValEditorSelected);
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
					snipPropValEditor.setDeletable(true);
					snipPropValEditor.setMode(QUERYMODE);
					snipPropValEditor.setParentConfTypeEntity(queryParamConfigEnt);
					snipPropValEditor.getParamNameField().setFocus();
					queryParamFlex.add(snipPropValEditor);
				}
			}
			else if(event.getEventType() == ConfigEvent.REMOVEPARAMPROPERTYVALUE){
				SnippetPropValueEditor snipPropValEditorSelected = (SnippetPropValueEditor) event.getEventSource();
				Entity entInContext = snipPropValEditorSelected.getConfigTypeEntity();
				if(entInContext.getPropertyByName("id")!=null)
					deleteParamValueEntity(entInContext);
				
				
				snipPropValEditorSelected.removeFromParent();

				if(snipPropValEditorSelected.getMode().equals(OPERATIONMODE)){
					
					if(opParamFlex.getWidgetCount() == 0){
						SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
						snipPropValEditor.setDeletable(true);
						snipPropValEditor.setParentConfTypeEntity(opParamConfigEnt);
						snipPropValEditor.createUi();
						opParamFlex.add(snipPropValEditor);
					}
				}
				else if(snipPropValEditorSelected.getMode().equals(QUERYMODE)){
					if(queryParamFlex.getWidgetCount()  == 0){
						SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
						snipPropValEditor.setDeletable(true);
						snipPropValEditor.setParentConfTypeEntity(queryParamConfigEnt);
						snipPropValEditor.createUi();
						queryParamFlex.add(snipPropValEditor);
					}
				}
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
							Entity configEntity = getQryNameEntity(paramValue);
							saveQryOpName(configEntity, true);
						}
					}
					else if(((TextField)event.getEventSource()).equals(opNamevalFld)){
						String paramValue = ((TextField)event.getEventSource()).getValue().toString();
						if(!paramValue.trim().equals("")){
							Entity configEntity = getOpNameEntity(paramValue);
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
			Map parameterMap = new HashMap();
			
			if(isQueryCall){
				if(queryParamConfigEnt == null){
					queryParamConfigEnt = new Entity();
					queryParamConfigEnt.setType(new MetaType("Configtype"));
				}
				
				queryParamConfigEnt.setPropertyByName(ConfigTypeConstant.EMSTYPEID,160);
				queryParamConfigEnt.setPropertyByName(ConfigTypeConstant.ISDEFAULT,false);
				
				queryParamConfigEnt.setPropertyByName(ConfigTypeConstant.KEYNAME,QUERYPARAMPROP);

				queryParamConfigEnt.setProperty("configtype", modelConfigType);

				queryParamConfigEnt.setPropertyByName(ConfigTypeConstant.SERVICEID, 10);
				
				parameterMap.put("configTypeEnt", queryParamConfigEnt);
			}
			else{
				if(opParamConfigEnt == null){
					opParamConfigEnt = new Entity();
					opParamConfigEnt.setType(new MetaType("Configtype"));
				}
				
				opParamConfigEnt.setPropertyByName(ConfigTypeConstant.EMSTYPEID,160);
				opParamConfigEnt.setPropertyByName(ConfigTypeConstant.ISDEFAULT,false);

				opParamConfigEnt.setPropertyByName(ConfigTypeConstant.KEYNAME,OPERATIONNPARAMPROP);
			
				opParamConfigEnt.setProperty("configtype", modelConfigType);

				opParamConfigEnt.setPropertyByName(ConfigTypeConstant.SERVICEID, 10);
				
				parameterMap.put("configTypeEnt", opParamConfigEnt);
			}
			
			
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
							/*	queryParamConfigEnt = result.getOperationResult();
								
								qryParamLblFld.setConfiguration(getQryParamLblFldConf(true));
								qryParamLblFld.configure();
								qryParamLblFld.create();
								
								queryDetailHolder.add(qryParamLblFld);
								queryDetailHolder.add(queryParamFlex);
								
								SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
								snipPropValEditor.setDeletable(true);
								snipPropValEditor.setParentConfTypeEntity(queryParamConfigEnt);
								snipPropValEditor.createUi();
								queryParamFlex.add( snipPropValEditor);*/
							}
							else{
								/*opParamConfigEnt = result.getOperationResult();
								
								opParamLblFld.setConfiguration(getQryParamLblFldConf(false));
								opParamLblFld.configure();
								opParamLblFld.create();
								
								operationDetailHolder.add(opParamLblFld);
								operationDetailHolder.add(opParamFlex);
								
								SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
								snipPropValEditor.setDeletable(true);
								snipPropValEditor.setParentConfTypeEntity(opParamConfigEnt);
								snipPropValEditor.createUi();
								opParamFlex.add( snipPropValEditor);*/
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
	
	private Entity getQryNameEntity(String paramValue) {
		try{
			if(queryNameConfigEntity == null){
				queryNameConfigEntity = new Entity();
				queryNameConfigEntity.setType(new MetaType("Configtype"));
			}

			queryNameConfigEntity.setPropertyByName(ConfigTypeConstant.EMSTYPEID,3);
			queryNameConfigEntity.setPropertyByName(ConfigTypeConstant.ISDEFAULT,true);
			
			queryNameConfigEntity.setPropertyByName(ConfigTypeConstant.KEYNAME,QUERYNAMEPROP);
						
			queryNameConfigEntity.setPropertyByName(ConfigTypeConstant.KEYVALUE, paramValue);
			queryNameConfigEntity.setProperty("configtype", modelConfigType);

			queryNameConfigEntity.setPropertyByName(ConfigTypeConstant.SERVICEID, 10);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return queryNameConfigEntity;
	}
	
	private Entity getOpNameEntity(String paramValue) {
		try{
			
			if(operationNameConfigEntity == null){
				operationNameConfigEntity = new Entity();
				operationNameConfigEntity.setType(new MetaType("Configtype"));
			}

			operationNameConfigEntity.setPropertyByName(ConfigTypeConstant.EMSTYPEID,3);
			operationNameConfigEntity.setPropertyByName(ConfigTypeConstant.ISDEFAULT,true);
			
			operationNameConfigEntity.setPropertyByName(ConfigTypeConstant.KEYNAME,OPERATIONNAMEPROP);

			operationNameConfigEntity.setPropertyByName(ConfigTypeConstant.KEYVALUE, paramValue);
			operationNameConfigEntity.setProperty("configtype", modelConfigType);

			operationNameConfigEntity.setPropertyByName(ConfigTypeConstant.SERVICEID, 10);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return operationNameConfigEntity;
	}
}
