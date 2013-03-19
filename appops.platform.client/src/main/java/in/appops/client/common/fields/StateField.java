package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.slider.field.NumericRangeSliderField;
import in.appops.client.common.fields.slider.field.StringRangeSliderField;
import in.appops.client.common.fields.suggestion.SuggestionField;
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
	private SuggestionField appopsSuggestionBox;
	private String fieldType;
	
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
	
	public StateField(){
	}
	
	@Override
	public void createField() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("Statefield configuration unavailable");
		
		String fieldMode = getConfiguration().getPropertyByName(STATEFIELD_MODE).toString();
		
		if(getConfiguration().getPropertyByName(STATEFIELD_TYPE) != null)
			fieldType = getConfiguration().getPropertyByName(STATEFIELD_TYPE).toString();
		
		if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_ENUM)){
			if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_NUMERICRANGE)){
				NumericRangeSliderField numericRangeSliderField = new NumericRangeSliderField();
				numericRangeSliderField.setConfiguration(getConfiguration());
				numericRangeSliderField.createField();
				initWidget(numericRangeSliderField);
			}else if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_STRINGRANGE)){
				StringRangeSliderField stringRangeSliderField = new StringRangeSliderField();
				stringRangeSliderField.setConfiguration(getConfiguration());
				stringRangeSliderField.createField();
				initWidget(stringRangeSliderField);
			}else{
				if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_LIST)){
					ListBoxField listBoxField = new ListBoxField();
					listBoxField.setConfiguration(getConfiguration());
					listBoxField.createField();
				}else if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_COMBO)){
					ComboBoxField comboBoxField = new ComboBoxField();
					comboBoxField.setConfiguration(getConfiguration());
					comboBoxField.createField();
				}
				if(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS) != null)
					listBox.setStylePrimaryName(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS).toString());
				if(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS) != null)
					listBox.addStyleName(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS).toString());
				if(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID) != null)
					listBox.ensureDebugId(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID).toString());
			}
		} else if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_SUGGESTIVE)){
			appopsSuggestionBox = new SuggestionField();
			if(getConfiguration().getPropertyByName(STATEFIELD_QUERY) != null)
				appopsSuggestionBox.setQueryName(getConfiguration().getPropertyByName(STATEFIELD_QUERY).toString());
			if(getConfiguration().getPropertyByName(STATEFIELD_OPERATION) != null)
				appopsSuggestionBox.setOperationName(getConfiguration().getPropertyByName(STATEFIELD_OPERATION).toString());
			
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
	public void clearField() {
		
	}

	@Override
	public void resetField() {
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
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}
}