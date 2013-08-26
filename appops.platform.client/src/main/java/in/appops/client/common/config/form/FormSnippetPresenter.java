/**
 * 
 */
package in.appops.client.common.config.form;

import java.io.Serializable;
import java.util.Set;
import java.util.Map.Entry;

import in.appops.client.common.config.dsnip.FieldEventConstant;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

/**
 * @author mahesh@ensarm.com
 */
public class FormSnippetPresenter extends HTMLSnippetPresenter {
	
	public static final String CONFIGTYPE = "config";
	
	public static final String frmDefActn = "FormDefaultAction";
	
	public static final String frmAltActn = "FormAlternateAction";
	public static final String resetActn = "ResetAction";
	public static final String backActn = "BackAction";
	public static final String previewActn = "PreviewAction";
	
	public static final String ISSAVEUPDATECALL = "saveUpdateCall";
	public static final String ENTITYTYPE = "entityType";
	public static final String ENTITYPROPERTY = "entityProperty";
	public static final String FldProp = "fieldProperty";
	public static final String UPDATECONFIG = "updateConfiguration";

	public static final String CE = "currentEntity";
	public static final String DEFENT = "defaultEntity";

	public static final String ONSUCCESS = "onSuccess";
	public static final String ONFAILURE = "onFailure";

	public static final String ALERT = "windowAlert";

	public static final String ALERTMSG = "alertMsg";

	public static final String DEFACTNCONFIG = "defaultActionConfig";

	
	/**
	 * This initialises a snippet w.r.t. the snippet type and instance.
	 * Applies configurations to view and model.
	 */
	public void init() {
		model = new FormModel();

		if(getModelConfiguration() != null) {
			model.setConfiguration(getModelConfiguration());
			model.configure();
		}

		if(getViewConfiguration() != null) {
			htmlSnippet.setConfiguration(getViewConfiguration());
		}
		
		model.setReceiver(this);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	@Override
	public void onEntityReceived(Entity entity) {
		if(entity != null) {
			this.entity = entity;
			if(Boolean.parseBoolean(entity.getPropertyByName(ISSAVEUPDATECALL).toString())){
				onSuccessAction();
			}
			else
				populateFields();
		}
		else{
			onFailureAction();
		}
	}
	
	/**
	 * On success of save/update operation what needs to be done 
	 * by the form is been done below
	 */
	private void onSuccessAction() {
		try{
			Configuration defaultConfig = (Configuration) getConfiguration().getProperty(DEFACTNCONFIG);
			if(defaultConfig != null){
				Configuration onSuccessConfig = (Configuration) defaultConfig.getProperty(ONSUCCESS);
				if(Boolean.parseBoolean(onSuccessConfig.getPropertyByName(ALERT).toString())){
					String msg = onSuccessConfig.getPropertyByName(ALERTMSG).toString();
					Window.alert(msg);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * On failure of save/update operation what needs to be done 
	 * by the form is been done below
	 */
	private void onFailureAction() {
		try{
			Configuration defaultConfig = (Configuration) getConfiguration().getProperty(DEFACTNCONFIG);
			if(defaultConfig != null){
				Configuration onSuccessConfig = (Configuration) defaultConfig.getProperty(ONSUCCESS);
				if(Boolean.parseBoolean(onSuccessConfig.getPropertyByName(ALERT).toString())){
					String msg = onSuccessConfig.getPropertyByName(ALERTMSG).toString();
					Window.alert(msg);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			String eventType = Integer.toString(event.getEventType());
			Object eventSource = event.getEventSource();
			
			String eventSourceId = null;
			
			if(eventSource instanceof BaseField) {
				BaseField field = (BaseField)eventSource;
				eventSourceId = field.getBaseFieldId();
			}

			String eventName = eventType + "##" + eventSourceId;
			
			if(isInterestedFieldEvent(eventName)) {
				Configuration fieldEventConf = getFieldEventConfiguration(eventName);
				
				String localAction = fieldEventConf.getPropertyByName(FieldEventConstant.LOCAL_ACTION);
				
				if(localAction.equalsIgnoreCase(backActn)){
					History.back();
				}
				else if(localAction.equalsIgnoreCase(previewActn)){
					executePreviewForm();
				}
				else if(localAction.equalsIgnoreCase(frmDefActn)){
					executeDefaultAction();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method mainly responsible for executing the form default action save or update depending on
	 * the default action configuration provided.
	 * Which creates a new entity or edit the entity property by fetching the values from the fields if
	 * any changes were made there. Also update the update configuration which is need to make the changes in the 
	 * param-map and operation and then calls the model saveUpdateMethod.
	 */
	private void executeDefaultAction() {
		try{
			Configuration defaultActionConfig = (Configuration) getConfiguration().getProperty(DEFACTNCONFIG);
			
			if(defaultActionConfig != null){
				
				//Creation and updation of current entity
				if(entity == null){
					//save entity
					entity = new Entity();
					entity.setType(new MetaType(defaultActionConfig.getPropertyByName(ENTITYTYPE).toString()));
				}
				Configuration propertyConfig = defaultActionConfig.getPropertyByName(ENTITYPROPERTY);

				Set<Entry<String, Property<? extends Serializable>>> confSet = propertyConfig.getValue().entrySet();

				for(Entry<String, Property<? extends Serializable>> entry : confSet) {
					String key = entry.getKey();

					Serializable propvalue = entry.getValue().getValue();
					if(propvalue != null) {
						String propStrVal = propvalue.toString();

						if(propStrVal.indexOf(".") == -1) {
							propvalue = entry.getValue().getValue();
						}
						else if(propStrVal.startsWith(FldProp)){
							String fieldName = propStrVal.substring(propStrVal.indexOf(".") + 1);
							BaseField field = (BaseField) htmlSnippet.getSnippetElementMap().get(fieldName);
							propvalue = (Serializable) field.getValue();
						}
						entity.setPropertyByName(key, propvalue);
					}
				}
				
				//Hardcode part must be removed
				Entity defaultAuthEnt = new Entity(new MetaType(TypeConstants.USER));
				defaultAuthEnt.setPropertyByName("id", new Key<Serializable>("2"));
				
				Configuration updateConfig = (Configuration) defaultActionConfig.getProperty(UPDATECONFIG);
				Set<Entry<String, Property<? extends Serializable>>> updateConfSet = updateConfig.getValue().entrySet();

				for(Entry<String, Property<? extends Serializable>> entry : updateConfSet) {
					String key = entry.getKey();
					String propvalue = entry.getValue().getValue().toString();
					
					if(propvalue.equals(CE)){
						getConfiguration().setGraphPropertyValue(key, entity, null);
					}
					else if (propvalue.equals(DEFENT)){
						getConfiguration().setGraphPropertyValue(key, defaultAuthEnt, null);
					}
				}
				
				((FormModel)model).saveUpdateEntity();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method responsible for preview the form data depending on the preview configuration.
	 * Else will switch to defualt preview action for form which is diaplabling all the fields in the save except 
	 * default form action. 
	 */
	private void executePreviewForm() {
		
	}

	
}
