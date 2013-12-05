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

import com.google.gwt.event.shared.SimpleEventBus;

/**
 * 
 * @author nairutee
 *
 */
public class FormSnippetPresenter extends HTMLSnippetPresenter {

	protected SimpleEventBus localEventBus;
		
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
		localEventBus = injector.getLocalEventBus();
		model = dynamicFactory.requestModel(DynamicMvpFactory.FORMSNIPPET);
		view = dynamicFactory.requestView(DynamicMvpFactory.FORMSNIPPET);
		view.setLocalEventBus(localEventBus);
		view.setModel(model);
		((FormSnippetView) view).setSnippetType(type);
		view.initialize();
		
	}
	
	@Override
	public void configure() {
		super.configure();
		
	}
	
	private Boolean doPostValidation(ValidationRule validationRule){
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
					    			String validationMode = validationRule.getValidationMode();
					    			model.getViewConfiguration().setPropertyByName(BaseFieldConstant.BF_VALIDATEFIELD, true);
					    			if(validationMode.equals(ValidationRule.POSTVALIDATION)){
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isValid;
	}
	
	@Override
	public void create() {
		super.create();
	}

	@Override
	protected void registerHandlers() {
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
	}
	
	@Override
	public void processFormEventRuleMap(String eventName, Object eventData) {

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
	}
	
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
		}
	}
	
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
		}
		return queryParamMap;
	}
	
	private Entity getPopulatedEntity() {
		Entity entity = null;
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
		return entity;
	}

	private Object getInstanceValue(String instanceName){
		
		try {
			Map<String, BaseComponentPresenter> fieldMap= ((FormSnippetView) view).getElementMap();
			if(fieldMap.containsKey(instanceName)){
				return fieldMap.get(instanceName).getView().getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Iterates the element map and clears all the fields
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
		}
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			final String SEPARATOR = "##";
			String eventType = Integer.toString(event.getEventType());
			BaseComponent eventSource = (BaseComponent) event.getEventSource();
			String formAction = eventSource.getFormAction();
			String eventName = eventType + SEPARATOR + formAction;
			
			
			
			if(((FormSnippetView)view).getValidationRule()!=null){
				String validationOnInstance = ((FormSnippetView)view).getValidationRule().getValidateOn();
				if(eventSource.getModel().getInstance().equals(validationOnInstance)){
					if(validateField()){
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
		}
	}
	
	
	private Boolean validateField(){
		Boolean isValid = false;
		try {
			ValidationRule validationRule = ((FormSnippetView) view).getValidationRule();
			if(validationRule!=null && validationRule.isValidate()){
				isValid = doPostValidation(validationRule);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		}
	}

}
