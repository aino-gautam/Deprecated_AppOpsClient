package in.appops.client.common.fields;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;

public class StateField extends Composite implements Field, ChangeHandler{

	private Configuration configuration;
	private String fieldValue;
	private ListBox listBox;
	private SuggestBox suggestBox;
	private String fieldType;
	
	public static final String STATEFIELD_MODE ="stateFieldMode;";
	public static final String STATEFIELD_TYPE = "stateFieldType";
	public static final String STATEFIELD_READONLY = "stateFieldReadOnly";
	
	public static final String STATEFIELDTYPE_LIST = "stateFieldModeList";
	public static final String STATEFIELDTYPE_COMBO = "stateFieldModeCombo";
	public static final String STATEFIELDMODE_ENUM = "stateFieldTypeEnum";
	public static final String STATEFIELDMODE_SUGGESTIVE = "stateFieldTypeSuggestive";
	
	public StateField(){
		
	}
	
	@Override
	public void createField() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("Statefield configuration unavailable");
		
		String fieldMode = getConfiguration().getPropertyByName(STATEFIELD_MODE).toString();
		fieldType = getConfiguration().getPropertyByName(STATEFIELD_TYPE).toString();
		
		if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_ENUM)){
			 if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_LIST)){
				 listBox = new ListBox(true);
				 populateList(null, listBox); // need to provide or fetch the entitylist to be populated
			 }else if(fieldType.equalsIgnoreCase(STATEFIELDTYPE_COMBO)){
				 listBox = new ListBox(false);
				 populateList(null, listBox); // need to provide or fetch the entitylist to be populated
			 }
			
		} else if(fieldMode.equalsIgnoreCase(STATEFIELDMODE_SUGGESTIVE)){
				suggestBox = new SuggestBox();
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

}
