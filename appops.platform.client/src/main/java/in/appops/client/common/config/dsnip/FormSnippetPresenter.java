package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent;
import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.event.form.FormEventRule;
import in.appops.client.common.config.dsnip.event.form.FormEventRuleMap;
import in.appops.client.common.config.dsnip.event.form.FormEventRulesList;
import in.appops.client.common.config.dsnip.event.form.InvokeActionRule;
import in.appops.client.common.config.dsnip.event.form.ValidationRule;
import in.appops.client.common.config.field.BaseField.BaseFieldConstant;
import in.appops.client.common.config.field.FieldPresenter;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.SimpleEventBus;

/**
 * @author nairutee
 */

public class FormSnippetPresenter extends HTMLSnippetPresenter {

	protected SimpleEventBus localEventBus;
	private Logger logger = Logger.getLogger("FormSnippetPresenter");
		
	public interface FormSnippetConstant extends BaseComponentConstant {
		String HAS_INLINE_EDITING = "hasInlineEditing";
		String FS_PCLS = "formPrimaryCss";
		String FS_DCLS = "formDependentCss";
		String ENTITYTYPE = "entityType";
	}
	
	public FormSnippetPresenter(String snippetType, String snippetInstance) {
		super(snippetType, snippetInstance);
	}

	@Override
	protected void initialize() {
		try {
			localEventBus = injector.getLocalEventBus();
			model = dynamicFactory.requestModel(DynamicMvpFactory.FORMSNIPPET);
			view = dynamicFactory.requestView(DynamicMvpFactory.FORMSNIPPET);
			view.setLocalEventBus(localEventBus);
			view.setModel(model);
			((FormSnippetView) view).setSnippetType(type);
			view.initialize();
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In initialize  method :"+e);
		}
		
	}
	
	@Override
	public void configure() {
		try {
			super.configure();
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In configure  method :"+e);
		}
		
	}
	
