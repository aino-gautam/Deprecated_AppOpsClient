package in.appops.client.common.fields;

import java.util.HashMap;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.slider.field.NumericRangeSliderField;
import in.appops.client.common.fields.slider.field.StringRangeSliderField;
import in.appops.client.common.fields.suggestion.AppopsSuggestion;
import in.appops.client.common.fields.suggestion.AppopsSuggestionBox;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
public class StateField extends Composite implements Field, ChangeHandler{

	private Configuration configuration;
	private String fieldValue;
	private ListBox listBox;
	private AppopsSuggestionBox appopsSuggestionBox;
	private String fieldType;
	private String fieldMode ;
	public static final String STATEFIELD_MODE ="stateFieldMode;";
	public static final String STATEFIELD_TYPE = "stateFieldType";
	public static final String STATEFIELD_READONLY = "stateFieldReadOnly";
	public static final String STATEFIELD_PRIMARYCSS = "stateFieldPrimaryCss";
	public static final String STATEFIELD_DEPENDENTCSS = "stateFieldDependentCss";
	public static final String STATEFIELD_DEBUGID = "stateFieldDebugId";
	public static final String STATEFIELD_QUERY = "stateFieldQuery";
	public static final String STATEFIELDTYPE_LIST = "stateFieldModeList";
	public static final String STATEFIELDTYPE_COMBO = "stateFieldModeCombo";
	public static final String STATEFIELDTYPE_NUMERICRANGE = "stateFieldModeNumericRange";
	public static final String STATEFIELDTYPE_STRINGRANGE = "stateFieldModeStringRange";
	public static final String STATEFIELDMODE_ENUM = "stateFieldTypeEnum";
	public static final String STATEFIELDMODE_SUGGESTIVE = "stateFieldTypeSuggestive";
	public static final String STATEFIELD_OPERATION = "stateFieldOperation";
	public static final String PROPERTY_BY_FIELD_NAME = "propertyByFieldName";
	public static final String STATEFIELD_QUERY_MAXRESULT = "stateFieldQueryMaxresult";
	public static final String STATEFIELD_PROPERTY_TO_DISPLAY = "propertyToDisplay";
	public static final String STATEFIELD_QUERY_RESTRICTION = "queryRestriction";
	public static final String IS_SEARCH_QUERY = "isSearchQuery";
	public static final String IS_AUTOSUGGESTION = "isAutoSuggestion";
	
	public StateField(){
	}
	
	@Override
	public void create() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("Statefield configuration unavailable");
		
		 fieldMode = getConfiguration().getPropertyByName(STATEFIELD_MODE).toString();
		
		if(getConfiguration().getPropertyByName(STATEFIELD_TYPE) != null)
			fieldType = getConfiguration().getPropertyByName(STATEFIELD_TYPE).toString();
		
