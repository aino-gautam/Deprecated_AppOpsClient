package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ModelConfigurationInstanceEditor extends Composite{

	private Entity modelConfigInstance;
//	private Entity queryParamConfigEnt;
//	private Entity opParamConfigEnt;
//	private Entity queryNameConfigEntity;
//	private Entity operationNameConfigEntity;
	
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
	
	public ModelConfigurationInstanceEditor(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new HorizontalPanel();
			initWidget(basePanel);
			opParamFlex = new VerticalPanel();
			queryParamFlex = new VerticalPanel();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
					populateModel(modelConfigTypeEnt);
				}
			}

		});
	}


	@SuppressWarnings("unchecked")
	private void populateModel(Entity modelConfigTypeEnt) {
		ArrayList<Entity> childConfTypeList = (ArrayList<Entity>) modelConfigInstance.getPropertyByName("configtypes");
		
		for(Entity childConfType : childConfTypeList) {
			String keyName = childConfType.getPropertyByName("keyname");
			
			if(keyName.equalsIgnoreCase("queryname")) {
				qryNamevalFld.setValue(keyName);
			} else if(keyName.equalsIgnoreCase("queryname")) {
				opNamevalFld.setValue(keyName);
			}
		}
		
	}
}