	@Override
	public void create() {
		try {
			super.create();
			ValidationRule validationRule = ((FormSnippetView) view).getValidationRule();
			if(validationRule.isValidate()){
				String validationMode= validationRule.getValidationMode();
				if(validationMode.equals(ValidationRule.PREVALIDATION) && ((FormSnippetModel)model).getModelConfiguration()!=null){
					validateForm();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In create  method :"+e);
		}
	}
	
	/**
	 * Method validates all the form fields one by one.
	 * @param validationRule
	 * @return whether field is valid or not. 
	 */
	private Boolean validateAllFormFields(ValidationRule validationRule){
		Boolean isValid = false;
		try {
			Map<String, BaseComponentPresenter> fieldMap= ((FormSnippetView) view).getElementMap();
			Iterator it = fieldMap.entrySet().iterator();
			while (it.hasNext()) {
			    Map.Entry entry = (Map.Entry)it.next();
			    if(entry.getValue() instanceof FieldPresenter){
			    	FieldPresenter fieldPresenter = (FieldPresenter) entry.getValue();
			    	IsConfigurationModel model = fieldPresenter.getModel();
			    	if(model!=null){
			    		if(model.getViewConfiguration()!=null){
				    		if(model.getViewConfiguration().hasConfiguration(BaseComponentConstant.BC_ISINPUTFIELD)){
					    		Boolean isInputField = (Boolean)model.getViewConfiguration().getPropertyByName(BaseComponentConstant.BC_ISINPUTFIELD);
					    		if(isInputField){
					    			model.getViewConfiguration().setPropertyByName(BaseFieldConstant.BF_VALIDATEFIELD, true);
					    			isValid = fieldPresenter.getView().validate();
					    			if(!isValid){
					    				return isValid;
					    			}
					    		}
					    	}
				    	}
			    	}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In doPostValidation  method :"+e);
		}
		return isValid;
	}
	
	/**
	 * Method register handlers.
	 */
	@Override
	protected void registerHandlers() {
		try {
			super.registerHandlers();
			
			FormEventRuleMap formEventRuleMap = ((FormSnippetModel)model).getFormEventRuleMap();
			if(formEventRuleMap != null) {
				Set<String> events = formEventRuleMap.getFormEventNames();
				if(!events.isEmpty()) {
					for(String event : events) {
						AppUtils.ACTIONEVENT_BUS.registerHandler(event, this);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In registerHandlers  method :"+e);
		}
	}
	
	/**
	 * Method processes form event rule map.
	 */
	@Override
	public void processFormEventRuleMap(String eventName, Object eventData) {

		try {
			FormEventRuleMap formEventRuleMap = ((FormSnippetModel) model).getFormEventRuleMap();
				if (formEventRuleMap.hasConfiguration(eventName)) {
					FormEventRulesList formEventRulesList = formEventRuleMap.getFormEventRules(eventName);
					if (formEventRulesList != null && !formEventRulesList.isEmpty()) {
						for (FormEventRule formEventRule : formEventRulesList) {
							if (formEventRule instanceof InvokeActionRule) {
								InvokeActionRule invokeActionRule = ((InvokeActionRule) formEventRule);
								boolean hasOperation = invokeActionRule.hasOperation();
								if (hasOperation) {
									executeOperation(invokeActionRule);
								}
							}
						}
					} else if (eventName.equals(FormConstant.CLEAR)) {
						clear();
					} else if (eventName.equals(FormConstant.RESET)) {
						reset();
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In processFormEventRuleMap  method :"+e);
		}
	}
	
	/**
	 * Method get the processed parameter map and set it to invoke action rule. and then call invoke action of model.
	 * @param invokeActionRule
	 */
	private void executeOperation(InvokeActionRule invokeActionRule){
		try {
			HashMap paramMap = null;
			if (invokeActionRule.hasParameter()) {
				if (invokeActionRule.getParameterType().equals(InvokeActionRule.QUERYPARAM_MAP)) {
					paramMap.put("query", getParameterValueMap(invokeActionRule.getParametermap()));
				}else{
					paramMap = getParameterValueMap(invokeActionRule.getParametermap());
				}
			}
			invokeActionRule.setProcessedParamerMap(paramMap);
			((FormSnippetModel) model).invokeAction(invokeActionRule);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In executeOperation  method :"+e);
		}
	}
	
	/**
	 * Method fetch the parameter value from the field using instance .
	 * @param param
	 * @return parameter map.
	 */
	private HashMap<String, Object> getParameterValueMap(Configuration param) {

		HashMap<String, Object> queryParamMap = null;
		try {
			if (param != null) {
				queryParamMap = new HashMap<String, Object>();
				Set<Entry<String, Property<? extends Serializable>>> confSet = param.getValue().entrySet();
				for (Entry<String, Property<? extends Serializable>> entry : confSet) {
					String paramName = entry.getKey();
					Serializable value = entry.getValue().getValue();
					if (value != null) {
						if(value.toString().startsWith(FormConstant.FORM)){
							if(value.toString().contains(".")){
								String instanceName = value.toString().split("\\.")[1];
								queryParamMap.put(paramName, getInstanceValue(instanceName));
							}
						}else if(value.toString().startsWith(FormConstant.CURRENTENTITY)){
								queryParamMap.put(paramName, getPopulatedEntity());
						}else{
							queryParamMap.put(paramName, value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In getParameterValueMap  method :"+e);
		}
		return queryParamMap;
	}
	
	/**
	 * Method creates the entity using form values and return. If initially form is not populated using entity then new entity is created and entity type
	 * is set which is provided from FORM configuration.  
	 * @return populated entity.
	 */
	private Entity getPopulatedEntity() {
		Entity entity = null;
		try {
			if(model!=null){
				entity  = ((FormSnippetModel)model).getEntity();
			}
			
			if(entity == null){
				entity = new Entity();
				entity.setType(new MetaType(((FormSnippetView) view).getEntityType()));
			}
			
			Map<String, BaseComponentPresenter> fieldMap= ((FormSnippetView) view).getElementMap();
			Iterator it = fieldMap.entrySet().iterator();
			while (it.hasNext()) {
			    Map.Entry entry = (Map.Entry)it.next();
			    if(entry.getValue() instanceof FieldPresenter){
			    	FieldPresenter fieldPresenter = (FieldPresenter) entry.getValue();
			    	IsConfigurationModel model = fieldPresenter.getModel();
			    	if(model!=null){
			    		if(model.getViewConfiguration()!=null){
				    		if(model.getViewConfiguration().hasConfiguration(BaseComponentConstant.BC_ISINPUTFIELD)){
					    		Boolean isInputField = (Boolean)model.getViewConfiguration().getPropertyByName(BaseComponentConstant.BC_ISINPUTFIELD);
					    		if(isInputField){
					    			String bindProperty = model.getViewConfiguration().getPropertyByName(BaseFieldConstant.BF_BINDPROP);
					    			entity.setPropertyByName(bindProperty, fieldPresenter.getView().getValue().toString());
					    		}
					    	}
				    	}
			    	}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In getPopulatedEntity  method :"+e);
		}
		return entity;
	}

	/**
	 * Method returns value of the specific field using instance.
	 * @param instanceName
	 * @return instance value.
	 */
	private Object getInstanceValue(String instanceName){
		
		try {
			Map<String, BaseComponentPresenter> fieldMap= ((FormSnippetView) view).getElementMap();
			if(fieldMap.containsKey(instanceName)){
				return fieldMap.get(instanceName).getView().getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In getInstanceValue  method :"+e);
		}
		return null;
	}
	
	/**
	 * Iterates the element map and clears all the fields.
	 */
	public void clear(){
		try {
			Map<String, BaseComponentPresenter> fieldMap= ((FormSnippetView) view).getElementMap();
			Iterator it = fieldMap.entrySet().iterator();
			while (it.hasNext()) {
			    Map.Entry entry = (Map.Entry)it.next();
			    if(entry.getValue() instanceof FieldPresenter){
			    	FieldPresenter fieldPresenter = (FieldPresenter) entry.getValue();
			    	IsConfigurationModel model = fieldPresenter.getModel();
			    	if(model!=null){
			    		if(model.getViewConfiguration()!=null){
				    		if(model.getViewConfiguration().hasConfiguration(BaseComponentConstant.BC_ISINPUTFIELD)){
					    		Boolean isInputField = (Boolean)model.getViewConfiguration().getPropertyByName(BaseComponentConstant.BC_ISINPUTFIELD);
					    		if(isInputField)
					    			fieldPresenter.getView().clear();
					    	}
				    	}
			    	}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In clear  method :"+e);
		}
	}
	
	/**
	 * Resets the form to it's initial state
	 */
	public void reset(){
		try {
			Map<String, BaseComponentPresenter> fieldMap= ((FormSnippetView) view).getElementMap();
			Iterator it = fieldMap.entrySet().iterator();
			while (it.hasNext()) {
			    Map.Entry entry = (Map.Entry)it.next();
			    if(entry.getValue() instanceof FieldPresenter){
			    	FieldPresenter fieldPresenter = (FieldPresenter) entry.getValue();
			    	IsConfigurationModel model = fieldPresenter.getModel();
			    	if(model!=null){
			    		if(model.getViewConfiguration()!=null){
				    		if(model.getViewConfiguration().hasConfiguration(BaseComponentConstant.BC_ISINPUTFIELD)){
					    		Boolean isInputField = (Boolean)model.getViewConfiguration().getPropertyByName(BaseComponentConstant.BC_ISINPUTFIELD);
					    		if(isInputField)
					    			fieldPresenter.getView().reset();
					    	}
				    	}
			    	}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In reset  method :"+e);
		}
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			BaseComponent eventSource = (BaseComponent) event.getEventSource();
			String formAction = eventSource.getFormAction();
			
			ValidationRule validationRule = ((FormSnippetView)view).getValidationRule();
			if(validationRule!=null && validationRule.isValidate()){
				String validationOnInstance = validationRule.getValidateOn();
				if(validationOnInstance !=null && eventSource.getModel().getInstance().equals(validationOnInstance)){
					if(validateForm()){
						if (eventSource.isFormAction()) {
							if(((FormSnippetModel)model).getFormEventRuleMap() != null && ((FormSnippetModel)model).getFormEventRuleMap().getFormEventRules(formAction) != null ) {
								Object eventData = event.getEventData();
								processFormEventRuleMap(formAction, eventData);
							}
						}
					}
				}
			}else{
				if (eventSource.isFormAction()) {
					if(((FormSnippetModel)model).getFormEventRuleMap() != null && ((FormSnippetModel)model).getFormEventRuleMap().getFormEventRules(formAction) != null ) {
						Object eventData = event.getEventData();
						processFormEventRuleMap(formAction, eventData);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In onFieldEvent  method :"+e);
		}
	}
	
	/**
	 * Method validates the form fields.
	 * @return
	 */
	private Boolean validateForm(){
		Boolean isValid = false;
		try {
			ValidationRule validationRule = ((FormSnippetView) view).getValidationRule();
			isValid = validateAllFormFields(validationRule);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In validateField  method :"+e);
		}
		return isValid;
	}

	@Override
	public void onActionEvent(ActionEvent event) {
		try {
			String eventName = event.getEventName();
			Object eventData = event.getEventData();
			processFormEventRuleMap(eventName, eventData);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[FormSnippetPresenter]::Exception In onActionEvent  method :"+e);
		}
	}

}