		if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_ENUM)){
			if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_NUMERICRANGE)){
				NumericRangeSliderField numericRangeSliderField = new NumericRangeSliderField();
				numericRangeSliderField.setConfiguration(getConfiguration());
				numericRangeSliderField.create();
				initWidget(numericRangeSliderField);
			}else if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_STRINGRANGE)){
				StringRangeSliderField stringRangeSliderField = new StringRangeSliderField();
				stringRangeSliderField.setConfiguration(getConfiguration());
				stringRangeSliderField.create();
				initWidget(stringRangeSliderField);
			}else{
				if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_LIST)){
					ListBoxField listBoxField = new ListBoxField();
					listBoxField.setConfiguration(getConfiguration());
					listBoxField.create();
				}else if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_COMBO)){
					ComboBoxField comboBoxField = new ComboBoxField();
					comboBoxField.setConfiguration(getConfiguration());
					comboBoxField.create();
				}
				if(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS) != null)
					listBox.setStylePrimaryName(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS).toString());
				if(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS) != null)
					listBox.addStyleName(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS).toString());
				if(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID) != null)
					listBox.ensureDebugId(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID).toString());
			}
		} else if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_SUGGESTIVE)){
			appopsSuggestionBox = new AppopsSuggestionBox();
			if(getConfiguration().getPropertyByName(STATEFIELD_QUERY) != null)
				appopsSuggestionBox.setQueryName(getConfiguration().getPropertyByName(STATEFIELD_QUERY).toString());
			if(getConfiguration().getPropertyByName(IS_SEARCH_QUERY) != null){
				Boolean val = getConfiguration().getPropertyByName(IS_SEARCH_QUERY);
				appopsSuggestionBox.setIsSearchQuery(val);
			}if(getConfiguration().getPropertyByName(IS_AUTOSUGGESTION) != null){
				Boolean val = getConfiguration().getPropertyByName(IS_AUTOSUGGESTION);
				appopsSuggestionBox.setAutoSuggestion(val);
			}
			if(getConfiguration().getPropertyByName(STATEFIELD_OPERATION) != null)
				appopsSuggestionBox.setOperationName(getConfiguration().getPropertyByName(STATEFIELD_OPERATION).toString());
			if(getConfiguration().getPropertyByName(STATEFIELD_QUERY_MAXRESULT) != null)
				appopsSuggestionBox.setMaxResult((Integer)getConfiguration().getPropertyByName(STATEFIELD_QUERY_MAXRESULT));
			else
				appopsSuggestionBox.setMaxResult(25);
			if(getConfiguration().getPropertyByName(STATEFIELD_PROPERTY_TO_DISPLAY) != null) {
				appopsSuggestionBox.setPropertyToDisplay(getConfiguration().getPropertyByName(STATEFIELD_PROPERTY_TO_DISPLAY).toString());
			}
			if(getConfiguration().getPropertyByName(STATEFIELD_QUERY_RESTRICTION) != null) {
				appopsSuggestionBox.setQueryRestrictions((HashMap<String, Object>) getConfiguration().getPropertyByName(STATEFIELD_QUERY_RESTRICTION));
			}
			
			initWidget(appopsSuggestionBox);
			if(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS) != null)
				appopsSuggestionBox.getSuggestBox().setStylePrimaryName(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS).toString());
			if(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS) != null)
				appopsSuggestionBox.getSuggestBox().addStyleName(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS).toString());
			if(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID) != null)
				appopsSuggestionBox.getSuggestBox().ensureDebugId(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID).toString());
		}
	}

	@Override
	public void clear() {
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * populates the list box with values that the user can select from
	 * @param entityList - list of entities
	 */
	public void populateList(EntityList entityList, ListBox listbox){
		for(Entity entity : entityList){
			String item = entity.getPropertyByName(null); // we need to figure out how this property name will be provided
			listbox.addItem(item);
		}
		
	}
	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public String getFieldValue() {
		String fieldMode = getConfiguration().getPropertyByName(STATEFIELD_MODE).toString();
		if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_ENUM)){
			 if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_LIST)){
				 
			 }
		}else{
			setFieldValue(appopsSuggestionBox.getSuggestBox().getValue());
			return appopsSuggestionBox.getSuggestBox().getValue();
		}
		return this.getFieldValue();
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void onChange(ChangeEvent event) {
		if( event.getSource() instanceof ListBox){
			
		}
	}
	
	public AppopsSuggestion getFieldWidget(){
		if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_ENUM)){
			//TODO:Use the numberField,StringRangeField & listBox,Combo here for future
		}else if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_SUGGESTIVE)){
			
			return appopsSuggestionBox.getSelectedSuggestion();
			
		}
		return null;
	}
	
	
	public AppopsSuggestionBox getAppopsSuggestionBox() {
		return appopsSuggestionBox;
	}

	public void setAppopsSuggestionBox(AppopsSuggestionBox appopsSuggestionBox) {
		this.appopsSuggestionBox = appopsSuggestionBox;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
}